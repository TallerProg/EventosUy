package com.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import ServidorCentral.logica.*;

@WebServlet(name = "AltaPatrocinioSvt", urlPatterns = {"/organizador-patrocinios-alta"})
public class AltaPatrocinioSvt extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    req.setCharacterEncoding("UTF-8");

    // ===== AJAX: cálculo en vivo =====
    // /organizador-patrocinios-alta?calc=1&edicion=XYZ&tipoRegistro=ABC&aporte=1234
    if ("1".equals(req.getParameter("calc"))) {
      resp.setContentType("application/json;charset=UTF-8");
      String edicion      = req.getParameter("edicion");
      String tipoRegistro = req.getParameter("tipoRegistro");
      String aporteStr    = req.getParameter("aporte");

      int cantidad = 0;
      Float costo  = null;

      try {
        if (edicion != null && tipoRegistro != null && aporteStr != null && !aporteStr.isBlank()) {
          float aporte = Float.parseFloat(aporteStr);
          if (aporte > 0f) {
            IControllerEvento ctrl = Factory.getInstance().getIControllerEvento();
            DTTipoRegistro tr = ctrl.consultaTipoRegistro(edicion, tipoRegistro);
              costo = tr.getCosto();
              float maxUYU = aporte * 0.20f;
              cantidad = (int)Math.floor(maxUYU / costo);
          }
        }
      } catch (Exception ignore) {}

      String costoStr = (costo == null ? "null" : String.valueOf(costo));
      resp.getWriter().write("{\"cantidad\":"+cantidad+",\"costo\":"+costoStr+"}");
      return;
    }

    // ===== Pantalla normal =====
    String evento  = req.getParameter("evento");
    String edicion = req.getParameter("edicion");

    if (isBlank(evento) || isBlank(edicion)) {
      req.setAttribute("msgError", "Faltan parámetros (evento y/o edición). Volvé desde la edición y reintentá.");
      forward(req, resp);
      return;
    }

    try {
      IControllerEvento cevt = Factory.getInstance().getIControllerEvento();
      DTEdicion dto = cevt.consultaEdicionDeEvento(evento, edicion);
      if (dto == null) throw new IllegalArgumentException("No se encontró la edición '" + edicion + "' del evento '" + evento + "'.");

      req.setAttribute("EVENTO_SEL", evento);
      req.setAttribute("EDICION_SEL", edicion);
      cargarInstituciones(req);

    } catch (Exception e) {
      req.setAttribute("msgError", e.getMessage());
    }

    forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    req.setCharacterEncoding("UTF-8");

    String evento        = req.getParameter("evento");
    String edicion       = req.getParameter("edicion");
    String institucion   = req.getParameter("institucion");
    String nivelStr      = req.getParameter("nivel");
    String aporteStr     = req.getParameter("aporte");
    String tipoRegistro  = req.getParameter("tipoRegistro");
    String codigo        = req.getParameter("codigo");

    try {
      if (isBlank(evento) || isBlank(edicion) || isBlank(institucion)
          || isBlank(nivelStr) || isBlank(aporteStr) || isBlank(tipoRegistro) || isBlank(codigo)) {
        throw new IllegalArgumentException("Todos los campos son obligatorios.");
      }

      Float aporte = Float.valueOf(aporteStr);
      if (aporte <= 0f) throw new IllegalArgumentException("Aporte debe ser mayor que cero.");

      ETipoNivel nivel = parseNivel(nivelStr);
      IControllerEvento ctrl = Factory.getInstance().getIControllerEvento();

      DTTipoRegistro tr = ctrl.consultaTipoRegistro(edicion, tipoRegistro);
      Float costoTR = (tr != null ? tr.getCosto() : null);
      float costo = (costoTR != null) ? costoTR : 0f;

      int cantidadCalculada = 0;
      if (costo > 0f) {
        float maxUYU = aporte * 0.20f;
        cantidadCalculada = (int) Math.floor(maxUYU / costo);
      }

      ctrl.altaPatrocinio(
          codigo,
          LocalDate.now(),
          cantidadCalculada,
          aporte,
          nivel,
          institucion,
          edicion,
          tipoRegistro
      );

      // ===== Redirigimos a la edición con mensaje OK =====
      String ok = "Patrocinio creado con éxito.";
      resp.sendRedirect(req.getContextPath()
          + "/ediciones-consulta?evento=" + url(evento)
          + "&edicion=" + url(edicion)
          + "&msgOk=" + url(ok));
      return;

    } catch (Exception e) {
      // Si hay error, volvemos a la misma pantalla con el error
      req.setAttribute("msgError", e.getMessage());
      req.setAttribute("EVENTO_SEL", evento);
      req.setAttribute("EDICION_SEL", edicion);
      req.setAttribute("form_institucion", institucion);
      req.setAttribute("form_nivel", nivelStr);
      req.setAttribute("form_aporte", aporteStr);
      req.setAttribute("form_tipoRegistro", tipoRegistro);
      req.setAttribute("form_codigo", codigo);
      cargarInstituciones(req);
      forward(req, resp);
    }
  }

  // ---- Helpers
  private static void forward(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.getRequestDispatcher("/WEB-INF/views/AltaPatrocinio.jsp").forward(req, resp);
  }

  private static boolean isBlank(String s){ return s == null || s.trim().isEmpty(); }

  private static ETipoNivel parseNivel(String s){
    if (s == null) return ETipoNivel.Platino;
    switch (s) {
      case "Platino": return ETipoNivel.Platino;
      case "Oro":     return ETipoNivel.Oro;
      case "Plata":   return ETipoNivel.Plata;
      case "Bronce":  return ETipoNivel.Bronce;
      default:        return ETipoNivel.Platino;
    }
  }

  private static void cargarInstituciones(HttpServletRequest req) {
    try {
      IControllerInstitucion cIns = Factory.getInstance().getIControllerInstitucion();
      List<Institucion> instituciones = cIns.getInstituciones();
      String[] nombresIns = (instituciones == null) ? new String[0]
          : instituciones.stream().map(Institucion::getNombre).toArray(String[]::new);
      req.setAttribute("INSTITUCIONES", nombresIns);
    } catch (Exception e) {
      req.setAttribute("INSTITUCIONES", new String[0]);
    }
  }

  private static String url(String s) throws java.io.UnsupportedEncodingException {
    return java.net.URLEncoder.encode((s==null?"":s), java.nio.charset.StandardCharsets.UTF_8.name());
  }
}
