package org.gpopov.interview.sap.dto;

import java.util.List;

import lombok.Data;

@Data
public class RepositoryDetails extends Repository {
    
    List<Secret> secrets;
}
