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

## H2 Console (development)

Access the in-memory database at **http://localhost:8080/h2-console**  
JDBC URL: `jdbc:h2:mem:btpciddb` · User: `sa` · Password: *(empty)*

---

## REST API

### Repositories

| Method | Path | Description |
|--------|------|-------------|
| `POST` | `/api/repositories` | Create a repository |
| `GET` | `/api/repositories` | List all repositories |
| `DELETE` | `/api/repositories/{id}` | Delete a repository (cascades secrets) |

**Create repository – request body**
```json
{ "url": "https://github.com/org/repo", "name": "My Repo", "description": "Optional" }
```

### Secrets

| Method | Path | Description |
|--------|------|-------------|
| `POST` | `/api/repositories/{repoId}/secrets` | Add a secret to a repository |
| `GET` | `/api/repositories/{repoId}/secrets` | List secrets for a repository |
| `DELETE` | `/api/secrets/{id}` | Delete a secret |
| `POST` | `/api/secrets/{id}/validate` | Validate a secret value |

**Add secret – request body**
```json
{ "name": "GitHub Token", "secretType": "TOKEN", "value": "ghp_..." }
```

Secret types: `TOKEN`, `PASSWORD`, `SSH_KEY`, `CERTIFICATE`

**Validate secret – request body**
```json
{ "value": "ghp_..." }
```

**Validate secret – response**
```json
{ "valid": true, "message": "Secret is valid" }
```

> Secret values are stored as **BCrypt hashes** and cannot be retrieved — only validated.
