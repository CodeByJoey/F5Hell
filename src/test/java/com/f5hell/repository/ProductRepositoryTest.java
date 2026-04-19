package com.f5hell.repository;

import com.f5hell.domain.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
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

    @Test
    @DisplayName("상품 목록 조회 테스트")
    void findAllProductTest() {
        // 1. given (준비)
        Product product = Product.builder()
                .name("나이키 트래비스 스캇")
                .price(1000000L)
                .stock(100)
                .create();
        productRepository.save(product);

        // 2. when (실행)
        List<Product> products = productRepository.findAll();

        // 3. then (검증)
        assertThat(products).hasSize(1);
        assertThat(products).extracting(p -> p.getName()).contains("나이키 트래비스 스캇");
        assertThat(products).extracting(Product::getPrice).contains(1000000L);
        assertThat(products).extracting(Product::getStock).contains(100);
    }

    @Test
    @DisplayName("상품 목록 조회 조건 입력 테스트")
    void findProductsByName() {
        // given
        Product product = Product.builder()
                .name("나이키 신발")
                .price(100000L)
                .stock(40)
                .create();

        Product product2 = Product.builder()
                .name("나이키 신발2")
                .price(100000L)
                .stock(30)
                .create();

        Product product3 = Product.builder()
                .name("아디다스 신발")
                .price(100000L)
                .stock(40)
                .create();

        productRepository.save(product);
        productRepository.save(product2);
        productRepository.save(product3);
        // when
        Page<Product> products = productRepository.searchProducts(null, null, null);

        // then
        assertThat(products).hasSize(3);
    }

}
