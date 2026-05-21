package org.gpopov.interview.sap.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.gpopov.interview.sap.dto.Repository;
import org.gpopov.interview.sap.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/repositories")
@RequiredArgsConstructor
public class RepositoryController {

	@Autowired
	private RepositoryService repositoryService;

	@GetMapping("/{id}")
	public ResponseEntity<Repository> getRepository(@PathVariable UUID id) {
		return ResponseEntity.ok(repositoryService.findById(id));
	}

	@GetMapping
	public ResponseEntity<List<Repository>> listAllRepositories() {
		return ResponseEntity.ok(repositoryService.listAll());
	}

	@PostMapping
	public ResponseEntity<Repository> addRepository(@Valid @RequestBody Repository request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(repositoryService.create(request));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteRepository(@PathVariable UUID id) {
		repositoryService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{repoId}?secret={secretId}")
	public ResponseEntity<Void> addSecretToRepository(@PathVariable UUID repoId, @RequestParam UUID secretId) {
		repositoryService.assignSecretToRepository(repoId, secretId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
