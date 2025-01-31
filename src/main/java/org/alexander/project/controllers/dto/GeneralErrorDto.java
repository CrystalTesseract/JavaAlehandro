package org.alexander.project.controllers.dto;

import lombok.Data;

@Data
public class GeneralErrorDto {
    private final String statusCode;
    private final String message;

    public GeneralErrorDto(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
