package org.gpopov.interview.sap.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @ManyToMany
    @JoinTable(
    		  name = "repositories_secrets", 
    		  joinColumns = @JoinColumn(name = "repository_id"), 
    		  inverseJoinColumns = @JoinColumn(name = "secret_id"))
    private List<SecretEntity> secrets = new ArrayList<>();

}
