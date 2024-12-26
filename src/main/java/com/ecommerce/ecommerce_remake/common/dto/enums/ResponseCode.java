package com.ecommerce.ecommerce_remake.common.dto.enums;

public enum ResponseCode {

    RESP_NOT_FOUND("404"),
    RESP_SUCCESS("200");

    private String value;

    private ResponseCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

