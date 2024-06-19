package com.example.thuvien.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.thuvien.dao.UserRepository;
import com.example.thuvien.entity.User;

@Service
public class UserService {
	@Autowired
	private UserRepository repo;
	
	public boolean checkLogin(String username, String password) {
		User user = repo.findByUsername(username);
		if(user != null && user.getPassword().equals(password)) {
			return true;
		}
		return false;
	}

}
