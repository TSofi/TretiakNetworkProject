package com.tretiak.project.tretiaknetworkproject.controller;

import com.tretiak.project.tretiaknetworkproject.exceptions.CheckBindingExceptions;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.book.CreateBookDto;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.book.CreateBookResponseDto;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.book.GetBookDto;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.entity.BookEntity;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.repository.BookRepository;
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
public class BookContoller {
    private final BookService bookService;
    private final BookRepository bookRepository;


    @Autowired
    public BookContoller(BookService bookService, BookRepository bookRepository) {
        this.bookService = bookService;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/getAll")
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody List<GetBookDto> getAllBooks() {
        return bookService.getAll();
    }


    @PostMapping("/create")
    @ResponseStatus(code = HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CreateBookResponseDto> create(@Valid @RequestBody CreateBookDto book, BindingResult bindingResult) {
        CheckBindingExceptions.check(bindingResult);
        CreateBookResponseDto newBook = bookService.create(book);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<GetBookDto> getBook(@PathVariable Long id) {
        return new ResponseEntity<>(bookService.getOne(id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/autocomplete")
    public ResponseEntity<List<String>> autocomplete(@RequestParam("query") String query) {
        List<String> suggestions = bookService.autocomplete(query);
        return ResponseEntity.ok(suggestions);
    }

    /*
    @GetMapping("/autocomplete")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<GetBookDto>> autocompleteBooks(@RequestParam("query") String query) {
        List<GetBookDto> books = bookService.findBooksByTitle(query); // Assuming you have a service method to query books by title
        return ResponseEntity.ok(books);
    }

     */

/*
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GetBookDto> updateBook(@PathVariable Long id, @Valid @RequestBody UpdateBookDto updateBookDto, BindingResult bindingResult) {
        CheckBindingExceptions.check(bindingResult);
        var updatedBook = bookService.update(id, updateBookDto);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

 */


}
