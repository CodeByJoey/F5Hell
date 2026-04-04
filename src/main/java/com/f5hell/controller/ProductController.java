package com.f5hell.controller;

import com.f5hell.domain.dto.ProductRequest;
import com.f5hell.domain.entity.Product;
import com.f5hell.service.ProductService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("productForm", new ProductRequest());
        return "product/createForm";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("productForm") ProductRequest request,
                         BindingResult result) {
        if(result.hasErrors()) {
            return "product/createForm";
        }
        productService.create(request);
        return "redirect:/products";
    }

    @GetMapping("/getList")
    public String getList(Model model, Pageable pageable) {
        //List<Product> products = productService.getList();
        Page<Product> listWithPaging = productService.getListWithPaging(pageable.getPageNumber(), pageable.getPageSize());
        log.info("listWithPaging: {}", listWithPaging);
        model.addAttribute("products", listWithPaging);
        return "product/productList";
    }
}
