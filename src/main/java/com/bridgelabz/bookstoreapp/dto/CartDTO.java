package com.bridgelabz.bookstoreapp.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CartDTO {
    private Long userId;
    private Long bookId;
    private Double quantity;

}