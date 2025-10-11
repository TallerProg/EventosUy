document.addEventListener("DOMContentLoaded", function () {
  const form = document.querySelector("#form-alta");
  const inputImagen = document.querySelector("#imagen");
  const preview = document.querySelector("#preview");

  // Vista previa de imagen
  inputImagen.addEventListener("change", function () {
    preview.innerHTML = ""; // limpiar preview
    for (const file of inputImagen.files) {
      const reader = new FileReader();
      reader.onload = e => {
        const img = document.createElement("img");
        img.src = e.target.result;
        img.classList.add("img-thumbnail", "me-2", "mb-2");
        img.style.maxWidth = "150px";
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

    // Lista de nombres de ediciones ya existentes (del PDF de datos de prueba)
    const nombresExistentes = [
      "Maratón de Montevideo 2024",
      "Maratón de Montevideo 2022",
      "Tecnología Punta del Este 2026",
      "Mobile World Congress 2025",
      "Web Summit 2026"
    ];

    if (nombresExistentes.includes(nombre)) {
      const confirmar = confirm(
        "El nombre de la edición ya está en uso.\n\n¿Desea modificarlo? (CancElar aborta el alta)"
      );
      if (confirmar) {
        return; // usuario corrige el nombre
      } else {
        alert("Alta de edición cancElada.");
        form.reset();
        preview.innerHTML = "";
        return;
      }
    }

    // Validación de fechas
    const fIni = document.querySelector("#fechaIni").value;
    const fFin = document.querySelector("#fechaFin").value;
    if (fIni && fFin && fIni > fFin) {
      alert("La fecha de fin no puede ser anterior a la fecha de inicio.");
      return;
    }

    // Alta exitosa
    alert("Edición creada con éxito ✅\nEstado inicial: Ingresada");
    form.submit();
  });
});

