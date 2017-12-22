package py.org.fundacionparaguaya.pspserver.system.services.impl;

import org.springframework.stereotype.Service;
import py.org.fundacionparaguaya.pspserver.common.exceptions.UnknownResourceException;
import py.org.fundacionparaguaya.pspserver.system.dtos.CountryDTO;
import py.org.fundacionparaguaya.pspserver.system.entities.CountryEntity;
import py.org.fundacionparaguaya.pspserver.system.mapper.CountryMapper;
import py.org.fundacionparaguaya.pspserver.system.repositories.CountryRepository;
import py.org.fundacionparaguaya.pspserver.system.services.CountryService;

import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;



@Service
public class CountryServiceImpl implements CountryService {

	CountryRepository countryRepository;
	
	private final CountryMapper countryMapper;
	

	public CountryServiceImpl(CountryRepository countryRepository, CountryMapper countryMapper) {
		this.countryRepository = countryRepository;
		this.countryMapper    = countryMapper;
	}


	@Override
	public CountryDTO getCountryById(Long countryId) {
		checkArgument(countryId > 0, "Argument was %s but expected nonnegative", countryId);

        return Optional.ofNullable(countryRepository.findOne(countryId))
                .map(countryMapper::entityToDto)
                .orElseThrow(() -> new UnknownResourceException("Country does not exist"));
	}


	@Override
	public List<CountryDTO> getAllCountries() {
		List<CountryEntity> applications = countryRepository.findAll();
		return countryMapper.entityListToDtoList(applications);
	}
	
	
	


}
