package ru.yandex.practicum.catsgram.exception;

public class IncorrectCountException extends RuntimeException {
    public IncorrectCountException(String message) {
        super(message);
    }
}
