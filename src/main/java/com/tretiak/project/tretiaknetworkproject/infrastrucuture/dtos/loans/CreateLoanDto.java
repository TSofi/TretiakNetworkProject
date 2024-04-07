package com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.loans;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
public class CreateLoanDto {
    @NotNull(message = "User ID is required")
    private Long userId;
    @NotNull(message = "Book ID is required")
    private Long bookId;
    @NotNull(message = "Due date is required")
    private LocalDate dueDate;

    public CreateLoanDto(Long userId, Long bookId, LocalDate loanDate, LocalDate dueDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.dueDate = dueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
    public Long getUserId() {
        return userId;
    }
    public Long getBookId() {
        return bookId;
    }


}
