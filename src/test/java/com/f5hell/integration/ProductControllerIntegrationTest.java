package com.f5hell.integration;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProductControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("[성공]: 상품 저장 통합테스트")
    void success() throws Exception {
        // given: 실제 서비스와 DB가 준비된 상태에서

        // when: 실제 URL로 요청을 던지면
        mockMvc.perform(MockMvcRequestBuilders.post("/products/create")
                        .param("name", "실제 상품")
                        .param("price", "10000")
                        .param("stock", "5"))
                // then: 실제 서비스 -> 리포지토리 -> DB까지 다 거쳐서 성공해야 함
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection()) // 저장 후 리다이렉트 확인
                .andExpect(MockMvcResultMatchers.redirectedUrl("/products"));
    }

    @Test
    @DisplayName("[성공]: 상품 단건 조회 통합테스트")
    void getSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products/get/{id}", 1L))
                .andExpect(MockMvcResultMatchers.view().name("product/productDetail")) // 어떤 페이지로 가는지
                .andExpect(MockMvcResultMatchers.model().attributeExists("product")) // 모델에 product가 있는지
                .andExpect(MockMvcResultMatchers.model().attribute("product",
                        Matchers.hasProperty("name",
                                Matchers.is("F5 상품 1")))) // 특정 속성값 확인
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
