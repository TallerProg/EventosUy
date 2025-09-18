document.addEventListener("DOMContentLoaded", function () {
  const tipoUsuario = document.getElementById("tipoUsuario");
  const organizadorFields = document.getElementById("organizadorFields");
  const asistenteFields = document.getElementById("asistenteFields");
  const apellidoAsistente = document.getElementById("apellidoAsistente");
  const descripcion = document.getElementById("descripcion");
  const apellido = document.getElementById("apellido");
  const fechaNacimiento = document.getElementById("fechaNacimiento");
  const password = document.getElementById("password");
  const confirmPassword = document.getElementById("confirmPassword");

  // Mostrar/ocultar campos según el tipo de usuario
  if (tipoUsuario) {
    tipoUsuario.addEventListener("change", function () {
      if (this.value === "organizador") {
        organizadorFields.style.display = "block";
        descripcion.setAttribute("required", "true");

        asistenteFields.style.display = "none";
        apellidoAsistente.style.display = "none";
        apellido.removeAttribute("required");
        fechaNacimiento.removeAttribute("required");
      } else if (this.value === "asistente") {
        asistenteFields.style.display = "block";
        apellidoAsistente.style.display = "block";
        apellido.setAttribute("required", "true");
        fechaNacimiento.setAttribute("required", "true");

        organizadorFields.style.display = "none";
        descripcion.removeAttribute("required");
      } else {
        organizadorFields.style.display = "none";
        asistenteFields.style.display = "none";
        apellidoAsistente.style.display = "none";
        descripcion.removeAttribute("required");
        apellido.removeAttribute("required");
        fechaNacimiento.removeAttribute("required");
      }
    });
  }

  // Limitar fecha de nacimiento a hoy como máximo
  if (fechaNacimiento) {
    const hoy = new Date().toISOString().split("T")[0];
    fechaNacimiento.setAttribute("max", hoy);
  }

  // Validación de formulario
  const form = document.getElementById("registroForm");
  if (form) {
    form.addEventListener("submit", function (event) {
      // Resetear mensajes de error
      confirmPassword.setCustomValidity("");

      // Verificar contraseñas
      if (password.value !== confirmPassword.value) {
        confirmPassword.setCustomValidity("Las contraseñas no coinciden");
      }

      if (!form.checkValidity()) {
        event.preventDefault();
        event.stopPropagation();
      } else {
        event.preventDefault();
        window.location.href = "IndexLogeado.html";
      }

      form.classList.add("was-validated");
    }, false);
  }
});
