package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.entity.BookDetailsModel;
import com.bridgelabz.bookstoreapp.entity.CartDetailsModel;

import java.util.List;

public interface ICartService {
    BookDetailsModel addBookToCart(String token, Long bookId);

    List<CartDetailsModel> getAll();

    CartDetailsModel deleteCartItemById(Long cartId);

    CartDetailsModel getCartItemById(Long cartId);
}
