package org.gpopov.interview.sap.dto;

import lombok.Data;

@Data
public class ValidationResponse {

    private boolean valid;
    private String message;

    public static ValidationResponse valid() {
        ValidationResponse r = new ValidationResponse();
        r.valid = true;
        r.message = "Secret is valid";
        return r;
    }

    public static ValidationResponse invalid() {
        ValidationResponse r = new ValidationResponse();
        r.valid = false;
        r.message = "Secret is invalid";
        return r;
    }
}
