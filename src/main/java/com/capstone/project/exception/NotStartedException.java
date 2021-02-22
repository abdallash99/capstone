package com.capstone.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class NotStartedException extends RuntimeException{
    public NotStartedException(String message) {
        super(message);
    }
}
