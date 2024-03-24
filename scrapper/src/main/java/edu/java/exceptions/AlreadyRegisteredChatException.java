package edu.java.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlreadyRegisteredChatException extends RuntimeException {
    private final String message = "";
}
