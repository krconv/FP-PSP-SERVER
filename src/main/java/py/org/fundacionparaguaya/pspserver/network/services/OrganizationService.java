package py.org.fundacionparaguaya.pspserver.network.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import py.org.fundacionparaguaya.pspserver.network.dtos.OrganizationDTO;
import py.org.fundacionparaguaya.pspserver.security.dtos.UserDetailsDTO;

public interface OrganizationService{

	OrganizationDTO updateOrganization(Long organizationId, OrganizationDTO organizationDTO);

	OrganizationDTO addOrganization(OrganizationDTO organizationDTO);
	
	OrganizationDTO getOrganizationById(Long organizationId);
	
	List<OrganizationDTO> getAllOrganizations();
	
	void deleteOrganization(Long organizationId);

	//Page<OrganizationDTO> getAllOrganizations(PageRequest pageRequest);

	Page<OrganizationDTO> listOrganizations(PageRequest pageRequest, UserDetailsDTO userDetails);	

}
