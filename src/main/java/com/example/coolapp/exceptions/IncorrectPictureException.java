package com.example.coolapp.exceptions;

public class IncorrectPictureException extends RuntimeException {
    public IncorrectPictureException() {
        super("Submitted incorrect picture!!!");
    }
}
