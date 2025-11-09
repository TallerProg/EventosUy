document.addEventListener("DOMContentLoaded", () => {
    const eventoSel = document.querySelector("#evento");
    const edicionSel = document.querySelector("#edicion");
    const detalleEdicion = document.querySelector("#detalle-edicion");
    const tiposRegistro = document.querySelector("#tipos-registro");
    const listaRegistros = document.querySelector("#lista-registros");
    const miRegistro = document.querySelector("#mi-registro");

    // Datos hardcodeados desde PDF
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
          miRegistro: { tipo: "Estudiante", fecha: "12/09/2025", costo: "$1000", estado: "Confirmado" }
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
          miRegistro: { tipo: "Estudiante", fecha: "20/11/2025", costo: "$300", estado: "Pendiente" }
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
          imagen: "../img/events/maratonmontevideo.jpg",
          registros: ["Corredor 21K (costo: $500, cupos: 500)"],
          miRegistro: { tipo: "Corredor 21K", fecha: "01/09/2024", costo: "$500", estado: "Confirmado" }
        }
      ]
    };

    // Cargar ediciones
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
      miRegistro.style.display = "none";
    });

    // Mostrar detalle
    edicionSel.addEventListener("change", () => {
      const eventoId = eventoSel.value;
      const edicion = edicionesPorEvento[eventoId]?.find(e => e.id === edicionSel.value);
      if (edicion) {
        detalleEdicion.style.display = "flex";
        document.querySelector("#nombre-edicion").textContent = edicion.nombre;
        document.querySelector("#sigla-edicion").textContent = edicion.sigla;
        document.querySelector("#organizador").textContent = edicion.organizador;
        document.querySelector("#fechas").textContent = edicion.fechas;
        document.querySelector("#ciudad").textContent = edicion.ciudad;
        document.querySelector("#pais").textContent = edicion.pais;
        document.querySelector("#imagen-edicion").src = edicion.imagen;

        // Tipos de registro
        listaRegistros.innerHTML = "";
        edicion.registros.forEach(r => listaRegistros.innerHTML += `<li>${r}</li>`);
        tiposRegistro.style.display = "block";

        // Mi registro
        document.querySelector("#tipo-registro").textContent = edicion.miRegistro.tipo;
        document.querySelector("#fecha-registro").textContent = edicion.miRegistro.fecha;
        document.querySelector("#costo-registro").textContent = edicion.miRegistro.costo;
        document.querySelector("#estado-registro").textContent = edicion.miRegistro.estado;
        miRegistro.style.display = "block";
      }
    });
  });