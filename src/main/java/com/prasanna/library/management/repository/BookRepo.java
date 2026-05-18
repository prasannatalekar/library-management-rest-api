package com.prasanna.library.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.prasanna.library.management.model.Book;

@Repository
public interface BookRepo extends JpaRepository<Book, Long>{
	
	List<Book> findByIsAvailableTrue();
	
	List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title,String author);
	
	@Query("SELECT b FROM Book b WHERE b.isAvailable = true AND "
			+ "(LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword ,'%')) OR "
			+ "LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword ,'%')))")
	List<Book> searchBook(@Param("keyword") String keyword);
}
