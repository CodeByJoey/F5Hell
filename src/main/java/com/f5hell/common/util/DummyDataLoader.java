package com.f5hell.common.util;

import com.f5hell.domain.entity.Product;
import com.f5hell.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DummyDataLoader implements CommandLineRunner {
    private final ProductRepository productRepository;

    @Override
    public void run(String... args) {
        // 이미 데이터가 있다면 중복 생성 방지
        if (productRepository.count() > 0) {
            log.info("이미 데이터가 존재하여 더미 데이터 생성을 건너뜁니다.");
            return;
        }

        log.info("1,000개의 더미 데이터를 생성을 시작합니다...");
        List<Product> products = new ArrayList<>();

        for (int i = 1; i <= 1000; i++) {
            products.add(Product.builder()
                    .name("F5 상품 " + i)
                    .price(1000L + (i * 10)) // 가격도 다양하게
                    .stock(i % 100)        // 재고도 0~99 사이로
                    .create());

            // 너무 큰 리스트는 메모리 부하가 올 수 있으니 100개 단위로 끊어서 저장
            if (i % 100 == 0) {
                productRepository.saveAll(products);
                products.clear();
                log.info("{}개 데이터 저장 완료...", i);
            }
        }

        log.info("더미 데이터 생성 완료!");
    }
}
