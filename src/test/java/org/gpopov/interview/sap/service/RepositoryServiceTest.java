package org.gpopov.interview.sap.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.gpopov.interview.sap.BaseTest;
import org.gpopov.interview.sap.dto.Repository;
import org.gpopov.interview.sap.dto.RepositoryDetails;
import org.gpopov.interview.sap.dto.Secret;
import org.gpopov.interview.sap.dto.SecretType;
import org.gpopov.interview.sap.repository.RepositoryRepo;
import org.gpopov.interview.sap.repository.RepositorySecretRepo;
import org.gpopov.interview.sap.repository.SecretRepo;
import org.gpopov.interview.sap.service.impl.RepositoryServiceImpl;
import org.gpopov.interview.sap.service.impl.SecretServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;


public class RepositoryServiceTest extends BaseTest {
	
	@Autowired
    private RepositoryRepo repositoryRepo;
	
	@Autowired
    private SecretRepo secretRepo;
	
	@Autowired
	private RepositorySecretRepo repositorySecretRepo;
	
	@Autowired
	private RestClient restClient;
	
    private RepositoryService repositoryService;
    private SecretService secretService;
    
    @BeforeEach
    public void setup() {
    	repositoryRepo.deleteAll();
    	secretRepo.deleteAll();
    	
    	repositoryService = new RepositoryServiceImpl(repositoryRepo, secretRepo, repositorySecretRepo);
    	secretService = new SecretServiceImpl(secretRepo, repositoryRepo, repositorySecretRepo, restClient);
    }
	
	@Test
	public void testCreateRepository() {
		String url = "http://create.new.repository/test";
		String name = "Create New Repository";
		String description = "This is the test repository which have to be created";
		
		Repository repository = new Repository();
		repository.setUrl(url);
		repository.setName(name);
		repository.setDescription(description);
		
		Repository newRepo = repositoryService.create(repository);
		
		assertNotNull(newRepo.getId());
		assertEquals(url, newRepo.getUrl());
		assertEquals(name, newRepo.getName());
		assertEquals(description, newRepo.getDescription());
	}
	
	@Test
	@Transactional
	public void testDeleteRepositories() {
		String url = "http://delete.repository/test";
		String name = "Delete Repository ";
		String description = "This is the test repository which will be deleted ";
		
		int initCount = repositoryService.listAll().size();
		for (int i = 1; i <= 3; i++) {
			Repository repository = new Repository();
			repository.setUrl(url + i);
			repository.setName(name + i);
			repository.setDescription(description + i);
			
			repositoryService.create(repository);
		}
		
		List<Repository> listRepositories = repositoryService.listAll();
		
		assertNotNull(listRepositories);
		assertEquals(3, listRepositories.size() - initCount);
		
		repositoryService.delete(listRepositories.get(1).getId());
		
		List<Repository> newListRepositories = repositoryService.listAll();
		
		assertNotNull(newListRepositories);
		assertEquals(2, newListRepositories.size() - initCount);
		assertEquals(listRepositories.get(0).getId(), newListRepositories.get(0).getId());
		assertEquals(listRepositories.get(2).getId(), newListRepositories.get(1).getId());
	}
	
	@Test
	@Transactional
	public void testAssignSecretToRepository() {
		// Create repository
		String repoUrl = "http://assign.secrets.to.repository/test";
		String repoName = "Assign Secrets to Repository";
		String repoDescription = "This is the test to assign secrets to repository";
		
		Repository newRepo = new Repository();
		newRepo.setUrl(repoUrl);
		newRepo.setName(repoName);
		newRepo.setDescription(repoDescription);
		
		Repository newRepository = repositoryService.create(newRepo);
		
		RepositoryDetails repository = repositoryService.findById(newRepository.getId());
		
		assertNotNull(repository);
		assertEquals(0, repository.getSecrets().size());
		
		// Create secrets
		String secretName = "Delete Secret ";
		String secretValue = "ThisIsTheValueOFBearerToken";
		List<Secret> listSecrets = new ArrayList<>();
		
		for (int i = 1; i <= 3; i++) {
			Secret secret = new Secret();
			secret.setName(secretName +  i);
			secret.setType(SecretType.BEARER);
			secret.setValue(secretValue + i);
			
			listSecrets.add(secretService.create(secret));
		}
		
		listSecrets.forEach(secret -> repositoryService.assignSecretToRepository(newRepository.getId(), secret.getId()));
		
		repository = repositoryService.findById(newRepository.getId());
		assertNotNull(repository);
		assertEquals(3, repository.getSecrets().size());
		
		listSecrets.forEach(secret -> repositoryService.detachSecretFromRepository(newRepository.getId(), secret.getId()));
		
		repository = repositoryService.findById(newRepository.getId());
		assertNotNull(repository);
		assertEquals(0, repository.getSecrets().size());
	}
}
