package com.f5hell.domain.entity;

import com.f5hell.common.exception.BusinessException;
import com.f5hell.common.message.ErrorCode;
import com.f5hell.domain.dto.ProductRequest;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본생성자는 막되, JPA 프록시가 접근할 수 있게 PROTECTED
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;        // 상품 이름
    private Long price;         // 상품 가격
    private Integer stock;      // 재고

    @Builder(buildMethodName = "create")
    private Product(String name, Long price, Integer stock) {
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
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
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
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
        }
        if(this.stock < quantity) {
            throw new BusinessException(ErrorCode.NOT_ENOUGH_STOCK, this.stock);
        }
        return this.stock -= quantity;
    }

    public Long update(ProductRequest request) {
        this.name = request.getName();
        this.price = request.getPrice();
        this.stock = request.getStock();
        return this.id;
    }
}
