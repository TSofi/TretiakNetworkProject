package com.tretiak.project.tretiaknetworkproject.controller;

import com.tretiak.project.tretiaknetworkproject.exceptions.CheckBindingExceptions;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.loans.CreateLoanDto;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.loans.CreateLoanResponseDto;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.loans.GetLoanDto;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.loans.GetLonsList;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.entity.LoanEntity;
import com.tretiak.project.tretiaknetworkproject.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/getAll")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<GetLonsList> getAll(@RequestParam(required = false) Long userId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        GetLonsList loans = loanService.getAll(userId, page, size);
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<CreateLoanResponseDto> create(@Valid @RequestBody CreateLoanDto loan, BindingResult bindingResult) {
        CheckBindingExceptions.check(bindingResult);
        var newLoan = loanService.create(loan);
        return new ResponseEntity<>(newLoan, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("isAuthenticated()")
    public GetLoanDto getLoan(@PathVariable Long id) {
        return loanService.getOne(id);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        loanService.delete(id);
        return ResponseEntity.noContent().build();
    }
}




/*
    @GetMapping("/getAll")
    public @ResponseBody List<GetLoanDto> getAllBooks() {
        return loanService.getAll();
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED) //code 201
    public ResponseEntity<CreateLoanResponseDto> create(@Valid @RequestBody CreateLoanDto loan, BindingResult bindingResult) {
        CheckBindingExceptions.check(bindingResult);
        var newLoan = loanService.create(loan);
        return new ResponseEntity<>(newLoan, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public GetLoanDto getLoan(@PathVariable Long id) {
        return loanService.getOne(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        loanService.delete(id);
        return ResponseEntity.noContent().build();
    }

 */
