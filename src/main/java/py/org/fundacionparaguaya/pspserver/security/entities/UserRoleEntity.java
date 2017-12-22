package py.org.fundacionparaguaya.pspserver.security.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import py.org.fundacionparaguaya.pspserver.common.entities.BaseEntity;
import py.org.fundacionparaguaya.pspserver.security.constants.Role;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users_roles", schema = "security")
public class UserRoleEntity extends BaseEntity {

	@Id
	@GenericGenerator(
			name = "usersRolesSequenceGenerator",
			strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
			parameters = {
					@Parameter(name = SequenceStyleGenerator.SCHEMA, value = "security"),
					@Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "users_roles_id_seq"),
					@Parameter(name = SequenceStyleGenerator.INITIAL_PARAM, value = "1"),
					@Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1")
			}
	)
	@GeneratedValue(generator = "usersRolesSequenceGenerator")
	private Long id;

	@ManyToOne(targetEntity = UserEntity.class)
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@Column(name = "role")
	@NotNull
	@Enumerated(EnumType.STRING)
	private Role role;
	
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserRoleEntity [id=" + id + ", user=" + user + ", role=" + role + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (id == null || obj == null || getClass() != obj.getClass())
			return false;
		UserRoleEntity toCompare = (UserRoleEntity) obj;
		return id.equals(toCompare.id);
	}
	
	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}
	
}
