document.addEventListener("DOMContentLoaded", () => {
  const contenedorPatrocinios = document.querySelector("#contenido-patrocinios");

  // Patrocinios hardcodeados de esta edición
  const listaPatrocinios = ["Facultad de Ingeniería (Oro, aporte 20000)"];
  // const listaPatrocinios = []; // prueba sin patrocinadores

  if (listaPatrocinios.length) {
    contenedorPatrocinios.innerHTML = `
      <div id="carouselPatrocinadores" class="carousel slide" data-bs-ride="carousel">
        <div class="carousel-inner text-center">
          ${listaPatrocinios.map((p, i) => `
            <div class="carousel-item ${i === 0 ? 'active' : ''}">
              <p>${p}</p>
            </div>
          `).join("")}
        </div>
        <!-- Controles -->
        <button class="carousel-control-prev" type="button" data-bs-target="#carouselPatrocinadores" data-bs-slide="prev">
          <span class="carousel-control-prev-icon bg-dark rounded-circle p-2" aria-hidden="true"></span>
          <span class="visually-hidden">Anterior</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#carouselPatrocinadores" data-bs-slide="next">
          <span class="carousel-control-next-icon bg-dark rounded-circle p-2" aria-hidden="true"></span>
          <span class="visually-hidden">Siguiente</span>
        </button>
      </div>
    `;
  } else {
    contenedorPatrocinios.innerHTML = `
      <div class="carousel-inner text-center">
        <div class="carousel-item active">
          <p>No hay patrocinios registrados</p>
        </div>
      </div>
    `;
  }
});
