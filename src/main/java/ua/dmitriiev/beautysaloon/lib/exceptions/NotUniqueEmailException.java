package ua.dmitriiev.beautysaloon.lib.exceptions;

public class NotUniqueEmailException extends RuntimeException {
    public NotUniqueEmailException(String message) {
        super(message);
    }
}