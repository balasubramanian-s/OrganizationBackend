package com.revature.organization.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.revature.organization.model.User;




public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUserName(String userName);
}
