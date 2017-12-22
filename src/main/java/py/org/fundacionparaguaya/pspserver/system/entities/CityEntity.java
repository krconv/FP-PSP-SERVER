package py.org.fundacionparaguaya.pspserver.system.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import py.org.fundacionparaguaya.pspserver.common.entities.BaseEntity;

import javax.persistence.*;


@Entity
@Table(name = "cities", schema = "system")
public class CityEntity extends BaseEntity {
	
	@Id
	@GenericGenerator(
			name = "citiesSequenceGenerator",
			strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
			parameters = {
					@Parameter(name = SequenceStyleGenerator.SCHEMA, value = "system"),
					@Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "cities_id_seq"),
					@Parameter(name = SequenceStyleGenerator.INITIAL_PARAM, value = "1"),
					@Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1")
			}
	)
	@GeneratedValue(generator = "citiesSequenceGenerator")
	@Column(name = "id")
	private Long id;
	
	private String city;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "City [id=" + id + ", city=" + city + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (id == null || obj == null || getClass() != obj.getClass())
			return false;
		CityEntity toCompare = (CityEntity) obj;
		return id.equals(toCompare.id);
	}
	
	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}

}
