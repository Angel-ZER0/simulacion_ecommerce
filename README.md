Simulacion de e-commerce
========================

Este proyecto simula un e-commerce pequeño en el que los usuarios pueden ver los productos ofertados, añadirlos a su carrito de compras, registrar métodos de pago, y una direccion a donde quieran que los mismos sean llevados, los usuarios con rol de proveedor pueden registrar marcas, y productos y vincularlos entre si para depues ponerlos en la página donde se exhiben junto con los demás

Este proyecto usa las tecnologías:

Spring Boot
-----------

*   spring-boot-starter-data-jpa: Proporciona integración con JPA (Java Persistence API) para trabajar con bases de datos relacionales. Incluye Hibernate como implementación de JPA. Facilita el manejo de entidades, repositorios y consultas de bases de datos.
*   spring-boot-starter-security: Implementa seguridad en el proyecto con Spring Security. Permite la configuración de autenticación y autorización. Permite la configuración de autenticación y autorización.
*   spring-boot-starter-validation: Proporciona soporte para la validación de datos a través de anotaciones de Bean Validation (JSR 380). Permite definir reglas de validación sobre clases de DTOs (Data Transfer Objects) y entidades.
*   spring-boot-starter-web: Añade soporte para construir servicios REST y aplicaciones web. Incluye un servidor embebido (por defecto, Tomcat) para ejecutar la aplicación. Facilita la creación de controladores y la configuración de rutas.
*   spring-boot-devtools: Agilizar desarrolo del proyecto mediante recarga automática

MySQL 8.0
---------

Usado como base de datos del proyecto

Lombok
------

Agilización de escritura del código mediante sus anotaciones

Java JWT de Auth0
-----------------

Usado en conjunto con spring-boot-starter-security para implementar un sistema de token que se usan en el proyeto para autenticar y validar a los usuarios mediante el uso de Jason Web Token y permitir o no, realizar acciones a los usuarios en los diferentes endpoints del proyecto.

Jackson
-------

Usuado para la desealización de algunos json que le llegan a la parte del servicio dado a la implementación de herencia de algunas tablas y dtos dentro del proyecto
