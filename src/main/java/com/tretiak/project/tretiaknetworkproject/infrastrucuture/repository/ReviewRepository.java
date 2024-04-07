package com.tretiak.project.tretiaknetworkproject.infrastrucuture.repository;

import com.tretiak.project.tretiaknetworkproject.infrastrucuture.entity.ReviewEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<ReviewEntity, Long> {
}
