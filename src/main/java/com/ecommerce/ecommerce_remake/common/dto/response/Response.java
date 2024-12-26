package com.ecommerce.ecommerce_remake.common.dto.response;

import com.ecommerce.ecommerce_remake.common.dto.enums.ResponseCode;

public class Response {
    private ResponseCode responseCode;
    private String responseDescription;
    private Object responseObject;

    public Response(ResponseCode responseCode, String responseDescription) {
        this.responseCode = responseCode;
        this.responseDescription = responseDescription;
    }

    public Response(ResponseCode responseCode, String responseDescription, Object responseObject) {
        this.responseCode = responseCode;
        this.responseDescription = responseDescription;
        this.responseObject = responseObject;
    }

    public ResponseCode getResponseCode() {
        return this.responseCode;
    }

    public String getResponseDescription() {
        return this.responseDescription;
    }

    public Object getResponseObject() {
        return this.responseObject;
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public void setResponseDescription(String responseDescription) {
        this.responseDescription = responseDescription;
    }

    public void setResponseObject(Object responseObject) {
        this.responseObject = responseObject;
    }

}
