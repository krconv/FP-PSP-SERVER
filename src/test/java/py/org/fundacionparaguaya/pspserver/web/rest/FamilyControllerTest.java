package py.org.fundacionparaguaya.pspserver.web.rest;

import static org.hamcrest.CoreMatchers.is;
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

import py.org.fundacionparaguaya.pspserver.families.dtos.FamilyDTO;
import py.org.fundacionparaguaya.pspserver.families.dtos.PersonDTO;
import py.org.fundacionparaguaya.pspserver.families.services.FamilyMapService;
import py.org.fundacionparaguaya.pspserver.families.services.FamilyService;
import py.org.fundacionparaguaya.pspserver.network.dtos.ApplicationDTO;
import py.org.fundacionparaguaya.pspserver.network.dtos.OrganizationDTO;
import py.org.fundacionparaguaya.pspserver.system.dtos.CityDTO;
import py.org.fundacionparaguaya.pspserver.system.dtos.CountryDTO;
import py.org.fundacionparaguaya.pspserver.util.TestHelper;

/**
 * 
 * Created by mcespedes on 9/4/17
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(FamilyController.class)
@ActiveProfiles("test")
public class FamilyControllerTest {

	@Autowired
	private FamilyController controller;

	@MockBean
	FamilyService familyService;
	
	@MockBean
	FamilyMapService familyMapService;

	@Autowired
	private MockMvc mockMvc;
	private FamilyDTO mockFamily;

	@Before
	public void setup() {
		mockFamily = FamilyDTO.builder()
				.name("foo.family")
				.code("foo.code")
				.country(getCountryTest())
				.city(getCityTest())
				.locationType("foo.locationType")
				.locationPositionGps("foo.locationPositionGps")
				.person(getPersonTest())
				.application(getApplcationTest())
				.organization(getOrganizationTest())
				.build();
	}
	
	@Test
	public void requestingPutFamilyShouldAddNewFamily() throws Exception {
		when(familyService.addFamily(anyObject())).thenReturn(mockFamily);

		String json = TestHelper.mapToJson(mockFamily);
		mockMvc.perform(post("/api/v1/families").content(json).contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.name", is(mockFamily.getName())));
	}
	
	@Test
    public void requestingPostFamilyShouldUpdateFamily() throws Exception {
        Long familyId = 9999L;
        when(familyService.updateFamily(eq(familyId), anyObject())).thenReturn(mockFamily);

        String json = TestHelper.mapToJson(mockFamily);
        mockMvc.perform(put("/api/v1/families/{familyId}", familyId).content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(mockFamily.getName())));

    }
	
	private OrganizationDTO getOrganizationTest() {
		OrganizationDTO dto = new OrganizationDTO();
		dto.setId(new Long(1));
		dto.setName("foo.ORGANIZATION");
		return dto;
	}

	private ApplicationDTO getApplcationTest() {
		ApplicationDTO dto = new ApplicationDTO();
		dto.setId(new Long(1));
		dto.setName("foo.APPLICATION");
		//...
		return dto;
	}

	private PersonDTO getPersonTest() {
		PersonDTO dto = new PersonDTO();
		dto.setPersonId(new Long(1));
		dto.setFirstName("foo.FIRSTNAME");
		dto.setLastName("foo.LASTNAME");
		return dto;
	}

	private CityDTO getCityTest() {
		CityDTO dto = new CityDTO();
		dto.setId(new Long(1));
		dto.setCity("foo.CITY");
		return dto;
	}

	private CountryDTO getCountryTest() {
		CountryDTO dto = new CountryDTO();
		dto.setId(new Long(1));
		dto.setCountry("foo.COUNTRY");
		return dto;
	}

}
