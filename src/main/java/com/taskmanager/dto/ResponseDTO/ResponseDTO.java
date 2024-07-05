package com.taskmanager.dto.ResponseDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.taskmanager.constants.CommonConstants;
import lombok.*;

/**
 * @author harjeevanSingh
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDTO<T> {

    private boolean success;
    private String statusCode;
    private String debugCode;
    private String message;
    private T data;

    public ResponseDTO(T data) {
        this.success = true;
        this.statusCode = CommonConstants.SUCCESS_200;
        this.message = CommonConstants.GENERIC_SUCCESS;
        this.data = data;
    }
}