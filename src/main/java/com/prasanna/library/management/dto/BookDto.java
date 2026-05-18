package com.prasanna.library.management.dto;

import lombok.Data;

@Data
public class BookDto {

	private String title;
	private String author;
	private Long quantity;
	private boolean isAvailable;
	
}
