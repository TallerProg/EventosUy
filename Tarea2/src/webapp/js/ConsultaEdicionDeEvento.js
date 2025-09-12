document.addEventListener("DOMContentLoaded", () => {
  const eventoSel = document.querySelector("#evento");
  const edicionSel = document.querySelector("#edicion");
  const detalleEdicion = document.querySelector("#detalle-edicion");
  const imagenEdicion = document.querySelector("#imagen-edicion");

  // Simulamos imágenes por edición (ahora solo una por edición)
  const imagenesPorEdicion = {
    ed1: "../img/events/gallery-7.webp",
    ed2: "../img/events/speaker-1.webp"
  };

  eventoSel.addEventListener("change", () => {
    edicionSel.innerHTML = "<option value=''>-- Seleccione una edición --</option>";
    if (eventoSel.value === "1") {
      edicionSel.innerHTML += "<option value='ed1'>Conferencia Tech 2025</option>";
    }
    if (eventoSel.value === "2") {
      edicionSel.innerHTML += "<option value='ed2'>Feria del Libro 2025</option>";
    }
  });

  edicionSel.addEventListener("change", () => {
    if (!edicionSel.value) return;
    detalleEdicion.style.display = "flex";

    // Datos simulados
    document.querySelector("#nombre-edicion").textContent = "Ejemplo Edición";
    document.querySelector("#sigla-edicion").textContent = "EE25";
    document.querySelector("#ciudad-edicion").textContent = "Montevideo";
    document.querySelector("#pais-edicion").textContent = "Uruguay";
    document.querySelector("#fecha-alta").textContent = "12/09/2025";
    document.querySelector("#fecha-inicio").textContent = "15/10/2025";
    document.querySelector("#fecha-fin").textContent = "20/10/2025";
    document.querySelector("#organizador").textContent = "Ana Gómez";

    // Mostrar la imagen correspondiente
    imagenEdicion.src = imagenesPorEdicion[edicionSel.value] || "../img/default-event.png";
  });
});

