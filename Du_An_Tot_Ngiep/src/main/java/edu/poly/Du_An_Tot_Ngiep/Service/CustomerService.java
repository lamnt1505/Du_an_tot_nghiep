package edu.poly.Du_An_Tot_Ngiep.Service;

import java.util.Optional;

import edu.poly.Du_An_Tot_Ngiep.Entity.Customer;

public interface CustomerService {

	void deleteById(Integer id);

	Iterable<Customer> findAll();

	Optional<Customer> findById(Integer id);

	<S extends Customer> S save(S entity);

	Optional<Customer> findByPhoneCus(String phone);

}
