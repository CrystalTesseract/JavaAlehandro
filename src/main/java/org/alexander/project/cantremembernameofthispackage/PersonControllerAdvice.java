package org.alexander.project.cantremembernameofthispackage;

import org.alexander.project.controllers.dto.GeneralErrorDto;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class PersonControllerAdvice {

    @ExceptionHandler(GeneralError.class)
    @ResponseBody
    public GeneralErrorDto badPersonRequestResponce(GeneralError ex) {
        return new GeneralErrorDto(ex.getStatusCode(), ex.getMessage());
    }
}
