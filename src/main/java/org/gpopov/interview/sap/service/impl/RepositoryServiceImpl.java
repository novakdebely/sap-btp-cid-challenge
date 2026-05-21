package org.gpopov.interview.sap.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

import org.gpopov.interview.sap.dto.Repository;
import org.gpopov.interview.sap.model.RepositoryEntity;
import org.gpopov.interview.sap.model.SecretEntity;
import org.gpopov.interview.sap.repository.RepositoryRepo;
import org.gpopov.interview.sap.repository.SecretRepo;
import org.gpopov.interview.sap.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RepositoryServiceImpl implements RepositoryService{

	@Autowired
    private RepositoryRepo repositoryRepo;
	
	@Autowired
    private SecretRepo secretRepo;

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

        return toRepository(repositoryRepo.save(entity));
    }

	@Override
    @Transactional(readOnly = true)
    public List<Repository> listAll() {
        return repositoryRepo.findAll().stream()
                .map(this::toRepository)
                .collect(Collectors.toList());
    }

	@Override
    @Transactional(readOnly = true)
    public Repository findById(UUID id) {
		Optional<RepositoryEntity> repositoryEntityOpt = repositoryRepo.findById(id);
    	if (repositoryEntityOpt.isEmpty()) {
            throw new EntityNotFoundException("Repository not found: " + id);
        }

        return toRepository(repositoryEntityOpt.get());
    }

	@Override
    @Transactional
    public void delete(UUID id) {
        if (!repositoryRepo.existsById(id)) {
            throw new EntityNotFoundException("Repository not found: " + id);
        }
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
		RepositoryEntity repositoryEntity = repositoryOpt.get();
		repositoryEntity.getSecrets().add(secretOpt.get());
		repositoryRepo.save(repositoryEntity);
	}

    private Repository toRepository(RepositoryEntity entity) {
    	Repository r = new Repository();
        r.setId(entity.getId());
        r.setUrl(entity.getUrl());
        r.setName(entity.getName());
        r.setDescription(entity.getDescription());
        return r;
    }
}
