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
});
