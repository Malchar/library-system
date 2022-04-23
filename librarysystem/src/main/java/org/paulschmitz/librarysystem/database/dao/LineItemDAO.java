package org.paulschmitz.librarysystem.database.dao;

import java.util.List;

import org.paulschmitz.librarysystem.database.entity.Checkout;
import org.paulschmitz.librarysystem.database.entity.LineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LineItemDAO extends JpaRepository<LineItem, Integer> {
		
	public LineItem findLineItemById(@Param("id") Integer id);
	
	// Returns all line items in a particular checkout/order
	public List<LineItem> findLineItemsByCheckout(Checkout cart);
	
	// Native query returns true iff this checkout contains any outstanding line items
	// Note that the data type is Integer, so 0 = false, 1 = true.
	@Query(value = "select count(*) >= 1 from line_items where checkout_id = :checkout and returned is null", nativeQuery = true)
	public Integer queryOutstanding(@Param("checkout") Checkout checkout);

}
