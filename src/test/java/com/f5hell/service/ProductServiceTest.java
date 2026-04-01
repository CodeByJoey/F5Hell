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

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("")
    void 상품_등록_성공() {
        // given
        ProductRequest request = ProductRequest.builder()
                .name("F5 전용 키보드")
                .price(50000L)
                .stock(10)
                .create();

        // 2. 가짜 엔티티 생성 (ID가 있는 상태로 가정)
        Product savedProduct = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .stock(request.getStock())
                .create();

        ReflectionTestUtils.setField(savedProduct, "id", 3L);

        // Repository가 저장 후 ID가 1인 엔티티를 반환한다고 가정
        BDDMockito.given(productRepository.save(ArgumentMatchers.any(Product.class))).willReturn(savedProduct);

        // when
        Long savedId = productService.save(request);

        // then
        assertThat(savedId).isEqualTo(1L);
    }
}
