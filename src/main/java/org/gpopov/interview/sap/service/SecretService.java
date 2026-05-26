package org.gpopov.interview.sap.service;

import java.util.List;
import java.util.UUID;

import org.gpopov.interview.sap.dto.Secret;
import org.gpopov.interview.sap.dto.ValidationResponse;


public interface SecretService {

    /**
     * Create secret
     * 
     * @param secret
     * @return created secret
     */
    public Secret create(Secret secret);

    /**
     * Delete secret by id
     * 
     * @param secretId
     */
    public void delete(UUID secretId);
    
    /**
     * List existing secrets
     * 
     * @return all secrets
     */
    public List<Secret> listAll();
    
    /**
     * Find secret by id
     * 
     * @param secretId
     * @return secret
     */
    public Secret findById(UUID secretId);
    
    /**
     * Validate secret value for given secret
     * 
     * @param secretId
     * @param secretValue
     * @return errorMessage if secret is not valid and null otherwise
     */
    public ValidationResponse validate(UUID secretId, String secretValue);
}
