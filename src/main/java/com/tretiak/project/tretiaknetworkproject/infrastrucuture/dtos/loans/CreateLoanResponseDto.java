package com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.loans;

import java.time.LocalDate;

public class CreateLoanResponseDto {
    private Long id;
    private Long userId;
    private Long bookId;
    private LocalDate loanDate;
    private LocalDate dueDate;

    public CreateLoanResponseDto(Long id, Long userId, Long bookId, LocalDate loanDate, LocalDate dueDate) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
