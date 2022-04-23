package org.paulschmitz.librarysystem.database.entity;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

/**
 * This entity represents a line item on a purchase order or invoice. The line
 * item tracks the underlying inventory item, which checkout it belongs to, the
 * item due date, and the date the item was actually returned. The dates track
 * year, month, and day only.
 * 
 * @author p_schmitz
 *
 */
@Data
@Entity
@Table(name = "line_items")
public class LineItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "item_id", nullable = false)
	private Item item;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "checkout_id", nullable = false)
	private Checkout checkout;

	// these dates only track year, month, and day
	private LocalDate due;

	private LocalDate returned;

}
