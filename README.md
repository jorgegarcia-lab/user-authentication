# User Authentication
**This is a service implemented in Java with Spring Boot, provide registration and authentication for users by Restful APIs, using spring-security and JWT.**

## Content
- [Description](#description)
- [Technologies](#technologies)
- [Instalation](#instalation)
- [Usage](#usage)
- [Endpoints](#endpoints)
- [Configuration](#configuration)
- [Coverage](#coverage)
- [License](#license)

## Description
This is a REST service that allows users to register and login, using JWT.
- The system exposes two endpoints: /signup y /signin.
- Authentication is based on JSON Web Tokens (JWT), guaranting safer and stateless sessions.
- It is implemented in JAVA with Spring Boot and uses H2 as an in-memory database.

## Technologies
**Backend:** Java 8, Spring Boot

**Database:** H2 (en memoria)

**Authentication:** JWT (JSON Web Tokens)

**Security:** Spring Security

**Dependencies:**
- Spring Boot Starter Web
- Spring Boot Starter Security
- Spring Boot Starter Data JPA
- JWT (jjwt)
- H2 Database

## Instalation
Follow these steps to make it run locally:

1. **Clone the repo:**

```bash
git clone [https://github.com/usuario/proyecto.git](https://github.com/jorgegarcia-lab/user-authentication/tree/master)
cd user-authentication
```
Install dependencies:

```bash
./gradlew build
```
Run the project:
```bash
./gradlew bootRun
```
Server will be up at: http://localhost:8081.
**Spring security credentials:**
- user: admin
- password: admin

## Usage
This project is designed to interact throught HTTP request by RESTfull endpoints.

## Endpoints
Method	Endpoint	Description
POST	/signup	Register a new user
POST	/signin	Login

Example of usage:
Register an user:

```bash
POST http://localhost:8081/signup
Content-Type: application/json

{
  "name": "jorge puerta",
  "email": "jorge.puerta@mail.com",
  "password": "a2a1sfGf",
  "phones": [
    {
      "number": "3124345643",
      "cityCode": "601",
      "countryCode": "55"
    }
  ]
}
```
Response (Success):

```bash
{
  "id": "20a488c1-27a8-4fdd-a26b-01553f218ca7",
  "createdAt": "Jul 07, 2025 08:30:11 PM",
  "lastLogin": "Jul 07, 2025 08:30:13 PM",
  "token": "JWT",
  "isActive": true
}
```

## Configuration
**JWT Configuration**
The system uses a configuration file to define secret keys and timeouts. You can find it in JwgtConfig.java and application.properties.

## Coverage
To see coverage report go to:
```bash
http://localhost:63342/user-authentication/build/reports/jacoco/test/html/index.html
```

## License
Distributed under MIT licence.
