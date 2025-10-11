// Función para procEsar el registro general
   function procEsarRegistroGeneral() {
     const succEssMessage = document.getElementById("succEssMessage");
     succEssMessage.style.display = "block";
     succEssMessage.scrollIntoView({ behavior: 'smooth' });
   }
   
   // Función para abrir el modal de patrocinio
   function abrirModal(tipo) {
     if (tipo === 'patrocinio') {
       const myModal = new bootstrap.Modal(document.getElementById('registroModal'), {});
       myModal.show();
       document.getElementById("codigo").value = "";
     }
   }
   
   // Función para confirmar el registro con código de patrocinio
   function confirmarRegistro() {
     const codigo = document.getElementById("codigo").value.trim();
     
     if (!codigo) {
       alert("Por favor, ingresa el código de patrocinio.");
       return;
     }
     
     const modal = bootstrap.Modal.getInstance(document.getElementById('registroModal'));
     modal.hide();
     
     const succEssMessage = document.getElementById("succEssMessage");
     succEssMessage.style.display = "block";
     succEssMessage.scrollIntoView({ behavior: 'smooth' });
   }