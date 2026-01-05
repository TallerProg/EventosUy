<h1 align="center">eventos.uy</h1>

<p align="center">
  Plataforma web para la <strong>gestión integral de eventos</strong> desarrollada como proyecto del curso
  <strong>Taller de Programación</strong> (FING – UdelaR).
</p>

<p>
  El sistema permite a organizadores publicar y administrar eventos y ediciones, y a los usuarios
  registrarse, recibir recomendaciones y obtener certificados de asistencia.
</p>

<hr />

<h2>Contexto del proyecto</h2>

<p>
  El proyecto fue desarrollado siguiendo un <strong>proceso iterativo e incremental</strong>, dividido en tres tareas,
  cada una con foco en una parte distinta del sistema:
</p>

<ul>
  <li>
    <strong>Tarea 1</strong>: Servidor Central + aplicación de administración (Java Swing)
  </li>
  <li>
    <strong>Tarea 2</strong>: Servidor Web (Servlets + JSP) sin comunicación remota
  </li>
  <li>
    <strong>Tarea 3</strong>: Integración distribuida mediante Web Services
  </li>
</ul>

<p>
  La aplicación está inspirada en plataformas reales de gestión de eventos y fue diseñada con énfasis en
  <strong>buenas prácticas de diseño orientado a objetos</strong>, <strong>arquitectura en capas</strong> y
  <strong>calidad de código</strong>.
</p>

<hr />

<h2>Arquitectura</h2>

<p>
  El sistema sigue una <strong>arquitectura distribuida</strong>, separando claramente responsabilidades entre
  los distintos componentes:
</p>

<h3>Servidor Central</h3>

<ul>
  <li>Lógica de negocio y gestión de datos</li>
  <li>Persistencia del sistema</li>
  <li>Servicios expuestos vía Web Services (SOAP)</li>
  <li>Aplicación de administración desarrollada en Java Swing</li>
</ul>

<h3>Servidor Web (Apache Tomcat)</h3>

<ul>
  <li>Interfaz web para usuarios finales</li>
  <li>Implementado con Servlets y JSP</li>
  <li>Consume los servicios del Servidor Central</li>
</ul>

<h3>Clientes</h3>

<ul>
  <li>Navegadores web de escritorio</li>
  <li>Navegadores web en dispositivos móviles</li>
</ul>

<hr />

<h2>Tecnologías utilizadas</h2>

<h3>Lenguaje</h3>
<ul>
  <li>Java 21</li>
</ul>

<h3>Backend</h3>
<ul>
  <li>Java SE / Java EE</li>
  <li>Web Services (SOAP)</li>
  <li>Servlets y JSP</li>
</ul>

<h3>Frontend</h3>
<ul>
  <li>JSP</li>
  <li>HTML5 / CSS3</li>
  <li>Bootstrap (diseño responsive y componentes visuales)</li>
</ul>

<h3>Infraestructura</h3>
<ul>
  <li>Apache Tomcat 11</li>
</ul>

<h3>Aplicación de administración</h3>
<ul>
  <li>Java Swing</li>
</ul>

<h3>Build, testing y calidad</h3>
<ul>
  <li>Maven</li>
  <li>JUnit</li>
  <li>Checkstyle</li>
  <li>PMD</li>
</ul>

<h3>Control de versiones</h3>
<ul>
  <li>Git</li>
</ul>
