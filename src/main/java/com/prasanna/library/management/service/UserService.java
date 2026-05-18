package com.prasanna.library.management.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.prasanna.library.management.exception.BadRequestException;
import com.prasanna.library.management.exception.ResourceNotFoundException;
import com.prasanna.library.management.model.Book;
import com.prasanna.library.management.model.IssueRecord;
import com.prasanna.library.management.model.User;
import com.prasanna.library.management.repository.BookRepo;
import com.prasanna.library.management.repository.IssueRecordsRepo;
import com.prasanna.library.management.repository.UserRepo;

@Service
public class UserService {

	@Autowired
	private BookRepo bookRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private IssueRecordsRepo issueRecordsRepo;
	
	public List<Book> getAllBooks(){		
		return bookRepo.findByIsAvailableTrue();
	}
	
	public Book getBookById(Long bookId) {
		Book book=bookRepo.findById(bookId).orElseThrow(()->new ResourceNotFoundException("Book not found"));
		
		if(!book.isAvailable())
			throw new BadRequestException("Book not available!");
		
		return book;
	}
	
	public Book issueBook(Long bookId) {
		Book book=bookRepo.findById(bookId).orElseThrow(()->new ResourceNotFoundException("Book not found!"));
		
		if(book.isIssued() || !book.isAvailable() || book.getQuantity() <= 0)
			throw new BadRequestException("Book not available!");
		
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
		
		User user=userRepo.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("User not found!"));
		
		if(issueRecordsRepo.existsByUserAndBookAndReturnDateIsNull(user, book))
			throw new BadRequestException("You already issued this book!");
		
		IssueRecord issueRecord=new IssueRecord();
		
		issueRecord.setUser(user);
		issueRecord.setBook(book);
		issueRecord.setIssuedDate(LocalDate.now());
		
		issueRecordsRepo.save(issueRecord);
		
		book.setQuantity(book.getQuantity()-1);
		if (book.getQuantity()<=0) 
			book.setIssued(true);		
		
		return bookRepo.save(book);	
	}
	
	public Book returnBook(Long bookId) {
		Book book=bookRepo.findById(bookId).orElseThrow(()->new ResourceNotFoundException("Book not found!"));
		
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
		
		User user=userRepo.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("User not found!"));
		
		IssueRecord issueRecord=issueRecordsRepo.findByUserAndBookAndReturnDateIsNull(user,book).orElseThrow(()->new ResourceNotFoundException("No issued record found!"));
		issueRecord.setReturnDate(LocalDate.now());
		issueRecordsRepo.save(issueRecord);
		
		book.setQuantity(book.getQuantity()+1);
		book.setIssued(false);
		
		return bookRepo.save(book);
	}
	
	public List<Book> searchBook(String keyword){
		return bookRepo.searchBook(keyword);
	}
	
}
