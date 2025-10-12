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
});
