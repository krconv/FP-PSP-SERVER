package py.org.fundacionparaguaya.pspserver.surveys.dtos;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * A container for filter parameters on a snapshot.
 */
public class SnapshotFilterDTO {
    private Map<String, List<String>> indicators;
	private Long organizationId;
	private Long applicationId;
	private Long countryId;
	private Long cityId;
	
	public SnapshotFilterDTO() {
	    super();
	}

    /**
     * Creates a new container with the given parameters.
     * @param indicatorsRaw A JSON formated string which maps indicator keys
     *      to a list of potential values to search for.
     * @param organizationId The organization ID to search for.
     * @param applicationId The applicaiton ID to search for.
     * @param countryId The country ID to search for.
     * @param cityId The city ID to search for.
     */
    public SnapshotFilterDTO(
            String indicatorsRaw,
            Long organizationId,
            Long applicationId,
            Long countryId,
            Long cityId
        ) {
        super();
        this.indicators = toMap(indicatorsRaw);
        this.organizationId = organizationId;
        this.applicationId = applicationId;
        this.countryId = countryId;
        this.cityId = cityId;
    }

    public Map<String, List<String>> getIndicators() {
        return indicators;
    }

    public void setIndicators(Map<String, List<String>> indicators) {
        this.indicators = indicators;
    }

    public void setIndicators(String indicatorsRaw) {
        this.indicators = toMap(indicatorsRaw);
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
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
	
    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("indicators", fromMap(indicators));
        builder.append("organizationId", organizationId);
        builder.append("applicationId", applicationId);
        builder.append("countryId", countryId);
        builder.append("cityId", cityId);
        return builder.build();
    }

    private Map<String, List<String>> toMap(String json) {
        if (json == null) { return null; }
        return new GsonBuilder().create().fromJson(
                json, 
                new TypeToken<Map<String, List<String>>>(){}.getType()
            );
    }

    private String fromMap(Map<String, List<String>> map) {
        if (map == null) { return null; }
        return new GsonBuilder().create().toJson(map);
    }
}