package com.ppm.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ppm.demo.domain.User;
import com.ppm.demo.exceptions.UsernameException;
import com.ppm.demo.exceptions.UserNotFoundException;
import com.ppm.demo.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public User saveUserOrUpdate(User newUser) {
		try {

			if (newUser.getId() != null) {
				newUser.setId(newUser.getId());
			}

			newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
			newUser.setUsername(newUser.getUsername());
			newUser.setConfirmPassword("");
//			newUser.setCreated_At(newUser.getCreated_At());

			return userRepository.save(newUser);

		} catch (Exception e) {
			// TODO: handle exception
			throw new UsernameException("Username '" + newUser.getUsername() + "',Already exist. ");
		}

	}

	public List<User> findAll() {
		List<User> users = (List<User>) userRepository.findAll();
		return users;
	}

	public void deleteUser(Long id) {
		User user = userRepository.getById(id);
		if (user == null) {
			throw new UserNotFoundException("User with: '" + id + "',Doesn't exist. ");

		}
		userRepository.delete(user);
	}

	public User findUserByUsername(String username) {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UserNotFoundException("User '" + username + "' ,Not found.");
		}
		return user;
	}

}