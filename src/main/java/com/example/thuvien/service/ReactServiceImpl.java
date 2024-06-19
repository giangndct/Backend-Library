package com.example.thuvien.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.thuvien.Dto.ReactDto;
import com.example.thuvien.dao.BookRepository;
import com.example.thuvien.dao.ReactRepository;
import com.example.thuvien.dao.UserRepository;
import com.example.thuvien.entity.Book;
import com.example.thuvien.entity.React;

@Service
public class ReactServiceImpl implements ReactService{

	@Autowired
    ModelMapper modelMapper;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReactRepository reactRepository;
    
    @Override
    public List<ReactDto> getAllReactOfBook(Long bookId) {
        Book book = bookRepository.findById(bookId).get();
        List<ReactDto> reactDtos = book.getReacts().stream()
                .map(react -> {
                    ReactDto reactDto = modelMapper.map(react, ReactDto.class);
                    reactDto.setBookId(react.getBook().getId());
                    reactDto.setUserId(react.getUser().getId());
                    reactDto.setUsername(react.getUser().getName());
                    return reactDto;
                }).collect(Collectors.toList());
        Collections.sort(reactDtos, Comparator.comparing(ReactDto::getTime).reversed());
        Collections.sort(reactDtos, Comparator.comparing(ReactDto::getDate).reversed());
        return reactDtos;
    }

	@Override
	public void createReactOfBook(Long userId, Long bookId, Integer voted, String message) {
        React react = new React();
        react.setVoted(voted);
        react.setMessage(message);
        react.setDate(LocalDate.now());
        LocalTime tm = LocalTime.now();
        react.setTime(LocalTime.parse(String.format("%02d:%02d:%02d", tm.getHour(), tm.getMinute(), tm.getSecond())));
        react.setUser(userRepository.findById(userId).get());
        react.setBook(bookRepository.findById(bookId).get());
        reactRepository.save(react);
    }
    

}