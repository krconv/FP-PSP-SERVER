package py.org.fundacionparaguaya.pspserver.surveys.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by rodrigovillalba on 10/17/17.
 */
public class Snapshot {

    @JsonProperty("survey_id")
    private Long surveyId = null;

    @JsonProperty("snapshot_economic_id")
    private Long snapshotEconomicId = null;
    
    @JsonProperty("personal_survey_data")
    private SurveyData personalSurveyData = null;
    
    @JsonProperty("economic_survey_data")
    private SurveyData economicSurveyData = null;

    @JsonProperty("indicator_survey_data")
    private SurveyData indicatorSurveyData = null;

    @JsonProperty("created_at")
    private String createdAt;
    
    public Snapshot personalSurveyData(SurveyData surveyData) {
        this.personalSurveyData = surveyData;
        return this;
    }

    public Snapshot economicSurveyData(SurveyData surveyData) {
        this.economicSurveyData = surveyData;
        return this;
    }

    public Snapshot indicatorSurveyData(SurveyData surveyData) {
        this.indicatorSurveyData = surveyData;
        return this;
    }

    /**
     * Key/value pairs representing the filled out 'Socio Economics' survey
     * @return surveyData
     **/
    @ApiModelProperty(value = "Key/value pairs representing the filled out 'Personal' survey")
    public SurveyData getPersonalSurveyData() {
        return personalSurveyData;
    }

    public void setPersonalSurveyData(SurveyData surveyData) {
        this.personalSurveyData = surveyData;
    }
    
    /**
     * Key/value pairs representing the filled out 'Socio Economics' survey
     * @return surveyData
     **/
    @ApiModelProperty(value = "Key/value pairs representing the filled out 'Socio Economics' survey")
    public SurveyData getEconomicSurveyData() {
        return economicSurveyData;
    }

    public void setEconomicSurveyData(SurveyData surveyData) {
        this.economicSurveyData = surveyData;
    }

    /**
     * The survey's id that this snapshot belongs to.
     * @return the survey id number respresentation
     */
    @ApiModelProperty(value = "The survey's id that this snapshot belongs to.")
    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }
    
    /**
     * The survey's id that this snapshot belongs to.
     * @return the survey id number respresentation
     */
    @ApiModelProperty(value = "The id that this snapshot belongs to.")
    public Long getSnapshotEconomicId() {
        return snapshotEconomicId;
    }

    public void setSnapshotEconomicId(Long snapshotEconomicId) {
        this.snapshotEconomicId = snapshotEconomicId;
    }

    /**
     * Key/value pairs representing the filled out 'Indicators' survey
     * @return surveyData
     **/
    @ApiModelProperty(value = "Key/value pairs representing the filled out 'Indicators' survey")
    public SurveyData getIndicatorSurveyData() {
        return indicatorSurveyData;
    }

    public void setIndicatorSurveyData(SurveyData indicatorSurveyData) {
        this.indicatorSurveyData = indicatorSurveyData;
    }

    /**
     * [ISO 8601](https://es.wikipedia.org/wiki/ISO_8601) formatted creation date
     * @return
     */
    @ApiModelProperty(value = " [ISO 8601](https://es.wikipedia.org/wiki/ISO_8601) formatted creation date")
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Snapshot {\n");
        sb.append("    surveyId: ").append(toIndentedString(surveyId)).append("\n");
        sb.append("    snapshotEconomicId: ").append(toIndentedString(snapshotEconomicId)).append("\n");
        sb.append("    personalSurveyData: ").append(toIndentedString(personalSurveyData)).append("\n");
        sb.append("    economicSurveyData: ").append(toIndentedString(economicSurveyData)).append("\n");
        sb.append("    indicatorSurveyData: ").append(toIndentedString(indicatorSurveyData)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Snapshot that = (Snapshot) o;

        return com.google.common.base.Objects.equal(this.surveyId, that.surveyId) &&
        		com.google.common.base.Objects.equal(this.snapshotEconomicId, that.snapshotEconomicId) &&
        		com.google.common.base.Objects.equal(this.personalSurveyData, that.personalSurveyData) &&
                com.google.common.base.Objects.equal(this.economicSurveyData, that.economicSurveyData) &&
                com.google.common.base.Objects.equal(this.indicatorSurveyData, that.indicatorSurveyData);
    }

    @Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(surveyId, snapshotEconomicId, personalSurveyData, economicSurveyData, indicatorSurveyData);
    }

    public Snapshot snapshotEconomicId(Long id) {
        this.snapshotEconomicId = id;
        return this;
    }

    public Snapshot surveyId(Long surveyId) {
        this.surveyId = surveyId;
        return this;
    }

    public Snapshot createdAt(String createdAtAsISOString) {
        this.createdAt = createdAtAsISOString;
        return this;
    }


}
