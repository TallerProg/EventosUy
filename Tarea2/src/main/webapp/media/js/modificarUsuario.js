document.addEventListener("DOMContentLoaded", () => {
  const tipoUsuario = "asistente"; // o "organizador"

  const asistenteFields = document.querySelector("#asistente-fields");
  const organizadorFields = document.querySelector("#organizador-fields");

  if (tipoUsuario === "asistente") {
    asistenteFields.classList.remove("hidden");
  } else if (tipoUsuario === "organizador") {
    organizadorFields.classList.remove("hidden");
  }

  // Vista previa de imagen
  document.querySelector("#imagen").addEventListener("change", function() {
    const file = this.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = e => document.querySelector("#preview").src = e.target.result;
      reader.readAsDataURL(file);
    }
  });
});
