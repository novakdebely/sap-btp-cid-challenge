package org.gpopov.interview.sap.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ValidationRequest {
	
	@JsonProperty("secret_id")
	@NotNull(message = "Secret Id can not be null")
	private UUID secretId;
	
	@JsonProperty("secret_value")
	@NotBlank(message = "Secret Value can not be empty")
	private String secretValue;

}
