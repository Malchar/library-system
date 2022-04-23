package org.paulschmitz.librarysystem.database.entity;

import javax.persistence.*;
import lombok.*;

/**
 * This entity represents an authority level that a user can have. All users
 * should be created with the role "USER". In addition, administrators should also have
 * the role "ADMIN". The relationship is wholly owned by the User entity.
 * 
 * @author p_schmitz
 *
 */
@Data
@Entity
@Table(name = "user_roles")
public class UserRole {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "role_name")
	private String roleName;

}
