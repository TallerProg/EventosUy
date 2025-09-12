document.addEventListener("DOMContentLoaded", function () {
  const form = document.querySelector("#form-alta");
  const inputImagenes = document.querySelector("#imagenes");
  const preview = document.querySelector("#preview");

  // Vista previa de imágenes
  inputImagenes.addEventListener("change", function () {
    preview.innerHTML = ""; // limpiar preview
    for (const file of inputImagenes.files) {
      const reader = new FileReader();
      reader.onload = e => {
        const img = document.createElement("img");
        img.src = e.target.result;
        preview.appendChild(img);
      };
      reader.readAsDataURL(file);
    }
  });

  form.addEventListener("submit", function (e) {
    e.preventDefault();

    const nombre = document.querySelector("#nombre").value.trim();
    const sigla = document.querySelector("#sigla").value.trim();
    const ciudad = document.querySelector("#ciudad").value.trim();
    const pais = document.querySelector("#pais").value.trim();

    // Validación de campos obligatorios
    if (!nombre || !sigla || !ciudad || !pais) {
      alert("Debe completar todos los campos obligatorios.");
      return;
    }

    // Validación de nombre ya usado (ejemplo con lista fija)
    const nombresExistentes = ["Montevideo Rock 2025", "Feria del Libro 2024"];
    if (nombresExistentes.includes(nombre)) {
      alert("El nombre ya está en uso. Por favor, elija otro.");
      return;
    }

    // Validación de fechas
    const fIni = document.querySelector("#fechaIni").value;
    const fFin = document.querySelector("#fechaFin").value;
    if (fIni && fFin && fIni > fFin) {
      alert("La fecha de fin no puede ser anterior a la fecha de inicio.");
      return;
    }

    alert("Edición creada con éxito ✅");
    form.submit();
  });
});