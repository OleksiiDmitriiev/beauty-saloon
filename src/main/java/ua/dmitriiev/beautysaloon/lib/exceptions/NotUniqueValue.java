package ua.dmitriiev.beautysaloon.lib.exceptions;

public class NotUniqueValue extends RuntimeException {

    public NotUniqueValue(String message) {
        super(message);
    }

    public NotUniqueValue(String message, Throwable cause) {
        super(message, cause);
    }
}
