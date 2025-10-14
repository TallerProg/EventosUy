document.addEventListener("DOMContentLoaded", () => {
  const tipo = (document.querySelector("#tipoUsuario")?.value || "").toLowerCase();

  const asistenteFields = document.querySelector("#asistente-fields");
  const organizadorFields = document.querySelector("#organizador-fields");

  if (tipo === "asistente") {
    asistenteFields?.classList.remove("hidden");
  } else if (tipo === "organizador") {
    organizadorFields?.classList.remove("hidden");
  }

  // Previsualizar imagen antes de subir
  const inputImg = document.querySelector("#imagen");
  const img = document.querySelector("#preview");

  if (inputImg && img) {
    inputImg.addEventListener("change", () => {
      const file = inputImg.files?.[0];
      if (!file) return;

      // Más simple y rápido que FileReader
      const url = URL.createObjectURL(file);
      img.src = url;
      img.onload = () => URL.revokeObjectURL(url);
    });
  }
  // Validar contraseñas antes de enviar
  const form = document.querySelector("#form-modificar");
  const pass = document.querySelector("#password");
  const confirm = document.querySelector("#confirmPassword");

  if (form && pass && confirm) {
    form.addEventListener("submit", (e) => {
      const p1 = pass.value.trim();
      const p2 = confirm.value.trim();
      if ((p1 !== "" || p2 !== "") && p1 !== p2) {
        e.preventDefault();
        alert("Las contraseñas no coinciden.");
      }
    });
  }

});
