package ua.dmitriiev.beautysaloon.lib.exceptions;

public class NotUniquePhoneNumberException extends RuntimeException {
    public NotUniquePhoneNumberException(String message) {
        super(message);
    }
}
