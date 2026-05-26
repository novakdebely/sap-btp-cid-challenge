package org.gpopov.interview.sap.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.gpopov.interview.sap.dto.Repository;
import org.gpopov.interview.sap.dto.RepositoryDetails;
import org.gpopov.interview.sap.model.RepositoryEntity;
import org.gpopov.interview.sap.model.RepositorySecretEntity;
import org.gpopov.interview.sap.model.SecretEntity;
import org.gpopov.interview.sap.repository.RepositoryRepo;
import org.gpopov.interview.sap.repository.RepositorySecretRepo;
import org.gpopov.interview.sap.repository.SecretRepo;
import org.gpopov.interview.sap.service.RepositoryService;
import org.gpopov.interview.sap.util.EntityToModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RepositoryServiceImpl implements RepositoryService{

	@Autowired
    private RepositoryRepo repositoryRepo;
	
	@Autowired
    private SecretRepo secretRepo;
	
	@Autowired
	private RepositorySecretRepo repositorySecretRepo;

	@Override
    @Transactional
    public Repository create(Repository repository) {
        if (repositoryRepo.existsByUrl(repository.getUrl())) {
            throw new IllegalArgumentException("Repository URL already exists: " + repository.getUrl());
        }

        RepositoryEntity entity = new RepositoryEntity();
        entity.setUrl(repository.getUrl());
        entity.setName(repository.getName());
        entity.setDescription(repository.getDescription());

        return EntityToModelConverter.toRepository(repositoryRepo.save(entity));
    }

	@Override
    @Transactional(readOnly = true)
    public List<Repository> listAll() {
        return repositoryRepo.findAll().stream()
                .map(EntityToModelConverter::toRepository)
                .collect(Collectors.toList());
    }

	@Override
    @Transactional(readOnly = true)
    public RepositoryDetails findById(UUID id) {
		Optional<RepositoryEntity> repositoryEntityOpt = repositoryRepo.findById(id);
    	if (repositoryEntityOpt.isEmpty()) {
            throw new EntityNotFoundException("Repository not found: " + id);
        }
    	RepositoryEntity entiry = repositoryEntityOpt.get();
    	List<SecretEntity> secrets = repositorySecretRepo.findSecretsByRepositoryId(entiry.getId());

        return EntityToModelConverter.toRepositoryDetails(repositoryEntityOpt.get(), secrets);
    }

	@Override
    @Transactional
    public void delete(UUID id) {
        Optional<RepositoryEntity> repositoryEntityOpt = repositoryRepo.findById(id);
        if (repositoryEntityOpt.isEmpty()) {
            throw new EntityNotFoundException("Repository not found: " + id);
        }
        RepositoryEntity entity = repositoryEntityOpt.get();
        // Remove assigned secrets
        repositorySecretRepo.deleteAllByRepositoryId(entity.getId());
        
        repositoryRepo.deleteById(id);
    }

	@Override
	@Transactional
	public void assignSecretToRepository(UUID repoId, UUID secretId) {
		Optional<RepositoryEntity> repositoryOpt = repositoryRepo.findById(repoId);
		if (repositoryOpt.isEmpty()) {
            throw new EntityNotFoundException("Repository not found: " + repoId);
        }
		Optional<SecretEntity> secretOpt = secretRepo.findById(secretId);
		if (secretOpt.isEmpty()) {
            throw new EntityNotFoundException("Secret not found: " + secretId);
        }
		RepositorySecretEntity newEntiry = new RepositorySecretEntity(repositoryOpt.get(), secretOpt.get());
		repositorySecretRepo.save(newEntiry);
		
		//repositoryRepo.save(repositoryOpt.get().addSecret(secretOpt.get()));
	}
	
	@Override
	@Transactional
	public void detachSecretFromRepository(UUID repoId, UUID secretId) {
		Optional<RepositoryEntity> repositoryOpt = repositoryRepo.findById(repoId);
		if (repositoryOpt.isEmpty()) {
            throw new EntityNotFoundException("Repository not found: " + repoId);
        }
		Optional<SecretEntity> secretOpt = secretRepo.findById(secretId);
		if (secretOpt.isEmpty()) {
            throw new EntityNotFoundException("Secret not found: " + secretId);
        }

		repositorySecretRepo.deleteAllByRepositoryIdAndSecretId(
				repositoryOpt.get().getId(),
				secretOpt.get().getId());
	}
}
