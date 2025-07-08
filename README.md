# User Authentication
**Este proyecto es un servicio desarrollado en Java con Spring Boot que permite autenticar usuarios mediante peticiones RESTful utilizando spring-security y JWT.**

## Tabla de Contenidos
- [Descripción](#descripcion)
- [Tecnologías Usadas](#tecnologias)
- [Instalación](#instalacion)
- [Uso](#uso)
- [Endpoints](#endpoints)
- [Configuración](#configuracion)
- [Licencia](#licencia)

## Descripción
Este es un servicio REST que permite registrar, autenticar y gestionar usuarios utilizando JWT para autenticación.
- El sistema expone dos endpoints como lo son: /signup y /signin.
- La autenticación está basada en JSON Web Tokens (JWT), lo que garantiza sesiones seguras y sin estado.
- Está implementado en Java con Spring Boot y utiliza H2 como base de datos en memoria.

## Tecnologías Usadas
**Backend:** Java 8, Spring Boot

**Base de Datos:** H2 (en memoria)

**Autenticación:** JWT (JSON Web Tokens)

**Seguridad:** Spring Security

**Dependencias:**
- Spring Boot Starter Web
- Spring Boot Starter Security
- Spring Boot Starter Data JPA
- JWT (jjwt)
- H2 Database

## Instalación
Sigue estos pasos para configurar el proyecto en tu máquina local:

1. **Clona el repositorio:**

```bash
git clone [https://github.com/usuario/proyecto.git](https://github.com/jorgegarcia-lab/user-authentication/tree/master)
cd user-authentication
```
Instala dependencias:

```bash
./gradlew build
```
Ejecuta el proyecto:
```bash
./gradlew bootRun
```
El servidor se levantará en http://localhost:8081.
**Las credenciales de spring security son:**
- user: admin
- password: admin

## Uso
Este proyecto está diseñado para interactuar mediante peticiones HTTP a través de RESTful endpoints. A continuación se describen algunos ejemplos de los endpoints disponibles.

## Endpoints
Método	Endpoint	Descripción
POST	/signup	Registra un nuevo usuario
POST	/signin	Inicia sesión con email y contraseña

Ejemplo de uso:
Registrar un usuario:

```bash
POST http://localhost:8081/signup
Content-Type: application/json

{
  "name": "jorge puerta",
  "email": "jorge.puerta@globallogic.com",
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
Respuesta (Success):

```bash
{
  "id": "20a488c1-27a8-4fdd-a26b-01553f218ca7",
  "createdAt": "Jul 07, 2025 08:30:11 PM",
  "lastLogin": "Jul 07, 2025 08:30:13 PM",
  "token": "JWT",
  "isActive": true
}
```

## Configuración
**Configuración JWT**
El sistema utiliza un archivo de configuración para definir claves secretas y tiempos de expiración del JWT. Puedes encontrarlo en el archivo JwtConfig.java y en application.properties.

**Licencia**
Distribuido bajo la Licencia MIT. Ver LICENSE para más información.
