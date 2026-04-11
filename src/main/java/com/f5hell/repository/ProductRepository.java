package com.f5hell.repository;

import com.f5hell.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAll();
    Page<Product> findAll(Pageable pageable);

    @Query("SELECT p FROM Product p " +
           "where (:name IS NULL OR p.name LIKE %:name%) " +
            "and (:categoryId IS NULL OR p.categoryId = :categoryId)")
    Page<Product> searchProducts(@Param("name") String name,
                                     @Param("categoryId") Long categoryId,
                                     Pageable pageable);
}
