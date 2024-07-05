package com.taskmanager.config;

import com.taskmanager.dto.ResponseDTO.ResponseDTO;
import exceptions.RootException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author harjeevanSingh
 */

@Configuration
@Slf4j
@RestControllerAdvice
public class ExceptionConfig {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ResponseDTO<Object>> handleException(Exception ex) {
        ResponseDTO<Object> response = new ResponseDTO<>();
        response.setMessage(ex.getMessage());
        response.setStatusCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
        response.setSuccess(false);
        response.setData("Unhandled Exception");
        log.warn("Unhandled exception: " + ex.getMessage(), ex);
        return ResponseEntity.status(500).body(response);
    }

    @ExceptionHandler(RootException.class)
    @ResponseBody
    public ResponseEntity<ResponseDTO<Object>> handleRootException(RootException ex){
        ResponseDTO response = new ResponseDTO();
        response.setMessage(ex.getMessage());
        response.setSuccess(false);
        response.setStatusCode(String.valueOf(ex.getStatusCode().value()));
        response.setDebugCode(ex.getCode());
        log.warn("Unhandled root exception: " + ex.getMessage(), ex);
        return ResponseEntity.status(ex.getStatusCode()).body(response);
    }
}
