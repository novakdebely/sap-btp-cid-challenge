package org.gpopov.interview.sap.controller;

import java.util.List;
import java.util.UUID;

import org.gpopov.interview.sap.dto.Secret;
import org.gpopov.interview.sap.dto.ValidationRequest;
import org.gpopov.interview.sap.dto.ValidationResponse;
import org.gpopov.interview.sap.service.impl.SecretServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/secrets")
@RequiredArgsConstructor
public class SecretController {

	@Autowired
    private SecretServiceImpl secretService;

	/**
	 * Find secret by id
	 * 
	 * @param id
	 * @return secret
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Secret> getSecret(@PathVariable UUID id) {
		return ResponseEntity.ok(secretService.findById(id));
	}

	/**
     * List existing secrets
     * 
     * @return all secrets
     */
	@GetMapping
	public ResponseEntity<List<Secret>> listAllSecrets() {
		return ResponseEntity.ok(secretService.listAll());
	}
    
	/**
     * Create secret
     * 
     * @param secret
     * @return created secret
     */
    @PostMapping()
    public ResponseEntity<Secret> createSecret(@Valid @RequestBody Secret secret) {
    	return ResponseEntity.status(HttpStatus.CREATED).body(secretService.create(secret));
    }

    /**
     * Delete secret by id
     * 
     * @param secretId
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSecret(@PathVariable UUID id) {
        secretService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Validate secret value for given id
     * 
     * @param secretId
     */
    @PostMapping("/validate")
    public ResponseEntity<ValidationResponse> validateSecret(@Valid @RequestBody ValidationRequest request) {
    	ValidationResponse result = secretService.validate(request.getSecretId(), request.getSecretValue());
    	if (result == ValidationResponse.valid()) {
	    	return ResponseEntity.status(HttpStatus.OK).body(result);
    	} else {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    	}
    }
}
