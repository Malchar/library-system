package org.paulschmitz.librarysystem.database.entity;

import lombok.Data;
import javax.persistence.*;

/**
 * This entity represents a particular book but with abstract instantiation.
 * Books may be realized by the item entity. As such, this entity acts as a
 * "details holder" for the associated item entities.
 * 
 * @author p_schmitz
 *
 */
@Data
@Entity
@Table(name = "books")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String title;

	private String author;

	private String description;

	// https://en.wikipedia.org/wiki/International_Standard_Book_Number
	private String isbn;

	@Column(name = "image_url")
	private String imageUrl;

	private String category;

}
