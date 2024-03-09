package edu.java.exceptions;

import edu.java.model.ApiErrorResponse;
import java.util.Arrays;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionsHandler {
    private final static String ERROR = "ERROR ";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ApiErrorResponse handle(MethodArgumentNotValidException e) {
        log.error(ERROR + e.getMessage());
        return new ApiErrorResponse(
            "Некорректные параметры запроса",
            "400",
            "BadRequest",
            e.getMessage(),
            Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).toList()
        );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public ApiErrorResponse handle(NoSuchElementException e) {
        log.error(ERROR + e.getMessage());
        return new ApiErrorResponse(
            e.getMessage(),
            "404",
            "NotFound",
            e.getMessage(),
            Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).toList()
        );
    }
}
