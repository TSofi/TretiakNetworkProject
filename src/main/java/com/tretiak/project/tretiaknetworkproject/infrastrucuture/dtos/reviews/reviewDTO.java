package com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.reviews;

import java.time.LocalDate;

public class reviewDTO {
    private Long id;
    private Long bookId;
    private Long userId;
    private String bookTitle;
    private String userName;
    private Float rating;
    private String comment;
    private LocalDate reviewDate;

    public reviewDTO(Long bookId, Long userId, Float rating, String comment, LocalDate reviewDate) {
        this.bookId = bookId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    // Constructor for creating a response after creating a review
    public reviewDTO(Long id, Long bookId, Long userId, Float rating, String comment, LocalDate reviewDate) {
        this.id = id;
        this.bookId = bookId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    public reviewDTO(Long id, String bookTitle, String userName, Float rating, String comment, LocalDate reviewDate) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.userName = userName;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

}
