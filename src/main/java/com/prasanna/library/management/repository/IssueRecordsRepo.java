package com.prasanna.library.management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prasanna.library.management.model.Book;
import com.prasanna.library.management.model.IssueRecord;
import com.prasanna.library.management.model.User;

@Repository
public interface IssueRecordsRepo extends JpaRepository<IssueRecord, Long>{
	boolean existsByUserAndBookAndReturnDateIsNull(User user,Book book);
	
	Optional<IssueRecord> findByUserAndBookAndReturnDateIsNull(User user,Book book);
}
