package com.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import ServidorCentral.logica.Factory;
import ServidorCentral.logica.IControllerEvento;
import ServidorCentral.logica.Evento;
import ServidorCentral.logica.Organizador;
import ServidorCentral.logica.ControllerUsuario.DTSesionUsuario;
import ServidorCentral.logica.ControllerUsuario.RolUsuario;

@WebServlet(name = "AltaEdicionSvt", urlPatterns = {"/ediciones-alta"})
@MultipartConfig(
    fileSizeThreshold = 1 * 1024 * 1024,
    maxFileSize = 10 * 1024 * 1024,
    maxRequestSize = 15 * 1024 * 1024
)
public class AltaEdicionSvt extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    // Debe estar logueado como ORGANIZADOR
    HttpSession ses = req.getSession(false);
    DTSesionUsuario u = (ses == null) ? null : (DTSesionUsuario) ses.getAttribute("usuario_logueado");
    if (u == null || u.getRol() != RolUsuario.ORGANIZADOR) {
      req.setAttribute("msgError", "Debés iniciar sesión como Organizador para dar de alta una edición.");
      req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
      return;
    }

    String nombreEvento = req.getParameter("evento");
    if (isBlank(nombreEvento)) {
      req.setAttribute("msgError", "Falta el parámetro 'evento'. Volvé a la página del evento y elegí 'Dar de alta edición'.");
      req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
      return;
    }

    // Llena el form
    req.setAttribute("form_evento", nombreEvento);
    req.setAttribute("form_fechaAlta", LocalDate.now().toString());

    req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    req.setCharacterEncoding("UTF-8");

    // Debe estar logueado como ORGANIZADOR
    HttpSession ses = req.getSession(false);
    DTSesionUsuario u = (ses == null) ? null : (DTSesionUsuario) ses.getAttribute("usuario_logueado");
    if (u == null || u.getRol() != RolUsuario.ORGANIZADOR) {
      req.setAttribute("msgError", "Debés iniciar sesión como Organizador para dar de alta una edición.");
      req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
      return;
    }

    String nombreEvento = req.getParameter("evento");
    String nombre       = req.getParameter("nombre");
    String sigla        = req.getParameter("sigla");
    String ciudad       = req.getParameter("ciudad");
    String pais         = req.getParameter("pais");
    String sIni         = req.getParameter("fechaIni");
    String sFin         = req.getParameter("fechaFin");

    // Fecha de alta del servidor (hoy)
    LocalDate fAlta = LocalDate.now();

    // Aguantar valores del form por si hay error
    req.setAttribute("form_evento", nombreEvento);
    req.setAttribute("form_nombre", nombre);
    req.setAttribute("form_sigla", sigla);
    req.setAttribute("form_ciudad", ciudad);
    req.setAttribute("form_pais", pais);
    req.setAttribute("form_fechaIni", sIni);
    req.setAttribute("form_fechaFin", sFin);
    req.setAttribute("form_fechaAlta", fAlta.toString());

    if (isBlank(nombreEvento) || isBlank(nombre) || isBlank(sigla) || isBlank(ciudad)
        || isBlank(pais) || isBlank(sIni) || isBlank(sFin)) {
      req.setAttribute("msgError", "Todos los campos son obligatorios (salvo la imagen).");
      req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
      return;
    }

    LocalDate fIni, fFin;
    try {
      fIni  = LocalDate.parse(sIni);
      fFin  = LocalDate.parse(sFin);
    } catch (DateTimeParseException ex) {
      req.setAttribute("msgError", "Formato de fecha inválido. Usá el selector de fecha.");
      req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
      return;
    }

    // Problemas de fechas
    if (fIni.isBefore(LocalDate.now())) {
      req.setAttribute("msgError", "La fecha de inicio no puede ser anterior a hoy.");
      req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
      return;
    }
    if (fFin.isBefore(fIni)) {
      req.setAttribute("msgError", "La fecha de fin no puede ser anterior a la fecha de inicio.");
      req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
      return;
    }

    String imagenWebPath = null;
    try {
      Part imagenPart = req.getPart("imagen");
      if (imagenPart != null && imagenPart.getSize() > 0) {
        String fileName = sanitizeFileName(getFileName(imagenPart));
        if (!isBlank(fileName)) {
          String relativeDir = "/img/ediciones";
          String absoluteDir = getServletContext().getRealPath(relativeDir);
          File dir = new File(absoluteDir);
          if (!dir.exists()) dir.mkdirs();

          String prefixed = System.currentTimeMillis() + "_" + fileName;
          File destino = new File(dir, prefixed);
          Files.copy(imagenPart.getInputStream(), destino.toPath());
          imagenWebPath = relativeDir + "/" + prefixed;
        }
      }
    } catch (Exception e) {
      req.setAttribute("msgError", "No se pudo guardar la imagen: " + e.getMessage());
      req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
      return;
    }

    try {
      IControllerEvento ctrl = Factory.getInstance().getIControllerEvento();

      Evento evento = ctrl.obtenerEventoPorNombre(nombreEvento);
      if (evento == null) {
        throw new IllegalArgumentException("El evento '" + nombreEvento + "' no existe.");
      }

      // Ver organizador a partir de la sesión (nickname)
      Organizador org = resolverOrganizador(ctrl, u);
      if (org == null) {
        throw new IllegalStateException("No se pudo resolver el Organizador a partir del usuario en sesión.");
      }

      // Alta con fecha de alta automática 
      ctrl.altaEdicionDeEvento(
          nombre, sigla, ciudad, pais,
          fIni, fFin, fAlta,
          evento, org, imagenWebPath
      );

      req.setAttribute("msgOk", "Edición de evento creada exitosamente.");
      clearForm(req);

    } catch (Exception e) {
      req.setAttribute("msgError", "No se pudo crear la edición: " + e.getMessage());
    }

    req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
  }

  // Intentamos matchear por nickname
  private static Organizador resolverOrganizador(IControllerEvento ctrl, DTSesionUsuario u) {
    if (u == null) return null;
    String nick = u.getNickname();
    String correo = u.getCorreo();
    try {
      List<Organizador> lista = ctrl.listarOrganizadores(); 
      if (lista != null) {
        for (Organizador o : lista) {
          if (o == null) continue;
          // 1) si Organizador.getNombre() es el nickname guardado
          try {
            String n = safeStr(o.getNombre());
            if (!n.isEmpty() && n.equalsIgnoreCase(nick)) return o;
          } catch (Exception ignore) {}

          // 2) si tiene getNickname()
          try {
            Object v = o.getClass().getMethod("getNickname").invoke(o);
            String n = safeStr(v);
            if (!n.isEmpty() && n.equalsIgnoreCase(nick)) return o;
          } catch (Exception ignore) {}

          // 3) si tiene getCorreo()
          try {
            Object v = o.getClass().getMethod("getCorreo").invoke(o);
            String c = safeStr(v);
            if (!c.isEmpty() && !safeStr(correo).isEmpty() && c.equalsIgnoreCase(correo)) return o;
          } catch (Exception ignore) {}
        }
      }
    } catch (Exception ignore) {}
    // último intento (si tenés un helper en tu controller concreto)
    try {
      Organizador o2 = ctrl.obtenerOrganizadorPorNombre(nick);
      if (o2 != null) return o2;
    } catch (Exception ignore) {}
    return null;
  }

  private static String safeStr(Object o) { return (o == null) ? "" : String.valueOf(o).trim(); }

  private static boolean isBlank(String s) {
    return s == null || s.trim().isEmpty();
  }
  private static String getFileName(Part part) {
    String cd = part.getHeader("content-disposition");
    if (cd == null) return null;
    for (String sub : cd.split(";")) {
      String t = sub.trim();
      if (t.startsWith("filename")) {
        return t.substring(t.indexOf('=') + 1).trim().replace("\"", "");
      }
    }
    return null;
  }
  private static String sanitizeFileName(String fileName) {
    if (fileName == null) return null;
    fileName = fileName.replace("\\", "/");
    fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
    return fileName.replaceAll("[^a-zA-Z0-9._-]", "_");
  }
  private static void clearForm(HttpServletRequest req) {
    req.setAttribute("form_nombre", null);
    req.setAttribute("form_sigla", null);
    req.setAttribute("form_ciudad", null);
    req.setAttribute("form_pais", null);
    req.setAttribute("form_fechaIni", null);
    req.setAttribute("form_fechaFin", null);
    req.setAttribute("form_fechaAlta", LocalDate.now().toString()); // solo display
  }
}

