package com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.book;

public class CreateBookResponseDto {
    private Long id;
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private int publicationYear;
    private int availableCopies;

    public CreateBookResponseDto(Long id, String isbn, String title, String author, String publisher, int publicationYear, int availableCopies) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.availableCopies = availableCopies;
    }

    public CreateBookResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
