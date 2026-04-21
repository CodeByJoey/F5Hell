package com.f5hell.service;

import com.f5hell.domain.entity.Category;
import com.f5hell.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getList() {
        return categoryRepository.findAll();
    }
}
