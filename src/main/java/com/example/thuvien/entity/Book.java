package com.example.thuvien.entity;


import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;



@Entity
@Table(name = "book")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="author")
	private String author;
	
	@Column(name="release_date")
	private String releaseDate;
	
	@Column(name="length")
	private Integer length;
	
	@Column(name="sold")
	private int sold;
	
	@Column(name= "img")
	private String imgBook;
	
	@Column(name="genre")
	private String genre;
	
	@Column(name= "description")
	private String description;
	
	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<React> reacts = new HashSet<>();
	
	public Book() {
		// TODO Auto-generated constructor stub
	}

	public Book(String title, String author, String releaseDate, Integer length, int sold, String imgBook, String genre,
			String description) {
		super();
		this.title = title;
		this.author = author;
		this.releaseDate = releaseDate;
		this.length = length;
		this.sold = sold;
		this.imgBook = imgBook;
		this.genre = genre;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public int getSold() {
		return sold;
	}

	public void setSold(int sold) {
		this.sold = sold;
	}

	public String getImgBook() {
		return imgBook;
	}

	public void setImgBook(String imgBook) {
		this.imgBook = imgBook;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@JsonIgnore
    public Set<React> getReacts() {
        return reacts;
    }

    public void setReacts(Set<React> reacts) {
        this.reacts = reacts;
    }
	
	
	
}