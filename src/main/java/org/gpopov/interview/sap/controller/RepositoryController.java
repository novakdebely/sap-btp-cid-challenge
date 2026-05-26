package org.gpopov.interview.sap.controller;

import java.util.List;
import java.util.UUID;

import org.gpopov.interview.sap.dto.Repository;
import org.gpopov.interview.sap.dto.RepositoryDetails;
import org.gpopov.interview.sap.dto.SecretAssigment;
import org.gpopov.interview.sap.dto.ValidationResponse;
import org.gpopov.interview.sap.service.RepositoryService;
import org.gpopov.interview.sap.service.SecretService;
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
@RequestMapping("/api/v1/repositories")
@RequiredArgsConstructor
public class RepositoryController {

	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private SecretService secretService;

	/**
     * Find repository by id
     * 
     * @param id
     * @return repository
     */
	@GetMapping("/{id}")
	public ResponseEntity<RepositoryDetails> getRepository(@PathVariable UUID id) {
		return ResponseEntity.ok(repositoryService.findById(id));
	}

	/**
     * List existing repositories
     * 
     * @return list of repositories
     */
	@GetMapping
	public ResponseEntity<List<Repository>> listAllRepositories() {
		return ResponseEntity.ok(repositoryService.listAll());
	}

	/**
	 * Create repository
	 * 
	 * @param request
	 * @return created repository
	 */
	@PostMapping
	public ResponseEntity<Repository> createRepository(@Valid @RequestBody Repository request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(repositoryService.create(request));
	}

	/**
     * Delete repository by id
     * 
     * @param id
     */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteRepository(@PathVariable UUID id) {
		repositoryService.delete(id);
		return ResponseEntity.noContent().build();
	}

	/**
     * Assign secret to repository
     * 
     * @param repoId
     * @param secretId
     */
	@PostMapping("/assign")
	public ResponseEntity<Void> addSecretToRepository(@Valid @RequestBody SecretAssigment assignment) {
		ValidationResponse valdiationResult = secretService.validate(assignment.getSecretId(), assignment.getRepoId());
		if (!valdiationResult.isValid()) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.build();
		}
		repositoryService.assignSecretToRepository(assignment.getRepoId(), assignment.getSecretId());
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
