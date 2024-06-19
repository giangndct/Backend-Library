package com.example.thuvien.entity;

import org.springframework.web.multipart.MultipartFile;




public class BookResp {

	private Long id;
	
	private String title;
	
	private String author;
	

	private String releaseDate;
	

	private Integer length;
	

	private MultipartFile imgBook;
	

	private String genre;
	

	private String description;
	
	public BookResp() {

	}
	
	public BookResp(String title, String author, String releaseDate, Integer length, MultipartFile imgBook,
			String genre, String description) {
		super();
		this.title = title;
		this.author = author;
		this.releaseDate = releaseDate;
		this.length = length;
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
	public MultipartFile getImgBook() {
		return imgBook;
	}
	public void setImgBook(MultipartFile imgBook) {
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
	
	
	

}