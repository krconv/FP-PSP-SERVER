package py.org.fundacionparaguaya.pspserver.families.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import py.org.fundacionparaguaya.pspserver.common.mapper.BaseMapper;
import py.org.fundacionparaguaya.pspserver.families.dtos.PersonDTO;
import py.org.fundacionparaguaya.pspserver.families.entities.PersonEntity;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SurveyData;
import py.org.fundacionparaguaya.pspserver.system.entities.CountryEntity;
import py.org.fundacionparaguaya.pspserver.system.repositories.CountryRepository;

/**
 * Created by jaimeferreira on 8/31/2017
 */
@Component
public class PersonMapper implements BaseMapper<PersonEntity, PersonDTO> {

	private final ModelMapper modelMapper;
	
	private final CountryRepository countryRepository;

	public PersonMapper(ModelMapper modelMapper, CountryRepository countryRepository) {
		this.modelMapper = modelMapper;
		this.countryRepository = countryRepository;
	}

	@Override
	public List<PersonDTO> entityListToDtoList(List<PersonEntity> entityList) {
		return entityList.stream().filter(Objects::nonNull).map(this::entityToDto).collect(Collectors.toList());
	}

	@Override
	public PersonDTO entityToDto(PersonEntity entity) {
		PersonDTO dto = modelMapper.map(entity, PersonDTO.class);
		return dto;
	}

	@Override
	public PersonEntity dtoToEntity(PersonDTO dto) {
		return modelMapper.map(dto, PersonEntity.class);
	}

	public PersonEntity snapshotPersonalToEntity(SurveyData snapshot) {

		PersonEntity pe = modelMapper.map(snapshot, PersonEntity.class);
		if (snapshot.getAsString("birthdate") != null) {
			pe.setBirthdate(LocalDate.parse(snapshot.getAsString("birthdate"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		}
		if(snapshot.get("countryOfBirth") != null) {
			Optional<CountryEntity> country = countryRepository.findByAlfa2Code(snapshot.getAsString("countryOfBirth"));
			pe.setCountryOfBirth(country.orElse(null));
		}
		
		return pe;
	}

}
