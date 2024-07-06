package com.lys.exception;

public enum BusinessExceptionEnum {
    SERVER_ERROR(500,"后端接口报错");

    private final Integer code;
    private final String message;

    BusinessExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }



}
