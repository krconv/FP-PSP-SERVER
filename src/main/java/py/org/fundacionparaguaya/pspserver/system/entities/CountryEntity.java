package py.org.fundacionparaguaya.pspserver.system.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import py.org.fundacionparaguaya.pspserver.common.entities.BaseEntity;

import javax.persistence.*;
@Entity
@Table(name = "countries", schema = "system")
public class CountryEntity extends BaseEntity {
	
	@Id
	@GenericGenerator(
			name = "countriesSequenceGenerator",
			strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
			parameters = {
					@Parameter(name = SequenceStyleGenerator.SCHEMA, value = "system"),
					@Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "countries_id_seq"),
					@Parameter(name = SequenceStyleGenerator.INITIAL_PARAM, value = "1"),
					@Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1")
			}
	)
	@GeneratedValue(generator = "countriesSequenceGenerator")
	@Column(name = "id")
	private Long id;
	
	private String country;
	
	@Column(name = "numeric_code")
	private String numericCode;

	@Column(name = "alfa_2_code")
	private String alfa2Code;

	@Column(name = "alfa_3_code")
	private String alfa3Code;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getNumericCode() {
        return numericCode;
    }

    public void setNumericCode(String numericCode) {
        this.numericCode = numericCode;
    }

    public String getAlfa2Code() {
        return alfa2Code;
    }

    public void setAlfa2Code(String alfa2Code) {
        this.alfa2Code = alfa2Code;
    }

    public String getAlfa3Code() {
        return alfa3Code;
    }

    public void setAlfa3Code(String alfa3Code) {
        this.alfa3Code = alfa3Code;
    }
	
	@Override
	public String toString() {
		return "Country [id=" + id + ", country=" + country + ", numericCode=" + numericCode + ", alfa2Code="
                + alfa2Code + ", alfa3code=" + alfa3Code + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (id == null || obj == null || getClass() != obj.getClass())
			return false;
		CountryEntity toCompare = (CountryEntity) obj;
		return id.equals(toCompare.id);
	}
	
	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}
	
}
