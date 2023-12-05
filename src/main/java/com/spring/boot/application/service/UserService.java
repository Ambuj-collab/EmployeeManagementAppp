package com.spring.boot.application.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.application.exceptions.UserAlreadyRegisteredException;
import com.spring.boot.application.exceptions.UserNotFoundException;
import com.spring.boot.application.model.UserDetails;
import com.spring.boot.application.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public boolean saveNewUser(UserDetails userDetails) {

		Optional<UserDetails> userDetailsById = userRepository.findById(userDetails.getEmail());
		if (userDetailsById.isPresent()) {
			throw new UserAlreadyRegisteredException("Already we an account with this email address");
		} else {
			UserDetails savedUserDetails = userRepository.save(userDetails);
			if (savedUserDetails == null) {
				return false;
			} else if (!savedUserDetails.getFirstname().equals(userDetails.getFirstname())) {
				return false;
			} else if (!savedUserDetails.getLastname().equals(userDetails.getLastname())) {
				return false;
			} else if (!savedUserDetails.getEmail().equals(userDetails.getEmail())) {
				return false;
			} else if (!savedUserDetails.getPassword().equals(userDetails.getPassword())) {
				return false;
			}
		}
		return true;
	}

	public boolean validateUser(UserDetails userDetails) {
		Optional<UserDetails> userDetailsById = userRepository.findById(userDetails.getEmail());
		if (userDetailsById.isEmpty()) {
			throw new UserNotFoundException("This account doesn't exist");
		} else {
			if(!userDetailsById.get().getPassword().equals(userDetails.getPassword())) {
				return false;
			}
		}
		return true;
	}

}
