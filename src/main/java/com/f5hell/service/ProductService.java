package com.f5hell.service;

import com.f5hell.common.entity.UploadFile;
import com.f5hell.common.util.FileService;
import com.f5hell.domain.dto.ProductRequest;
import com.f5hell.domain.entity.Category;
import com.f5hell.domain.entity.Product;
import com.f5hell.repository.CategoryRepository;
import com.f5hell.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // JPA가 변경감지를 하지않게 설정 (실수 방지 및 메모리 성능 최적화)
public class ProductService {
    private final FileService fileService;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional // 대신 insert, update, delete에는 모두 붙여준다. (1. 더티체크 적용, 2. 예외발생 시 롤백)
    public Long create(ProductRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId()).orElse(null);

        UploadFile uploadFile = fileService.saveFile(request.getImageFile());

        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .stock(request.getStock())
                .category(category)
                .uploadFileName(uploadFile.getUploadFileName())
                .storeFileName(uploadFile.getStoreFileName())
                .create();

        return productRepository.save(product).getId();
    }

    public List<Product> getList() {
        return productRepository.findAll();
    }

    public Page<Product> getListWithPaging(String name, Long categoryId, Pageable pageable) {
        return productRepository.searchProducts(name, categoryId, pageable);
    }

    public Product get(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Transactional
    public Long update(Long id, ProductRequest request) {
        Product findProduct = productRepository.findById(id).orElse(null);
        Category category = categoryRepository.findById(request.getCategoryId()).orElse(null);

        return findProduct.update(request, category);
    }

    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

}
