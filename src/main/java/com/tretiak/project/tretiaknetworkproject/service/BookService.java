package com.tretiak.project.tretiaknetworkproject.service;

// this clas for some additional check to check is everything is ok
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.map.MapBook;
import com.tretiak.project.tretiaknetworkproject.exceptions.NotFoundException;
import com.tretiak.project.tretiaknetworkproject.exceptions.AlreadyExistsException;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.book.AddBookResponseDto;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.book.GetBookDto;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.book.CreateBookDto;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.book.CreateBookResponseDto;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.entity.BookEntity;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<GetBookDto> getAll() {
        var books = bookRepository.findAll();

        return books.stream()
                .map(MapBook::toGetBookDto).collect(Collectors.toList());
    }

    public GetBookDto getOne(Long id) {
        var bookEntity = bookRepository.findById(id).orElseThrow(NotFoundException::book);

        return MapBook.toGetBookDto(bookEntity);
    }

    public CreateBookResponseDto create(CreateBookDto book) {

        if (bookRepository.existsByIsbn(book.getIsbn())) {
            log.info("Book with given ISBN already exists");
            throw AlreadyExistsException.bookByIsbn(book.getIsbn());
        }

        var bookEntity = new BookEntity();
        bookEntity.setIsbn(book.getIsbn());
        bookEntity.setTitle(book.getTitle());
        bookEntity.setAuthor(book.getAuthor());
        bookEntity.setPublisher(book.getPublisher());
        bookEntity.setYearPublished(book.getPublicationYear());
        bookEntity.setAvailableCopies(book.getAvailableCopies());
        bookEntity.setReviews(new ArrayList<>());
        bookEntity.setLoaned(new ArrayList<>());

        var newBook = bookRepository.save(bookEntity);
        return new CreateBookResponseDto(
                newBook.getId(),
                newBook.getIsbn(),
                newBook.getTitle(),
                newBook.getAuthor(),
                newBook.getPublisher(),
                newBook.getYearPublished(),
                newBook.getAvailableCopies()
        );
    }

    public AddBookResponseDto add(Long id, Integer copies) {
        var bookEntity = bookRepository.findById(id).orElseThrow(NotFoundException::book);
        bookEntity.setAvailableCopies(bookEntity.getAvailableCopies() + copies);
        bookRepository.save(bookEntity);
        return new AddBookResponseDto(bookEntity.getId(), bookEntity.getAvailableCopies());
    }

    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            log.info("Book with given id not found");
            throw NotFoundException.book();
        }
        bookRepository.deleteById(id);
    }


}
