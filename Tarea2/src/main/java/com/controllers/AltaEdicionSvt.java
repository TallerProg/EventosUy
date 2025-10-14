package com.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import servidorcentral.logica.ControllerUsuario.DTSesionUsuario;
import servidorcentral.logica.ControllerUsuario.RolUsuario;
import servidorcentral.logica.Evento;
import servidorcentral.logica.ManejadorEvento;
import servidorcentral.logica.Factory;
import servidorcentral.logica.IControllerEvento;
import servidorcentral.logica.Organizador;

@WebServlet(name = "AltaEdicionSvt", urlPatterns = {"/ediciones-alta"})
@MultipartConfig(
    fileSizeThreshold = 1 * 1024 * 1024,
    maxFileSize = 10 * 1024 * 1024,
    maxRequestSize = 15 * 1024 * 1024
)
public class AltaEdicionSvt extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

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

    req.setAttribute("form_evento", nombreEvento);
    req.setAttribute("form_fechaAlta", LocalDate.now().toString());
    req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    req.setCharacterEncoding("UTF-8");

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
    LocalDate fAlta     = LocalDate.now();

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
      fIni = LocalDate.parse(sIni);
      fFin = LocalDate.parse(sFin);
    } catch (DateTimeParseException ex) {
      req.setAttribute("msgError", "Formato de fecha inválido. Usá el selector de fecha.");
      req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
      return;
    }

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

    try {
      IControllerEvento ctrl = Factory.getInstance().getIControllerEvento();

      Evento evento = ctrl.obtenerEventoPorNombre(nombreEvento);
      if (evento == null) {
        req.setAttribute("msgError", "El evento '" + nombreEvento + "' no existe.");
        req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
        return;
      }

      Organizador org = resolverOrganizador(ctrl, u);
      if (org == null) {
        req.setAttribute("msgError", "No se pudo resolver el Organizador a partir del usuario en sesión.");
        req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
        return;
      }

      if (edicionDuplicada(evento, nombre, sigla,req)) {
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

  private static boolean edicionDuplicada(Evento evento, String nombre, String sigla, HttpServletRequest req) {
      ManejadorEvento ctrl = ManejadorEvento.getInstancia();

	  try {
      try {
        if (ctrl.existeEdicion(nombre)) {
        	req.setAttribute("msgError", "Ya existe una edición con ese nombre");
        	return true;
        }
      } catch (Throwable ignore) {}
      try {
        if (ctrl.tieneEdicionSigla(sigla)) {
        	req.setAttribute("msgError", "Ya existe una edición con ese sigla.");
        	return true;
        }
      } catch (Throwable ignore) {}
      try {
        java.util.List<?> eds = null;
        try { eds = (java.util.List<?>) evento.getClass().getMethod("getEdiciones").invoke(evento); }
        catch (Throwable ignored) {}
        if (eds != null) {
          String nTarget = norm(nombre);
          String sTarget = norm(sigla);
          for (Object o : eds) {
            if (o == null) continue;
            try {
              String n = null, s = null;
              try { n = String.valueOf(o.getClass().getMethod("getNombre").invoke(o)); } catch (Throwable ignored2) {}
              try { s = String.valueOf(o.getClass().getMethod("getSigla").invoke(o)); }  catch (Throwable ignored2) {}
              if (!isBlank(n) && norm(n).equals(nTarget)) return true;
              if (!isBlank(s) && norm(s).equals(sTarget)) return true;
            } catch (Throwable ignored3) {}
          }
        }
      } catch (Throwable ignore) {}
    } catch (Throwable ignore) {}
    return false;
  }

  private static Organizador resolverOrganizador(IControllerEvento ctrl, DTSesionUsuario u) {
    if (u == null) return null;
    String nick = u.getNickname();
    String correo = u.getCorreo();
    try {
      java.util.List<Organizador> lista = ctrl.listarOrganizadores();
      if (lista != null) {
        for (Organizador o : lista) {
          if (o == null) continue;
          try {
            String n = safeStr(o.getNombre());
            if (!n.isEmpty() && n.equalsIgnoreCase(nick)) return o;
          } catch (Exception ignore) {}
          try {
            Object v = o.getClass().getMethod("getNickname").invoke(o);
            String n = safeStr(v);
            if (!n.isEmpty() && n.equalsIgnoreCase(nick)) return o;
          } catch (Exception ignore) {}
          try {
            Object v = o.getClass().getMethod("getCorreo").invoke(o);
            String c = safeStr(v);
            if (!c.isEmpty() && !safeStr(correo).isEmpty() && c.equalsIgnoreCase(correo)) return o;
          } catch (Exception ignore) {}
        }
      }
    } catch (Exception ignore) {}
    try {
      Organizador o2 = ctrl.obtenerOrganizadorPorNombre(nick);
      if (o2 != null) return o2;
    } catch (Exception ignore) {}
    return null;
  }

  private static String safeStr(Object o) { return (o == null) ? "" : String.valueOf(o).trim(); }
  private static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }

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
    req.setAttribute("form_fechaAlta", LocalDate.now().toString());
  }

  private static String norm(String s) {
    if (s == null) return "";
    String t = s.toLowerCase().trim();
    t = t.replace('á','a').replace('é','e').replace('í','i').replace('ó','o').replace('ú','u')
         .replace('ä','a').replace('ë','e').replace('ï','i').replace('ö','o').replace('ü','u')
         .replace('ñ','n');
    t = t.replaceAll("\\s+", " ");
    return t;
  }
}
