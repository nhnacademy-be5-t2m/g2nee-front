package com.t2m.g2nee.front.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.t2m.g2nee.front.exception.CustomException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class CustomExceptionAdvice {

    @ExceptionHandler(CustomException.class)
    public String showError(CustomException e, Model model){
        model.addAttribute("error", e);
        return "/error/errorPage";
    }

    @ExceptionHandler(JsonProcessingException.class)
    public String jsonError() {
        //todo: 뭘 처리 해야지...
        return null;
    }
}