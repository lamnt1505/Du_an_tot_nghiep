package edu.poly.Du_An_Tot_Ngiep.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.poly.Du_An_Tot_Ngiep.Entity.Customer;
import edu.poly.Du_An_Tot_Ngiep.Entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
	
	@Query(value = "SELECT * FROM users  WHERE phone = ?", nativeQuery = true)
	Optional<User> findByPhone(String phone);
	

//	@Query(value = "SELECT * FROM users  WHERE fullname = ?", nativeQuery = true)
//	Optional<User> findByName(String name);
//	
//	@Query(value ="select * from users", nativeQuery = true)
//	List<User> listUser();
//	
//	@Query(value = "select * from users where user_id = ?", nativeQuery = true)
//	public User findByIdUser(int id);

}
