package com.example.thuvien.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name ="react")
public class React {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "voted")
	private Integer voted;
	
	@Column(name = "message")
	@Lob
	private String message;
	
	@Column(name = "date")
	private LocalDate date;
	
	@Column(name ="time")
	private LocalTime time;
	
	@ManyToOne
	@JoinColumn(name="book_id", nullable = false)
	@JsonIgnore
	private Book book;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;

	public React() {
		
	}
	 public React(Integer voted, String message, LocalDate date, LocalTime time) {
	        this.voted = voted;
	        this.message = message;
	        this.date = date;
	        this.time = time;
	 }
	 	public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public Integer getVoted() {
	        return voted;
	    }

	    public void setVoted(Integer voted) {
	        this.voted = voted;
	    }

	    public String getMessage() {
	        return message;
	    }

	    public void setMessage(String message) {
	        this.message = message;
	    }

	    public LocalDate getDate() {
	        return date;
	    }

	    public void setDate(LocalDate date) {
	        this.date = date;
	    }

	    public LocalTime getTime() {
	        return time;
	    }

	    public void setTime(LocalTime time) {
	        this.time = time;
	    }

	    public Book getBook() {
	        return book;
	    }

	    public void setBook(Book book) {
	        this.book = book;
	    }

	    public User getUser() {
	        return user;
	    }

	    public void setUser(User user) {
	        this.user = user;
	    }

}