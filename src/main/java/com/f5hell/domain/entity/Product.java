package com.f5hell.domain.entity;

import com.f5hell.common.exception.NotEnoughStockException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;        // 상품 이름
    private Long price;         // 상품 가격
    private Integer stock;      // 재고

    @Builder(buildMethodName = "create")
    public Product(String name, Long price, Integer stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    /**
     * 상품의 재고를 증가시킵니다.
     * @param quantity 추가할 재고 수량 (양수여야함)
     * @return Integer 추가된 재고 수량
     */
    public Integer addStock(int quantity) {
        if(quantity <= 0) {
            throw new IllegalArgumentException("추가할 재고 수량은 0보다 커야합니다.");
        }
        return this.stock += quantity;
    }

    /**
     * 상품의 재고를 차감합니다.
     * @param quantity 차감할 재고 수량 (양수여야함)
     * @return Integer 차감된 재고 수량
     */
    public Integer removeStock(int quantity) {
        if(quantity <= 0) {
            throw new IllegalArgumentException("차감할 재고 수량은 0보다 커야합니다.");
        }
        if(this.stock < quantity) {
            throw new NotEnoughStockException("재고가 부족합니다. (현재 재고 : " + this.stock + ")");
        }
        return this.stock -= quantity;
    }
}
