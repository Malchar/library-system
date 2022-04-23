package org.paulschmitz.librarysystem.database.dao;

import org.paulschmitz.librarysystem.database.entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckoutDAO extends JpaRepository<Checkout, Integer> {
	
	public Checkout findCheckoutById(Integer id);

}
