//muchos datos de prueba y no funcional aun como debe
function cargarEdiciones() {
       const eventoSeleccionado = document.getElementById("evento").value;
       const edicionSelect = document.getElementById("edicion");
       const detallesEdicion = document.getElementById("detallesEdicion");
       const tiposRegistro = document.getElementById("tiposRegistro");
       const successMessage = document.getElementById("successMessage");
       
       edicionSelect.innerHTML = '<option value="">-- Elige una Edición --</option>';
       detallesEdicion.style.display = "none"; 
       tiposRegistro.style.display = "none"; 
       successMessage.style.display = "none"; 
       
       if (eventoSeleccionado === "") {
           edicionSelect.disabled = true;
           return;
       }
       edicionSelect.disabled = false;
       
       let ediciones = [];
       if (eventoSeleccionado === "1") {
           ediciones = [
               { id: "1", nombre: "Edición 1 - Montevideo", fecha: "10/10/2025", ubicacion: "Montevideo" },
               { id: "2", nombre: "Edición 2 - Punta del Este", fecha: "15/11/2025", ubicacion: "Punta del Este" }
           ];
       } else if (eventoSeleccionado === "2") {
           ediciones = [
               { id: "3", nombre: "Edición 1 - Montevideo", fecha: "20/11/2025", ubicacion: "Montevideo" },
               { id: "4", nombre: "Edición 2 - Colonia", fecha: "05/12/2025", ubicacion: "Colonia" }
           ];
       } else if (eventoSeleccionado === "3") {
           ediciones = [
               { id: "5", nombre: "Edición 1 - Montevideo Rock", fecha: "01/01/2025", ubicacion: "Montevideo" },
               { id: "6", nombre: "Edición 2 - Rocha", fecha: "12/01/2025", ubicacion: "Rocha" }
           ];
       }
       
       ediciones.forEach(edicion => {
           const option = document.createElement("option");
           option.value = edicion.id;
           option.textContent = edicion.nombre;
           edicionSelect.appendChild(option);
       });
   }
   
   function mostrarDetallesEdicion() {
       const edicionSeleccionada = document.getElementById("edicion").value;
       const detallesEdicion = document.getElementById("detallesEdicion");
       const tiposRegistro = document.getElementById("tiposRegistro");
       const successMessage = document.getElementById("successMessage");
       successMessage.style.display = "none";
       const ediciones = [
           { id: "1", fecha: "10/10/2025", ubicacion: "Montevideo" },
           { id: "2", fecha: "15/11/2025", ubicacion: "Punta del Este" },
           { id: "3", fecha: "20/11/2025", ubicacion: "Montevideo" },
           { id: "4", fecha: "05/12/2025", ubicacion: "Colonia" },
           { id: "5", fecha: "01/01/2025", ubicacion: "Montevideo" },
           { id: "6", fecha: "12/01/2025", ubicacion: "Rocha" }
       ];
       
       const edicion = ediciones.find(e => e.id === edicionSeleccionada);
       if (edicion) {
           document.getElementById("fechaEdicion").textContent = edicion.fecha;
           document.getElementById("ubicacionEdicion").textContent = edicion.ubicacion;
           detallesEdicion.style.display = "block"; 
           tiposRegistro.style.display = "block";
       } else {
           detallesEdicion.style.display = "none";
           tiposRegistro.style.display = "none";
       }
   }
   
   function procesarRegistroGeneral() {
       const evento = document.getElementById("evento").value;
       const edicion = document.getElementById("edicion").value;
       
       if (!evento || !edicion) {
           alert("Por favor, selecciona un evento y una edición antes de continuar.");
           return;
       }
       
       const successMessage = document.getElementById("successMessage");
       successMessage.style.display = "block";
       successMessage.scrollIntoView({ behavior: 'smooth' });
       

   }
   
   function abrirModal(tipo) {
       const evento = document.getElementById("evento").value;
       const edicion = document.getElementById("edicion").value;
       
       if (!evento || !edicion) {
           alert("Por favor, selecciona un evento y una edición antes de continuar.");
           return;
       }
       
       if (tipo === 'patrocinio') {
           const myModal = new bootstrap.Modal(document.getElementById('registroModal'), {});
           myModal.show(); 
           document.getElementById("codigo").value = "";
       }
   }
   
   function confirmarRegistro() {
       const codigo = document.getElementById("codigo").value.trim();
       
       if (!codigo) {
           alert("Por favor, ingresa el código de patrocinio.");
           return;
       }
       const modal = bootstrap.Modal.getInstance(document.getElementById('registroModal'));
       modal.hide();      
       const successMessage = document.getElementById("successMessage");
       successMessage.style.display = "block";
       successMessage.scrollIntoView({ behavior: 'smooth' });
       

   }