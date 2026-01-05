eventos.uy

Plataforma web para la gestión integral de eventos desarrollada como proyecto del curso Taller de Programación (FING – UdelaR).

El sistema permite a organizadores publicar y administrar eventos y ediciones, y a los usuarios registrarse, recibir recomendaciones y obtener certificados de asistencia.

Contexto del proyecto

El proyecto fue desarrollado siguiendo un proceso iterativo e incremental, dividido en tres tareas, cada una con foco en una parte distinta del sistema:

Tarea 1: Servidor Central + aplicación de administración (Java Swing)

Tarea 2: Servidor Web (Servlets + JSP) sin comunicación remota

Tarea 3: Integración distribuida mediante Web Services

La aplicación está inspirada en plataformas reales de gestión de eventos y fue diseñada con énfasis en buenas prácticas de diseño orientado a objetos, arquitectura en capas y calidad de código.

Arquitectura

El sistema sigue una arquitectura distribuida, separando claramente responsabilidades entre los distintos componentes:

Servidor Central

Lógica de negocio y gestión de datos

Persistencia del sistema

Servicios expuestos vía Web Services (SOAP)

Aplicación de administración desarrollada en Java Swing

Servidor Web (Apache Tomcat)

Interfaz web para usuarios finales

Implementado con Servlets y JSP

Consume los servicios del Servidor Central

Clientes

Navegadores web de escritorio y dispositivos móviles

Tecnologías utilizadas
Lenguaje

Java 21

Backend

Java SE / Java EE

Web Services (SOAP)

Servlets y JSP

Frontend

JSP

HTML5 / CSS3

Bootstrap (diseño responsive y componentes visuales)

Infraestructura

Apache Tomcat 11

Aplicación de administración

Java Swing

Build, testing y calidad

Maven

JUnit

Checkstyle

PMD

Control de versiones

Git
