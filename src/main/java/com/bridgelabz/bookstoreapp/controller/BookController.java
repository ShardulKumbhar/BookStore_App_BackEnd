package com.bridgelabz.bookstoreapp.controller;

import com.bridgelabz.bookstoreapp.dto.BookDTO;
import com.bridgelabz.bookstoreapp.dto.ResponseDTO;
import com.bridgelabz.bookstoreapp.entity.BookDetailsModel;
import com.bridgelabz.bookstoreapp.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private IBookService bookService;

    @GetMapping("/welcome")
    public String welcomeBook() {
        return "Hello in Online Book Store DashBoard";
    }

    @PostMapping("/addBook")
    public ResponseEntity<ResponseDTO> addBook(@RequestBody BookDTO bookDTO,@RequestHeader String token) {
        System.out.println(bookDTO);
        BookDetailsModel bookDetailsModel = bookService.addBook(bookDTO,token);
        ResponseDTO responseDTO = new ResponseDTO("Book Added Successfully", bookDetailsModel);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);


    }

    @GetMapping("/getAllBooks")
    public ResponseEntity<ResponseDTO> getAllBooks(){
        List<BookDetailsModel> bookDetailsModels = bookService.getAllBooks();
        ResponseDTO responseDTO = new ResponseDTO("Got All Books List",bookDetailsModels);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @GetMapping("/books/count")
    public ResponseEntity<ResponseDTO> getTotalCount() {
        int count  = bookService.getCountOfBooks();
        return new ResponseEntity<>(new ResponseDTO("The books count is : ",count), HttpStatus.OK);
    }


    @GetMapping("/getBookAscending")
    public ResponseEntity<ResponseDTO> getBooksWithIncreasingOrderOfTheirPrice(){
        List<BookDetailsModel> bookDetailsModels = bookService.getBooksWithIncreasingOrderOfTheirPrice();
        ResponseDTO responseDTO = new ResponseDTO("Showing books in ascending order",
                bookDetailsModels);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @GetMapping("/getBookDescending")
    public ResponseEntity<ResponseDTO> getBooksWithDecreasingOrderOfTheirPrice(){
        List<BookDetailsModel> bookDetailsModels = bookService.getBooksWithDecreasingOrderOfTheirPrice();
        ResponseDTO responseDTO = new ResponseDTO("Showing books in descending order",
                bookDetailsModels);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @GetMapping("/getBooksByPublishingYear")
    public ResponseEntity<ResponseDTO> getBooksWithPublishingYear(){
        List<BookDetailsModel> bookDetailsModels = bookService.getBooksWithPublishingYear();
        ResponseDTO responseDTO = new ResponseDTO("Showing books according to publishing year",
                bookDetailsModels);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/getBooksByNewLaunch")
    public ResponseEntity<ResponseDTO> getBooksByNewLaunch(){
        List<BookDetailsModel> bookDetailsModels = bookService.getBooksByNewLaunch();
        ResponseDTO responseDTO = new ResponseDTO("Showing books according to New Launch",
                bookDetailsModels);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseDTO> getBookById(@PathVariable("id") Long id){
        BookDetailsModel bookDetailsModel = bookService.getBookById(id);
        ResponseDTO responseDTO = new ResponseDTO("Got Book By Id",bookDetailsModel);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/filterBySearch")
    public ResponseEntity<ResponseDTO> searchByName(@RequestParam String name){
        List<BookDetailsModel> bookDetailsModels=bookService.searchByName(name);
        ResponseDTO responseDTO = new ResponseDTO("Books Filterd by search sequence",bookDetailsModels);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


}
