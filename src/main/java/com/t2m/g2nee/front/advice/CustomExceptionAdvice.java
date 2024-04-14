package com.t2m.g2nee.front.advice;

import com.t2m.g2nee.front.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class CustomExceptionAdvice {

    @ExceptionHandler(CustomException.class)
    public String showError(CustomException e, Model model){
        model.addAttribute("error", e);
        return "/error/errorPage";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String notFoundError(Model model){
        model.addAttribute("error", new CustomException(HttpStatus.NOT_FOUND, "페이지를 찾을 수 없습니다."));
        return "/error/errorPage";
    }

    @ExceptionHandler(ResponseStatusException.class)
    public String serverError(ResponseStatusException ex, Model model){
        if(ex.getStatus() == HttpStatus.INTERNAL_SERVER_ERROR){
            model.addAttribute("error", new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 문제가 발생하였습니다."));
            return "/error/errorPage";
        }
        throw ex;
    }
}