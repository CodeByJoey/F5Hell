package com.f5hell.controller;

import com.f5hell.domain.dto.ProductRequest;
import com.f5hell.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
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
        productService.save(request);
        return "redirect:/products";
    }
}
