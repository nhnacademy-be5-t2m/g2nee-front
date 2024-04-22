package com.t2m.g2nee.front.advice;

import com.t2m.g2nee.front.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 예외 발생시, 처리하기 위한 ControllerAdvice입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@ControllerAdvice
public class CustomExceptionAdvice {

    /**
     * e에는 백엔드 shop서버에서 받아오는 에러 코드, 메시지를 가지고 있고
     * 이를 errorPage에서 보여줍니다.
     *
     * @param e
     * @param model
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public String showError(CustomException e, Model model) {
        model.addAttribute("error", e);
        return "/error/errorPage";
    }

    /**
     * 프론트쪽에서 404 not found 시 처리합니다.
     *
     * @param model
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public String notFoundError(Model model) {
        model.addAttribute("error", new CustomException(HttpStatus.NOT_FOUND, "페이지를 찾을 수 없습니다."));
        return "/error/errorPage";
    }

    /**
     * front에서 500 error 시 처리합니다.
     *
     * @param ex
     * @param model
     * @return
     */
    @ExceptionHandler(ResponseStatusException.class)
    public String serverError(ResponseStatusException ex, Model model) {
        if (ex.getStatus() == HttpStatus.INTERNAL_SERVER_ERROR) {
            model.addAttribute("error", new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 문제가 발생하였습니다."));
            return "/error/errorPage";
        }
        throw ex;
    }


}