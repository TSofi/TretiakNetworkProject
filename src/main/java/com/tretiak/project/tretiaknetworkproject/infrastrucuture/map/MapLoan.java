package com.tretiak.project.tretiaknetworkproject.infrastrucuture.map;

import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.loans.GetLoanDto;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.entity.LoanEntity;

public class MapLoan {
    public static GetLoanDto toGetLoanDto(LoanEntity loanEntity) {
        return new GetLoanDto(
                loanEntity.getId(),
                loanEntity.getLoanDate(),
                loanEntity.getDueDate(),
                MapUser.toGetUserDto(loanEntity.getUser()),
                MapBook.toGetBookDto(loanEntity.getBook())
        );
    }
}
