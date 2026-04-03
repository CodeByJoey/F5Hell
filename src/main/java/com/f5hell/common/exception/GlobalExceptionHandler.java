package com.f5hell.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.thymeleaf.exceptions.TemplateProcessingException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public String handleBusinessException(BusinessException e, Model model) {
        log.error("Business Error: {}", e.getMessage());
        model.addAttribute("errorMessage", e.getMessage());
        return "error/error-page";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handle404(Exception e, Model model) {
        log.error("Unexpected Error: ", e);
        model.addAttribute("errorMessage", "시스템 오류가 발생했습니다.");
        return "error/404";
    }

    @ExceptionHandler(TemplateProcessingException.class)
    public String handleTemplate(Exception e, Model model) {
        model.addAttribute("errorMessage", "템플릿 오류");
        return "error/500";
    }

    @ExceptionHandler(Exception.class)
    public String handleAllException(Exception e, Model model) {
        log.error("Unexpected Error: ", e);
        model.addAttribute("errorMessage", "시스템 오류가 발생했습니다.");
        return "error/500"; // templates/error/500.html
    }
}
