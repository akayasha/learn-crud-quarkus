package com.example;

import jakarta.ws.rs.core.Response;

public class ResponseHandler {

    public static Response generateResponse(int code, String message, Object data) {
        return Response.status(code)
                .entity(new ResponseData(code, message, data))
                .build();
    }

    public static Response generateErrorResponse(String errorMessage) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ResponseData(Response.Status.BAD_REQUEST.getStatusCode(), errorMessage, null))
                .build();
    }

    public static class ResponseData {
        private int code;
        private String message;
        private Object data;

        public ResponseData(int code, String message, Object data) {
            this.code = code;
            this.message = message;
            this.data = data;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }
}
