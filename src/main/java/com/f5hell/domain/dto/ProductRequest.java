package com.f5hell.domain.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder(buildMethodName = "create")
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    @NotBlank
    private String name;
    @NotNull
    @Min(value = 0)
    private Long price;
    @NotNull
    @Min(value = 1)
    private Integer stock;
    private Long categoryId;
}