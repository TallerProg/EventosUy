// validacionRegistro.js

document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById('form-alta');
  const nombreInput = document.getElementById("nombre");

  // Lista de nombres ya existentes (ejemplo)
  const nombresExistentes = ["Full", "VIP", "General"];

  if (form) {
    form.addEventListener('submit', function (event) {
      event.preventDefault();
      let valido = true;

      // Resetear estado previo
      nombreInput.setCustomValidity("");

      // Validación HTML5
      if (!form.checkValidity()) {
        valido = false;
      }

      // Validación de nombre duplicado
      const nombreValor = nombreInput.value.trim();
      if (nombresExistentes.includes(nombreValor)) {
        valido = false;
        nombreInput.setCustomValidity("duplicado");
        nombreInput.classList.add("is-invalid");
      }

      if (valido) {
        window.location.href = "IndexLogeado.html";
      }

      form.classList.add("was-validated");
    }, false);
  }
});
