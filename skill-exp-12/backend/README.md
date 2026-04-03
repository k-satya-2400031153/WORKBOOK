# Backend API (Spring Boot)

This backend exposes a `students` CRUD API for the React app running on `http://localhost:3000`.

## Endpoints

- `GET /students`
- `POST /students`
- `PUT /students/{id}`
- `DELETE /students/{id}`

## Run

```powershell
.\mvnw.cmd spring-boot:run
```

## Test

```powershell
.\mvnw.cmd test
```

## Notes

- Global CORS is configured in `src/main/java/com/klu/backend/CorsConfig.java`.
- H2 in-memory database is enabled for development.

