document.addEventListener("DOMContentLoaded", () => {
  const eventoSel = document.querySelector("#evento");
  const edicionSel = document.querySelector("#edicion");
  const detalleEdicion = document.querySelector("#detalle-edicion");
  const imagenEdicion = document.querySelector("#imagen-edicion");
  const tiposRegistro = document.querySelector("#tipos-registro");
  const listaRegistros = document.querySelector("#lista-registros");
  const patrocinios = document.querySelector("#patrocinios");
  const listaPatrocinios = document.querySelector("#lista-patrocinios");

  // --- Datos hardcodeados desde el PDF ---
  const edicionesPorEvento = {
    EV01: [
      {
        id: "EDEV08",
        nombre: "Tecnología Punta del Este 2026",
        sigla: "CONFTECH26",
        organizador: "Universidad de la República",
        fechas: "06/04/2026 - 10/04/2026",
        ciudad: "Punta del Este",
        pais: "Uruguay",
        imagen: "../img/events/ConfTecnologia.jpg",
        registros: ["Estudiante (costo: $1000, cupos: 50)"],
        patrocinios: ["Facultad de Ingeniería (Oro, aporte 20000)"]
      },
      {
        id: "EDEV10",
        nombre: "Web Summit 2026",
        sigla: "WS26",
        organizador: "Corporación Tecnológica",
        fechas: "13/01/2026 - 01/02/2026",
        ciudad: "Lisboa",
        pais: "Portugal",
        imagen: "../img/events/showcase-8.webp",
        registros: ["Estudiante (costo: $300, cupos: 1)"],
        patrocinios: []
      }
    ],
    EV04: [
      {
        id: "EDEV03",
        nombre: "Maratón de Montevideo 2024",
        sigla: "MARATON24",
        organizador: "Intendencia de Montevideo",
        fechas: "14/09/2024",
        ciudad: "Montevideo",
        pais: "Uruguay",
        imagen: "../img/ediciones/maratonMvd2024.jpeg",
        registros: ["Corredor 21K (costo: $500, cupos: 500)"],
        patrocinios: []
      }
    ]
  };

  // --- Cargar ediciones según evento ---
  eventoSel.addEventListener("change", () => {
    edicionSel.innerHTML = "<option value=''>-- Seleccione una edición --</option>";
    const eventoId = eventoSel.value;
    if (edicionesPorEvento[eventoId]) {
      edicionesPorEvento[eventoId].forEach(ed => {
        edicionSel.innerHTML += `<option value="${ed.id}">${ed.nombre}</option>`;
      });
    }
    detalleEdicion.style.display = "none";
    tiposRegistro.style.display = "none";
    patrocinios.style.display = "none";
  });

  // --- Mostrar detalle de edición ---
  edicionSel.addEventListener("change", () => {
    if (!edicionSel.value) return;
    const eventoId = eventoSel.value;
    const edicion = edicionesPorEvento[eventoId]?.find(e => e.id === edicionSel.value);
    if (!edicion) return;

    detalleEdicion.style.display = "flex";
    document.querySelector("#nombre-edicion").textContent = edicion.nombre;
    document.querySelector("#sigla-edicion").textContent = edicion.sigla;
    document.querySelector("#organizador").textContent = edicion.organizador;
    document.querySelector("#fechas").textContent = edicion.fechas;
    document.querySelector("#ciudad-edicion").textContent = edicion.ciudad;
    document.querySelector("#pais-edicion").textContent = edicion.pais;
    imagenEdicion.src = edicion.imagen || "../img/default-event.png";

    // --- Tipos de registro ---
    listaRegistros.innerHTML = "";
    edicion.registros.forEach(r => {
      listaRegistros.innerHTML += `<li>${r}</li>`;
    });
    tiposRegistro.style.display = edicion.registros.length ? "block" : "none";

    // --- Patrocinios ---
    listaPatrocinios.innerHTML = "";
    edicion.patrocinios.forEach(p => {
      listaPatrocinios.innerHTML += `<li>${p}</li>`;
    });
    patrocinios.style.display = edicion.patrocinios.length ? "block" : "none";
  });

  // --- Detectar evento y edición desde URL ---
  const urlParams = new URLSearchParams(window.location.search);
  const eventoId = urlParams.get("evento");
  const edicionId = urlParams.get("edicion");

  if (eventoId) {
    eventoSel.value = eventoId;
    eventoSel.dispatchEvent(new Event("change"));

    if (edicionId) {
      edicionSel.value = edicionId;
      edicionSel.dispatchEvent(new Event("change"));
    } else if (edicionesPorEvento[eventoId]?.length === 1) {
      edicionSel.value = edicionesPorEvento[eventoId][0].id;
      edicionSel.dispatchEvent(new Event("change"));
    }
  }
});

