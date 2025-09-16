function getURLParameter(name) {
  const urlParams = new URLSearchParams(window.location.search);
  return urlParams.get(name);
}

function loadEventDetails(eventId) {
  // Simulación de datos del evento
  const eventData = {
    conferenciadetechnologia: {
      title: "Conferencia de Tecnología",
      sigla: "CONFTEC",
      image: "../img/events/ConfTecnologia.jpg",
      description: "Evento sobre innovación tecnológica.",
      categories: ["Tecnología", "Innovación"],
      dateCreated: "2025-01-10", 
      editions: [
        { name: "Edición prueba1 - 2025", date: "10/01/2025", location: "Montevideo", editionId: "CONFTEC25" },
        { name: "Edición prueba2 - 2026", date: "10/01/2026", location: "Montevideo", editionId: "CONFTEC26" },
      ],
    },
    maratonmontevideo: {
      title: "Maratón de Montevideo",
      sigla: "MARATON",
      image: "../img/events/maratonmontevideo.jpg",
      description: "Competencia deportiva anual en la capital",
      categories: ["Deporte", "Salud"],
      dateCreated: "2022-01-01", 
      editions: [
        { name: "Edición prueba1 - 2022", date: "01/01/2022", location: "Montevideo", editionId: "MARATON22" },
      ],
    },
  };

  console.log("Datos del evento: ", eventData); 
  const event = eventData[eventId];
  console.log('Evento encontrado: ', event);

  if (event) {
    document.getElementById("eventTitle").innerText = event.title;
    document.getElementById("eventSigla").innerText = `Sigla: ${event.sigla}`;
    
    document.getElementById("eventDateCreated").innerText = `Fecha de alta: ${event.dateCreated}`;

    document.getElementById("eventImage").innerHTML = `<img src="${event.image}" alt="${event.title}" class="img-fluid">`;
    document.getElementById("eventInfo").innerHTML = `<p>${event.description}</p><h4>Categorías:</h4><ul>${event.categories.map(category => `<li>${category}</li>`).join('')}</ul>`;

    const editionsList = document.getElementById("editionsList");
    event.editions.forEach(edition => {
      const editionItem = document.createElement("div");
      editionItem.classList.add("col-lg-4", "col-md-6");
      editionItem.innerHTML = `
        <div class="edition-card">
          <div class="edition-header">
            <h5>${edition.name}</h5>
            <p>${edition.date}</p>
          </div>
          <div class="edition-body">
            <p>Ubicación: ${edition.location}</p>
            <!-- Aquí hacemos que la edición sea clickeable, redirigiendo a una página de detalles de la edición -->
            <a href="consultaEdicion.html?editionId=${edition.name}" class="btn btn-primary">Ver detalles de la edición</a>
          </div>
        </div>
      `;
      editionsList.appendChild(editionItem);
    });
  } else {
    document.getElementById("eventTitle").innerText = "Evento no encontrado";
  }
}

document.addEventListener("DOMContentLoaded", function() {
  const eventId = getURLParameter('eventId');
  console.log('eventId en la URL:', eventId);  
  if (eventId) {
    loadEventDetails(eventId);
  } else {
    console.error('No se ha proporcionado el eventId en la URL');
  }
});

