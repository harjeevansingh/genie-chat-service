package exceptions;


import com.taskmanager.constants.enums.exceptionCodes.RootExceptionCodes;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author harjeevanSingh
 */
public class RootException extends RuntimeException {
    HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
    private String code;
    private String message;


    private Map<String, String> errors = new HashMap<String, String>();

    public RootException() {
        super();
    }

    public RootException(String code, String message) {
        super(code.concat(" : " + message));
        this.code = code;
        this.message = message;
        errors.put("message", message);
        errors.put("error_code", code);
    }

    public RootException(RootExceptionCodes exceptionCode) {
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
        errors.put("message", message);
        errors.put("error_code", code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public void addError(String code, String message) {
        this.code = code;
        this.message = message;
        errors.put("message", message);
        errors.put("error_code", code);
    }
}
