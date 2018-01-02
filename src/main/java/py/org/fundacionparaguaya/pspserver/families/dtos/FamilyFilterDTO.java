/**
 * 
 */
package py.org.fundacionparaguaya.pspserver.families.dtos;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author bsandoval
 *
 */
public class FamilyFilterDTO {

	private Long applicationId;
	private Long organizationId;
	private Long countryId;
	private Long cityId;
	private String name;
	
	public FamilyFilterDTO() {
	    super();
	}

    public FamilyFilterDTO(Long organizationId, Long countryId, Long cityId, String name) {
        super();
        this.organizationId = organizationId;
        this.countryId = countryId;
        this.cityId = cityId;
        this.name = name;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
	
    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("applicationId", applicationId);
        builder.append("organizationId", organizationId);
        builder.append("countryId", countryId);
        builder.append("cityId", cityId);
        builder.append("name", name);
        return builder.build();
    }
}
