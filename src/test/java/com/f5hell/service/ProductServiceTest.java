package com.f5hell.service;

import com.f5hell.domain.dto.ProductRequest;
import com.f5hell.domain.entity.Product;
import com.f5hell.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품 등록")
    void createTest() {
        // given
        ProductRequest request = ProductRequest.builder()
                .name("F5 전용 키보드")
                .price(50000L)
                .stock(10)
                .create();

        // 2. 가짜 엔티티 생성 (ID가 있는 상태로 가정)
        Product mockProduct = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .stock(request.getStock())
                .create();

        ReflectionTestUtils.setField(mockProduct, "id", 3L);

        // Repository가 저장 후 ID가 1인 엔티티를 반환한다고 가정
        BDDMockito.given(productRepository.save(ArgumentMatchers.any(Product.class))).willReturn(mockProduct);

        // when
        Long savedId = productService.create(request);

        // then
        assertThat(savedId).isEqualTo(3L);
    }

    @Test
    @DisplayName("상품 목록 조회")
    void getAllTest() {
        Product mockProduct1 = Product.builder()
                .name("상품1")
                .price(50000L)
                .stock(10)
                .create();
        Product mockProduct2 = Product.builder()
                .name("상품2")
                .price(50000L)
                .stock(10)
                .create();

        List<Product> mockProducts = List.of(mockProduct1, mockProduct2);

        BDDMockito.given(productRepository.findAll()).willReturn(mockProducts);
        List<Product> result = productService.getList();
        assertThat(result).hasSize(2);
        assertThat(result).extracting(Product::getName).contains("상품1", "상품2");
    }

    @Test
    @DisplayName("상품 단건 조회")
    void getByIdTest() {
        Product mockProduct = Product.builder()
                .name("상품1")
                .price(25000L)
                .stock(1)
                .create();

        BDDMockito.given(productRepository.findById(1L)).willReturn(Optional.ofNullable(mockProduct));
        Product result = productService.get(1L);
        assertThat(result).isEqualTo(mockProduct);
    }

    @Test
    @DisplayName("상품 단건 삭제")
    void deleteByIdTest() {

        Product mockProduct = Product.builder()
                .name("상품1")
                .price(10000L)
                .stock(1)
                .create();

        productService.delete(mockProduct.getId());
        BDDMockito.given(productRepository.findById(mockProduct.getId())).willReturn(null);
        Product result = productService.get(mockProduct.getId());
        assertThat(result).isNull();
    }

}
