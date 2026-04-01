package com.f5hell.controller;

import com.f5hell.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    @DisplayName("[성공]: 상품 등록 폼 조회")
    void success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products/create"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("product/createForm"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("productForm"));
    }

    @Test
    @DisplayName("[예외]: 가격 음수 수량 입력 저장")
    void fail_negative() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/products/create")
                        .param("name", "불량상품")
                        .param("price", "-100") // @Min(0) 위반
                        .param("stock", "10"))
                .andExpect(MockMvcResultMatchers.view().name("product/createForm")) // 다시 폼으로 이동
                .andExpect(MockMvcResultMatchers.model().hasErrors()); // 에러가 있는지 확인
    }
}