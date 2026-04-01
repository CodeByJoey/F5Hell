package com.f5hell.domain.entity;

import com.f5hell.common.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {

    private Product createProduct(int stock) {
        return Product.builder()
                .name("곰돌이인형")
                .stock(stock)
                .create();
    }

    @Nested
    @DisplayName("상품 재고 차감 테스트")
    class RemoveStockTest {


        @Test
        @DisplayName("[성공]: 정상 입력")
        void success() {
            // given (준비)
            Product product = createProduct(2);

            // when (실행)
            product.removeStock(1);

            // then (검증)
            assertThat(product.getStock()).isEqualTo(1);
        }

        @Test
        @DisplayName("[성공]: 경계값 입력")
        void boundary() {
            // given (준비)
            Product product = createProduct(2);

            // when (실행)
            product.removeStock(2);

            // 3. then (검증)
            assertThat(product.getStock()).isEqualTo(0);
        }

        @Test
        @DisplayName("[예외]: 음수 수량입력")
        void fail_negative() {
            // given (준비)
            Product product = createProduct(2);

            // when + then (검증)
            assertThatThrownBy(() -> product.removeStock(-1))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("[예외]: 재고부족")
        void fail_outOfStock() {
            // given (준비)
            Product product = createProduct(2);

            // when + then (검증)
            assertThatThrownBy(() -> product.removeStock(3))
                    .isInstanceOf(BusinessException.class)
                    .hasMessageContaining("재고가 부족합니다.");
        }
    }

    @Nested
    @DisplayName("상품 재고 추가 테스트")
    class AddStockTest {
        @Test
        @DisplayName("[성공]: 정상 입력")
        void success() {
            // given
            Product product = createProduct(0);

            // when
            product.addStock(1);

            // then
            assertThat(product.getStock()).isEqualTo(1);
        }

        @Test
        @DisplayName("[예외]: 음수 입력")
        void fail_negative() {
            // given
            Product product = createProduct(0);

            // when + then
            assertThatThrownBy(() -> product.addStock(-1))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("추가할 재고 수량은 0보다 커야합니다.");
        }
    }


}
