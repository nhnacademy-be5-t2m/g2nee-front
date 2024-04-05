package com.t2m.g2nee.front.advice;

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
}