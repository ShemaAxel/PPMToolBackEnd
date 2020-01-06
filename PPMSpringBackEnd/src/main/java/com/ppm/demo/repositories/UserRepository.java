package com.ppm.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ppm.demo.domain.User;


@Repository
public interface UserRepository  extends CrudRepository<User, Long>{

	User findByUsername(String username);
	User getById(long id);
}