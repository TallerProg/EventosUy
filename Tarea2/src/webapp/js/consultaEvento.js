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
        	{name: "Tecnología Punta del Este 2026", date: "06/04/2026", location: "Uruguay"},
			{name: "Web Summit 2026", date: "13/01/2026", location: "Portugal"}
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
        {  name: "Maratón de Montevideo 2024", date: "14/09/2024", location: "Montevideo"}			
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
            <a href="ConsultaEdicionDeEvento.html" class="btn btn-primary">Ver detalles de la edición</a>
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

