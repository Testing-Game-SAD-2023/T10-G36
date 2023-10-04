package com.sad.t5t1.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FileNotFoundExceptionT1 extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public FileNotFoundExceptionT1(String message) {
        super(message);
    }

    public FileNotFoundExceptionT1(String message, Throwable cause) {
        super(message, cause);
    }
}
