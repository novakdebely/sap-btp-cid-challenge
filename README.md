# SAP BTP CID Challenge – Repository Secret Manager

A Spring Boot service for managing repository URLs and their authentication secrets.

## Requirements

- Java 21+
- Maven 3.8+

## Build

```bash
mvn clean package
```

## Run

```bash
# Using the Spring Boot Maven plugin
mvn spring-boot:run

# Or run the built JAR directly
java -jar target/btp-cid-challenge-0.0.1-SNAPSHOT.jar
```

The application starts on **http://localhost:8080**.

## UI

Open **http://localhost:8080** in your browser for the graphical interface.

---

## REST API

### Repositories

| Method   | Path                          | Description |
|----------|-------------------------------|-------------|
| `POST`   | `/api/v1/repositories`        | Create a repository                                                      |
| `GET`    | `/api/v1/repositories`        | List all repositories                                                    |
| `GET`    | `/api/v1/repositories/{id}`   | Get custom repository details                                            |
| `DELETE` | `/api/v1/repositories/{id}`   | Delete a repository (cascades secrets)                                   |
| `POST`   | `/api/v1/repositories/assign` | Assign secret to repository as first validate secret against repository. |

**Create repository – request body**
```json
{ "url": "https://github.com/org/repo", "name": "My Repo", "description": "Optional" }
```

**Assign secret to repository – request body**
```json
{"repo_id": "8b440756-0cf2-470c-af11-34b17cdb4a8b", "secret_id": "630f34d0-ecd9-48fd-a8e2-e2ffdb3e1969"}
```

### Secrets

| Method   | Path                                                   | Description |
|----------|--------------------------------------------------------|-------------|
| `POST`   | `/api/v1/secrets`                                      | Create a secret  |
| `GET`    | `/api/v1/secrets`                                      | List secrets |
| `GET`    | `/api/v1/secrets/{id}`                                 | Get custom secret |
| `DELETE` | `/api/v1/secrets/{id}`                                 | Delete a secret |
| `POST`   | `/api/v1/secrets/validate`                             | Validate secret's value |

**Add secret – request body**
```json
{ "name": "GitHub Token", "type": "BEARER", "value": "ghp_..." }
```

Secret types: `TOKEN`, `BEARER`

> Secret values are stored as *Base64 encoded string** and can be retrieved.

ValidateRequest
**Validate secret – request body**
```json
{ "secret_id": "8b440756-0cf2-470c-af11-34b17cdb4a8b", "secret_value": "ghp_..." }
```
