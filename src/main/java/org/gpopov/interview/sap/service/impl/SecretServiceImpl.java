package org.gpopov.interview.sap.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.gpopov.interview.sap.dto.Secret;
import org.gpopov.interview.sap.dto.ValidationResponse;
import org.gpopov.interview.sap.model.SecretEntity;
import org.gpopov.interview.sap.repository.RepositoryRepo;
import org.gpopov.interview.sap.repository.RepositorySecretRepo;
import org.gpopov.interview.sap.repository.SecretRepo;
import org.gpopov.interview.sap.service.SecretService;
import org.gpopov.interview.sap.util.EntityToModelConverter;
import org.gpopov.interview.sap.util.SecretUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SecretServiceImpl implements SecretService {
	
	private static final String HEADER_PREFIX_BEARER = "Bearer ";
	private static final String HEADER_PREFIX_TOKEN = "token ";

	@Autowired
    private SecretRepo secretRepo;
	
	@Autowired
    private RepositoryRepo repositoryRepo;
	
	@Autowired
	private RepositorySecretRepo repositorySecretRepo;

	@Override
    @Transactional
    public Secret create(Secret request) {
        SecretEntity entity = new SecretEntity();
        entity.setName(request.getName());
        entity.setType(request.getType().name());
        entity.setSecretValue(SecretUtil.encode(request.getValue()));

        return EntityToModelConverter.toSecret(secretRepo.save(entity));
    }

    @Override
    @Transactional
    public void delete(UUID secretId) {
    	Optional<SecretEntity> secretEntityOpt = secretRepo.findById(secretId);
        if (secretEntityOpt.isEmpty()) {
            throw new EntityNotFoundException("Secret not found: " + secretId);
        }
        SecretEntity entity = secretEntityOpt.get();
        // Remove secrets from repositories
        repositorySecretRepo.deleteAllBySecretId(entity.getId());
        
        secretRepo.deleteById(secretId);
    }

    @Override
    @Transactional(readOnly = true)
    public Secret findById(UUID id) {
    	Optional<SecretEntity> secretEntityOpt = secretRepo.findById(id);
    	if (secretEntityOpt.isEmpty()) {
            throw new EntityNotFoundException("Secret not found: " + id);
        }
    	return EntityToModelConverter.toSecret(secretEntityOpt.get());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Secret> listAll() {
    	 return secretRepo.findAll().stream()
                 .map(EntityToModelConverter::toSecret)
                 .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ValidationResponse validate(UUID secretId, String secretValue) {
    	Optional<SecretEntity> secretEntityOpt = secretRepo.findById(secretId);
    	if (secretEntityOpt.isEmpty()) {
            throw new EntityNotFoundException("Secret not found: " + secretId);
        }
    	
    	String encodedSecret = SecretUtil.encode(secretValue);
    	if (encodedSecret.equals(secretEntityOpt.get().getSecretValue())) {
    		return ValidationResponse.valid();
    	} else {
    		return ValidationResponse.invalid();
    	}
    }
}
