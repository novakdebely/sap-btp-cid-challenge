package org.gpopov.interview.sap.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Repository {

	private UUID id;
	
    @NotBlank(message = "URL must not be blank")
    private String url;

    private String name;

    private String description;
}
