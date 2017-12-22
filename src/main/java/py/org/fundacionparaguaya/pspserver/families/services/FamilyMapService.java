package py.org.fundacionparaguaya.pspserver.families.services;

import py.org.fundacionparaguaya.pspserver.families.dtos.FamilyMapDTO;



public interface FamilyMapService {

	FamilyMapDTO getFamilyMapById(Long familyId);
	
}
