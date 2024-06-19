package com.tretiak.project.tretiaknetworkproject.service;

// this clas for some additional check to check is everything is ok
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.book.*;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.map.MapBook;
import com.tretiak.project.tretiaknetworkproject.exceptions.NotFoundException;
import com.tretiak.project.tretiaknetworkproject.exceptions.AlreadyExistsException;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.entity.BookEntity;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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
                .map(book -> new GetBookDto(
                book.getId(),
                book.getIsbn(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublisher(),
                book.getYearPublished(),
                book.getAvailableCopies() >0
        ))
                .collect(Collectors.toList());
    }

    public GetBookDto getOne(Long id) {
        var bookEntity = bookRepository.findById(id).orElseThrow(NotFoundException::book);

        return new GetBookDto(
                bookEntity.getId(),
                bookEntity.getIsbn(),
                bookEntity.getTitle(),
                bookEntity.getAuthor(),
                bookEntity.getPublisher(),
                bookEntity.getYearPublished(),
                bookEntity.getAvailableCopies()>0
        );
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
                newBook.getAvailableCopies() // Map availableCopies to isAvailable
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
    public List<String> autocomplete(String query) {
        List<String> suggestions = Arrays.asList("Crown of Midnight", "Throne of Glass", "Kingdom of Ash");
        return suggestions.stream()
                .filter(title -> title.toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<GetBookDto> findBooksByTitle(String query) {
        List<BookEntity> books = bookRepository.findByTitleContainingIgnoreCase(query);
        return books.stream().map(book -> mapToGetBookDto(book)).collect(Collectors.toList());
    }

    private GetBookDto mapToGetBookDto(BookEntity book) {
        return new GetBookDto(
                book.getId(),
                book.getIsbn(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublisher(),
                book.getYearPublished(),
                book.getAvailableCopies() > 0
        );
    }

    /*
    public GetBookDto update(Long id, UpdateBookDto updateBookDto) {
        var bookEntity = bookRepository.findById(id).orElseThrow(NotFoundException::book);

        bookEntity.setTitle(updateBookDto.getTitle());
        bookEntity.setAuthor(updateBookDto.getAuthor());
        bookEntity.setPublisher(updateBookDto.getPublisher());
        bookEntity.setYearPublished(updateBookDto.getYearPublished());
        bookEntity.setAvailableCopies(updateBookDto.getAvailableCopies());

        var updatedBook = bookRepository.save(bookEntity);

        return new GetBookDto(
                updatedBook.getId(),
                updatedBook.getIsbn(),
                updatedBook.getTitle(),
                updatedBook.getAuthor(),
                updatedBook.getPublisher(),
                updatedBook.getYearPublished(),
                //updatedBook.getAvailableCopies()
        );
    }

     */


}
