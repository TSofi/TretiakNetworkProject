package com.tretiak.project.tretiaknetworkproject.service;

import com.tretiak.project.tretiaknetworkproject.exceptions.NotFoundException;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.loans.CreateLoanDto;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.loans.CreateLoanResponseDto;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.loans.GetLoanDto;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.loans.GetLonsList;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.entity.BookEntity;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.entity.LoanEntity;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.entity.UserEntity;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.map.MapLoan;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.repository.AuthRepository;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.repository.BookRepository;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.repository.LoanRepository;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


import java.util.List;

@Service
public class LoanService {

    private static final Logger log = LoggerFactory.getLogger(LoanService.class);
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository, UserRepository userRepository, BookRepository bookRepository, AuthRepository authRepository) {
        super();
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @PreAuthorize("hasRole('ADMIN') or isAuthenticated() and this.isOwner(authentication.name, #userId)")
    public GetLonsList getAll(Long userId, int page, int size) {
        Page<LoanEntity> loansPage;
        Pageable pageable = PageRequest.of(page, size);

        if (userId != null) {
            loansPage = loanRepository.findByUserId(userId, pageable);
        } else {
            loansPage = loanRepository.findAll(pageable);
        }

        List<GetLoanDto> loansDto = loansPage.getContent().stream()
                .map(MapLoan::toGetLoanDto).toList();

        return new GetLonsList(
                loansDto,
                loansPage.getNumber(),
                loansPage.getTotalPages(),
                loansPage.getTotalElements(),
                loansPage.hasNext());
    }

    @PostAuthorize("hasRole('ADMIN') or isAuthenticated() and this.isOwner(authentication.name, returnObject.user.id)")
    public GetLoanDto getOne(Long id) {
        var loanEntity = loanRepository.findById(id).orElseThrow(NotFoundException::loan);

        return MapLoan.toGetLoanDto(loanEntity);
    }

    @PreAuthorize("hasRole('ADMIN') or isAuthenticated() and this.isOwner(authentication.name, #loan.userId)")
    public CreateLoanResponseDto create(CreateLoanDto loan) {
        UserEntity user = userRepository.findById(loan.getUserId()).orElseThrow(NotFoundException::user);
        BookEntity book = bookRepository.findById(loan.getBookId()).orElseThrow(NotFoundException::book);
        LocalDate currentDate = LocalDate.now();
        LoanEntity loanEntity = new LoanEntity();
        loanEntity.setUser(user);
        loanEntity.setBook(book);
        loanEntity.setLoanDate(currentDate);
        loanEntity.setDueDate(loan.getDueDate());
        var newLoan = loanRepository.save(loanEntity);

        return new CreateLoanResponseDto(
                newLoan.getId(),
                newLoan.getUser().getId(),
                newLoan.getBook().getId(),
                newLoan.getLoanDate(),
                newLoan.getDueDate()
        );
    }

    public void delete(Long id) {
        if(!loanRepository.existsById(id)) {
            log.info("Loan with id: {} not found", id);
            throw NotFoundException.loan();
        }
        loanRepository.deleteById(id);
    }

}