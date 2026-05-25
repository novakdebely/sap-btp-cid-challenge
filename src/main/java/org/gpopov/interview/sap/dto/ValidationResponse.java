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

    public static ValidationResponse invalid(String errorMessage) {
        ValidationResponse r = new ValidationResponse();
        r.valid = false;
        r.message = errorMessage == null? "Secret is invalid":errorMessage;
        return r;
    }
    
    public boolean isValid() {
    	return this.valid;
    }
}
