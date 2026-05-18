package com.prasanna.library.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prasanna.library.management.exception.ResourceNotFoundException;
import com.prasanna.library.management.model.Book;
import com.prasanna.library.management.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/books")
	public ResponseEntity<List<Book>> getAllBooks(){
		
		List<Book> books=userService.getAllBooks();
		
		if(books.isEmpty())
			throw new ResourceNotFoundException("Book not found!");
		
		return ResponseEntity.ok(books);
	}
	
	@GetMapping("/book/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable Long id){
		return ResponseEntity.ok(userService.getBookById(id));
	}
	
	@PostMapping("/issuebook/{id}")
	public ResponseEntity<Book> issueBook(@PathVariable Long id){
		return ResponseEntity.ok(userService.issueBook(id));
	}
	
	@PutMapping("/returnbook/{id}")
	public ResponseEntity<Book> returnBook(@PathVariable Long id){
		return ResponseEntity.ok(userService.returnBook(id));
	}
	
	@GetMapping("/searchbook")
	public ResponseEntity<List<Book>> searchBook(@RequestParam String keyword){
		
		List<Book> book=userService.searchBook(keyword);
		
		if(book.isEmpty())
			throw new ResourceNotFoundException("Book not found!");
		
		return ResponseEntity.ok(book);
	}
}
