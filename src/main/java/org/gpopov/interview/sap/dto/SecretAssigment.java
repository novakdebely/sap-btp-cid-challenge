package org.gpopov.interview.sap.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SecretAssigment {
	 
	@JsonProperty("repo_id")
	@NotNull(message = "Repository ID must not be blank")
    private UUID repoId;

	@JsonProperty("secret_id")
	@NotNull(message = "Secret ID must not be blank")
	private UUID secretId;
}
