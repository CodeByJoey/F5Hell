package com.f5hell.domain.dto;

import com.f5hell.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private Long price;
    private Integer stock;
    private Long categoryId;
    private String categoryName;
    private String uploadFileName;
    private String storeFileName;

    public static ProductResponse from(Product product) {
        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getStock(),
            product.getCategory().getId(),
            product.getCategory().getName(),
            product.getUploadFileName(),
            product.getStoreFileName()
        );
    }
}
