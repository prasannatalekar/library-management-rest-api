package com.prasanna.library.management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prasanna.library.management.dto.BookDto;
import com.prasanna.library.management.exception.ResourceNotFoundException;
import com.prasanna.library.management.model.Book;
import com.prasanna.library.management.model.IssueRecord;
import com.prasanna.library.management.repository.BookRepo;
import com.prasanna.library.management.repository.IssueRecordsRepo;

@Service
public class AdminService {

	@Autowired
	private BookRepo bookRepo;
	
	@Autowired
	private IssueRecordsRepo issueRecordsRepo;
	
	public List<Book> getAllBooks(){
		return bookRepo.findAll();
	}
	
	public Book getBookById(Long bookId) {
		return bookRepo.findById(bookId).orElseThrow(()->new ResourceNotFoundException("Book not found!"));
	}
	
	public List<Book> searchBook(String keyword){
		return bookRepo.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(keyword, keyword);
	}
	
	public Book addBook(BookDto bookDto) {
		Book book=new Book();
		book.setTitle(bookDto.getTitle());
		book.setAuthor(bookDto.getAuthor());
		book.setQuantity(bookDto.getQuantity());
		book.setIssued(false);
		book.setAvailable(bookDto.isAvailable());
		
		return bookRepo.save(book);
	}
	
	public Book updateBook(Long bookId,BookDto bookDto) {
		Book book=bookRepo.findById(bookId).orElseThrow(()->new ResourceNotFoundException("Book not found!"));
		
		book.setTitle(bookDto.getTitle());
		book.setAuthor(bookDto.getAuthor());
		book.setQuantity(bookDto.getQuantity());
		book.setAvailable(bookDto.isAvailable());
		
		return bookRepo.save(book);
	}
	
	public String deleteBook(Long bookId) {
		Book book=bookRepo.findById(bookId).orElseThrow(()->new ResourceNotFoundException("Book not found!"));
		book.setAvailable(false);
		bookRepo.save(book);
		
		return "Book has been deleted";
	}
	
	public List<IssueRecord> getIssueRecords(){
		return issueRecordsRepo.findAll();
	}
	
}
