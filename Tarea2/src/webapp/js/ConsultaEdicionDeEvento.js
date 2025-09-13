document.addEventListener("DOMContentLoaded", () => {
  const eventoSel = document.querySelector("#evento");
  const edicionSel = document.querySelector("#edicion");
  const detalleEdicion = document.querySelector("#detalle-edicion");
  const imagenEdicion = document.querySelector("#imagen-edicion");

  // --- Ediciones simuladas ---
  const edicionesPorEvento = {
    1: [
      { id: "ed1", nombre: "Conferencia Tech 2024", sigla: "CT24", ciudad: "Montevideo", pais: "Uruguay", fechaAlta: "01/08/2024", fechaInicio: "10/01/2024", fechaFin: "12/01/2024", organizador: "Juan Pérez", imagen: "../img/events/ConfTecnologia.jpg" },
      { id: "ed2", nombre: "Conferencia Tech 2025", sigla: "CT25", ciudad: "Montevideo", pais: "Uruguay", fechaAlta: "15/09/2025", fechaInicio: "10/01/2025", fechaFin: "12/01/2025", organizador: "Ana Gómez", imagen: "../img/events/gallery-7.webp" }
    ],
    2: [
      { id: "ed3", nombre: "Feria del Libro 2024", sigla: "FL24", ciudad: "Montevideo", pais: "Uruguay", fechaAlta: "10/02/2024", fechaInicio: "01/02/2024", fechaFin: "05/02/2024", organizador: "Editorial UY", imagen: "../img/events/feriadellibro.jpg" },
      { id: "ed4", nombre: "Feria del Libro 2025", sigla: "FL25", ciudad: "Montevideo", pais: "Uruguay", fechaAlta: "20/02/2025", fechaInicio: "01/02/2025", fechaFin: "06/02/2025", organizador: "Asociación de Escritores", imagen: "../img/events/speaker-1.webp" }
    ],
    3: [
      { id: "ed5", nombre: "Montevideo Rock 2023", sigla: "MR23", ciudad: "Montevideo", pais: "Uruguay", fechaAlta: "15/02/2023", fechaInicio: "15/03/2023", fechaFin: "17/03/2023", organizador: "Producciones Rock", imagen: "../img/events/montevideorock.png" }
    ],
    4: [
      { id: "ed6", nombre: "Maratón Montevideo 2022", sigla: "MM22", ciudad: "Montevideo", pais: "Uruguay", fechaAlta: "01/12/2021", fechaInicio: "01/01/2022", fechaFin: "01/01/2022", organizador: "Asociación Deportiva", imagen: "../img/events/maratonmontevideo.jpg" }
    ],
    5: [
      { id: "ed7", nombre: "Montevideo Comics 2024", sigla: "MC24", ciudad: "Montevideo", pais: "Uruguay", fechaAlta: "15/03/2024", fechaInicio: "10/04/2024", fechaFin: "12/04/2024", organizador: "ComicUY", imagen: "../img/events/montevideocomics.png" }
    ]
  };

  // --- Cargar ediciones ---
  eventoSel.addEventListener("change", () => {
    edicionSel.innerHTML = "<option value=''>-- Seleccione una edición --</option>";
    const eventoId = eventoSel.value;
    if (edicionesPorEvento[eventoId]) {
      edicionesPorEvento[eventoId].forEach(ed => {
        edicionSel.innerHTML += `<option value="${ed.id}">${ed.nombre}</option>`;
      });
    }
  });

  // --- Mostrar detalle ---
  edicionSel.addEventListener("change", () => {
    if (!edicionSel.value) return;
    const eventoId = eventoSel.value;
    const edicion = edicionesPorEvento[eventoId].find(e => e.id === edicionSel.value);
    if (!edicion) return;

    detalleEdicion.style.display = "flex";
    document.querySelector("#nombre-edicion").textContent = edicion.nombre;
    document.querySelector("#sigla-edicion").textContent = edicion.sigla;
    document.querySelector("#ciudad-edicion").textContent = edicion.ciudad;
    document.querySelector("#pais-edicion").textContent = edicion.pais;
    document.querySelector("#fecha-alta").textContent = edicion.fechaAlta;
    document.querySelector("#fecha-inicio").textContent = edicion.fechaInicio;
    document.querySelector("#fecha-fin").textContent = edicion.fechaFin;
    document.querySelector("#organizador").textContent = edicion.organizador;
    imagenEdicion.src = edicion.imagen || "../img/default-event.png";
  });

  // --- Detectar evento desde URL ---
  const urlParams = new URLSearchParams(window.location.search);
  const eventoId = urlParams.get("evento");
  if (eventoId) {
    eventoSel.value = eventoId;
    eventoSel.dispatchEvent(new Event("change"));
  }
});
