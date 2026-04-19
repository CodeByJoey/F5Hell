package com.f5hell.common.util;

import com.f5hell.domain.entity.Category;
import com.f5hell.domain.entity.Product;
import com.f5hell.repository.CategoryRepository;
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
    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) {

        if (productRepository.count() == 0) {
            log.info("카테고리 데이터 생성합니다.");
            List<Category> categories = new ArrayList<>();
            categories.add(Category.builder().name("의류").useYn("Y").depth(0).build());
            categories.add(Category.builder().name("신발").useYn("Y").depth(0).build());
            categories.add(Category.builder().name("가방").useYn("Y").depth(0).build());

            categoryRepository.saveAll(categories);
        }

        if (productRepository.count() == 0) {
            log.info("1,000개의 더미 데이터를 생성을 시작합니다...");
            List<Product> products = new ArrayList<>();

            List<Category> categories = categoryRepository.findAll();
            for (int i = 1; i <= 1000; i++) {
                Category targetCategory = categories.get(i % categories.size());
                products.add(Product.builder()
                        .name("F5 상품 " + i)
                        .price(1000L + (i * 10)) // 가격도 다양하게
                        .stock(i % 100)        // 재고도 0~99 사이로
                        .categoryId(targetCategory.getId())
                        .create());

                // 너무 큰 리스트는 메모리 부하가 올 수 있으니 100개 단위로 끊어서 저장
                if (i % 100 == 0) {
                    productRepository.saveAll(products);
                    products.clear();
                    log.info("{}개 데이터 저장 완료...", i);
                }
            }
        }

        log.info("더미 데이터 생성 완료!");
    }
}
