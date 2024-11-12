package com.service.beta.data.response;

import lombok.Data;

@Data
public class Response {
    private String message;
    private String code;
    private Object object;

    public Response(String message, String code, Object object) {
        this.message = message;
        this.code = code;
        this.object = object;
    }
}
