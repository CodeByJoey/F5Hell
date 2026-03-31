package com.f5hell.repository;

import com.f5hell.domain.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품 저장 테스트")
    void saveProductTest() {
        // 1. given (준비)
        Product product = Product.builder()
                .name("한정판 키티")
                .price(15000L)
                .stock(100)
                .create();

        // 2. when (실행)
        Product saveProduct = productRepository.save(product);

        // 3. then (검증)
        assertThat(saveProduct.getId()).isNotNull();
        assertThat(saveProduct.getName()).isEqualTo("한정판 키티");
    }
}
