document.addEventListener("DOMContentLoaded", () => {
  const eventoSel = document.querySelector("#evento");
  const edicionSel = document.querySelector("#edicion");
  const detalleEdicion = document.querySelector("#detalle-edicion");
  const imagenEdicion = document.querySelector("#imagen-edicion");
  const tiposRegistro = document.querySelector("#tipos-registro");
  const listaRegistros = document.querySelector("#lista-registros");
  const patrocinios = document.querySelector("#patrocinios");
  const listaPatrocinios = document.querySelector("#lista-patrocinios");

  // --- Ediciones simuladas (fusionadas de tus dos versiones) ---
  const edicionesPorEvento = {
    1: [
      {
        id: "edev8",
        nombre: "Tecnología Punta del Este 2026",
        sigla: "CONFTECH26",
        ciudad: "Punta del Este",
        pais: "Uruguay",
        fechaAlta: "01/08/2025",
        fechaInicio: "06/04/2026",
        fechaFin: "10/04/2026",
        organizador: "Universidad de la República",
        imagen: "../img/events/ConfTecnologia.jpg",
        registros: ["Entrada General", "VIP"],
        patrocinios: ["Antel", "CUTI"]
      },
      {
        id: "edev9",
        nombre: "Mobile World Congress 2025",
        sigla: "MWC",
        ciudad: "Barcelona",
        pais: "España",
        fechaAlta: "21/08/2025",
        fechaInicio: "12/12/2025",
        fechaFin: "15/12/2025",
        organizador: "Corporación Tecnológica",
        imagen: "../img/events/gallery-7.webp",
        registros: ["General"],
        patrocinios: ["Telefónica", "Huawei"]
      }
    ],
    2: [
      {
        id: "ed3",
        nombre: "Feria del Libro 2024",
        sigla: "FL24",
        ciudad: "Montevideo",
        pais: "Uruguay",
        fechaAlta: "10/02/2024",
        fechaInicio: "01/02/2024",
        fechaFin: "05/02/2024",
        organizador: "Editorial UY",
        imagen: "../img/events/feriadellibro.jpg",
        registros: ["Entrada General"],
        patrocinios: ["Santillana", "Planeta"]
      }
    ],
    3: [
      {
        id: "edev1",
        nombre: "Montevideo Rock 2025",
        sigla: "MONROCK25",
        ciudad: "Montevideo",
        pais: "Uruguay",
        fechaAlta: "12/03/2025",
        fechaInicio: "20/11/2025",
        fechaFin: "22/11/2025",
        organizador: "Intendencia de Montevideo",
        imagen: "../img/events/montevideorock.png",
        registros: ["General", "VIP"],
        patrocinios: ["Cerveza Patagonia", "Antel"]
      }
    ],
    4: [
      {
        id: "edev2",
        nombre: "Maratón de Montevideo 2025",
        sigla: "MARATON25",
        ciudad: "Montevideo",
        pais: "Uruguay",
        fechaAlta: "05/02/2025",
        fechaInicio: "14/09/2025",
        fechaFin: "14/09/2025",
        organizador: "Intendencia de Montevideo",
        imagen: "../img/events/maratonmontevideo.jpg",
        registros: ["42K", "21K", "10K"],
        patrocinios: ["Asics", "Gatorade"]
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

    // --- Tipos de registro ---
    listaRegistros.innerHTML = "";
    edicion.registros?.forEach(r => {
      listaRegistros.innerHTML += `<li><a href="#">${r}</a></li>`;
    });
    tiposRegistro.style.display = edicion.registros?.length ? "block" : "none";

    // --- Patrocinios ---
    listaPatrocinios.innerHTML = "";
    edicion.patrocinios?.forEach(p => {
      listaPatrocinios.innerHTML += `<li><a href="#">${p}</a></li>`;
    });
    patrocinios.style.display = edicion.patrocinios?.length ? "block" : "none";
  });

  // --- Detectar evento y edición desde URL ---
  const urlParams = new URLSearchParams(window.location.search);
  const eventoId = urlParams.get("evento");
  const edicionId = urlParams.get("edicion");

  if (eventoId) {
    eventoSel.value = eventoId;
    eventoSel.dispatchEvent(new Event("change"));

    const ediciones = edicionesPorEvento[eventoId];

    if (edicionId) {
      edicionSel.value = edicionId;
      edicionSel.dispatchEvent(new Event("change"));
    } else if (ediciones && ediciones.length === 1) {
      edicionSel.value = ediciones[0].id;
      edicionSel.dispatchEvent(new Event("change"));
    }
  }
});
