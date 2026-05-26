package org.gpopov.interview.sap.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

import org.gpopov.interview.sap.dto.Secret;
import org.gpopov.interview.sap.dto.SecretType;
import org.gpopov.interview.sap.dto.ValidationResponse;
import org.gpopov.interview.sap.model.RepositoryEntity;
import org.gpopov.interview.sap.model.SecretEntity;
import org.gpopov.interview.sap.repository.RepositoryRepo;
import org.gpopov.interview.sap.repository.SecretRepo;
import org.gpopov.interview.sap.service.SecretService;
import org.gpopov.interview.sap.util.EntityToModelConverter;
import org.gpopov.interview.sap.util.SecretUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
	private RestClient restClient;

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
        Set<RepositoryEntity> repositorites = entity.getRepositories();
        repositorites.stream().forEach(repo -> repo.removeSecret(entity));
        
        secretRepo.deleteById(secretId);
    }
    /**
     * 
     * @param secretId
     * @return s
     * @Override
    @Transactional(readOnly = true)ecret
     */
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
    public ValidationResponse validate(UUID secretId, UUID repositoryId) {
    	Optional<SecretEntity> secretEntityOpt = secretRepo.findById(secretId);
    	if (secretEntityOpt.isEmpty()) {
            throw new EntityNotFoundException("Secret not found: " + secretId);
        }
    	Optional<RepositoryEntity> repositoryEntityOpt = repositoryRepo.findById(repositoryId);
    	if (repositoryEntityOpt.isEmpty()) {
            throw new EntityNotFoundException("Repository not found: " + repositoryId);
        }
    	SecretEntity secret = secretEntityOpt.get();
    	String secretTypeHeader = SecretType.TOKEN.name().equals(secret.getType())? HEADER_PREFIX_TOKEN: HEADER_PREFIX_BEARER;
	    String errorMessage = restClient.get()
	    		.uri(URI.create(repositoryEntityOpt.get().getUrl()))
	    	    .header("Authorization", secretTypeHeader + SecretUtil.decode(secretEntityOpt.get().getSecretValue()))
	    	    .exchange((req, res) -> {
	                try {
	                    if (res.getStatusCode().is2xxSuccessful()) {
	                        return null;
	                    } else {
	                    	return "Validation failed: " + res.getBody().toString();
	                    }
	                } catch (Exception e) {
	                    e.printStackTrace();
	                    return "Validation failed: " + e.getMessage();
	                }
	            });
	    return errorMessage == null? ValidationResponse.valid(): ValidationResponse.invalid(errorMessage);
    }
}
