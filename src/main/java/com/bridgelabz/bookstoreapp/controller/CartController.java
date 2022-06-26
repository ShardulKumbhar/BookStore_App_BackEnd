package com.bridgelabz.bookstoreapp.controller;


import com.bridgelabz.bookstoreapp.dto.ResponseDTO;
import com.bridgelabz.bookstoreapp.entity.BookDetailsModel;
import com.bridgelabz.bookstoreapp.entity.CartDetailsModel;
import com.bridgelabz.bookstoreapp.service.ICartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin("*")
@RequestMapping("/cartApi")
public class CartController {

    @Autowired
    private ICartService cartService;

    @GetMapping("/welcome")
    public String welcomeToCart() {
        return "Welcome to cart controller";
    }

    @PostMapping("/addBookToCart/{bookId}")
    public ResponseEntity<ResponseDTO> addBookToCart(@RequestHeader String token, @PathVariable Long bookId) {
        BookDetailsModel bookDetailsModel = cartService.addBookToCart(token, bookId);
        ResponseDTO responseDTO = new ResponseDTO("Book Added Successfully", bookDetailsModel);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAll(){
        List<CartDetailsModel> cartDetailsModels = cartService.getAll();
        ResponseDTO responseDTO = new ResponseDTO("Cart details getting successfully",cartDetailsModels);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cartId}")
    public ResponseEntity<ResponseDTO> deleteCartItemById(@PathVariable Long cartId){
        CartDetailsModel cartDetailsModel = cartService.deleteCartItemById(cartId);
        ResponseDTO responseDTO = new ResponseDTO("Item Deleted successfully",cartDetailsModel);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/get/{cartId}")
    public ResponseEntity<ResponseDTO> getCartItemById(@PathVariable Long cartId){
        CartDetailsModel cartDetailsModel = cartService.getCartItemById(cartId);
        ResponseDTO responseDTO = new ResponseDTO("cart item found successfully",cartDetailsModel);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
