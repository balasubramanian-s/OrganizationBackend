package com.revature.organization.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import com.revature.organization.exception.DBException;
import com.revature.organization.model.User;



@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	Optional<User> findByUserName(String userName);
	
	
	@Query(value = "select password from user where username=?;", nativeQuery = true)
	String getEncryptedPassword(String name)throws DBException;
}
