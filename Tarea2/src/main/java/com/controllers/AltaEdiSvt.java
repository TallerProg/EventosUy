package com.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

/**
 * Alta de Edición de Evento
 * GET  -> muestra el formulario con la lista de eventos
 * POST -> procesa el alta; valida, guarda imagen (opcional) y llama la lógica
 */
@WebServlet(name = "AltaEdicionSvt", urlPatterns = {"/organizador/ediciones/alta"})
@MultipartConfig(
   fileSizeThreshold = 1 * 1024 * 1024, // 1MB en memoria
   maxFileSize = 10 * 1024 * 1024,      // 10MB por archivo
   maxRequestSize = 15 * 1024 * 1024    // 15MB total
)
public class AltaEdicionSvt extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    // 1) Traer la lista de eventos para el <select>
    // TODO: reemplazar por tu controlador real. Ej: Fabrica.getInstance().getCtrlEvento().listarEventos()
    List<String> eventos = Arrays.asList("Conferencia de Tecnología", "Feria del Libro");
    req.setAttribute("LISTA_EVENTOS", eventos.toArray(new String[0]));

    // 2) Forward a la vista
    req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    req.setCharacterEncoding("UTF-8");

    // --- 1) Leer parámetros ---
    String evento   = req.getParameter("evento");
    String nombre   = req.getParameter("nombre");
    String sigla    = req.getParameter("sigla");
    String ciudad   = req.getParameter("ciudad");
    String pais     = req.getParameter("pais");
    String sIni     = req.getParameter("fechaIni");
    String sFin     = req.getParameter("fechaFin");
    String sAlta    = req.getParameter("fechaAlta");

    // Para repoblar si hay error:
    req.setAttribute("form_evento", evento);
    req.setAttribute("form_nombre", nombre);
    req.setAttribute("form_sigla", sigla);
    req.setAttribute("form_ciudad", ciudad);
    req.setAttribute("form_pais", pais);
    req.setAttribute("form_fechaIni", sIni);
    req.setAttribute("form_fechaFin", sFin);
    req.setAttribute("form_fechaAlta", sAlta);

    // Reponer también la lista de eventos para el select
    List<String> eventos = Arrays.asList("Conferencia de Tecnología", "Feria del Libro");
    req.setAttribute("LISTA_EVENTOS", eventos.toArray(new String[0]));

    // --- 2) Validaciones básicas ---
    if (isBlank(evento) || isBlank(nombre) || isBlank(sigla) || isBlank(ciudad)
        || isBlank(pais) || isBlank(sIni) || isBlank(sFin) || isBlank(sAlta)) {
      req.setAttribute("msgError", "Todos los campos son obligatorios (salvo la imagen).");
      req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
      return;
    }

    LocalDate fIni, fFin, fAlta;
    try {
      fIni  = LocalDate.parse(sIni);
      fFin  = LocalDate.parse(sFin);
      fAlta = LocalDate.parse(sAlta);
    } catch (DateTimeParseException ex) {
      req.setAttribute("msgError", "Formato de fecha inválido. Usá el selector de fecha.");
      req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
      return;
    }

    if (fFin.isBefore(fIni)) {
      req.setAttribute("msgError", "La fecha de fin no puede ser anterior a la fecha de inicio.");
      req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
      return;
    }

    // --- 3) Subida de imagen (opcional) ---
    String imagenWebPath = null;
    Part imagenPart = req.getPart("imagen");
    if (imagenPart != null && imagenPart.getSize() > 0) {
      String fileName = sanitizeFileName(getFileName(imagenPart));
      if (!isBlank(fileName)) {
        String relativeDir = "/img/ediciones"; // carpeta dentro de tu webapp
        String absoluteDir = getServletContext().getRealPath(relativeDir);
        File dir = new File(absoluteDir);
        if (!dir.exists()) dir.mkdirs();

        String prefixed = System.currentTimeMillis() + "_" + fileName;
        File destino = new File(dir, prefixed);
        Files.copy(imagenPart.getInputStream(), destino.toPath());
        imagenWebPath = relativeDir + "/" + prefixed; // lo que podés mostrar en <img src="...">
      }
    }

    // --- 4) Llamar a tu lógica de negocio ---
    try {
      // TODO: acá llamás a tu capa lógica del JAR (Tarea1) para crear la edición.
      // Fabrica f = Fabrica.getInstance();
      // IControllerEdicion ctrl = f.getCtrlEdicion();
      // ctrl.altaEdicion(evento, nombre, sigla, ciudad, pais, fIni, fFin, fAlta, imagenWebPath);

      // Simulación ok:
      req.setAttribute("msgOk", "Edición creada correctamente.");
      // si querés, limpiar los campos del form:
      clearForm(req);

    } catch (Exception e) {
      req.setAttribute("msgError", e.getMessage());
    }

    // --- 5) Volver a la misma vista ---
    req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
  }

  // Helpers
  private static boolean isBlank(String s) {
    return s == null || s.trim().isEmpty();
  }
  private static String getFileName(Part part) {
    String cd = part.getHeader("content-disposition");
    if (cd == null) return null;
    for (String sub : cd.split(";")) {
      String t = sub.trim();
      if (t.startsWith("filename")) {
        String name = t.substring(t.indexOf('=') + 1).trim().replace("\"", "");
        return name;
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
    req.setAttribute("form_evento", null);
    req.setAttribute("form_nombre", null);
    req.setAttribute("form_sigla", null);
    req.setAttribute("form_ciudad", null);
    req.setAttribute("form_pais", null);
    req.setAttribute("form_fechaIni", null);
    req.setAttribute("form_fechaFin", null);
    req.setAttribute("form_fechaAlta", null);
  }
}
