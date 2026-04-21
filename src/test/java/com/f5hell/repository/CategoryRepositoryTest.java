package com.f5hell.repository;

import com.f5hell.domain.entity.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("카테고리 목록 조회")
    void findAllCategoryTest() {

        Category category = Category.builder()
                .name("카테고리1")
                .useYn("Y")
                .build();

        categoryRepository.save(category);

        List<Category> result = categoryRepository.findAll();

        assertThat(result).hasSize(1);
        assertThat(result).extracting(Category::getName).contains("카테고리1");
    }
}


