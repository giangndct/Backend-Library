package com.example.thuvien.controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.thuvien.dao.BookRepository;
import com.example.thuvien.entity.Book;
import com.example.thuvien.entity.BookResp;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class BookController {
	@Autowired
	private BookRepository bookRepo;
	
	private final Path root = Paths.get("Pics");
	
	@GetMapping("/books")
	public ResponseEntity<?> getAllBooks(){
		List<Book> books = bookRepo.findAll();
		return new ResponseEntity<>(books, HttpStatus.OK);
	}
	
	@GetMapping("/book/{id}")
	public ResponseEntity<?> getOneBook(@PathVariable String id){
		try {
			Book book = bookRepo.findById(Long.parseLong(id)).get();
			return new ResponseEntity<>(book, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>("Not found book with id = "+ id, HttpStatus.BAD_REQUEST);
		}	
	}
	
	 @PostMapping("/book")
	    public ResponseEntity<?> createBook(@RequestParam(value = "title", required = false) String title, @RequestParam(value = "file", required = false) MultipartFile file,
	    		@RequestParam(value = "author", required = false) String author, @RequestParam(value = "releaseDate", required = false) String releaseDate,
	    		@RequestParam(value = "description", required = false) String description, @RequestParam(value = "length", required = false) String length,
	    		@RequestParam(value = "genre", required = false) String genre) {
		 	if(title.equals("") || author.equals("") || releaseDate.equals("") ||
		 			length.equals("") || description.equals("") || genre.equals("")) {
		 		return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
		 	}else if(bookRepo.existsByTitleAndAuthor(title,author)) {
		 		return new ResponseEntity<>("Book exist", HttpStatus.NOT_ACCEPTABLE);
		 	}
		 	else{
		 		if(!file.isEmpty()) {
		 			String img = "";
		 			save(file);
			 		img += "http://localhost:8080/api/file/";
			 		img += file.getOriginalFilename();
			 		bookRepo.save(new Book(title, author,
		                releaseDate, length.equals("")?0:Integer.valueOf(length), 0, img, genre, description));
		 		}else {
		 			bookRepo.save(new Book(title, author,
			                releaseDate, Integer.valueOf(length), 0, "", genre, description));
		 		}

		 		return new ResponseEntity<>("OK", HttpStatus.OK);
		 	}
	 	}
	
	@PutMapping("/book/{id}")
	public ResponseEntity<?> fixBook(@PathVariable(value = "id") Long id, @RequestBody Book book) {
			if(book.getTitle().equals("") || book.getAuthor().equals("")|| book.getDescription().equals("")|| 
					book.getReleaseDate().equals("") || book.getLength().equals("") || book.getGenre().equals("")) {
				return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
		 	}
			else {
	        	Book bk = bookRepo.findById(id).get();
	        	if(!bk.getTitle().equalsIgnoreCase(book.getTitle()) || !bk.getAuthor().equalsIgnoreCase(book.getAuthor())) {
	        		if(bookRepo.existsByTitleAndAuthor(book.getTitle(),book.getAuthor())) {
	    		 		return new ResponseEntity<>("Book exist", HttpStatus.NOT_ACCEPTABLE);
	        		}
	        	}
	            bk.setTitle(book.getTitle());
	            bk.setAuthor(book.getAuthor());
	            bk.setReleaseDate(book.getReleaseDate());
	            bk.setLength(book.getLength());
	            bk.setSold(bk.getSold());
	            bk.setGenre(book.getGenre());
	            bk.setDescription(book.getDescription());
	            bookRepo.save(bk);
	
	            return new ResponseEntity<>("OK", HttpStatus.OK);
			}
        
    }
	
	@PostMapping("/book/sold/{id}")
	public ResponseEntity<?> buyBook( @PathVariable(value = "id") Long id, @RequestBody Book book) {
        Book bk = bookRepo.findById(id).get();
            

        bk.setSold(bk.getSold() + book.getSold());
        bookRepo.save(bk);

        return new ResponseEntity<>(bk, HttpStatus.OK);
    }
	
	@DeleteMapping("/book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable(value = "id") Long id) {
        try {
        	Book book = bookRepo.findById(id).get();

            bookRepo.deleteById(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e) {
			return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
		}
    }
	
	@PostMapping("/book/{id}/bookImg")
    public ResponseEntity<?> postImgBook(@PathVariable(value = "id") Long id, @RequestParam(value = "file", required = false) MultipartFile file ) {
	    if(file != null) {
	    	String img = "";
	        save(file);
	        img += "http://localhost:8080/api/file/";
	 		img += file.getOriginalFilename();
			bookRepo.uploadImg(id, img);
	        return ResponseEntity.ok("Change picture success");
	    }
	    return ResponseEntity.ok("Not change");
    }
	
	public void save(MultipartFile file) {
        try {
            if(!this.root.resolve(file.getOriginalFilename()).toFile().exists()) {
                InputStream in = file.getInputStream();
                Files.copy(in, this.root.resolve(file.getOriginalFilename()));
                in.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}