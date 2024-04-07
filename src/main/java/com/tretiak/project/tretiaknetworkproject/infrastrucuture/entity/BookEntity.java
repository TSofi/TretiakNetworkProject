package com.tretiak.project.tretiaknetworkproject.infrastrucuture.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "books", schema = "library")
public class BookEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @Basic
    @Column(name = "isbn")
    private String isbn;

    @Basic
    @Column(name = "title")
    private String title;

    @Basic
    @Column(name = "author")
    private String author;

    @Basic
    @Column(name = "publisher")
    private String publisher;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "loaned")
    private List<LoanEntity> loaned;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "reviews")
    private List<ReviewEntity> reviews;

    @Basic
    @Column(name = "year_published")
    private int yearPublished;

    @Basic
    @Column(name = "available_copies")
    private int availableCopies;

    // Getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public List<LoanEntity> getLoaned() {
        return loaned;
    }

    public void setLoaned(List<LoanEntity> loaned) {
        this.loaned = loaned;
    }

    public List<ReviewEntity> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewEntity> reviews) {
        this.reviews = reviews;
    }
}
