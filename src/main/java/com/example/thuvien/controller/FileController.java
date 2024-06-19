package com.example.thuvien.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@CrossOrigin
@RestController
@RequestMapping("api/file/{path}")
public class FileController {
	
	private final Path root = Paths.get("Pics");

	
	@GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getImage(@PathVariable String path) throws IOException{
		Resource imgFile = load(path);
		
		byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
	}
	

  

    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);

            Resource resource = new UrlResource(file.toUri());

            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new RuntimeException("coundn't not read the file!");
            }
        }
        catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

}