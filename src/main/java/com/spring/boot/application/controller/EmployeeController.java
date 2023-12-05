package com.spring.boot.application.controller;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.boot.application.exceptions.ErrorObject;
import com.spring.boot.application.exceptions.StringToJsonParsingException;
import com.spring.boot.application.model.UserDetails;
import com.spring.boot.application.service.UserService;

@Controller
public class EmployeeController {

	@Autowired
	private UserService userService;

	@GetMapping("/login")
	public String showLoginView() {
		return "login";
	}

	@GetMapping("/signup")
	public String showSignUpView() {
		return "signup";
	}

	@GetMapping("/home")
	public String showHomeView() {
		return "home";
	}

	@PostMapping("/register")
	@ResponseBody
	public ResponseEntity<JsonNode> registerUser(@RequestBody UserDetails userDetails) {
		String stringResponse;
		ObjectMapper mapper = new ObjectMapper();
		JsonNode json;
		boolean isUserSaved = userService.saveNewUser(userDetails);
		try {
			if (isUserSaved) {
				stringResponse = "{\"message\":\"Registration is successful\",\"firstname\":\""
						+ userDetails.getFirstname() + "\",\"lastname\":\"" + userDetails.getLastname() + "\"}";
				json = mapper.readTree(stringResponse);
				return ResponseEntity.status(HttpStatus.CREATED).body(json);
			} else {
				ErrorObject errorObject = new ErrorObject();
				errorObject.setMessage("Registration is not successful");
				errorObject.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				errorObject.setTimestamp(timestamp.toString());
				JsonNode node = mapper.valueToTree(errorObject);
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(node);
			}

		} catch (JsonProcessingException ex) {
			if (isUserSaved) {
				stringResponse = "Registration is successful," + userDetails.getFirstname() + ","
						+ userDetails.getLastname();
			} else {
				stringResponse = "Registration is not successful";
			}
			throw new StringToJsonParsingException(stringResponse);
		}
	}

	@PostMapping("/validate-user")
	@ResponseBody
	public ResponseEntity<JsonNode> loginUser(@RequestBody UserDetails userDetails) {
		String stringResponse = null;
		ObjectMapper mapper = new ObjectMapper();
		JsonNode json;
		boolean isValidUser = userService.validateUser(userDetails);
		if (isValidUser) {
			stringResponse = "{\"message\":\"Valid user\"}";
		} else {
			stringResponse = "{\"message\":\"Invalid user\"}";
		}
		try {
			json = mapper.readTree(stringResponse);
			return ResponseEntity.status(HttpStatus.OK).body(json);
		} catch (JsonProcessingException e) {
			if (isValidUser) {
				stringResponse = "Valid user";
			} else {
				stringResponse = "Invalid user";
			}
			throw new StringToJsonParsingException(stringResponse);
		}
	}
}
