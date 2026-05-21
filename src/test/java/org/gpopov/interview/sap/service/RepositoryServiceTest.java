package org.gpopov.interview.sap.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.gpopov.interview.sap.BaseTest;
import org.gpopov.interview.sap.dto.Repository;
import org.gpopov.interview.sap.repository.RepositoryRepo;
import org.gpopov.interview.sap.repository.SecretRepo;
import org.gpopov.interview.sap.service.impl.RepositoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


public class RepositoryServiceTest extends BaseTest {
	
	@Autowired
    private RepositoryRepo repositoryRepo;
	
	@Autowired
    private SecretRepo secretRepo;
	
    private RepositoryService repositoryService;
    
    @BeforeEach
    public void setup() {
    	repositoryRepo.deleteAll();
    	secretRepo.deleteAll();
    	
    	repositoryService = new RepositoryServiceImpl(repositoryRepo, secretRepo);
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
	public void testDeleteRepositories() {
		String url = "http://create.new.repository/test";
		String name = "Create New Repository ";
		String description = "This is the test repository which have to be created ";
		
		for (int i = 1; i <= 3; i++) {
			Repository repository = new Repository();
			repository.setUrl(url + i);
			repository.setName(name + i);
			repository.setDescription(description + i);
			
			repositoryService.create(repository);
		}
		
		List<Repository> listRepositories = repositoryService.listAll();
		
		assertNotNull(listRepositories);
		assertEquals(3, listRepositories.size());
		
		repositoryService.delete(listRepositories.get(1).getId());
		
		List<Repository> newListRepositories = repositoryService.listAll();
		
		assertNotNull(newListRepositories);
		assertEquals(2, newListRepositories.size());
		assertEquals(listRepositories.get(0).getId(), newListRepositories.get(0).getId());
		assertEquals(listRepositories.get(2).getId(), newListRepositories.get(1).getId());
	}
}
