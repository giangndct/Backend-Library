package com.example.thuvien.dao;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.thuvien.entity.React;

@Repository
public interface ReactRepository extends JpaRepository<React, Long>{
	
	List<React> findReactByBookId(Long bookId);

	List<React> findReactByUserIdAndBookId(Long userId, Long bookId);
	
	
}