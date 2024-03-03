package edu.java.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlreadyRegisteredChatException extends RuntimeException {
    private final String message;
}
