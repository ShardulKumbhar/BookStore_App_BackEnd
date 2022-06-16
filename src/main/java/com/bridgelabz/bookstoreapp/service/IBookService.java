package com.bridgelabz.bookstoreapp.service;


import com.bridgelabz.bookstoreapp.dto.BookDTO;

import com.bridgelabz.bookstoreapp.entity.BookDetailsModel;

import org.springframework.stereotype.Service;


import java.util.List;

@Service
public interface IBookService {

    BookDetailsModel addBook(BookDTO bookDTO, String token);

    List<BookDetailsModel> getAllBooks();

    int getCountOfBooks();

    List<BookDetailsModel> getBooksWithIncreasingOrderOfTheirPrice();

    List<BookDetailsModel> getBooksWithDecreasingOrderOfTheirPrice();

    List<BookDetailsModel> getBooksWithPublishingYear();

    List<BookDetailsModel> getBooksByNewLaunch();

    BookDetailsModel getBookById(Long id);
}
