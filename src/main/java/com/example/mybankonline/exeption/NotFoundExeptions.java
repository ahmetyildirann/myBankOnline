package com.example.mybankonline.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundExeptions extends RuntimeException {
    public NotFoundExeptions(String message) {
        super(message);
    }
}
