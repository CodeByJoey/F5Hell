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

    @Query("select p from Product p where p.name like %:name%")
    Page<Product> findProductsByName(@Param("name") String name, Pageable pageable);
}
