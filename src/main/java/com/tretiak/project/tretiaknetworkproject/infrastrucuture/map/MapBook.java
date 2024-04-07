package com.tretiak.project.tretiaknetworkproject.infrastrucuture.map;

import com.tretiak.project.tretiaknetworkproject.infrastrucuture.entity.BookEntity;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.book.GetBookDto;

// Map classes used to convert entity objects into DTOs

public class MapBook {
    public static GetBookDto toGetBookDto(BookEntity bookEntity) {
        return new GetBookDto(
                bookEntity.getId(),
                bookEntity.getIsbn(),
                bookEntity.getTitle(),
                bookEntity.getAuthor(),
                bookEntity.getPublisher(),
                bookEntity.getYearPublished(),
                bookEntity.getAvailableCopies()
        );
    }
}
