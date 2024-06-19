package com.example.thuvien.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.thuvien.entity.ERole;
import com.example.thuvien.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	
}