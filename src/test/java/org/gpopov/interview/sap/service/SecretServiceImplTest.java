package org.gpopov.interview.sap.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.gpopov.interview.sap.BaseTest;
import org.gpopov.interview.sap.dto.Secret;
import org.gpopov.interview.sap.repository.RepositoryRepo;
import org.gpopov.interview.sap.repository.SecretRepo;
import org.gpopov.interview.sap.service.impl.SecretServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SecretServiceImplTest extends BaseTest {
	
	@Autowired
    private SecretRepo secretRepo;
	
	@Autowired
    private RepositoryRepo repositoryRepo;
	
    private SecretService secretService;
    
    @BeforeEach
    public void setup() {
    	secretRepo.deleteAll();
    	repositoryRepo.deleteAll();
    	
    	secretService = new SecretServiceImpl(secretRepo, repositoryRepo);
    }

	@Test
	public void testCreateRepository() {
		String name = "Create New Secret";
		String value = "ThisIsTheValueOFBearerToken";
		
		Secret secret = new Secret();
		secret.setName(name);
		secret.setValue(value);
		
		Secret newSecret = secretService.create(secret);
		
		assertNotNull(newSecret.getId());
		assertEquals(name, newSecret.getName());
		assertEquals(value, newSecret.getValue());
	}

	@Test
	public void testDeleteRepository() {
		String name = "Delete Secret ";
		String value = "ThisIsTheValueOFBearerToken";
		
		int iniSecretsCount = secretService.listAll().size();
		for (int i = 1; i <= 3; i++) {
			Secret secret = new Secret();
			secret.setName(name +  i);
			secret.setValue(value + i);
			
			Secret newSecret = secretService.create(secret);
		}
		List<Secret> initSecrets = secretService.listAll();
		assertNotNull(initSecrets);
		assertEquals(3, initSecrets.size() - iniSecretsCount);
		
		secretService.delete(initSecrets.get(1).getId());

		List<Secret> lastSecrets = secretService.listAll();
		assertNotNull(lastSecrets);
		assertEquals(2, lastSecrets.size() - iniSecretsCount);
		assertEquals(initSecrets.get(0).getId(), lastSecrets.get(0).getId());
		assertEquals(initSecrets.get(2).getId(), lastSecrets.get(1).getId());
	}
}
