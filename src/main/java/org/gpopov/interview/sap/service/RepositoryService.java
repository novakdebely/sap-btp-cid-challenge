package org.gpopov.interview.sap.service;

import org.gpopov.interview.sap.dto.Repository;
import org.gpopov.interview.sap.model.RepositoryEntity;

import java.util.List;
import java.util.UUID;

public interface RepositoryService {

	/**
	 * 
	 * @param request
	 * @return
	 */
    public Repository create(Repository request);


    /**
     * 
     * @return
     */
    public List<Repository> listAll();

    /**
     * 
     * @param id
     * @return
     */
    public Repository findById(UUID id);
    
    /**
     * 
     * @param id
     */
    public void delete(UUID id);
    
    /**
     * 
     * @param id
     */
    public void assignSecretToRepository(UUID repoId, UUID secretId);
}
