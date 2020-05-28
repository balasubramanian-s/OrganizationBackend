package com.revature.organization.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.revature.organization.model.MyUserDetails;
import com.revature.organization.model.User;
import com.revature.organization.dao.UserRepository;
import com.revature.organization.exception.DBException;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		Optional<User> user = userRepository.findByUserName(userName);
		if (user == null) {
			throw new UsernameNotFoundException("Not Found" + userName);
		}

		return user.map(MyUserDetails::new).get();
	}

	public void SaveUser(User user) {
		userRepository.save(user);

	}

	public void UpdateUser(User user) {
		userRepository.save(user);
	}

	public void changeUserPassword(User user, String password) {
		user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
		userRepository.save(user);
	}

	public String getEncryptedPassword(String name) throws DBException {
		return userRepository.getEncryptedPassword(name);
	}
}
