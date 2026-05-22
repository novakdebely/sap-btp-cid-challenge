package org.gpopov.interview.sap.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "secrets")
public class SecretEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(name = "secret_value", nullable = false)
    private String secretValue;

    @ManyToMany(mappedBy = "secrets",
    		fetch = FetchType.EAGER,
    		cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<RepositoryEntity> repositories;
}
