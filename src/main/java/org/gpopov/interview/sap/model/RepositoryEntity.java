package org.gpopov.interview.sap.model;

import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "repositories")
public class RepositoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String url;

    @Column
    private String name;

    @Column
    private String description;

    @OneToMany()
    @JoinTable(name = "repositoriy_secret", 
    		   joinColumns = @JoinColumn(name = "repository_id", referencedColumnName = "id"),
    		   inverseJoinColumns = @JoinColumn(name =  "secret_id", referencedColumnName = "id"))
    private Set<SecretEntity> secrets;
    
    /**
     * 
     * @param newSecret
     */
    public RepositoryEntity addSecret(SecretEntity newSecret) {
    	this.getSecrets().add(newSecret);
    	newSecret.getRepositories().add(this);
    	return this;
    }
    
    /**
     * 
     * @param newSecret
     */
    public RepositoryEntity removeSecret(SecretEntity newSecret) {
    	this.getSecrets().remove(newSecret);
    	newSecret.getRepositories().remove(this);
    	return this;
    }

}
