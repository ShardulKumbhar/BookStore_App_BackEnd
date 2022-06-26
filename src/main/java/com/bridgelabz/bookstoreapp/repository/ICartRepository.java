package com.bridgelabz.bookstoreapp.repository;

import com.bridgelabz.bookstoreapp.entity.CartDetailsModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICartRepository extends JpaRepository<CartDetailsModel, Long> {



    @Query(value = "select * from cart_details_model where book_id=:bookId", nativeQuery = true)
    Optional<CartDetailsModel> findByBookDetailsById(Long bookId);
}
