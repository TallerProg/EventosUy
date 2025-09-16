(function () {
  "use strict";

  /**
   * Añadir clase .scrolled al body cuando se hace scroll
   */
  function toggleScrolled() {
    const selectBody = document.querySelector("body");
    const selectHeader = document.querySelector("#header");
    if (!selectHeader) return;
    window.scrollY > 100
      ? selectBody.classList.add("scrolled")
      : selectBody.classList.remove("scrolled");
  }
  document.addEventListener("scroll", toggleScrolled);
  window.addEventListener("load", toggleScrolled);

  /**
   * Mobile nav toggle
   */
  const mobileNavToggleBtn = document.querySelector(".mobile-nav-toggle");
  function mobileNavToggle() {
    document.querySelector("body").classList.toggle("mobile-nav-active");
    mobileNavToggleBtn.classList.toggle("bi-list");
    mobileNavToggleBtn.classList.toggle("bi-x");
  }
  if (mobileNavToggleBtn) {
    mobileNavToggleBtn.addEventListener("click", mobileNavToggle);
  }
    //modal cerrar secion
document.addEventListener("DOMContentLoaded", () => {
  const confirmBtn = document.getElementById("confirmLogout");
  if (confirmBtn) {
    confirmBtn.addEventListener("click", () => {
      window.location.href = "../index.html"; 
    });
  }
});
  /**
   * Cerrar nav móvil al hacer clic en un link
   */
  document.querySelectorAll("#navmenu a").forEach((navLink) => {
    navLink.addEventListener("click", () => {
      if (document.querySelector(".mobile-nav-active")) {
        mobileNavToggle();
      }
    });
  });

  /**
   * Preloader
   */
  const preloader = document.querySelector("#preloader");
  if (preloader) {
    window.addEventListener("load", () => {
      preloader.remove();
    });
  }

  /**
   * Botón Scroll top
   */
  const scrollTop = document.querySelector(".scroll-top");
  function toggleScrollTop() {
    if (!scrollTop) return;
    window.scrollY > 100
      ? scrollTop.classList.add("active")
      : scrollTop.classList.remove("active");
  }
  if (scrollTop) {
    scrollTop.addEventListener("click", (e) => {
      e.preventDefault();
      window.scrollTo({ top: 0, behavior: "smooth" });
    });
  }
  window.addEventListener("load", toggleScrollTop);
  document.addEventListener("scroll", toggleScrollTop);
})();
//Filtrar por categoria
document.addEventListener("DOMContentLoaded", () => {
  const buttons = document.querySelectorAll(".category-btn");
  const cards = document.querySelectorAll(".speaker-card");
  let activeCategories = new Set();

  buttons.forEach(btn => {
    btn.addEventListener("click", () => {
      const category = btn.dataset.category;

      // Toggle selección
      if (activeCategories.has(category)) {
        activeCategories.delete(category);
        btn.classList.remove("active");
      } else {
        activeCategories.add(category);
        btn.classList.add("active");
      }

      // Filtrar tarjetas
      cards.forEach(card => {
        const cardCategories = card.dataset.categories.split(",");
        if (activeCategories.size === 0 || [...activeCategories].every(cat => cardCategories.includes(cat))) {
          card.parentElement.style.display = "block";
        } else {
          card.parentElement.style.display = "none";
        }
      });
    });
  });
});

/**
 * Avatar y nombre de usuario
 */
document.addEventListener("DOMContentLoaded", () => {
  const userAvatar = document.getElementById("userAvatar");
  const userName = document.getElementById("userName");

  if (!userAvatar || !userName) return;

  // Simulación (reemplazar con datos del backend)
  const user = {
    nombre: "NombreUsuario",
    foto: "" // "../img/usuarios/foto.jpg" si existe
  };

  userName.textContent = user.nombre;

  if (user.foto && user.foto.trim() !== "") {
    userAvatar.innerHTML = `<img src="${user.foto}" alt="Perfil" class="rounded-circle" width="36" height="36">`;
  } else {
    userAvatar.innerHTML = `<i class="bi bi-person-circle text-primary" style="font-size:2rem;"></i>`;
  }

  document.querySelectorAll('.countdown').forEach(function(countDownItem) {
    updateCountDown(countDownItem);
    setInterval(function() {
      updateCountDown(countDownItem);
    }, 1000);
  });

  /**
   * Initiate Pure Counter
   */
  new PureCounter();

  /**
   * Init isotope layout and filters
   */
  document.querySelectorAll('.isotope-layout').forEach(function(isotopeItem) {
    let layout = isotopeItem.getAttribute('data-layout') ?? 'masonry';
    let filter = isotopeItem.getAttribute('data-default-filter') ?? '*';
    let sort = isotopeItem.getAttribute('data-sort') ?? 'original-order';

    let initIsotope;
    imagesLoaded(isotopeItem.querySelector('.isotope-container'), function() {
      initIsotope = new Isotope(isotopeItem.querySelector('.isotope-container'), {
        itemSelector: '.isotope-item',
        layoutMode: layout,
        filter: filter,
        sortBy: sort
      });
    });

    isotopeItem.querySelectorAll('.isotope-filters li').forEach(function(filters) {
      filters.addEventListener('click', function() {
        isotopeItem.querySelector('.isotope-filters .filter-active').classList.remove('filter-active');
        this.classList.add('filter-active');
        initIsotope.arrange({
          filter: this.getAttribute('data-filter')
        });
        if (typeof aosInit === 'function') {
          aosInit();
        }
      }, false);
    });

  });

  /*
   * Pricing Toggle
   */

  const pricingContainers = document.querySelectorAll('.pricing-toggle-container');

  pricingContainers.forEach(function(container) {
    const pricingSwitch = container.querySelector('.pricing-toggle input[type="checkbox"]');
    const monthlyText = container.querySelector('.monthly');
    const yearlyText = container.querySelector('.yearly');

    pricingSwitch.addEventListener('change', function() {
      const pricingItems = container.querySelectorAll('.pricing-item');

      if (this.checked) {
        monthlyText.classList.remove('active');
        yearlyText.classList.add('active');
        pricingItems.forEach(item => {
          item.classList.add('yearly-active');
        });
      } else {
        monthlyText.classList.add('active');
        yearlyText.classList.remove('active');
        pricingItems.forEach(item => {
          item.classList.remove('yearly-active');
        });
      }
    });
  });

  /**
   * Init swiper sliders
   */
  function initSwiper() {
    document.querySelectorAll(".init-swiper").forEach(function(swiperElement) {
      let config = JSON.parse(
        swiperElement.querySelector(".swiper-config").innerHTML.trim()
      );

      if (swiperElement.classList.contains("swiper-tab")) {
        initSwiperWithCustomPagination(swiperElement, config);
      } else {
        new Swiper(swiperElement, config);
      }
    });
  }

  window.addEventListener("load", initSwiper);

  /**
   * Initiate glightbox
   */
  const glightbox = GLightbox({
    selector: '.glightbox'
  });

  /**
   * Correct scrolling position upon page load for URLs containing hash links.
   */
  window.addEventListener('load', function(e) {
    if (window.location.hash) {
      if (document.querySelector(window.location.hash)) {
        setTimeout(() => {
          let section = document.querySelector(window.location.hash);
          let scrollMarginTop = getComputedStyle(section).scrollMarginTop;
          window.scrollTo({
            top: section.offsetTop - parseInt(scrollMarginTop),
            behavior: 'smooth'
          });
        }, 100);
      }
    }
  });

  /**
   * Navmenu Scrollspy
   */
  let navmenulinks = document.querySelectorAll('.navmenu a');

  function navmenuScrollspy() {
    navmenulinks.forEach(navmenulink => {
      if (!navmenulink.hash) return;
      let section = document.querySelector(navmenulink.hash);
      if (!section) return;
      let position = window.scrollY + 200;
      if (position >= section.offsetTop && position <= (section.offsetTop + section.offsetHeight)) {
        document.querySelectorAll('.navmenu a.active').forEach(link => link.classList.remove('active'));
        navmenulink.classList.add('active');
      } else {
        navmenulink.classList.remove('active');
      }
    })
  }
  window.addEventListener('load', navmenuScrollspy);
  document.addEventListener('scroll', navmenuScrollspy);

})();


// consultaUsuario 

function cargarUsuario() {
  // Obtener ID de la URL
  const params = new URLSearchParams(window.location.search);
  const id = params.get("id");

  // Datos de usuarios con ediciones como objetos
  const usuarios = {
    // ----- Asistentes -----
    "atorres": { nombre: "Ana", apellido: "Torres", email: "atorres@gmail.com", tipo: "Asistente", fechaNacimiento: "12/05/1990", foto: "../img/usuarios/anaTorres.jpg", 
		ediciones: [	
			{nombre: "Maratón de Montevideo 2024", foto: "../img/ediciones/maratonMvd2024.jpeg"},
	    	{nombre: "Web Summit 2026", foto: "../img/ediciones/default.jpeg"}
    	]},
    // ----- Organizadores -----
    "miseventos": { nombre: "MisEventos", email: "contacto@miseventos.com", tipo: "Organizador", descripcion: "Empresa de organización de eventos.", url: "https://miseventos.com", foto: "../img/usuarios/misEventos.jpeg",
    	ediciones: [
			  { nombre: "Maratón de Montevideo 2024", foto: "../img/ediciones/maratonMvd2024.jpeg" },
			  { nombre: "Maratón de Montevideo 2022", foto: "../img/ediciones/maratonMvd2022.jpeg" },
			  { nombre: "Tecnología Punta del Este 2026", foto: "../img/ediciones/default.jpeg" },
			  { nombre: "Mobile World Congress 2025", foto: "../img/ediciones/mwc2025.png" },
			  { nombre: "Web Summit 2026", foto: "../img/ediciones/default.jpeg" }
	    ] }
    // ... etc.
  };
 
  // Renderizar datos en la página
  if (id && usuarios[id]) {
    const user = usuarios[id];
    document.getElementById("user-photo").src = user.foto;
    document.getElementById("user-name").innerText = user.nombre + (user.apellido ? " " + user.apellido : "");
    document.getElementById("user-email").innerText = user.email;
    document.getElementById("user-role").innerText = "Tipo: " + user.tipo;

    if (user.tipo === "Asistente") {
      document.getElementById("user-extra1").innerText = "Apellido: " + user.apellido;
      document.getElementById("user-extra2").innerText = "Fecha de Nacimiento: " + user.fechaNacimiento;
    } else if (user.tipo === "Organizador") {
      document.getElementById("user-extra1").innerText = "Descripción: " + user.descripcion;
      document.getElementById("user-extra2").innerHTML = user.url ? `URL: <a href="${user.url}" target="_blank">${user.url}</a>` : "URL: ---";
    }

    // Cargar ediciones en carrusel
    const editionsCarousel = document.getElementById("editions-carousel");
    editionsCarousel.innerHTML = "";

    if (user.ediciones && user.ediciones.length > 0) {
      user.ediciones.forEach((e, i) => {
        const item = document.createElement("div");
        item.className = `carousel-item ${i === 0 ? "active" : ""}`;

        // Definir link según tipo
        let link = "#"; 
        if (user.tipo === "Asistente") {
          link = "consultaRegistroAsis.html";
        } else if (user.tipo === "Organizador") {
          link = "consultaEdicionDeEvento.html";
        }

        item.innerHTML = `
          <div class="text-center">
            <img src="${e.foto}" class="d-block w-100 edition-carousel-img" alt="${e.nombre}">
            <div class="mt-3">
              <h5>${e.nombre}</h5>
              <a href="${link}" class="btn btn-primary btn-sm">Ver detalles</a>
            </div>
          </div>
        `;
        editionsCarousel.appendChild(item);
      });
    } else {
      editionsCarousel.innerHTML = `
        <div class="carousel-item active">
          <div class="d-flex justify-content-center align-items-center" style="height:200px;">
            <p>No hay ediciones disponibles</p>
          </div>
        </div>
      `;
    }
  }
}




