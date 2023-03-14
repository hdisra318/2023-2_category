package com.product.api.dto;

public class ApiResponse {

    /* El mensaje de respuesta */
    private String message;

    public ApiResponse(String message){

        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
