package org.gpopov.interview.sap.service;

import org.gpopov.interview.sap.dto.Secret;
import org.gpopov.interview.sap.dto.ValidationResponse;

import java.util.List;
import java.util.UUID;


public interface SecretService {

    /**
     * 
     * @param repositoryId
     * @param request
     * @return
     */
    public Secret create(Secret request);

    /**
     * 
     * @param secretId
     */
    public void delete(UUID secretId);
    
    /**
     * 
     * @param repositoryId
     * @param request
     * @return
     */
    public List<Secret> listAll();
    
    /**
     * 
     * @param secretId
     * @return secret
     */
    public Secret findById(UUID secretId);

    /**
     * 
     * @param secretId
     * @param request
     * @return
     */
    public ValidationResponse validate(UUID secretId, UUID repositoryId);
}
