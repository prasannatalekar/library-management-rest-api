package com.prasanna.library.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prasanna.library.management.dto.BookDto;
import com.prasanna.library.management.exception.ResourceNotFoundException;
import com.prasanna.library.management.model.Book;
import com.prasanna.library.management.model.IssueRecord;
import com.prasanna.library.management.service.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@GetMapping("/books")
	public ResponseEntity<List<Book>> getAllBooks(){
		List<Book> books=adminService.getAllBooks();
		
		if (books.isEmpty()) 
			throw new ResourceNotFoundException("Book not found!");
		
		return ResponseEntity.ok(books);
	}
	
	@GetMapping("/book/{bookId}")
	public ResponseEntity<Book> getBookById(@PathVariable Long bookId){
		return ResponseEntity.ok(adminService.getBookById(bookId));
	}
	
	@GetMapping("/searchbook")
	public ResponseEntity<List<Book>> searchBook(@RequestParam String keyword){
		
		List<Book> book=adminService.searchBook(keyword);
		
		if(book.isEmpty())
			throw new ResourceNotFoundException("Book not found : "+keyword);
		
		return ResponseEntity.ok(book);
	}
	
	@PostMapping("/addbook")
	public ResponseEntity<Book> addBook(@RequestBody BookDto bookDto){
		return ResponseEntity.ok(adminService.addBook(bookDto));
	}
	
	@PutMapping("/updatebook/{bookId}")
	public ResponseEntity<Book> updateBook(@PathVariable Long bookId ,@RequestBody BookDto bookDto){
		return ResponseEntity.ok(adminService.updateBook(bookId,bookDto));
	}
	
	@DeleteMapping("/deletebook/{bookId}")
	public ResponseEntity<String> deleteBook(@PathVariable Long bookId){
		return ResponseEntity.ok(adminService.deleteBook(bookId));
	}
	
	@GetMapping("/issuerecords")
	public ResponseEntity<List<IssueRecord>> getIssueRecords(){
		
		List<IssueRecord> issueRecords=adminService.getIssueRecords();
		
		if(issueRecords.isEmpty())
			throw new ResourceNotFoundException("No issue records found!");
		
		return ResponseEntity.ok(issueRecords);
	}
	
}
