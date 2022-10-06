package edu.poly.Du_An_Tot_Ngiep.Service;

import java.util.Optional;

import edu.poly.Du_An_Tot_Ngiep.Entity.User;

public interface UserService {

	void deleteById(Integer id);

	Iterable<User> findAll();

	Optional<User> findById(Integer id);

	<S extends User> S save(S entity);

	Optional<User> findByPhone(String phone);

}
