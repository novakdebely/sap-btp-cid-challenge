package org.gpopov.interview.sap.service;

import java.util.List;
import java.util.UUID;

import org.gpopov.interview.sap.dto.Repository;
import org.gpopov.interview.sap.dto.RepositoryDetails;

public interface RepositoryService {

	/**
	 * Create repository
	 * 
	 * @param request
	 * @return created repository
	 */
    public Repository create(Repository request);


    /**
     * List existing repositories
     * 
     * @return list of repositories
     */
    public List<Repository> listAll();

    /**
     * Find repository by id
     * 
     * @param id
     * @return repository
     */
    public RepositoryDetails findById(UUID id);
    
    /**
     * Delete repository by id
     * 
     * @param id
     */
    public void delete(UUID id);
    
    /**
     * Assign secret to repository
     * 
     * @param repoId
     * @param secretId
     */
    public void assignSecretToRepository(UUID repoId, UUID secretId);
    
    /**
     * Detach secret from repository
     * 
     * @param repoId
     * @param secretId
     */
    public void detachSecretFromRepository(UUID repoId, UUID secretId);
}
