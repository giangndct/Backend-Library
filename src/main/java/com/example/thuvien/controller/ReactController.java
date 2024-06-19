package com.example.thuvien.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.thuvien.Dto.ReactDto;

import com.example.thuvien.dao.ReactRepository;

import com.example.thuvien.entity.React;
import com.example.thuvien.service.ReactService;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class ReactController {
	
	@Autowired
	ReactRepository reactRepository;
	
	@Autowired
	private ReactService reactService;
	
		// http://localhost:8080/api/book/{id}/reacts
	    @GetMapping("/book/{bookId}/reacts")
	    public ResponseEntity<?> getAllReactOfBook(@PathVariable(value = "bookId") Long bookId) {
	        List<ReactDto> reactDtos = reactService.getAllReactOfBook(bookId);
	        return new ResponseEntity<>(reactDtos, HttpStatus.OK);
	    }
	    
	    @GetMapping("/book/{bookId}/reacts/{userId}")
	    public ResponseEntity<?> getReactByUserAndBook(@PathVariable(value = "bookId") Long bookId,@PathVariable(value = "userId") Long userId ){
	    	List<React> reacts = reactRepository.findReactByUserIdAndBookId(userId, bookId);
	    	return new ResponseEntity<>(reacts, HttpStatus.OK);
	    }

	    // http://localhost:8080/api/book/{id}/react
	    @CrossOrigin
	    @PostMapping("/book/{bookId}/react")
	    public ResponseEntity<?> createReact(@PathVariable(value = "bookId") Long bookId, @RequestBody ReactDto reactDto) {
	        reactService.createReactOfBook(reactDto.getUserId(), bookId, reactDto.getVoted(), reactDto.getMessage());
	        return ResponseEntity.ok(HttpStatus.OK);

	    }
	    

	    // http://localhost:8080/api/book/{id}/react/{id}
	    @DeleteMapping("/book/{bookId}/react/{reactId}")
	    public ResponseEntity<?> deteleReactOfBook(
	           @PathVariable(value = "bookId") Long bookId,
	           @PathVariable(value = "reactId") Long reactId, @RequestParam(value="userId", required = true) String userId) {
	        React react = reactRepository.findById(reactId).get();
	        long userID = react.getUser().getId();
	        if(userID == Long.parseLong(userId)) {
	            reactRepository.delete(react);
	            return ResponseEntity.ok(HttpStatus.OK);
	        }
	        return new ResponseEntity<>(userID, HttpStatus.BAD_REQUEST);

	    }
	    
}