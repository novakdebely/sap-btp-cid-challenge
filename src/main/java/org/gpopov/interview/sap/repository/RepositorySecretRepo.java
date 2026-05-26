package org.gpopov.interview.sap.repository;

import java.util.List;
import java.util.UUID;

import org.gpopov.interview.sap.model.RepositoryEntity;
import org.gpopov.interview.sap.model.RepositorySecretEntity;
import org.gpopov.interview.sap.model.RepositorySecretId;
import org.gpopov.interview.sap.model.SecretEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RepositorySecretRepo extends JpaRepository<RepositorySecretEntity, RepositorySecretId> {

	@Query("SELECT secret FROM RepositorySecretEntity WHERE repository.id = :repositoryId")
    List<SecretEntity> findSecretsByRepositoryId(@Param("repositoryId") UUID repositoryId);
	
	@Query("SELECT repository FROM RepositorySecretEntity WHERE secret.id = :secretId")
    List<RepositoryEntity> findRepositoriesBySecretId(@Param("secretId") UUID secretId);
	
	@Query("DELETE FROM RepositorySecretEntity WHERE repository.id = :repositoryId")
    @Modifying
    void deleteAllByRepositoryId(@Param("repositoryId") UUID repositoryId);
	
	@Query("DELETE FROM RepositorySecretEntity WHERE secret.id = :secretId")
    @Modifying
    void deleteAllBySecretId(@Param("secretId") UUID secretId);
	
	@Query("DELETE FROM RepositorySecretEntity WHERE repository.id = :repositoryId AND secret.id = :secretId")
    @Modifying
    void deleteAllByRepositoryIdAndSecretId(@Param("repositoryId") UUID repositoryId, @Param("secretId") UUID secretId);
}
