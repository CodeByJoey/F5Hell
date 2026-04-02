package com.f5hell.controller;

import com.f5hell.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    @DisplayName("[성공]: 상품 등록 폼 조회")
    void create_form_success() throws Exception {
        mockMvc.perform(get("/products/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/createForm"))
                .andExpect(model().attributeExists("productForm"));
    }

    @Test
    @DisplayName("[예외]: 상품 등록(가격 음수 수량 입력 저장)")
    void create_fail_by_negative_price() throws Exception {
        mockMvc.perform(post("/products/create")
                        .param("name", "불량상품")
                        .param("price", "-100")
                        .param("stock", "10"))
                .andExpect(view().name("product/createForm"))
                .andExpect(model().hasErrors());
    }

    @Test
    @DisplayName("[성공]: 상품 목록 폼 조회")
    void getList_success() throws Exception {
        mockMvc.perform(get("products/getList"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/productList"))
                .andExpect(model().attributeExists("products"));
    }
}