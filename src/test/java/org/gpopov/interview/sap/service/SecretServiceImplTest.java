package org.gpopov.interview.sap.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.gpopov.interview.sap.BaseTest;
import org.gpopov.interview.sap.dto.Secret;
import org.gpopov.interview.sap.dto.SecretType;
import org.gpopov.interview.sap.dto.ValidationResponse;
import org.gpopov.interview.sap.repository.RepositoryRepo;
import org.gpopov.interview.sap.repository.RepositorySecretRepo;
import org.gpopov.interview.sap.repository.SecretRepo;
import org.gpopov.interview.sap.service.impl.SecretServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class SecretServiceImplTest extends BaseTest {
	
	@Autowired
    private SecretRepo secretRepo;
	
	@Autowired
    private RepositoryRepo repositoryRepo;
	
	@Autowired
	private RepositorySecretRepo repositorySecretRepo;
	
    private SecretService secretService;
    
    @BeforeEach
    public void setup() {
    	repositorySecretRepo.deleteAll();
    	secretRepo.deleteAll();
    	repositoryRepo.deleteAll();
    	
    	secretService = new SecretServiceImpl(secretRepo, repositoryRepo, repositorySecretRepo);
    }

	@Test
	public void testCreateSecret() {
		String name = "Create New Secret";
		String value = "ThisIsTheValueOFBearerToken";
		
		Secret secret = new Secret();
		secret.setName(name);
		secret.setType(SecretType.BEARER);
		secret.setValue(value);
		
		Secret newSecret = secretService.create(secret);
		
		assertNotNull(newSecret.getId());
		assertEquals(name, newSecret.getName());
		assertEquals(SecretType.BEARER, newSecret.getType());
	}

	@Test
	@Transactional
	public void testDeleteSecret() {
		String name = "Delete Secret ";
		String value = "ThisIsTheValueOFBearerToken";
		
		int iniSecretsCount = secretService.listAll().size();
		for (int i = 1; i <= 3; i++) {
			Secret secret = new Secret();
			secret.setName(name +  i);
			secret.setType(SecretType.TOKEN);
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
	
	@Test
	public void testValidateSecret() {
		String name = "Validate Secret";
		String value = "ThisIsTheValueOSecretForvalidation";
		
		Secret secret = new Secret();
		secret.setName(name);
		secret.setType(SecretType.BEARER);
		secret.setValue(value);
		
		Secret newSecret = secretService.create(secret);
		
		assertNotNull(newSecret.getId());
		assertEquals(name, newSecret.getName());
		
		ValidationResponse result = secretService.validate(newSecret.getId(), value);
		
		assertNotNull(result);
		assertEquals(ValidationResponse.valid(), result);
	}
}
