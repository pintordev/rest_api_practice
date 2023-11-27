package com.pintor.rest_api_practice.base.exceptionHandler;

import com.pintor.rest_api_practice.base.rsData.RsData;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice(annotations = {RestController.class}) // 모든 RestController에서 발생한 예외 핸들링 여기로
public class ApiGlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(NOT_FOUND)
    public RsData<String> errorHandler(MethodArgumentNotValidException exception) { // binding error 처리

        String message = exception
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("/"));

        String data = exception
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getCode)
                .collect(Collectors.joining("/"));

        return RsData.of("F-MethodArgumentNotValidException", message, data);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public RsData<String> errorHandler(RuntimeException exception) {

        String message = exception.toString();
        String data = exception.getMessage();

        return RsData.of("F-RunTimeException", message, data);
    }
}
