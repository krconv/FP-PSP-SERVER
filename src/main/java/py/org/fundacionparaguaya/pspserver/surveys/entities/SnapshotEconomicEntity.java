package py.org.fundacionparaguaya.pspserver.surveys.entities;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import py.org.fundacionparaguaya.pspserver.families.entities.FamilyEntity;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SurveyData;
import py.org.fundacionparaguaya.pspserver.surveys.entities.types.SecondJSONBUserType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by rodrigovillalba on 10/19/17.
 */
@Entity
@Table(name = "snapshots_economics", schema = "data_collect")
public class SnapshotEconomicEntity implements StoreableSnapshot {

    @Id
    @GenericGenerator(name = "snapshotsEconomicsSequenceGenerator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.SCHEMA, value = "data_collect"),
            @org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "snapshots_economics_id_seq"),
            @org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.INITIAL_PARAM, value = "1"),
            @org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(generator = "snapshotsEconomicsSequenceGenerator")
    @Column(name = "id")
    private Long id;

    @ManyToOne(targetEntity = FamilyEntity.class)
    @JoinColumn(name = "family_id")
    private FamilyEntity family;

    @ManyToOne
    private SurveyEntity surveyDefinition;

    @OneToOne(cascade = CascadeType.ALL)
    private SnapshotIndicatorEntity snapshotIndicator;

    @Column(name = "currency")
    private String currency;

    @Column(name = "area_of_residence")
    private String areaOfResidence;

    @Column(name = "employment_status_primary")
    private String employmentStatusPrimary;

    @Column(name = "activity_main")
    private String activityMain;

    @Column(name = "employment_status_secondary")
    private String employmentStatusSecondary;

    @Column(name = "activity_secondary")
    private String activitySecondary;

    @Column(name = "household_monthly_income")
    private Integer householdMonthlyIncome;

    @Column(name = "salary_income")
    private Integer salaryIncome;

    @Column(name = "benefit_income")
    private Integer benefitIncome;

    @Column(name = "pension_income")
    private Integer pensionIncome;

    @Column(name = "savings_income")
    private Integer savingsIncome;

    @Column(name = "other_income")
    private Integer otherIncome;

    @Column(name = "household_monthly_outgoing")
    private Integer householdMonthlyOutgoing;

    @Column(name = "net_suplus")
    private Integer netSuplus;

    @Column(name = "education_client_level")
    private String educationClientLevel;

    @Column(name = "education_person_most_studied")
    private String educationPersonMostStudied;

    @Column(name = "education_level_attained")
    private String educationLevelAttained;

    @Column(name = "housing_situation")
    private String housingSituation;

    @Column(name = "additional_properties")
    @Type(type = "py.org.fundacionparaguaya.pspserver.surveys.entities.types.SecondJSONBUserType", parameters = {
            @org.hibernate.annotations.Parameter(name = SecondJSONBUserType.CLASS, value = "py.org.fundacionparaguaya.pspserver.surveys.dtos.SurveyData") })
    private SurveyData additionalProperties;

    @Column(name = "created_at")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime createdAt;

    @Column(name = "family_ubication")
    private String familyUbication;
    
    @Column(name = "family_country")
    private String familyCountry;
    
    @Column(name = "family_city")
    private String familyCity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FamilyEntity getFamily() {
        return family;
    }

    public void setFamily(FamilyEntity family) {
        this.family = family;
    }

    public SurveyEntity getSurveyDefinition() {
        return surveyDefinition;
    }

    public void setSurveyDefinition(SurveyEntity surveyDefinition) {
        this.surveyDefinition = surveyDefinition;
    }

    public SnapshotIndicatorEntity getSnapshotIndicator() {
        return snapshotIndicator;
    }

    public void setSnapshotIndicator(SnapshotIndicatorEntity snapshotIndicator) {
        this.snapshotIndicator = snapshotIndicator;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAreaOfResidence() {
        return areaOfResidence;
    }

    public void setAreaOfResidence(String areaOfResidence) {
        this.areaOfResidence = areaOfResidence;
    }

    public String getEmploymentStatusPrimary() {
        return employmentStatusPrimary;
    }

    public void setEmploymentStatusPrimary(String employmentStatusPrimary) {
        this.employmentStatusPrimary = employmentStatusPrimary;
    }

    public String getActivityMain() {
        return activityMain;
    }

    public void setActivityMain(String activityMain) {
        this.activityMain = activityMain;
    }

    public String getEmploymentStatusSecondary() {
        return employmentStatusSecondary;
    }

    public void setEmploymentStatusSecondary(String employmentStatusSecundary) {
        this.employmentStatusSecondary = employmentStatusSecundary;
    }

    public String getActivitySecondary() {
        return activitySecondary;
    }

    public void setActivitySecondary(String activitySecondary) {
        this.activitySecondary = activitySecondary;
    }

    public Integer getHouseholdMonthlyIncome() {
        return householdMonthlyIncome;
    }

    public void setHouseholdMonthlyIncome(Integer householdMonthlyIncome) {
        this.householdMonthlyIncome = householdMonthlyIncome;
    }

    public Integer getSalaryIncome() {
        return salaryIncome;
    }

    public void setSalaryIncome(Integer salaryIncome) {
        this.salaryIncome = salaryIncome;
    }

    public Integer getBenefitIncome() {
        return benefitIncome;
    }

    public void setBenefitIncome(Integer benefitIncome) {
        this.benefitIncome = benefitIncome;
    }

    public Integer getPensionIncome() {
        return pensionIncome;
    }

    public void setPensionIncome(Integer pensionIncome) {
        this.pensionIncome = pensionIncome;
    }

    public Integer getSavingsIncome() {
        return savingsIncome;
    }

    public void setSavingsIncome(Integer savingsIncome) {
        this.savingsIncome = savingsIncome;
    }

    public Integer getOtherIncome() {
        return otherIncome;
    }

    public void setOtherIncome(Integer otherIncome) {
        this.otherIncome = otherIncome;
    }

    public Integer getHouseholdMonthlyOutgoing() {
        return householdMonthlyOutgoing;
    }

    public void setHouseholdMonthlyOutgoing(Integer householdMonthlyOutgoing) {
        this.householdMonthlyOutgoing = householdMonthlyOutgoing;
    }

    public Integer getNetSuplus() {
        return netSuplus;
    }

    public void setNetSuplus(Integer netSuplus) {
        this.netSuplus = netSuplus;
    }

    public String getEducationClientLevel() {
        return educationClientLevel;
    }

    public void setEducationClientLevel(String educationClientLevel) {
        this.educationClientLevel = educationClientLevel;
    }

    public String getEducationPersonMostStudied() {
        return educationPersonMostStudied;
    }

    public void setEducationPersonMostStudied(String educationPersonMostStudied) {
        this.educationPersonMostStudied = educationPersonMostStudied;
    }

    public String getEducationLevelAttained() {
        return educationLevelAttained;
    }

    public void setEducationLevelAttained(String educationLevelAttained) {
        this.educationLevelAttained = educationLevelAttained;
    }

    public String getHousingSituation() {
        return housingSituation;
    }

    public void setHousingSituation(String housingSituation) {
        this.housingSituation = housingSituation;
    }

    @Override
    public SurveyData getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(SurveyData additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getFamilyUbication() {
        return familyUbication;
    }

    public void setFamilyUbication(String familyUbication) {
        this.familyUbication = familyUbication;
    }
    
    public String getFamilyCountry() {
        return familyCountry;
    }

    public void setFamilyCountry(String familyCountry) {
        this.familyCountry = familyCountry;
    }

    public String getFamilyCity() {
        return familyCity;
    }

    public void setFamilyCity(String familyCity) {
        this.familyCity = familyCity;
    }

    public SnapshotEconomicEntity surveyDefinition(SurveyEntity definitionEntity) {
        this.surveyDefinition = definitionEntity;
        return this;
    }

    public SnapshotEconomicEntity surveyIndicator(SnapshotIndicatorEntity indicatorEntity) {
        this.snapshotIndicator = indicatorEntity;
        return this;

    }

    public SnapshotEconomicEntity additionalProperties(SurveyData additionalProperties) {
        this.additionalProperties = additionalProperties;
        return this;
    }

    public SnapshotEconomicEntity staticProperties(SurveyData economicSurveyData) {

        economicSurveyData.entrySet().stream().forEach((entry) -> {
            try {
                PropertyUtils.setProperty(this, entry.getKey(), entry.getValue());
            } catch (Exception e) {
                throw new RuntimeException(
                        "Could not set property '" + entry.getKey() + "' to value '" + entry.getValue() + "'", e);
            }
        });
        return this;
    }

    @PrePersist
    public void preSave() {
        this.createdAt = LocalDateTime.now();
    }

    @Transient
    public String getCreatedAtAsISOString() {
        if (this.createdAt != null) {
            return createdAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
        return null;
    }
}
