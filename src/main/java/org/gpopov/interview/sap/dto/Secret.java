package org.gpopov.interview.sap.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Secret {

	private UUID id;
	 
    @NotBlank(message = "Name must not be blank")
    private String name;

    private SecretType type;

    @NotBlank(message = "Value must not be blank")
	private String value;
}
