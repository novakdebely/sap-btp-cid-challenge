package org.gpopov.interview.sap.model;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class RepositorySecretId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "repository_id")
    private UUID repositoryId;

	@Column(name = "secret_id")
    private UUID secretId;
}
