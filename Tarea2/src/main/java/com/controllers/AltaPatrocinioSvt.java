package com.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import servidorcentral.logica.*;

@WebServlet(name = "AltaPatrocinioSvt", urlPatterns = {"/organizador-patrocinios-alta"})
public class AltaPatrocinioSvt extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    req.setCharacterEncoding("UTF-8");

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
            if (tr != null && tr.getCosto() > 0f) {
              costo = tr.getCosto();
              float maxUYU = aporte * 0.20f;
              cantidad = (int)Math.floor(maxUYU / costo);
            }
          }
        }
      } catch (Exception ignore) {}

      String costoStr = (costo == null ? "null" : String.valueOf(costo));
      resp.getWriter().write("{\"cantidad\":"+cantidad+",\"costo\":"+costoStr+"}");
      return;
    }

    // Pantalla normal
    String evento  = req.getParameter("evento");
    String edicion = req.getParameter("edicion");

    // Mostramos el form; si faltan parámetros, lo indicamos pero no hacemos lógica de negocio acá
    if (isBlank(evento) || isBlank(edicion)) {
      req.setAttribute("msgError", "Faltan parámetros (evento y/o edición). Volvé desde la edición y reintentá.");
      forward(req, resp);
      return;
    }
    IControllerEvento ctrl = Factory.getInstance().getIControllerEvento();
   
   
	  Edicion edi = ctrl.findEdicion(edicion);
		if (edi != null && edi.getEvento() != null && evento.equals(edi.getEvento().getNombre())) {
	    List<TipoRegistro> treg =edi.getTipoRegistros();
	
    String[] nombresTipos = (treg == null) ? new String[0]
        : treg.stream().map(TipoRegistro::getNombre).toArray(String[]::new);
    req.setAttribute("TIPOS_REGISTRO", nombresTipos);
    }
    try {
      // Chequeo liviano para completar encabezados (no valida reglas)
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
    String cantidadStr   = req.getParameter("cantidadRegistros");

    try {
      // Parseo mínimo para tipos (el Controller hace todas las validaciones de negocio)
      Float aporte = (aporteStr == null || aporteStr.isBlank()) ? null : Float.valueOf(aporteStr);
      Integer cantidad = (cantidadStr == null || cantidadStr.isBlank()) ? null : Integer.valueOf(cantidadStr);
      ETipoNivel nivel = parseNivel(nivelStr);

      IControllerEvento ctrl = Factory.getInstance().getIControllerEvento();

      // Delegamos TODO: si hay algo inválido, el Controller lanzará excepción
      ctrl.altaPatrocinio(
          codigo,
          LocalDate.now(),
          (cantidad == null ? 0 : cantidad),
          (aporte == null ? 0f : aporte),
          nivel,
          institucion,
          edicion,
          tipoRegistro
      );

      String ok = "Patrocinio creado con éxito.";
      resp.sendRedirect(req.getContextPath()
          + "/ediciones-consulta?evento=" + url(evento)
          + "&edicion=" + url(edicion)
          + "&msgOk=" + url(ok));
      return;

    } catch (Exception e) {
      // Cualquier excepción (incluida la del Controller) se muestra en el JSP
      req.setAttribute("msgError", e.getMessage());
      req.setAttribute("EVENTO_SEL", evento);
      req.setAttribute("EDICION_SEL", edicion);
      req.setAttribute("form_institucion", institucion);
      req.setAttribute("form_nivel", nivelStr);
      req.setAttribute("form_aporte", aporteStr);
      req.setAttribute("form_tipoRegistro", tipoRegistro);
      req.setAttribute("form_cantidad", cantidadStr);
      req.setAttribute("form_codigo", codigo);
      IControllerEvento ctrl = Factory.getInstance().getIControllerEvento();
      
      
	  Edicion edi = ctrl.findEdicion(edicion);
		if (edi != null && edi.getEvento() != null && evento.equals(edi.getEvento().getNombre())) {
	    List<TipoRegistro> treg =edi.getTipoRegistros();
	
    String[] nombresTipos = (treg == null) ? new String[0]
        : treg.stream().map(TipoRegistro::getNombre).toArray(String[]::new);
    req.setAttribute("TIPOS_REGISTRO", nombresTipos);
    }
      cargarInstituciones(req);
      forward(req, resp);
    }
  }

  // Helpers
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
