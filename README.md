# 🛫 Aeroplanner REST API

This project is a Flight Booking Application RESTful API built with Spring Boot.

## 📋 Prerequisites

Before you begin, ensure you have met the following requirements:

- You have installed **Java v17**
- You have installed **Maven (mvn)**
- You have a **Postgres server** running
- You have installed **Docker** and **docker-compose** (optional)
- You have **Docker service** running (optional)

## 🚀Running Aeroplanner REST API Locally

Follow these steps to get the application running on your machine:


1. **Clone the application**

Use the following command to clone the application:
```bash
git clone https://github.com/jcilacad/aeroplanner-rest-api.git
```


2. **Setup your database configuration**

Create a database named "aeroplanner_db" using the following command:
```sql
CREATE DATABASE aeroplanner_db
```
Tip: this can also be done using `pgAdmin`

3. **Generate a secret key and JWT expiration time**

- The secret key must be an HMAC hash string of 256 bits. 
  Example: `507c4db58311630bdfa4ed5d4b8a562ca2f43370e03a3df411b3784a805681f7`
- The JWT expiration time is expressed in milliseconds.
  Example: `3600000`


4. **Create a Gmail App Password**

Follow the instructions [here](https://knowledge.workspace.google.com/kb/how-to-create-app-passwords-000009237) to create a Gmail App Password.

5. **Create a "env.properties" file in the root directory**

Your directory structure should look like this:
```
- aeroplanner-rest-api 
  - src
  - env.properties
```

Inside the "env.properties" file, add the following properties (replace the placeholders with your actual data):
```properties
DATABASE_NAME=aeroplanner_db
DATABASE_HOST=<DATABASE HOST AND PORT>
DATABASE_USERNAME=<DATABASE USERNAME>
DATABASE_PASSWORD=<DATABASE PASSWORD>

JWT_SECRET_KEY=<GENERATED SECRET KEY>
JWT_EXPIRATION_TIME=<TOKEN EXPIRATION TIME>

SUPER_ADMIN_NAME=<SUPER ADMIN NAME>
SUPER_ADMIN_EMAIL=<SUPER ADMIN EMAIL>
# Password must contain at least one uppercase letter, one lowercase letter, one digit, one special character and be at least 8 characters long.
SUPER_ADMIN_PASSWORD=<SUPER ADMIN PASSWORD>

GMAIL_USERNAME=<GOOGLE ACCOUNT EMAIL>
GMAIL_PASSWORD=<GENERATED APP PASSWORD>
```

### Run as standalone

Use the following command to run the application:
```bash
mvn clean spring-boot:run
```

### Run with docker (optional)

Use the following commands to build and run the application as docker container
```bash
mvn clean package -DskipTests
docker build -t jcilacad/aeroplanner-rest-api .
docker run -p 8005:8005 jcilacad/aeroplanner-rest-api # add also --network <NETWORK_NAME> in case postgres is running in another docker network
```

### Run with docker-compose (optional)

Use the following commands to build and run the application along with the database as docker containers
```bash
mvn clean package -DskipTests
docker compose up -d
```

To stop the docker containers at once run `docker compose down` and add  `--volumes` option in case you want to remove also the database volumes

### Publish docker image in GitHub Container Registry (optional)

Follow the instructions below to push the docker image in the GitHub Container Registry
1. Create personal access token in GitHub via Settings / Developer settings / Personal access tokens
2. Tag the image running `docker build -t ghcr.io/jcilacad/aeroplanner-rest-api:latest -t ghcr.io/jcilacad/aeroplanner-rest-api:1.0.0 .`
3. Login to registry like `docker login ghcr.io -u jcilacad` providing also the personal access token when prompted
4. Push image to registry : `docker push ghcr.io/jcilacad/aeroplanner-rest-api:latest ghcr.io/jcilacad/aeroplanner-rest-api:1.0.0`

## Run unit tests

Use the following command to run the unit tests:
```bash
mvn verify
```
This way, unit tests are executed along JaCoCo tool to gather the code coverage which can be read from Sonarqube

## Run integration tests

Use the following command to run the integration tests. Please note that the Docker service must be running to execute this command.
```bash
mvn failsafe:integration-test
```

## Analyze code quality using SonarQube

- Have a **Sonarqube server** running
- Generate an authentication token
- Use the following command to analyze the application's code
```bash
mvn sonar:sonar -D"sonar.token=<SONAR_AUTH_TOKEN>" -D"sonar.host.url=<SONAR_HOST>"
```
- The `sonar.host.url` property can be omitted if using the default, which is `http://localhost:9000`
