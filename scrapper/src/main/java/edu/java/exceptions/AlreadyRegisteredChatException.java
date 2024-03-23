package edu.java.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlreadyRegisteredChatException extends RuntimeException {
    private String message;
}
