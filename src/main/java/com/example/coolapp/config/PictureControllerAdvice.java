package com.example.coolapp.config;

import com.example.coolapp.dto.ErrorResponse;
import com.example.coolapp.exceptions.IncorrectPictureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PictureControllerAdvice {
    @ExceptionHandler(IncorrectPictureException.class)
    public ResponseEntity<?> handleIncorrectPicture(IncorrectPictureException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(e.getMessage()));
    }
}
