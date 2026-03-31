package com.f5hell.domain.entity;

import com.f5hell.common.exception.NotEnoughStockException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class ProductTest {

    @Test
    @DisplayName("재고 차감 로직 : 정상")
    void removeStock_happyPath() {
        // 1. given (준비)
        Product product = Product.builder()
                .name("곰돌이인형")
                .stock(2)
                .create();

        // 2. when (실행)
        product.removeStock(1);

        // 3. then (검증)
        assertThat(product.getStock()).isEqualTo(1);
    }

    @Test
    @DisplayName("재고 추가 로직 : 경계값")
    void removeStock_boundary() {
        // 1. given (준비)
        Product product = Product.builder()
                .name("곰돌이인형")
                .stock(2)
                .create();

        // 2. when (실행)
        product.removeStock(2);

        // 3. then (검증)
        assertThat(product.getStock()).isEqualTo(0);
    }

    @Test
    @DisplayName("재고 추가 로직 : 실패 (음수입력)")
    void removeStock_negative_1() {
        // 1. given (준비)
        Product product = Product.builder()
                .name("곰돌이인형")
                .stock(2)
                .create();

        // 3. then (검증)
        assertThatThrownBy(() -> product.removeStock(-1)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("재고 추가 로직 : 실패 (재고부족)")
    void removeStock_negative_2() {
        // 1. given (준비)
        Product product = Product.builder()
                .name("곰돌이인형")
                .stock(2)
                .create();

        // 3. then (검증)
        assertThatThrownBy(() -> product.removeStock(3)).isInstanceOf(NotEnoughStockException.class);
    }
}
