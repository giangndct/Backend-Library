package com.example.thuvien.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.thuvien.entity.Book;

import jakarta.transaction.Transactional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
	@Modifying
    @Transactional
    @Query("UPDATE Book bk SET bk.imgBook = :path WHERE bk.id = :id")
    void uploadImg(Long id, String path);
	
	Optional<Book> findByTitleAndAuthor(String title, String author);
	Boolean existsByTitleAndAuthor(String title, String author);
	

}