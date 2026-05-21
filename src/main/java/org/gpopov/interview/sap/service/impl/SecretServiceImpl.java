package org.gpopov.interview.sap.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

import org.gpopov.interview.sap.dto.Secret;
import org.gpopov.interview.sap.dto.ValidationRequest;
import org.gpopov.interview.sap.dto.ValidationResponse;
import org.gpopov.interview.sap.model.SecretEntity;
import org.gpopov.interview.sap.repository.SecretRepo;
import org.gpopov.interview.sap.service.SecretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SecretServiceImpl implements SecretService {

	@Autowired
    private SecretRepo secretRepo;

	@Override
    @Transactional
    public Secret create(Secret request) {
        SecretEntity entity = new SecretEntity();
        entity.setName(request.getName());
        entity.setSecretValue(request.getValue());

        return toSecret(secretRepo.save(entity));
    }

    @Override
    @Transactional
    public void delete(UUID secretId) {
        if (!secretRepo.existsById(secretId)) {
            throw new EntityNotFoundException("Secret not found: " + secretId);
        }
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
    	return toSecret(secretEntityOpt.get());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Secret> listAll() {
    	 return secretRepo.findAll().stream()
                 .map(this::toSecret)
                 .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ValidationResponse validate(UUID secretId, UUID repositoryId) {
        SecretEntity entity = secretRepo.findById(secretId)
                .orElseThrow(() -> new EntityNotFoundException("Secret not found: " + secretId));
        return ValidationResponse.valid();
    }

    private Secret toSecret(SecretEntity entity) {
    	Secret r = new Secret();
        r.setId(entity.getId());
        r.setName(entity.getName());
        r.setValue(entity.getSecretValue());
        return r;
    }
}
