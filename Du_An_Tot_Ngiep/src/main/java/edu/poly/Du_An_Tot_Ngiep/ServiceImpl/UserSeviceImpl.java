package edu.poly.Du_An_Tot_Ngiep.ServiceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.poly.Du_An_Tot_Ngiep.Entity.User;
import edu.poly.Du_An_Tot_Ngiep.Repository.UserRepository;
import edu.poly.Du_An_Tot_Ngiep.Service.UserService;

@Service
public class UserSeviceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public Optional<User> findByPhone(String phone) {
		return userRepository.findByPhone(phone);
	}

	@Override
	public <S extends User> S save(S entity) {
		return userRepository.save(entity);
	}

	@Override
	public Optional<User> findById(Integer id) {
		return userRepository.findById(id);
	}

	@Override
	public Iterable<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public void deleteById(Integer id) {
		userRepository.deleteById(id);
	}

	public void deleteAll() {
		userRepository.deleteAll();
	}

}
