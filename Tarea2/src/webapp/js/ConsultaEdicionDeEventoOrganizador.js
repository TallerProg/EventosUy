document.addEventListener("DOMContentLoaded", () => {
     const eventoSel = document.querySelector("#evento");
     const edicionSel = document.querySelector("#edicion");
     const detalleEdicion = document.querySelector("#detalle-edicion");
     const tiposRegistro = document.querySelector("#tipos-registro");
     const listaRegistros = document.querySelector("#lista-registros");
     const patrocinios = document.querySelector("#patrocinios");
     const listaPatrocinios = document.querySelector("#lista-patrocinios");
     const listadoAsistentes = document.querySelector("#listado-asistentes");
     const tablaAsistentes = document.querySelector("#tabla-asistentes");

     // Datos hardcodeados desde el PDF
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
           patrocinios: ["Facultad de Ingeniería (Oro, aporte 20000)"],
           asistentes: [
             { nombre: "Luis Martínez", tipo: "Estudiante", fecha: "12/09/2025", estado: "Confirmado" }
           ]
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
           patrocinios: [],
           asistentes: [
             { nombre: "María López", tipo: "Estudiante", fecha: "20/11/2025", estado: "Pendiente" }
           ]
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
           patrocinios: [],
           asistentes: [
             { nombre: "Pedro Suárez", tipo: "Corredor 21K", fecha: "01/09/2024", estado: "Confirmado" },
             { nombre: "Ana Torres", tipo: "Corredor 21K", fecha: "02/09/2024", estado: "Pendiente" }
           ]
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
       patrocinios.style.display = "none";
       listadoAsistentes.style.display = "none";
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
		 tiposRegistro.style.display = edicion.registros.length ? "block" : "none";
		 // Agregar botón "Ver detalles" si no existe
		       let btnDetalles = document.querySelector("#btn-ver-detalles");
		       if (!btnDetalles) {
		         btnDetalles = document.createElement("a");
		         btnDetalles.id = "btn-ver-detalles";
		         btnDetalles.className = "btn btn-sm btn-primary ms-2";
		         btnDetalles.textContent = "Ver detalles";

		         // Insertar al lado del título
		         const titulo = tiposRegistro.querySelector("h5");
		         titulo.appendChild(btnDetalles);
		       }
		       btnDetalles.href = `ConsultaTipoRegistro.html?evento=${eventoId}&edicion=${edicion.id}`;
		     
		// Botón "Alta de tipo de Registro"
				 listaRegistros.innerHTML += `
				   <li class="text-end">
				     <a href="altaTipoRegistro.html" class="btn btn-primary btn-sm">
				       Alta de tipo de Registro
				     </a>
				   </li>
				 `;

         // Patrocinios
         listaPatrocinios.innerHTML = "";
         edicion.patrocinios.forEach(p => listaPatrocinios.innerHTML += `<li>${p}</li>`);
         patrocinios.style.display = edicion.patrocinios.length ? "block" : "none";

         // Asistentes
         tablaAsistentes.innerHTML = "";
         edicion.asistentes.forEach(a => {
           tablaAsistentes.innerHTML += `
             <tr>
               <td>${a.nombre}</td>
               <td>${a.tipo}</td>
               <td>${a.fecha}</td>
               <td>${a.estado}</td>
             </tr>`;
         });
         listadoAsistentes.style.display = edicion.asistentes.length ? "block" : "none";
       }
     });
   });
