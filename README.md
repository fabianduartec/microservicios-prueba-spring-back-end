# Microservicios Financiera - Cliente + Cuenta (Docker Compose + Git)

## Descripción
Arquitectura de **microservicios** con **Spring Boot 4.0.2**, **Kafka**, **PostgreSQL** y **Docker Compose**:

- **cliente-service** (`localhost:8080`) → CRUD Clientes + Eventos
- **cuenta-service** (`localhost:8081`) → CRUD Cuentas/Movimientos + Eventos
- **Comunicación asíncrona** via **Kafka** (cliente ↔ cuenta)
- **Bases de datos separadas** PostgreSQL (clientedb, cuentadb)

**Funcionalidades **: CRUD + Movimientos + Validación saldo + Reportes

## ️ Tecnologías
Frontend: REST API (Postman/cURL)
Backend: Spring Boot 4.0.2 + Java 17
Message Broker: Kafka 7.4.0 + Zookeeper
Database: PostgreSQL 15.15
Infraestructura: Docker Compose + Git


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
├── cuenta-service/
  ├── Dockerfile ← Build cuenta-service
  ├── pom.xml ← Dependencias cuenta
  ├── src/main/java/... ← Código fuente
  └── src/main/resources/

##  REQUISITOS PREVIOS

Docker 24+           docker --version
Docker Compose 2.20+  docker compose version  
Git 2.30+            git --version

##  DESPLIEGUE COMPLETO (Git + Docker)

###  1. Clonar Repositorio
git clone https://github.com/tu-usuario/microservicios-prueba-spring-back-end.git
cd microservicios-prueba-spring-back-end

###  2. Docker Build y Despliegue Completo (60 segundos)
docker-compose up -d --build

### 3. Verificar todos UP
docker-compose ps

### 4. Verificar todos los logs

# Abra otra terminal - Terminal 1
docker logs -f financiera-cliente-service

## Abra otra terminal - Terminal 2  
docker logs -f financiera-cuenta-service
sleep 90

# Verificar arranque exitoso

# Verificar Cliente - Terminal 1
docker logs financiera-cliente-service 2>&1 | findstr /C:"HikariPool-1 - Start completed." /C:"Started ClienteServiceApplication" /C:"Tomcat started on port 8080 (http)" /C:"Completed initialization in"
docker logs financiera-cliente-service | findstr /C:"Subscribed to topic(s): cuenta-events" /C:"Successfully joined group" /C:"Finished assignment for group" /C:"cliente-group: partitions assigned" /C:"KafkaMessageListenerContainer"

# Verificar Cuentas/Movimientos - Terminal -2
docker logs financiera-cuenta-service 2>&1 | findstr /C:"HikariPool-1 - Start completed." /C:"Started CuentaServiceApplication" /C:"Tomcat started on port 8080 (http)" /C:"Completed initialization in"
docker logs financiera-cuenta-service | findstr /C:"Subscribed to topic(s): cliente-events" /C:"Successfully joined group" /C:"Finished assignment for group" /C:"cuenta-group: partitions assigned" /C:"KafkaMessageListenerContainer"

#busca estos logs exactos en Terminal 1
 HikariPool-1 - Start completed.
 Started ClienteServiceApplication
 Tomcat started on port(s): 8080 (http)
 Subscribed to topic(s): cuenta-events
 Successfully joined group
 Finished assignment for group
 cliente-group: partitions assigned
 
 HikariPool-1 - Start completed.
 Started CuentaServiceApplication
 Tomcat started on port(s): 8081 (http)
  Subscribed to topic(s): cliente-events
 Successfully joined group
 Finished assignment for group
 cliente-group: partitions assigned
 
 ### 5. Verificar Health Checks
curl http://localhost:8080/actuator/health

#  Estado servicios - Verificar "healthy" en columna STATUS
docker-compose ps
 

## PRUEBAS UNITARIAS cliente-service

### **Test Unitarios JUnit (carpeta `src/test`)**

### 1. Ubicar el archivo
src\test\java\
			com.financiera.clienteservice.cliente.service
				ClienteServiceTest.java
### 2. Dar clic derecho y Run ClienteServiceTest

Esperar alerta "✓ 2 tests passed" en verde

### **Set de Pruebas Karate (carpeta `src/test`)**

### 2. Ubicar el archivo
src\test\java
	KarateTests.java
	
### 2. Dar clic derecho y Run KarateTests

Esperar alerta "✓ 5 tests passed" en verde

## PRUEBAS UNITARIAS cuenta-service

### **Set de Pruebas Karate (carpeta `src/test`)**

### 2. Ubicar el archivo
src\test\java
	KarateTests.java

### 2. Dar clic derecho y Run KarateTests

Esperar alerta "✓ 6 tests passed" en verde

# PRUEBAS UNITARIAS Postman Collection

Importar 'financiera.postman_collection.json' en Postman
Probar los metodos en orden

#  Parar todo - Limpia volúmenes DB
docker-compose down -v  
#  Parar todo - Conserva volumenes DB
docker-compose down


### Comandos Utiles (Opcionales)

#  Reiniciar servicio específico
docker-compose restart cliente-service
docker-compose restart cuenta-service

# CUENTA-SERVICE DOCKER PRUEBAS (puerto 8081)

# Reconstruir + subir
docker compose up -d --build cuenta-service
# Parar (mantiene cache)           
docker compose stop cuenta-service
# Logs en vivo                    
docker compose logs -f cuenta-service
# Estado                 
docker compose ps cuenta-service                      

# CLIENTE-SERVICE DOCKER PRUEBAS (puerto 8080)
# Reconstruir + subir 
docker compose up -d --build cliente-service
# Parar
docker compose stop cliente-service
# Logs                  
docker compose logs -f cliente-service  
# Estado              
docker compose ps cliente-service                     
