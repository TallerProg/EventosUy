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

    String evento  = req.getParameter("evento");
    String edicion = req.getParameter("edicion");

    if (isBlank(evento) || isBlank(edicion)) {
      req.setAttribute("msgError", "Faltan parámetros (evento y/o edición). Volvé desde la edición y reintentá.");
      forward(req, resp);
      return;
    }

    try {
      // Validamos que la edición exista
      IControllerEvento cEvt = Factory.getInstance().getIControllerEvento();
      DTEdicion dto = cEvt.consultaEdicionDeEvento(evento, edicion);
      if (dto == null) {
        throw new IllegalArgumentException("No se encontró la edición '" + edicion + "' del evento '" + evento + "'.");
      }

      // Contexto del form
      req.setAttribute("EVENTO_SEL", evento);
      req.setAttribute("EDICION_SEL", edicion);

      // Combo de instituciones
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

    // Repoblar (cantidad se calculará más abajo)
    req.setAttribute("EVENTO_SEL", evento);
    req.setAttribute("EDICION_SEL", edicion);
    req.setAttribute("form_institucion", institucion);
    req.setAttribute("form_nivel", nivelStr);
    req.setAttribute("form_aporte", aporteStr);
    req.setAttribute("form_tipoRegistro", tipoRegistro);
    req.setAttribute("form_codigo", codigo);

    try {
      if (isBlank(evento) || isBlank(edicion) || isBlank(institucion) ||
          isBlank(nivelStr) || isBlank(aporteStr) || isBlank(tipoRegistro) || isBlank(codigo)) {
        throw new IllegalArgumentException("Todos los campos son obligatorios.");
      }

      Float aporte = Float.valueOf(aporteStr);
      if (aporte <= 0f) {
        throw new IllegalArgumentException("Aporte debe ser mayor que cero.");
      }

      ETipoNivel nivel = parseNivel(nivelStr);
      IControllerEvento ctrl = Factory.getInstance().getIControllerEvento();

      // Obtener costo del tipo de registro
      DTTipoRegistro tr = ctrl.consultaTipoRegistro(edicion, tipoRegistro);
      Float costoTR = (tr != null ? tr.getCosto() : null);
      float costo = (costoTR != null) ? costoTR.floatValue() : 0f;

      // Calcular cantidad automática (máx. 20 % del aporte)
      int cantidadCalculada = 0;
      if (costo > 0f) {
        float maxUYU = aporte * 0.20f;
        cantidadCalculada = (int) Math.floor(maxUYU / costo);
      }

      // Alta del patrocinio
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

      req.setAttribute("msgOk", "Patrocinio registrado correctamente.");
      req.setAttribute("form_cantidad", String.valueOf(cantidadCalculada));

    } catch (Exception e) {
      req.setAttribute("msgError", e.getMessage());
    }

    // Recargar instituciones y mostrar el form
    cargarInstituciones(req);
    forward(req, resp);
  }

  /* ----------------- Helpers ------------------ */

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
}
