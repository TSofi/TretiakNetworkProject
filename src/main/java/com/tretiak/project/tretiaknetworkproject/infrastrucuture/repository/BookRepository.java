
package com.tretiak.project.tretiaknetworkproject.infrastrucuture.repository;

import com.tretiak.project.tretiaknetworkproject.infrastrucuture.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    boolean existsByIsbn(String isbn);
    List<BookEntity> findByTitleContainingIgnoreCase(String query);
}