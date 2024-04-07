package com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.loans;

import java.util.List;

public class GetLonsList {

    private List<GetLoanDto> loans;
    private int currentPage;
    private int totalPages;
    private long totalItems;
    private boolean hasMore;

    public GetLonsList(List<GetLoanDto> loans, int currentPage, int totalPages, long totalItems, boolean hasMore) {
        this.loans = loans;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
        this.hasMore = hasMore;
    }
}