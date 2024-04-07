package com.tretiak.project.tretiaknetworkproject.controller;

import com.tretiak.project.tretiaknetworkproject.exceptions.CheckBindingExceptions;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.book.CreateBookDto;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.book.CreateBookResponseDto;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.book.GetBookDto;
import com.tretiak.project.tretiaknetworkproject.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api/addBook")
//@PreAuthorize("isAuthenticated()")
public class BookContoller {
    private final BookService bookService;


    @Autowired
    public BookContoller(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/getAll")
  //  @PreAuthorize("permitAll()")
    public @ResponseBody List<GetBookDto> getAllBooks() {
        return bookService.getAll();
    }


    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CreateBookResponseDto> create(@Valid @RequestBody CreateBookDto book, BindingResult bindingResult) {
        CheckBindingExceptions.check(bindingResult);
        var newBook = bookService.create(book);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<GetBookDto> getBook(@PathVariable Long id) {
        return new ResponseEntity<>(bookService.getOne(id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
 //   @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }


    /*

    // Method to handle POST requests for adding a book
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BookEntity> addBook(@RequestBody BookEntity bookEntity) {
        BookEntity savedBook = bookService.addBook(bookEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

     */




}
