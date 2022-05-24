package de.hska.iwi.CategoryService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class CategoryNotEmptyAdvice {

    @ResponseBody
    @ExceptionHandler(CategoryNotEmptyException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String employeeNotFoundHandler(CategoryNotEmptyException ex) {
        return ex.getMessage();
    }
}