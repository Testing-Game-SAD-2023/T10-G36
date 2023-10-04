package com.sad.t5t1.project.exception;

public class FileStorageExceptionT1 extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public FileStorageExceptionT1(String message) {
        super(message);
    }

    public FileStorageExceptionT1(String message, Throwable cause) {
        super(message, cause);
    }
}
