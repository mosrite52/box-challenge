# Box Challenge - README

Este repositorio contiene el código fuente para el proyecto Box Challenge, un microservicio que realiza operaciones relacionadas con documentos y cifrado de hash. A continuación, se detallan los pasos necesarios para configurar y ejecutar el proyecto.

## Requisitos Previos

Asegúrese de tener los siguientes requisitos antes de comenzar con el proyecto:

- **Lombok:** El proyecto utiliza la biblioteca Lombok para simplificar la creación de clases Java. Asegúrese de instalar el complemento correspondiente en su IDE para visualizar el código correctamente. Se recomienda el uso de [IntelliJ IDEA](https://www.jetbrains.com/idea/), que fue utilizado para el desarrollo y las pruebas.

    - **Instalación de Lombok en IntelliJ IDEA:**
        1. Abre IntelliJ IDEA.
        2. Instala el plugin "Lombok" desde la sección de plugins.

- **JDK 17:** El proyecto requiere Java Development Kit (JDK) en la versión 17. Asegúrese de tener instalada esta versión en su entorno de desarrollo y configurar esta JDK en su ide de preferencia.

- **Gradle:** El proyecto incluye una versión compatible de Gradle, que está integrada en el proyecto y no requiere instalación adicional.

- **PostgreSQL:** Para ejecutar el proyecto, necesitará una base de datos PostgreSQL. Puedes utilizar Docker para iniciar una instancia de PostgreSQL con la siguiente línea de comando:

    ```bash
    docker run --name box-challenge-postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres:16.1
    ```

    Luego, puede iniciar la instancia de PostgreSQL utilizando:

    ```bash
    docker start box-challenge-postgres
    ```
    

## Levantar el Proyecto

Una vez que la base de datos PostgreSQL esté en ejecución con la configuración deseada, puedes levantar el microservicio de las siguientes maneras:

### Desde IntelliJ IDEA

1. Ejecute la clase `BoxChallengeApplication.java` directamente desde su IDE.

### Desde Línea de Comando

1. Posiciónese en el directorio raíz del proyecto.
2. Ejecute el siguiente comando para construir el proyecto:

    ```bash
    ./gradlew clean build
    ```

3. Luego, ejecute el siguiente comando para iniciar el microservicio:

    ```bash
    ./gradlew bootRun
    ```

Una vez que el microservicio esté en funcionamiento, puede probar los siguientes endpoints utilizando Postman o su herramienta favorita:

1. **POST:** `localhost:8080/api/documents/hash`
   - Agregue en el cuerpo (`body`) la clave "algorithm" con el valor "SHA-256" o "SHA-512".
   - Agregue en el cuerpo (`body`) la clave "files" y seleccionar el tipo de dato "file" para seleccionar uno o varios archivos.

2. **GET:** `localhost:8080/api/documents`
   - Lista todos los documentos creados.

3. **GET:** `localhost:8080/api/documents`
   - Agregue en el cuerpo las keys "hash" con el valor del hash en la base de datos y "hashType" con el valor "SHA-256" o "SHA-512".