package org.paulschmitz.librarysystem.database.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * This entity represents an order/invoice which can contain multiple line
 * items. This is used to track past orders or currently pending orders such as
 * the user's shopping cart. The relationship is wholly owned by the User entity.
 * 
 * @author p_schmitz
 *
 */
@Data
@Entity
@Table(name = "checkouts")
public class Checkout {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/*
	 * The value is true if and only if the checkout has not yet been completed.
	 * Each user can have zero or one pending cart as a time.
	 */
	private Boolean pending;

}
