package edu.poly.Du_An_Tot_Ngiep.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import edu.poly.Du_An_Tot_Ngiep.Entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

	@Query(value = "SELECT * FROM customers  WHERE phone = ?", nativeQuery = true)
	Optional<Customer> findByPhoneCus(String phone);
}
