package com.example.thuvien.service;


import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.thuvien.Dto.ReactDto;
import com.example.thuvien.dao.ReactRepository;

public interface ReactService{

    List<ReactDto> getAllReactOfBook(Long bookId);

    void createReactOfBook(Long userId, Long bookId, Integer voted, String message);
}