package com.bridgelabz.bookstoreapp.repository;

import com.bridgelabz.bookstoreapp.entity.BookDetailsModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IBookRepository extends JpaRepository<BookDetailsModel,Long> {
    Optional<BookDetailsModel> findByBookName(String bookName);
}
