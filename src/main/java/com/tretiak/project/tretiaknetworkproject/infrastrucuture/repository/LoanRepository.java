package com.tretiak.project.tretiaknetworkproject.infrastrucuture.repository;

import com.tretiak.project.tretiaknetworkproject.infrastrucuture.entity.LoanEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<LoanEntity, Long> {
    Page<LoanEntity> findByUserId(Long userId, Pageable pageable);
}
