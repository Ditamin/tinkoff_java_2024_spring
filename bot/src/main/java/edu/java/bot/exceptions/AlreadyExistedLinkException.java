package edu.java.bot.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlreadyExistedLinkException extends RuntimeException{
    private final String message;
}
