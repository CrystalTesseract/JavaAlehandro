package org.alexander.project.exception;

import lombok.Data;

@Data
public class GeneralError extends RuntimeException {
    private String statusCode;
    private String message;
    public GeneralError(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
