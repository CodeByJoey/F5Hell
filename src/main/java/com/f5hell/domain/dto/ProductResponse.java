package com.f5hell.domain.dto;

import com.f5hell.domain.entity.Product;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private Long price;
    private Long categoryId;

    public static ProductResponse from(Product product) {
        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getCategory().getId()
        );
    }
}
