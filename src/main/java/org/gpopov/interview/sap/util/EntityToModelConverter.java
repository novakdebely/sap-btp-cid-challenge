package org.gpopov.interview.sap.util;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import org.gpopov.interview.sap.dto.Repository;
import org.gpopov.interview.sap.dto.RepositoryDetails;
import org.gpopov.interview.sap.dto.Secret;
import org.gpopov.interview.sap.model.RepositoryEntity;
import org.gpopov.interview.sap.model.SecretEntity;

public class EntityToModelConverter {

	/**
	 * 
     * 
     * @param @RepositoryEntity entity
     * @return @Repository instance
	 */
    public static Repository toRepository(RepositoryEntity entity) {
    	Repository r = new Repository();
        r.setId(entity.getId());
        r.setUrl(entity.getUrl());
        r.setName(entity.getName());
        r.setDescription(entity.getDescription());
        
        return r;
    }
    
    /**
	 * 
     * 
     * @param @RepositoryEntity entity
     * @return @Repository instance
	 */
    public static RepositoryDetails toRepositoryDetails(RepositoryEntity entity) {
    	RepositoryDetails r = new RepositoryDetails();
        r.setId(entity.getId());
        r.setUrl(entity.getUrl());
        r.setName(entity.getName());
        r.setDescription(entity.getDescription());
        
        r.setSecretIds(Optional.ofNullable(entity.getSecrets()).orElse(Collections.emptySet()).stream()
        		.map(SecretEntity::getId)
        		.collect(Collectors.toList()));
        
        return r;
    }
    

    /**
     * 
     * @param @SecretEntity entity
     * @return @Secret instance
     */
    public static Secret toSecret(SecretEntity entity) {
    	Secret r = new Secret();
        r.setId(entity.getId());
        r.setName(entity.getName());
        r.setValue(entity.getSecretValue());
        return r;
    }

}
