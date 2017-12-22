package py.org.fundacionparaguaya.pspserver.surveys.services.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.org.fundacionparaguaya.pspserver.common.exceptions.CustomParameterizedException;
import py.org.fundacionparaguaya.pspserver.families.entities.FamilyEntity;
import py.org.fundacionparaguaya.pspserver.families.entities.specifications.FamilySpecifications;
import py.org.fundacionparaguaya.pspserver.families.repositories.FamilyRepository;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotEconomicEntity;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotIndicatorEntity;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SurveyEntity;
import py.org.fundacionparaguaya.pspserver.surveys.entities.specifications.SnapshotSpecifications;
import py.org.fundacionparaguaya.pspserver.surveys.entities.specifications.SnapshotSpecifications.*;
import py.org.fundacionparaguaya.pspserver.surveys.mapper.SnapshotEconomicMapper;
import py.org.fundacionparaguaya.pspserver.surveys.mapper.SnapshotIndicatorMapper;
import py.org.fundacionparaguaya.pspserver.surveys.repositories.SnapshotEconomicRepository;
import py.org.fundacionparaguaya.pspserver.surveys.repositories.SnapshotIndicatorRepository;
import py.org.fundacionparaguaya.pspserver.surveys.repositories.SurveyRepository;
import py.org.fundacionparaguaya.pspserver.surveys.services.SnapshotIndicatorPriorityService;
import py.org.fundacionparaguaya.pspserver.surveys.services.SnapshotService;
import py.org.fundacionparaguaya.pspserver.surveys.services.SurveyService;
import py.org.fundacionparaguaya.pspserver.surveys.validation.*;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by rodrigovillalba on 9/14/17.
 */
@Service
public class SnapshotServiceImpl implements SnapshotService {

    private final SnapshotIndicatorPriorityService priorityService;
    
    private final SurveyRepository surveyRepository;
    
    private final SnapshotEconomicRepository economicRepository;

    private final SnapshotEconomicMapper economicMapper;

    private final SurveyService surveyService;
    
    private final SnapshotIndicatorMapper indicatorMapper;
    
    private final FamilyRepository familyRepository;
    
    private static final String FAMILY_NAME = "Name";
    
    private static final String FAMILY_REFERENCE = "Person Reference";
    
    private static final String INDICATOR_NAME = "name";
    
    private static final String INDICATOR_VALUE = "value";


    public SnapshotServiceImpl(SnapshotEconomicRepository economicRepository, SnapshotEconomicMapper economicMapper, 
            SurveyService surveyService, SurveyRepository surveyRepository, SnapshotIndicatorRepository indicatorRepository, SnapshotIndicatorMapper indicatorMapper,
            FamilyRepository familyRepository, SnapshotIndicatorPriorityService priorityService) {
        this.economicRepository = economicRepository;
        this.economicMapper = economicMapper;
        this.surveyService = surveyService;
        this.surveyRepository = surveyRepository;
        this.indicatorMapper = indicatorMapper;
        this.familyRepository = familyRepository;
        this.priorityService = priorityService;
    }

    @Override
    @Transactional
    public Snapshot addSurveySnapshot(NewSnapshot snapshot) {
        checkNotNull(snapshot);

        ValidationResults results = surveyService.checkSchemaCompliance(snapshot);
        if (!results.isValid()) {
            throw new CustomParameterizedException("Invalid Snapshot", results.asMap());
        }

        SnapshotIndicatorEntity indicatorEntity = economicMapper.newSnapshotToIndicatorEntity(snapshot);

        SnapshotEconomicEntity snapshotEconomicEntity = saveEconomic(snapshot, indicatorEntity);

        return economicMapper.entityToDto(snapshotEconomicEntity);
    }

    private SnapshotEconomicEntity saveEconomic(NewSnapshot snapshot, SnapshotIndicatorEntity indicator) {

        SnapshotEconomicEntity entity = economicMapper.newSnapshotToEconomicEntity(snapshot, indicator);

        return this.economicRepository.save(entity);
    }

    @Override
    public List<Snapshot> find(Long surveyId, Long familiyId) {
        return economicRepository.findBySurveyDefinitionId(surveyId)
                .stream()
                .map(economicMapper::entityToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Snapshot> filter(Map<String, List<String>> indicators, Long organizationId, Long applicationId, Long countryId, Long cityId) {
        List<Long> familiesIds = familyRepository.findAll(Specifications
                .where(FamilySpecifications.belongsToOrganization(organizationId))
                .and(FamilySpecifications.belongsToApplication(applicationId))
                .and(FamilySpecifications.inCountry(countryId))
                .and(FamilySpecifications.inCity(cityId)))
                .stream()
                .map((f) -> f.getFamilyId())
                .collect(Collectors.toList());
        return economicRepository.findAll(Specifications
                .where(SnapshotSpecifications.hasIndicators(indicators))
                .and(SnapshotSpecifications.forFamilies(familiesIds)))
                .stream()
                .map(economicMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public String filterCSV(Map<String, List<String>> indicators, Long organizationId, Long applicationId, Long countryId, Long cityId) {
        List<FamilyEntity> families = familyRepository.findAll(Specifications
                .where(FamilySpecifications.belongsToOrganization(organizationId))
                .and(FamilySpecifications.belongsToApplication(applicationId))
                .and(FamilySpecifications.inCountry(countryId))
                .and(FamilySpecifications.inCity(cityId)))
                .stream()
                .collect(Collectors.toList());
        List<SnapshotEconomicEntity> snapshots = economicRepository.findAll(Specifications
                .where(SnapshotSpecifications.hasIndicators(indicators))
                .and(SnapshotSpecifications.forFamilies(families.stream().map(f -> f.getFamilyId()).collect(Collectors.toList()))))
                .stream()
                .collect(Collectors.toList());
        
        String csv = SnapshotEconomicEntity.getCSVFields() + "\n";
        for (SnapshotEconomicEntity snapshot : snapshots) {
            csv += snapshot.toCSV(families.stream().filter(f -> f.getFamilyId() == snapshot.getFamilyId()).findFirst().get())[1] + "\n";
        }
        return csv;
    }

    @Override
    public List<SnapshotIndicators> getSnapshotIndicators(Long surveyId, Long familiyId) {
        
       List<SnapshotIndicators> toRet = new ArrayList<>();
       List<SnapshotEconomicEntity> originalSnapshots = economicRepository.findBySurveyDefinitionId(surveyId)
               .stream()
               .collect(Collectors.toList());
       
       SurveyEntity survey = surveyRepository.getOne(surveyId);
       List<String> indicatorGroup = survey.getSurveyDefinition().getSurveyUISchema().getGroupIndicators();
       
       List<String> order = survey.getSurveyDefinition().getSurveyUISchema().getUiOrder().stream().filter(field -> indicatorGroup.contains(field))
               .collect(Collectors.toList());
       
       for(SnapshotEconomicEntity s : originalSnapshots) {
           
           SnapshotIndicators snapshotIndicators = new SnapshotIndicators();
           
           List<SnapshotIndicatorPriority> priorities = priorityService.getSnapshotIndicatorPriorityList(s.getSnapshotIndicator().getId());
           snapshotIndicators.setIndicatorsPriorities(priorities);
           
           SurveyData indicators = indicatorMapper.entityToDto(s.getSnapshotIndicator());
           List<SurveyData> indicatorsToRet = new ArrayList<>();
           SurveyData familyData = new SurveyData();
           if(indicatorGroup!=null && !indicatorGroup.isEmpty()) {
               if(order!=null && !order.isEmpty()) {
                     
                   order.forEach( indicator -> { 
                       if(indicators.containsKey(indicator)) {
                           SurveyData sd = new SurveyData();
                           sd.put(INDICATOR_NAME, getNameFromCamelCase(indicator));
                           sd.put(INDICATOR_VALUE, indicators.get(indicator));
                           
                           switch (sd.get(INDICATOR_VALUE).toString().toUpperCase()) {
                            case "RED":
                                snapshotIndicators.setCountRedIndicators(snapshotIndicators.getCountRedIndicators()+1);
                                break;
                            case "YELLOW":
                                snapshotIndicators.setCountYellowIndicators(snapshotIndicators.getCountYellowIndicators()+1);
                                break;
                            case "GREEN":
                                snapshotIndicators.setCountGreenIndicators(snapshotIndicators.getCountGreenIndicators()+1);
                                break;
                            default:
                                break;
                            }
                           indicatorsToRet.add(sd);
                       }         
                   });
               }
           }
           
           snapshotIndicators.setIndicatorsSurveyData(indicatorsToRet);
           snapshotIndicators.setCreatedAt(s.getCreatedAtAsISOString());
           snapshotIndicators.setSnapshotIndicatorId(s.getSnapshotIndicator().getId());
           
           if(familiyId!=null) {
               FamilyEntity family = familyRepository.getOne(familiyId);
               if(family!=null) {
                   familyData.put(FAMILY_NAME, family.getName()!=null? family.getName():"");
                   familyData.put(FAMILY_REFERENCE, (family.getPerson().getName()!=null? family.getPerson().getName(): "")
                           +(family.getPerson().getLastname()!=null?" "+family.getPerson().getLastname():""));
               }
           }
           snapshotIndicators.setFamilyData(familyData);
           
           toRet.add(snapshotIndicators);
       }
       return toRet;
    }
    
    private String getNameFromCamelCase(String name) {
        return StringUtils.capitalize(StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(name), " "));
    }

}
