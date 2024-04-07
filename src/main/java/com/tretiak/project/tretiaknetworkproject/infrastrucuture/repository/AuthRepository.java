package com.tretiak.project.tretiaknetworkproject.infrastrucuture.repository;

import com.tretiak.project.tretiaknetworkproject.infrastrucuture.entity.AuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<AuthEntity, Long> {
    Optional<AuthEntity> findByUsername(String username);
    boolean existsByUsername(String username);
}
