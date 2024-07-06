package com.chatservice.constants.enums.exceptionCodes;

/**
 * @author harjeevanSingh
 */

public enum RootExceptionCodes implements ExceptionCodes {
    INTERNAL_SERVER_ERROR("BE_500", "Internal server error!"),
    CONVERSATION_NOT_FOUND("BE_501", "Conversation not found!"),
    ;

    private final String code;
    private final String message;

    RootExceptionCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
