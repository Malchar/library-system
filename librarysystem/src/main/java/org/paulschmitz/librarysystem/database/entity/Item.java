package org.paulschmitz.librarysystem.database.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * This entity represents an inventory item. Each inventory item is a physical
 * object in the library which in an "instance" of a book. Thus, multiple
 * inventory items can be of the same book. itemCondition represents the
 * durability of the item ranging from new to fine to damaged, and it is
 * implemented with an embedded enumeration.
 * 
 * @author p_schmitz
 *
 */
@Data
@Entity
@Table(name = "items")
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "book_id", nullable = false)
	private Book book;

	// This needs to match the database column definition
	@Column(name = "item_condition", columnDefinition = "ENUM('New', 'Fine', 'Damaged')")
	@Enumerated(EnumType.STRING)
	private Condition itemCondition;

	// Embedded enumeration to support the itemCondition field
	public enum Condition {
		New, Fine, Damaged;
	}

}
