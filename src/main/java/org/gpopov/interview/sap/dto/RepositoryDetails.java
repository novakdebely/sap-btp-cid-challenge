package org.gpopov.interview.sap.dto;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RepositoryDetails {

	private UUID id;
	
    @NotBlank(message = "URL must not be blank")
    private String url;

    private String name;

    private String description;
    
    List<UUID> secretIds;
}
