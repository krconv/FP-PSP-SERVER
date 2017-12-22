package py.org.fundacionparaguaya.pspserver.families.entities;

import py.org.fundacionparaguaya.pspserver.network.entities.ApplicationEntity;
import py.org.fundacionparaguaya.pspserver.common.entities.BaseEntity;
import py.org.fundacionparaguaya.pspserver.network.entities.OrganizationEntity;
import py.org.fundacionparaguaya.pspserver.system.entities.CityEntity;
import py.org.fundacionparaguaya.pspserver.system.entities.CountryEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

@Entity
@Table(name = "family", schema = "ps_families")
public class FamilyEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ps_families.family_family_id_seq")
	@SequenceGenerator(name = "ps_families.family_family_id_seq", sequenceName = "ps_families.family_family_id_seq", allocationSize = 1)
	@Column(name = "family_id")
	private Long familyId;

	private String name;

	@ManyToOne(targetEntity = CountryEntity.class)
	@JoinColumn(name = "country")
	private CountryEntity country;

	@ManyToOne(targetEntity = CityEntity.class)
	@JoinColumn(name = "city")
	private CityEntity city;

	private String locationType;

	private String locationPositionGps;

	@ManyToOne(targetEntity = PersonEntity.class)
	@JoinColumn(name = "person_reference_id")
	private PersonEntity person;

	@ManyToOne(targetEntity = ApplicationEntity.class)
	@JoinColumn(name = "application_id")
	private ApplicationEntity application;

	@ManyToOne(targetEntity = OrganizationEntity.class)
	@JoinColumn(name = "organization_id")
	private OrganizationEntity organization;

	public Long getFamilyId() {
		return familyId;
	}

	public void setFamilyId(Long familyId) {
		this.familyId = familyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CountryEntity getCountry() {
		return country;
	}

	public void setCountry(CountryEntity country) {
		this.country= country;
	}

	public CityEntity getCity() {
		return city;
	}

	public void setCity(CityEntity city) {
		this.city= city;
	}

	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	public String getLocationPositionGps() {
		return locationPositionGps;
	}

	public void setLocationPositionGps(String locationPositionGps) {
		this.locationPositionGps = locationPositionGps;
	}

	public PersonEntity getPerson() {
		return person;
	}

	public void setPerson(PersonEntity person) {
		this.person = person;
	}

	public ApplicationEntity getApplication() {
		return application;
	}

	public void setApplication(ApplicationEntity application) {
		this.application = application;
	}

	public OrganizationEntity getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationEntity organization) {
		this.organization = organization;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (familyId == null || obj == null || getClass() != obj.getClass())
			return false;
		FamilyEntity toCompare = (FamilyEntity) obj;
		return familyId.equals(toCompare.familyId);
	}
	
	@Override
	public int hashCode() {
		return familyId == null ? 0 : familyId.hashCode();
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("familyId", familyId)
				.add("name", name)
				.add("country", country.toString())
				.add("city", city.toString())
				.add("locationType", locationType)
				.add("locationPositionGps", locationPositionGps)
				.add("person", person.toString())
				.add("application", application.toString())
				.add("organization", organization.toString())
				.toString();
	}

	public static String getCSVFields() {
		return "Family Name,Country,City,Location Type,Location,Person,Application,Organization";

	}
	public String[] toCSV() {
		return new String[] {
			getCSVFields(),
			name + "," + country.getCountry() + "," + city.getCity() + "," + locationType + "," 
					+ locationPositionGps + "," + person.getName() + "," + application.getName() 
					+ "," + organization.getName()
		};
	}
}
