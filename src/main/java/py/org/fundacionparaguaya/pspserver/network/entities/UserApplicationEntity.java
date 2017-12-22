package py.org.fundacionparaguaya.pspserver.network.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import py.org.fundacionparaguaya.pspserver.common.entities.BaseEntity;
import py.org.fundacionparaguaya.pspserver.security.entities.UserEntity;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "users_applications", schema = "ps_network")
public class UserApplicationEntity extends BaseEntity {

	@Id
	@GenericGenerator(
			name = "usersApplicationsSequenceGenerator",
			strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
			parameters = {
					@org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.SCHEMA, value = "ps_network"),
					@org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "users_applications_id_seq"),
					@org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.INITIAL_PARAM, value = "1"),
					@org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1")
			}
	)
	@GeneratedValue(generator = "usersApplicationsSequenceGenerator")
	@Column(name = "id")
	private Long id;

	@ManyToOne(targetEntity = UserEntity.class)
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@ManyToOne(targetEntity = ApplicationEntity.class)
	@JoinColumn(name = "application_id")
	private ApplicationEntity application;

	@ManyToOne(targetEntity = OrganizationEntity.class)
	@JoinColumn(name = "organization_id")
	private OrganizationEntity organization;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
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

	@Transient
	public Optional<OrganizationEntity> getOrganizationOpt() {
		return Optional.ofNullable(this.organization);
	}

	@Transient
	public Optional<ApplicationEntity> getApplicationOpt() {
		return Optional.ofNullable(this.application);
	}

	@Override
	public String toString() {
		return "UserApplicationEntity [id=" + id + ", user=" + user
				+ ", application=" + application + ", organization=" + organization + "]";
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (id == null || obj == null || getClass() != obj.getClass())
			return false;
		UserApplicationEntity toCompare = (UserApplicationEntity) obj;
		return id.equals(toCompare.id);
	}
	
	
	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}


}
