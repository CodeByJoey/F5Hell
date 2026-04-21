package com.f5hell.controller;

import com.f5hell.domain.dto.ProductRequest;
import com.f5hell.domain.dto.ProductResponse;
import com.f5hell.domain.entity.Category;
import com.f5hell.domain.entity.Product;
import com.f5hell.service.CategoryService;
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

import java.util.List;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @ModelAttribute("categoryList")
    public List<Category> categoryList() {
        return categoryService.getList();
    }

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
                      @RequestParam(required = false, name = "categoryId") Long categoryId,
                      @PageableDefault(page = 0, size = 12, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        //List<Product> products = productService.getList();
        Page<Product> listWithPaging = productService.getListWithPaging(name, categoryId, pageable);
        log.info("listWithPaging: {}", listWithPaging);
        model.addAttribute("products", listWithPaging);
        return "product/productList";
    }

    @GetMapping("/get/{id}")
    public String getList(@PathVariable("id") Long id, Model model) {
        Product product = productService.get(id);

        ProductResponse productResponse = ProductResponse.from(product);

        model.addAttribute("product", productResponse);
        return "product/productDetail";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.get(id);

        ProductResponse productResponse = ProductResponse.from(product);

        model.addAttribute("product", productResponse);
        model.addAttribute("productId", id);
        return "product/updateForm";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model,
                         @Valid @ModelAttribute("product") ProductRequest request,
                         BindingResult result) {
        if(result.hasErrors()) {
            model.addAttribute("productId", id);
            return "product/updateForm";
        }

        Long savedId = productService.update(id, request);
        return "redirect:/products/get/" + savedId;
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        productService.delete(id);
        return "redirect:/products/getList";
    }

}
