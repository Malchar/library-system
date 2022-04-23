package org.paulschmitz.librarysystem.database.entity;

import java.util.Collection;
import java.util.Set;

import javax.persistence.*;
import lombok.*;

/**
 * This entity represents a person who has an account in the library system. The
 * unique id is the user's email address and also their login "username". The
 * "name" field is the user's first and last name. This entity also owns the
 * relationship with their checkouts and userRoles.
 * 
 * @author p_schmitz
 *
 */
@Data
@Entity
@Table(name = "users")
public class User {

	@Id
	private String email;

	private String password;

	private String name;

	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@OneToMany(fetch = FetchType.EAGER, targetEntity = Checkout.class)
	@JoinColumn(name = "user_email", nullable = false)
	private Collection<Checkout> checkout;

	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@OneToMany(fetch = FetchType.EAGER, targetEntity = UserRole.class)
	@JoinColumn(name = "user_email", nullable = false)
	private Set<UserRole> userRole;

}
