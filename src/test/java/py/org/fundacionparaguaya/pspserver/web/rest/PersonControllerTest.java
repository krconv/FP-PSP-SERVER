package py.org.fundacionparaguaya.pspserver.web.rest;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isIn;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import py.org.fundacionparaguaya.pspserver.families.constants.Gender;
import py.org.fundacionparaguaya.pspserver.families.dtos.FamilyDTO;
import py.org.fundacionparaguaya.pspserver.families.dtos.PersonDTO;
import py.org.fundacionparaguaya.pspserver.families.services.PersonService;
import py.org.fundacionparaguaya.pspserver.system.dtos.CityDTO;
import py.org.fundacionparaguaya.pspserver.system.dtos.CountryDTO;
import py.org.fundacionparaguaya.pspserver.util.TestHelper;

/**
 * 
 *created by jaime on 9/5/17
 *
 */

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
@ActiveProfiles("test")
public class PersonControllerTest {

	@Autowired
    private PersonController controller;

    @MockBean
    private PersonService personService;

    @Autowired
    private MockMvc mockMvc;

    private PersonDTO mockPerson;

    @Before
    public void setup() {
        mockPerson = PersonDTO.builder()
                .firstName("foo.name")
                .lastName("foo.lastname")
                .identificationType("foo.identificationType")
                .identificationNumber("foo.identificationNumber")
                .personRole("foo.personRole")
                .gender(Gender.M)
                .activityPrimary("foo.activityPrimary")
                .activitySecundary("foo.activitySecundary")
                .countryOfBirth(getCountryTest())
                .city(getCityTest())
                .family(getFamilynTest())
                .build();
    }
    
    @Test
    public void requestingPutPersonShouldAddNewPerson() throws Exception {
    	when(personService.addPerson(anyObject())).thenReturn(mockPerson);

        String json = TestHelper.mapToJson(mockPerson);
        mockMvc.perform(post("/api/v1/people").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(mockPerson.getFirstName())));
    }
    
   @Test
    public void requestingPostPersonShouldUpdatePerson() throws Exception {
    	Long personId = 999L;
        when(personService.updatePerson(eq(personId), anyObject())).thenReturn(mockPerson);

        String json = TestHelper.mapToJson(mockPerson);
        mockMvc.perform(put("/api/v1/people/{personId}", personId).content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(mockPerson.getFirstName())));

    }
    
    private CountryDTO getCountryTest() {
		CountryDTO dto = new CountryDTO();
		dto.setId(new Long(1));
		dto.setCountry("foo.COUNTRY");
		return dto;
	}
    
    private FamilyDTO getFamilynTest() {
    	FamilyDTO dto = new FamilyDTO();
		dto.setFamilyId(new Long(1));
		dto.setName("foo.FAMILY");
		return dto;
	}
    
    private CityDTO getCityTest() {
		CityDTO dto = new CityDTO();
		dto.setId(new Long(1));
		dto.setCity("foo.CITY");
		return dto;
	}
}
