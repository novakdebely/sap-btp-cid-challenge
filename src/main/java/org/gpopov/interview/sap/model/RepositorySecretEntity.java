package org.gpopov.interview.sap.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "repository_secret")
public class RepositorySecretEntity {

	@EmbeddedId
    private RepositorySecretId id;

	@ManyToOne()
    @MapsId("repositoryId")
    @JoinColumn(name = "repository_id")
    private RepositoryEntity repository;
    
	@ManyToOne()
    @MapsId("secretId")
    @JoinColumn(name = "secret_id")
    private SecretEntity secret;
	
	public RepositorySecretEntity(RepositoryEntity repositoryEntity,  SecretEntity secretEntity) {
        if (repositoryEntity == null || repositoryEntity.getId() == null
        		|| secretEntity == null || secretEntity.getId() == null) {
            throw new IllegalArgumentException("RepositoryEntity and SecretEntity with ID cannot be null");
        }
        this.repository = repositoryEntity;
        this.secret = secretEntity;

        // Ensure the composite key is initialized
        this.id = new RepositorySecretId(repositoryEntity.getId(), secretEntity.getId());
    }
}
