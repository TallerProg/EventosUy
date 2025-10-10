document.addEventListener("DOMContentLoaded", () => {
  const tipo = (document.querySelector("#tipoUsuario")?.value || "").toLowerCase();

  // Mostrar los campos del tipo de usuario correspondiente
  const asistenteFields = document.querySelector("#asistente-fields");
  const organizadorFields = document.querySelector("#organizador-fields");

  if (tipo === "asistente") {
    asistenteFields?.classList.remove("hidden");
  } else if (tipo === "organizador") {
    organizadorFields?.classList.remove("hidden");
  }

  // Previsualizar imagen antes de subir
  const inputImg = document.querySelector("#imagen");
  if (inputImg) {
    inputImg.addEventListener("change", () => {
      const file = inputImg.files?.[0];
      if (file) {
        const reader = new FileReader();
        reader.onload = (e) => {
          const img = document.querySelector("#preview");
          if (img) img.src = e.target.result;
        };
        reader.readAsDataURL(file);
      }
    });
  }
});
