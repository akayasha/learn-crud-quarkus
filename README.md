# Learn CRUD with Quarkus

This project demonstrates a basic CRUD (Create, Read, Update, Delete) application using [Quarkus](https://quarkus.io/). It integrates with MySQL, Flyway for database migrations, and includes an OpenAPI specification for API documentation.

## Prerequisites

Before running the application, ensure you have the following installed:

- Java 17 or higher
- Maven 3.8+
- Docker (optional, for running MySQL)
- MySQL database

## Configuration

The application configuration is defined in the `application.yaml` file:

```yaml
quarkus:
  datasource:
    db-kind: mysql
    username: root
    password: 
    jdbc:
      url: jdbc:mysql://localhost:3306/learn-crud-quarkus
  hibernate-orm:
    dialect: org.hibernate.dialect.MySQLDialect
    log:
      sql: true
    sql-load-script: import.sql
  http:
    port: 8081
  flyway:
    migrate-at-start: true
    clean-at-start: false

logging:
  level: INFO

smallrye-openapi:
  info:
    title: Your API Title
    version: 1.0.0
  path: /openapi
```

## Database Schema

The following schema is used for creating the student table:

```
CREATE TABLE student
(
nim     VARCHAR(255) NOT NULL,
name    VARCHAR(255) NULL,
classes VARCHAR(255) NULL,
dob     DATE NULL,
phoneNo VARCHAR(255) NOT NULL,
email   VARCHAR(255) NOT NULL,
CONSTRAINT pk_student PRIMARY KEY (nim)
);
```

## Running the Application
### Start the MySQL Database

You can use Docker to run a MySQL instance:

```
docker run --name quarkus-mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=learn-crud-quarkus -p 3306:3306 -d mysql:8.0
```

## Build and Run the Application
### Build and run the application in development mode:

```
mvn clean compile quarkus:dev
```

### Access the API

*   **API Base URL**: http://localhost:8081

*   **OpenAPI documentation**: http://localhost:8081/openapi


Maven Configuration
-------------------

The pom.xml is configured to include dependencies for Quarkus, Flyway, Hibernate, and more:

*   Quarkus Hibernate ORM

*   Quarkus Flyway

*   Quarkus RESTEasy

*   Quarkus SmallRye OpenAPI


Refer to the pom.xml file in the repository for full details.

API Endpoints
-------------

### 1\. **Create Student**

**POST** /students

*   **Request Body**:

```
{
    "nim": "12345678",
    "name": "John Doe",
    "classes": "Mathematics",
    "dob": "2000-01-01",
    "phoneNo": "1234567890",
    "email": "johndoe@example.com"
}
```


### 2\. **Get All Students**

**GET** /students

### 3\. **Get Student by NIM**

**GET** /students/{nim}

### 4\. **Update Student**

**PUT** /students/{nim}

*   **Request Body**:

```
{
    "name": "John Updated",
    "classes": "Physics",
    "dob": "2000-02-02",
    "phoneNo": "0987654321",
    "email": "johnupdated@example.com"

}
```

### 5\. **Delete Student**

**DELETE** /students/{nim}

Development Notes
-----------------

*   **Live reload**: Quarkus automatically reloads changes in dev mode (quarkus:dev).

*   **Database migrations**: Flyway is configured to automatically handle database migrations on application startup.


License
-------

This project is licensed under the MIT License. See the LICENSE file for details.

Plain textANTLR4BashCC#CSSCoffeeScriptCMakeDartDjangoDockerEJSErlangGitGoGraphQLGroovyHTMLJavaJavaScriptJSONJSXKotlinLaTeXLessLuaMakefileMarkdownMATLABMarkupObjective-CPerlPHPPowerShell.propertiesProtocol BuffersPythonRRubySass (Sass)Sass (Scss)SchemeSQLShellSwiftSVGTSXTypeScriptWebAssemblyYAMLXML``   vbnetCopyEdit  This is the corrected and fully formatted version in Markdown. You can now copy and paste this into your `README.md` file in your repository. Let me know if you need any further adjustments!   ``
