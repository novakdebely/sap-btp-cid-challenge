package org.gpopov.interview.sap.repository;

import org.gpopov.interview.sap.model.SecretEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SecretRepo extends JpaRepository<SecretEntity, UUID> {
}
