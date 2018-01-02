package py.org.fundacionparaguaya.pspserver.families.services.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static org.springframework.data.jpa.domain.Specifications.where;
import static py.org.fundacionparaguaya.pspserver.families.specifications.FamilySpecification.byFilter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.common.exceptions.UnknownResourceException;
import py.org.fundacionparaguaya.pspserver.families.dtos.FamilyDTO;
import py.org.fundacionparaguaya.pspserver.families.dtos.FamilyFilterDTO;
import py.org.fundacionparaguaya.pspserver.families.entities.FamilyEntity;
import py.org.fundacionparaguaya.pspserver.families.entities.PersonEntity;
import py.org.fundacionparaguaya.pspserver.families.mapper.FamilyMapper;
import py.org.fundacionparaguaya.pspserver.families.repositories.FamilyRepository;
import py.org.fundacionparaguaya.pspserver.families.services.FamilyService;
import py.org.fundacionparaguaya.pspserver.network.dtos.ApplicationDTO;
import py.org.fundacionparaguaya.pspserver.network.dtos.OrganizationDTO;
import py.org.fundacionparaguaya.pspserver.network.entities.OrganizationEntity;
import py.org.fundacionparaguaya.pspserver.network.repositories.OrganizationRepository;
import py.org.fundacionparaguaya.pspserver.security.dtos.UserDetailsDTO;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.NewSnapshot;
import py.org.fundacionparaguaya.pspserver.system.entities.CityEntity;
import py.org.fundacionparaguaya.pspserver.system.entities.CountryEntity;
import py.org.fundacionparaguaya.pspserver.system.repositories.CityRepository;
import py.org.fundacionparaguaya.pspserver.system.repositories.CountryRepository;

@Service
public class FamilyServiceImpl implements FamilyService {

    private Logger LOG = LoggerFactory.getLogger(FamilyServiceImpl.class);

    private final FamilyMapper familyMapper;

    private final FamilyRepository familyRepository;

    private final CountryRepository countryRepository;

    private final CityRepository cityRepository;

    private final OrganizationRepository organizationRepository;

    private static final String SPACE = " ";

    public FamilyServiceImpl(FamilyRepository familyRepository, FamilyMapper familyMapper,
    		CountryRepository countryRepository, CityRepository cityRepository,
            OrganizationRepository organizationRepository) {
        this.familyRepository = familyRepository;
        this.familyMapper = familyMapper;
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
        this.organizationRepository = organizationRepository;
    }

    @Override
    public FamilyDTO updateFamily(Long familyId, FamilyDTO familyDTO) {
        checkArgument(familyId > 0, "Argument was %s but expected nonnegative", familyId);

        return Optional.ofNullable(familyRepository.findOne(familyId)).map(family -> {
            BeanUtils.copyProperties(familyDTO, family);
            LOG.debug("Changed Information for Family: {}", family);
            return family;
        }).map(familyMapper::entityToDto).orElseThrow(() -> new UnknownResourceException("Family does not exist"));
    }

    @Override
    public FamilyDTO addFamily(FamilyDTO familyDTO) {
        FamilyEntity family = new FamilyEntity();
        BeanUtils.copyProperties(familyDTO, family);
        FamilyEntity newFamily = familyRepository.save(family);
        return familyMapper.entityToDto(newFamily);
    }

    @Override
    public FamilyDTO getFamilyById(Long familyId) {
        checkArgument(familyId > 0, "Argument was %s but expected nonnegative", familyId);

        return Optional.ofNullable(familyRepository.findOne(familyId)).map(familyMapper::entityToDto)
                .orElseThrow(() -> new UnknownResourceException("Family does not exist"));
    }

    @Override
    public List<FamilyDTO> getAllFamilies() {
        List<FamilyEntity> families = familyRepository.findAll();
        return familyMapper.entityListToDtoList(families);
    }

    @Override
    public void deleteFamily(Long familyId) {
        checkArgument(familyId > 0, "Argument was %s but expected nonnegative", familyId);

        Optional.ofNullable(familyRepository.findOne(familyId)).ifPresent(family -> {
            familyRepository.delete(family);
            LOG.debug("Deleted Family: {}", family);
        });
    }

    @Override
    public String generateFamilyCode(PersonEntity person) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String birthdate = person.getBirthdate().format(formatter);

        String code = person.getCountryOfBirth().getAlfa2Code().concat(".")
                .concat(person.getFirstName().substring(0, 1)).concat(person.getLastName().substring(0, 1)).concat(".")
                .concat(birthdate);

        return code;
    }

    @Override
    public List<FamilyDTO> getFamiliesByFilter(Long organizationId, Long countryId, Long cityId, String freeText) {
		
	  	List<FamilyEntity> listFamilies = new ArrayList<FamilyEntity>();
	  	
	  	if (organizationId != null && countryId != null && cityId != null) {
	  		listFamilies = familyRepository.findByOrganizationIdAndCountryIdAndCityIdAndNameContainingIgnoreCase(organizationId, countryId, cityId, freeText);
		}else{
			listFamilies = familyRepository.findByNameContainingIgnoreCase(freeText);
		}
		
		return familyMapper.entityListToDtoList(listFamilies);
	}
    
    @Override
    public List<FamilyDTO> listFamilies(FamilyFilterDTO filter, UserDetailsDTO userDetails) {
        loadFilterByDetails(filter, userDetails);
		
		List<FamilyEntity> entityList = familyRepository.findAll(where(byFilter(filter)));
		
		return familyMapper.entityListToDtoList(entityList);
	}
    
    @Override
    public FamilyEntity createFamilyFromSnapshot(NewSnapshot snapshot, String code, PersonEntity person) {
        FamilyEntity newFamily = new FamilyEntity();
        newFamily.setPerson(person);
        newFamily.setCode(code);
        newFamily.setName(person.getFirstName().concat(SPACE).concat(person.getLastName()));
        newFamily.setLocationPositionGps(snapshot.getEconomicSurveyData().getAsString("familyUbication"));

        Optional<CountryEntity> country = countryRepository
                .findByCountry(snapshot.getEconomicSurveyData().getAsString("familyCountry"));
        newFamily.setCountry(country.orElse(null));

        Optional<CityEntity> city = cityRepository
                .findByCity(snapshot.getEconomicSurveyData().getAsString("familyCity"));
        newFamily.setCity(city.orElse(null));
        
        if(snapshot.getOrganizationId()!=null) {
            OrganizationEntity organization = organizationRepository.findOne(snapshot.getOrganizationId());
            newFamily.setOrganization(organization);
            newFamily.setApplication(organization.getApplication());
        }

        newFamily = familyRepository.save(newFamily);

        return newFamily;
    }
    
    @Override
    public Long countFamiliesByDetails(UserDetailsDTO userDetails) {
        return familyRepository.count(byFilter(buildFilterByDetails(userDetails)));
    }
    
    private FamilyFilterDTO buildFilterByDetails(UserDetailsDTO userDetails) {
        FamilyFilterDTO filter = new FamilyFilterDTO();
        loadFilterByDetails(filter, userDetails);
        return filter;
    }
    
    private void loadFilterByDetails(FamilyFilterDTO target, UserDetailsDTO userDetails) {
        Long applicationId = Optional.ofNullable(userDetails.getApplication())
                .orElse(new ApplicationDTO()).getId();
        
        Long organizationId = Optional.ofNullable(Optional.ofNullable(userDetails.getOrganization())
                .orElse(new OrganizationDTO()).getId())
                .orElse(target.getOrganizationId());
        
        target.setApplicationId(applicationId);
        target.setOrganizationId(organizationId);
    }
}
