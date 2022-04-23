package org.paulschmitz.librarysystem.database.dao;

import java.util.List;

import org.paulschmitz.librarysystem.database.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookDAO extends JpaRepository<Book, Integer> {
	
	public Book findBookById(@Param("id") Integer id);

	public Book findBookByTitle(@Param("title") String string);
	
	// Returns all books with title partially matching the input term.
	public List<Book> findBooksByTitleContainingIgnoreCase(@Param("searchInput") String searchInput);

	public List<Book> findBooksByAuthorContainingIgnoreCase(@Param("searchInput") String searchInput);
	
	public List<Book> findBooksByCategoryContainingIgnoreCase(@Param("searchInput") String searchInput);
	
	public List<Book> findBooksByTitleContaining(@Param("searchInput") String searchInput);
	
	public List<Book> findBooksByAuthorContaining(@Param("searchInput") String searchInput);
	
	public List<Book> findBooksByCategoryContaining(@Param("searchInput") String searchInput);
	

}
