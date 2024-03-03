package edu.java.bot.exceptions;

import edu.java.bot.model.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Arrays;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class ApiExceptionsHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ApiErrorResponse handle(MethodArgumentNotValidException e) {
        log.error("Error " + e.getMessage());
        return new ApiErrorResponse(
            "Некорректные параметры запроса",
            "400",
            "BadRequest",
            e.getMessage(),
            Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).toList()
        );
    }
}
