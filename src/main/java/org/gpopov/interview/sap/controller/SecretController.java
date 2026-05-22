package org.gpopov.interview.sap.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.gpopov.interview.sap.dto.Secret;
import org.gpopov.interview.sap.dto.ValidationResponse;
import org.gpopov.interview.sap.service.impl.SecretServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/secrets")
@RequiredArgsConstructor
public class SecretController {

	@Autowired
    private SecretServiceImpl secretService;

	@GetMapping("/{id}")
	public ResponseEntity<Secret> getSecret(@PathVariable UUID id) {
		return ResponseEntity.ok(secretService.findById(id));
	}

	@GetMapping
	public ResponseEntity<List<Secret>> listAllSecrets() {
		return ResponseEntity.ok(secretService.listAll());
	}
    
    @PostMapping()
    public ResponseEntity<Secret> addSecret(@Valid @RequestBody Secret secret) {
    	return ResponseEntity.status(HttpStatus.CREATED).body(secretService.create(secret));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSecret(@PathVariable UUID id) {
        secretService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{secretId}/validate?repositoryId={repositoryId}")
    public ResponseEntity<ValidationResponse> validateSecret(
            @PathVariable UUID secretId,
            @RequestParam UUID repositoryId) {
        return ResponseEntity.ok(secretService.validate(secretId, repositoryId));
    }
}
