package com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.reviews;

import java.time.LocalDate;

public class CreateReviewResponse {
    public class CreateReviewResponseDto {
        private Long id;
        private Long bookId;
        private Long userId;
        private Float rating;
        private String comment;
        private LocalDate reviewDate;

        public CreateReviewResponseDto(Long id, Long bookId, Long userId, Float rating, String comment, LocalDate reviewDate) {
            this.id = id;
            this.bookId = bookId;
            this.userId = userId;
            this.rating = rating;
            this.comment = comment;
            this.reviewDate = reviewDate;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getBookId() {
            return bookId;
        }

        public void setBookId(Long bookId) {
            this.bookId = bookId;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public Float getRating() {
            return rating;
        }

        public void setRating(Float rating) {
            this.rating = rating;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public LocalDate getReviewDate() {
            return reviewDate;
        }

        public void setReviewDate(LocalDate reviewDate) {
            this.reviewDate = reviewDate;
        }
    }
}
