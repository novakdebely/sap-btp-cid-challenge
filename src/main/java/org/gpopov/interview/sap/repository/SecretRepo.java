package org.gpopov.interview.sap.repository;

import java.util.UUID;

import org.gpopov.interview.sap.model.SecretEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecretRepo extends JpaRepository<SecretEntity, UUID> {
}
