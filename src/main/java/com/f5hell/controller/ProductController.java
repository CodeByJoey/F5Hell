package com.f5hell.controller;

import com.f5hell.domain.dto.ProductRequest;
import com.f5hell.domain.entity.Product;
import com.f5hell.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        return "redirect:/products/getList";
    }

    @GetMapping("/getList")
    public String getList(Model model,
                      @RequestParam(required = false, name = "name", defaultValue = "") String name,
                      @PageableDefault(page = 0, size = 12, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        //List<Product> products = productService.getList();
        Page<Product> listWithPaging = productService.getListWithPagingByName(name, pageable);
        log.info("listWithPaging: {}", listWithPaging);
        model.addAttribute("products", listWithPaging);
        return "product/productList";
    }

    @GetMapping("/get/{id}")
    public String getList(@PathVariable("id") Long id, Model model) {
        Product product = productService.get(id);
        model.addAttribute("product", product);
        return "product/productDetail";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.get(id);
        model.addAttribute("productForm", product);
        return "product/updateForm";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id,
                         @Valid @ModelAttribute("productForm") ProductRequest request,
                         BindingResult result) {
        if(result.hasErrors()) {
            return "product/updateForm";
        }
        Long savedId = productService.update(id, request);
        return "redirect:/products/get/" + savedId;
    }
}
