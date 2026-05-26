package org.gpopov.interview.sap.repository;

import java.util.UUID;

import org.gpopov.interview.sap.model.RepositoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryRepo extends JpaRepository<RepositoryEntity, UUID> {

    boolean existsByUrl(String url);
}
