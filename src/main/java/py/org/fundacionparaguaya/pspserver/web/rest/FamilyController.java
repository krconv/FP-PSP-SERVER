package py.org.fundacionparaguaya.pspserver.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import py.org.fundacionparaguaya.pspserver.families.dtos.FamilyDTO;
import py.org.fundacionparaguaya.pspserver.families.dtos.FamilyMapDTO;
import py.org.fundacionparaguaya.pspserver.families.services.FamilyMapService;
import py.org.fundacionparaguaya.pspserver.families.services.FamilyService;


@RestController
@RequestMapping(value = "/api/v1/families")
public class FamilyController {

	private static final Logger LOG = LoggerFactory.getLogger(FamilyController.class);

	private FamilyService familyService;

	private FamilyMapService familyMapService;

	public FamilyController(FamilyService familyService, FamilyMapService familyMapService) {
		this.familyService = familyService;
		this.familyMapService = familyMapService;
	}

	@PostMapping()
	public ResponseEntity<FamilyDTO> addFamily(@Valid @RequestBody FamilyDTO familyDTO) throws URISyntaxException {
		FamilyDTO result = familyService.addFamily(familyDTO);
		return ResponseEntity.created(new URI("/api/v1/families/" + result.getFamilyId())).body(result);
	}

	@PutMapping("/{familyId}")
	public ResponseEntity<FamilyDTO> updateFamily(@PathVariable("familyId") Long familyId,
			@RequestBody FamilyDTO familyDTO) {
		FamilyDTO result = familyService.updateFamily(familyId, familyDTO);
		return ResponseEntity.ok(result);
	}

	
	@GetMapping("/{familyId}")
	public ResponseEntity<FamilyMapDTO> getFamilyMapById(@PathVariable("familyId") Long familyId) {
		FamilyMapDTO dto = familyMapService.getFamilyMapById(familyId);
		return ResponseEntity.ok(dto);
	}
	

	@GetMapping()
	public ResponseEntity<List<FamilyDTO>> getAllFamilies() {
		List<FamilyDTO> families = familyService.getAllFamilies();
		return ResponseEntity.ok(families);
	}

	@DeleteMapping("/{familyId}")
	public ResponseEntity<Void> deleteFamily(@PathVariable("familyId") Long familyId) {
		LOG.debug("REST request to delete Family: {}", familyId);
		familyService.deleteFamily(familyId);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/filter")
	public ResponseEntity<List<FamilyDTO>> getFamiliesByFilter(
			@RequestParam(value = "organization_id", required = false) Long organizationId,
			@RequestParam(value = "country_id", required = false) Long countryId,
			@RequestParam(value = "city_id", required = false) Long cityId,
			@RequestParam(value = "free_text", required = false) String freeText) {
		List<FamilyDTO> families = familyService.getFamiliesByFilter(organizationId, countryId, cityId, freeText);
		return ResponseEntity.ok(families);
	}

}