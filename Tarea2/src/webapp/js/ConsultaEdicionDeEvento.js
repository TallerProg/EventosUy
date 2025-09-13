document.addEventListener("DOMContentLoaded", () => {
     const eventoSel = document.querySelector("#evento");
     const edicionSel = document.querySelector("#edicion");
     const detalleEdicion = document.querySelector("#detalle-edicion");
     const imagenEdicion = document.querySelector("#imagen-edicion");

     // --- Ediciones simuladas (extraídas de tu CargarDatos.java) ---
     const edicionesPorEvento = {
       1: [
         { id: "edev8", nombre: "Tecnología Punta del Este 2026", sigla: "CONFTECH26", ciudad: "Punta del Este", pais: "Uruguay", fechaAlta: "01/08/2025", fechaInicio: "06/04/2026", fechaFin: "10/04/2026", organizador: "Universidad de la República", imagen: "../img/events/ConfTecnologia.jpg" },
         { id: "edev9", nombre: "Mobile World Congress 2025", sigla: "MWC", ciudad: "Barcelona", pais: "España", fechaAlta: "21/08/2025", fechaInicio: "12/12/2025", fechaFin: "15/12/2025", organizador: "Corporación Tecnológica", imagen: "../img/events/gallery-7.webp" },
         { id: "edev10", nombre: "Web Summit 2026", sigla: "WS26", ciudad: "Lisboa", pais: "Portugal", fechaAlta: "04/06/2025", fechaInicio: "13/01/2026", fechaFin: "01/02/2026", organizador: "Corporación Tecnológica", imagen: "../img/events/showcase-8.webp" }
       ],
       2: [
         { id: "ed3", nombre: "Feria del Libro 2024", sigla: "FL24", ciudad: "Montevideo", pais: "Uruguay", fechaAlta: "10/02/2024", fechaInicio: "01/02/2024", fechaFin: "05/02/2024", organizador: "Editorial UY", imagen: "../img/events/feriadellibro.jpg" }
       ],
       3: [
         { id: "edev1", nombre: "Montevideo Rock 2025", sigla: "MONROCK25", ciudad: "Montevideo", pais: "Uruguay", fechaAlta: "12/03/2025", fechaInicio: "20/11/2025", fechaFin: "22/11/2025", organizador: "Intendencia de Montevideo", imagen: "../img/events/montevideorock.png" }
       ],
       4: [
         { id: "edev2", nombre: "Maratón de Montevideo 2025", sigla: "MARATON25", ciudad: "Montevideo", pais: "Uruguay", fechaAlta: "05/02/2025", fechaInicio: "14/09/2025", fechaFin: "14/09/2025", organizador: "Intendencia de Montevideo", imagen: "../img/events/maratonmontevideo.jpg" },
         { id: "edev3", nombre: "Maratón de Montevideo 2024", sigla: "MARATON24", ciudad: "Montevideo", pais: "Uruguay", fechaAlta: "21/04/2024", fechaInicio: "14/09/2024", fechaFin: "14/09/2024", organizador: "Intendencia de Montevideo", imagen: "../img/events/maratonmontevideo.jpg" },
         { id: "edev4", nombre: "Maratón de Montevideo 2022", sigla: "MARATON22", ciudad: "Montevideo", pais: "Uruguay", fechaAlta: "21/05/2022", fechaInicio: "14/09/2022", fechaFin: "14/09/2022", organizador: "Intendencia de Montevideo", imagen: "../img/events/maratonmontevideo.jpg" }
       ],
       5: [
         { id: "edev5", nombre: "Montevideo Comics 2024", sigla: "COMICS24", ciudad: "Montevideo", pais: "Uruguay", fechaAlta: "20/06/2024", fechaInicio: "18/07/2024", fechaFin: "21/07/2024", organizador: "MisEventos", imagen: "../img/events/montevideocomics.png" },
         { id: "edev6", nombre: "Montevideo Comics 2025", sigla: "COMICS25", ciudad: "Montevideo", pais: "Uruguay", fechaAlta: "04/07/2025", fechaInicio: "04/08/2025", fechaFin: "06/08/2025", organizador: "MisEventos", imagen: "../img/events/montevideocomics.png" }
       ],
       6: [
         { id: "edev7", nombre: "Expointer Uruguay 2025", sigla: "EXPOAGRO25", ciudad: "Durazno", pais: "Uruguay", fechaAlta: "01/02/2025", fechaInicio: "11/09/2025", fechaFin: "17/09/2025", organizador: "MisEventos", imagen: "../img/events/gallery-8.webp" }
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

	 // --- Detectar evento y edición desde URL ---
	 const urlParams = new URLSearchParams(window.location.search);
	 const eventoId = urlParams.get("evento");
	 const edicionId = urlParams.get("edicion");

	 if (eventoId) {
	   eventoSel.value = eventoId;
	   eventoSel.dispatchEvent(new Event("change"));

	   const ediciones = edicionesPorEvento[eventoId];

	   // Si vino la edición en la URL, seleccionarla
	   if (edicionId) {
	     edicionSel.value = edicionId;
	     edicionSel.dispatchEvent(new Event("change"));
	   }
	   // Si no vino pero hay solo una edición, mostrarla directo
	   else if (ediciones && ediciones.length === 1) {
	     edicionSel.value = ediciones[0].id;
	     edicionSel.dispatchEvent(new Event("change"));
	   }
	 }


   });
