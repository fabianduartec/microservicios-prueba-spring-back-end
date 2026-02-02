# Microservicios Financiera - Cliente + Cuenta (Docker Compose + Git)

## Descripción
Arquitectura de **microservicios** con **Spring Boot 4.0.2**, **Kafka**, **PostgreSQL** y **Docker Compose**:

- **cliente-service** (`localhost:8080`) → CRUD Clientes + Eventos
- **cuenta-service** (`localhost:8081`) → CRUD Cuentas/Movimientos + F2/F3
- **Comunicación asíncrona** via **Kafka** (cliente ↔ cuenta)
- **Bases separadas** PostgreSQL (clientedb, cuentadb)

**Cumple F1+F2+F3**: CRUD + Movimientos + Validación saldo

## ️ Tecnologías
Frontend: REST API (Postman/cURL)
Backend: Spring Boot 4.0.2 + Java 17
Message Broker: Kafka 7.4.0 + Zookeeper
Database: PostgreSQL 15.15
Infra: Docker Compose + Git

text

## Estructura Repositorio Git

microservicios-prueba-spring-back-end/
├── README.md ← Este archivo
├── .gitignore ← Archivos ignorados
├── docker-compose.yml ← Orquestación completa
├── cliente-service/
│ ├── Dockerfile ← Build cliente-service
│ ├── pom.xml ← Dependencias cliente
│ ├── src/main/java/... ← Código fuente
│ └── src/main/resources/
│ └── application-docker.yml
├── cuenta-service/
  ├── Dockerfile ← Build cuenta-service
  ├── pom.xml ← Dependencias cuenta
  ├── src/main/java/... ← Código fuente
  └── src/main/resources/
  └── application-docker.yml


text

##  DESPLIEGUE COMPLETO (Git + Docker)

###  1. Clonar Repositorio
```bash
git clone https://github.com/tu-usuario/microservicios-prueba-spring-back-end.git
cd microservicios-prueba-spring-back-end
 2. Verificar rama correcta
bash
git checkout main
git pull origin main
git status
 3. Requisitos Previos
bash
# Docker + Docker Compose
docker --version    # >= 20.10
docker-compose --version  # >= 2.0

# Maven (opcional, Docker lo maneja)
mvn --version      # >= 3.8 4. DESPLIEGUE PASO A PASO
Paso 1: Build JARs desde Git
bash
# Opción A: Build local (recomendado)
mvn clean package -DskipTests

# Opción B: Docker build (automático)
Paso 2: Levantar TODOS los servicios
bash
#  DESPLIEGUE COMPLETO (60 segundos)
docker-compose up -d --build

# Verificar todos UP
docker-compose ps
Paso 3: Verificar logs
bash
# Terminal 1
docker logs -f financiera-cliente-service

# Terminal 2  
docker logs -f financiera-cuenta-service
Esperar:

text
 HikariPool-1 - Start completed.
 Started ClienteServiceApplication in 45s
 Tomcat started on port(s): 8080 (http)
 Started CuentaServiceApplication in 42s
 Tomcat started on port(s): 8081 (http)

## Pruebas F1+F2+F3 (Postman/cURL)

# Clonar y actualizar
git clone https://github.com/fabianduartec/microservicios-prueba-spring-back-end.git
cd microservicios-prueba-spring-back-end
git checkout main
git pull origin main

Docker Comandos ÚTILES
bash
#  Despliegue completo
docker-compose up -d --build

#  Logs en tiempo real
docker logs -f financiera-cliente-service
docker logs -f financiera-cuenta-service

#  Reiniciar servicio específico
docker-compose restart cliente-service
docker-compose restart cuenta-service

#  Parar todo
docker-compose down
docker-compose down -v  # Limpia volúmenes DB

#  Estado servicios
docker-compose ps
