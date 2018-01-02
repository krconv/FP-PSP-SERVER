package py.org.fundacionparaguaya.pspserver.surveys.services.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import py.org.fundacionparaguaya.pspserver.common.exceptions.CustomParameterizedException;
import py.org.fundacionparaguaya.pspserver.common.exceptions.UnknownResourceException;
import py.org.fundacionparaguaya.pspserver.families.entities.FamilyEntity;
import py.org.fundacionparaguaya.pspserver.families.entities.PersonEntity;
import py.org.fundacionparaguaya.pspserver.families.entities.specifications.FamilySpecifications;
import py.org.fundacionparaguaya.pspserver.families.mapper.PersonMapper;
import py.org.fundacionparaguaya.pspserver.families.repositories.FamilyRepository;
import py.org.fundacionparaguaya.pspserver.families.services.FamilyService;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.NewSnapshot;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.Snapshot;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SnapshotIndicatorPriority;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SnapshotIndicators;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SurveyData;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotEconomicEntity;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotIndicatorEntity;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SurveyEntity;
import py.org.fundacionparaguaya.pspserver.surveys.entities.specifications.SnapshotSpecifications;
import py.org.fundacionparaguaya.pspserver.surveys.enums.SurveyStoplightEnum;
import py.org.fundacionparaguaya.pspserver.surveys.mapper.SnapshotEconomicMapper;
import py.org.fundacionparaguaya.pspserver.surveys.mapper.SnapshotIndicatorMapper;
import py.org.fundacionparaguaya.pspserver.surveys.repositories.SnapshotEconomicRepository;
import py.org.fundacionparaguaya.pspserver.surveys.repositories.SurveyRepository;
import py.org.fundacionparaguaya.pspserver.surveys.services.SnapshotIndicatorPriorityService;
import py.org.fundacionparaguaya.pspserver.surveys.services.SnapshotService;
import py.org.fundacionparaguaya.pspserver.surveys.services.SurveyService;
import py.org.fundacionparaguaya.pspserver.surveys.validation.ValidationResults;

/**
 * Created by rodrigovillalba on 9/14/17.
 */
@Service
public class SnapshotServiceImpl implements SnapshotService {
	
	private Logger LOG = LoggerFactory.getLogger(SnapshotServiceImpl.class);

    private final SnapshotIndicatorPriorityService priorityService;

    private final SurveyRepository surveyRepository;

    private final SnapshotEconomicRepository economicRepository;

    private final SnapshotEconomicMapper economicMapper;

    private final SurveyService surveyService;

    private final SnapshotIndicatorMapper indicatorMapper;
    
    private final PersonMapper personMapper;
    
    private final FamilyRepository familyRepository;
    
    private final FamilyService familyService;
    
    private static final String INDICATOR_NAME = "name";

    private static final String INDICATOR_VALUE = "value";

    public SnapshotServiceImpl(SnapshotEconomicRepository economicRepository, SnapshotEconomicMapper economicMapper,
            SurveyService surveyService, SurveyRepository surveyRepository, SnapshotIndicatorMapper indicatorMapper,
            SnapshotIndicatorPriorityService priorityService, PersonMapper personMapper,
            FamilyRepository familyRepository, FamilyService familyService) {
        this.economicRepository = economicRepository;
        this.economicMapper = economicMapper;
        this.surveyService = surveyService;
        this.surveyRepository = surveyRepository;
        this.indicatorMapper = indicatorMapper;
        this.priorityService = priorityService;
        this.personMapper = personMapper;
        this.familyRepository = familyRepository;
        this.familyService = familyService;
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

        SnapshotEconomicEntity snapshotEconomicEntity = null;

        PersonEntity personEntity = personMapper.snapshotPersonalToEntity(snapshot.getPersonalSurveyData());

        String code = familyService.generateFamilyCode(personEntity);

        Optional<FamilyEntity> family = familyRepository.findByCode(code);

        if (family.isPresent()) {
            snapshotEconomicEntity = saveEconomic(snapshot, indicatorEntity, family.get());
        } else {
            FamilyEntity newFamily = familyService.createFamilyFromSnapshot(snapshot, code, personEntity);
            snapshotEconomicEntity = saveEconomic(snapshot, indicatorEntity, newFamily);
        }

        return economicMapper.entityToDto(snapshotEconomicEntity);
    }

    private SnapshotEconomicEntity saveEconomic(NewSnapshot snapshot, SnapshotIndicatorEntity indicator,
            FamilyEntity family) {

        SnapshotEconomicEntity entity = economicMapper.newSnapshotToEconomicEntity(snapshot, indicator);
        entity.setFamily(family);

        return this.economicRepository.save(entity);
    }

    @Override
    public List<Snapshot> find(Long surveyId, Long familiyId) {
        return economicRepository.findBySurveyDefinitionId(surveyId).stream().map(economicMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Snapshot> filter(Map<String, List<String>> indicators, Long organizationId, Long applicationId, Long countryId, Long cityId) {
        List<Long> familiesIds = filterFamilies(organizationId, applicationId, countryId, cityId);

        return economicRepository.findAll(Specifications
                .where(SnapshotSpecifications.hasIndicators(indicators))
                .and(SnapshotSpecifications.forFamilies(familiesIds)))
                .stream()
                .map(economicMapper::entityToDto)
                .collect(Collectors.toList());

    }

    private List<Long> filterFamilies(Long organizationId, Long applicationId, Long countryId, Long cityId) {
        if (organizationId == null && applicationId == null && countryId == null && cityId == null) {
            return null;
        }

        return familyRepository.findAll(Specifications
                .where(FamilySpecifications.belongsToOrganization(organizationId))
                .and(FamilySpecifications.belongsToApplication(applicationId))
                .and(FamilySpecifications.inCountry(countryId))
                .and(FamilySpecifications.inCity(cityId)))
                .stream()
                .map((f) -> f.getFamilyId())
                .collect(Collectors.toList());
    }

    @Override
    public SnapshotIndicators getSnapshotIndicators(Long snapshotId) {

        SnapshotIndicators toRet = new SnapshotIndicators();

        SnapshotEconomicEntity originalSnapshot = economicRepository.findOne(snapshotId);

        SurveyEntity survey = surveyRepository.getOne(originalSnapshot.getSurveyDefinition().getId());
        List<String> indicatorGroup = survey.getSurveyDefinition().getSurveyUISchema().getGroupIndicators();

        List<String> order = survey.getSurveyDefinition().getSurveyUISchema().getUiOrder().stream()
                .filter(field -> indicatorGroup.contains(field)).collect(Collectors.toList());

        List<SnapshotIndicatorPriority> priorities = priorityService
                .getSnapshotIndicatorPriorityList(originalSnapshot.getSnapshotIndicator().getId());
        toRet.setIndicatorsPriorities(priorities);

        SurveyData indicators = indicatorMapper.entityToDto(originalSnapshot.getSnapshotIndicator());
        List<SurveyData> indicatorsToRet = new ArrayList<>();
        if (indicatorGroup != null && !indicatorGroup.isEmpty() && order != null && !order.isEmpty()) {

            order.forEach(indicator -> {
                if (indicators.containsKey(indicator)) {
                    SurveyData sd = new SurveyData();
                    sd.put(INDICATOR_NAME, getNameFromCamelCase(indicator));
                    sd.put(INDICATOR_VALUE, indicators.get(indicator));
                    
                    countIndicators(toRet, sd.get(INDICATOR_VALUE));
                    indicatorsToRet.add(sd);
                }
            });

        }

        toRet.setIndicatorsSurveyData(indicatorsToRet);
        toRet.setCreatedAt(originalSnapshot.getCreatedAtAsISOString());
        toRet.setSnapshotIndicatorId(originalSnapshot.getSnapshotIndicator().getId());
        toRet.setSnapshotEconomicId(originalSnapshot.getId());
        toRet.setSurveyId(originalSnapshot.getSurveyDefinition().getId());
        
        //set family for information purpose
        Long familyId = originalSnapshot.getFamily().getFamilyId();
        toRet.setFamilyId(familyId);
        toRet.setFamily(familyService.getFamilyById(familyId));
        
        return toRet;
    }

    private String getNameFromCamelCase(String name) {
        return StringUtils.capitalize(StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(name), " "));
    }

    @Override
    public SnapshotIndicators getLastSnapshotIndicatorsByFamily(Long familyId) {
        SnapshotIndicators toRet = new SnapshotIndicators();
        Optional<SnapshotEconomicEntity> snapshot = economicRepository.findFirstByFamilyFamilyIdOrderByCreatedAtDesc(familyId);
        
        if(snapshot.isPresent()) {
            toRet = getSnapshotIndicators(snapshot.get().getId());
        }
        return toRet;
    }

    @Override
    public List<SnapshotIndicators> getSnapshotIndicatorsByFamily(Long familyId) {
        List<SnapshotIndicators> toRet = new ArrayList<>();
        List<SnapshotEconomicEntity> originalSnapshots = economicRepository.findByFamilyFamilyId(familyId).stream()
                .collect(Collectors.toList());
        
        for (SnapshotEconomicEntity os : originalSnapshots) {
            SnapshotIndicators snapshotIndicators = countSnapshotIndicators(os);

            List<SnapshotIndicatorPriority> priorities = priorityService
                    .getSnapshotIndicatorPriorityList(os.getSnapshotIndicator().getId());
            snapshotIndicators.setIndicatorsPriorities(priorities);
            snapshotIndicators.setCreatedAt(os.getCreatedAtAsISOString());
            snapshotIndicators.setSnapshotIndicatorId(os.getSnapshotIndicator().getId());
            snapshotIndicators.setFamilyId(os.getFamily().getFamilyId());
            snapshotIndicators.setSnapshotEconomicId(os.getId());
            snapshotIndicators.setSurveyId(os.getSurveyDefinition().getId());
            
            toRet.add(snapshotIndicators);
        }
        return toRet;
    }
    
    private SnapshotIndicators countSnapshotIndicators(SnapshotEconomicEntity snapshot) {
    	SnapshotIndicators indicators = new SnapshotIndicators();
    	
    	try {
    		//Map<String, String> beanProperties = BeanUtils.describe(snapshot.getSnapshotIndicator());
    		SurveyData properties = indicatorMapper.entityToDto(snapshot.getSnapshotIndicator());
    		
    		properties.forEach((k, v) -> {
    			countIndicators(indicators, v);
    		});
    		
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new UnknownResourceException("Could not get indicators of "
					+ "the snapshot with id " + snapshot.getId());
		}
    	
    	return indicators;
    }

	private void countIndicators(SnapshotIndicators indicators, Object v) {
		Optional.ofNullable(SurveyStoplightEnum.fromValue(String.valueOf(v))).
			ifPresent(light -> {
				switch (light) {
		            case RED:
		            	indicators.setCountRedIndicators(indicators.getCountRedIndicators() + 1);
		                break;
		            case YELLOW:
		            	indicators.setCountYellowIndicators(indicators.getCountYellowIndicators() + 1);
		                break;
		            case GREEN:
		            	indicators.setCountGreenIndicators(indicators.getCountGreenIndicators() + 1);
		                break;
		            default:
		                break;
		        }
			}	
		);
	}

}
