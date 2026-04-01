package com.f5hell.common.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final, @NonNull이 붙은 필드만을 매개변수로 하는 생성자를 생성하겠다.
@Getter
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE("C001", "적절하지 않은 입력값입니다."),

    // Product
    NOT_ENOUGH_STOCK("P001", "재고가 부족합니다.");

    private final String code;
    private final String message;
    private String test;
}
