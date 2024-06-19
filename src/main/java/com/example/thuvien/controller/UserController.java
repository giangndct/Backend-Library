package com.example.thuvien.controller;

import java.io.IOException;
import java.security.PublicKey;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.thuvien.dao.BookRepository;
import com.example.thuvien.dao.UserRepository;
import com.example.thuvien.entity.User;
import com.example.thuvien.payload.request.LoginRequest;
import com.example.thuvien.payload.request.SignupRequest;
import com.example.thuvien.service.UserService;


@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class UserController {
	@Autowired
	UserRepository userRepository;
	
	@Autowired 
	UserService service;
	
	@PostMapping("/signup")
	public ResponseEntity<?> create(@RequestBody SignupRequest user){
		if(user.getUsername().equals("") || user.getName().equals("") || user.getPassword().equals("")
				|| user.getEmail().equals("")) {
			return new ResponseEntity<>("Not blank", HttpStatus.UNAUTHORIZED);
		}else if(user.getUsername().length() < 5 || user.getUsername().length() > 30){
			return new ResponseEntity<>("Username more than 5 characters and less 30 characters", HttpStatus.UNAUTHORIZED);
		}else  if(!user.getEmail().contains("@gmail.com")) {
			return new ResponseEntity<>("Email not format", HttpStatus.UNAUTHORIZED);
		}else if(userRepository.existsByUsername(user.getUsername())) {
			return new ResponseEntity<>("Username exist", HttpStatus.UNAUTHORIZED);
			
		}else if(userRepository.existsByEmail(user.getEmail())){
			return new ResponseEntity<>("Email exist", HttpStatus.UNAUTHORIZED);
		}else {
			User res = new User(user.getUsername(), user.getName(), user.getEmail(), user.getPassword());
			if(user.getRole() == null) {
				res.setRole("user");
			}else {
				if(user.getRole().equals("admin")) {
					res.setRole("admin");
				}else {
					res.setRole("user");
				}
			}
			userRepository.save(res);
			return new ResponseEntity<>("Ok", HttpStatus.CREATED);
		}
		
		
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> login(@RequestBody LoginRequest user){
		if(user.getUsername().equals("") || user.getPassword().equals("")) {
			return new ResponseEntity<>("Not found", HttpStatus.UNAUTHORIZED);
		}else {
			if(service.checkLogin(user.getUsername(), user.getPassword())){
				User res = userRepository.findByUsername(user.getUsername());
				return new ResponseEntity<>(res, HttpStatus.OK);
			}else {
				return new ResponseEntity<>("Wrong", HttpStatus.UNAUTHORIZED);
			}
		}
		
	}
	
	@GetMapping("/user/{username}")
	public ResponseEntity<?> getUser(@PathVariable String username){
		User user = userRepository.findByUsername(username);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	

}