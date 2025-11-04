package com.controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import cliente.ws.sc.DtEdicion;
import cliente.ws.sc.DtInstitucion;
import cliente.ws.sc.DtInstitucionArray;
import cliente.ws.sc.DtTipoRegistro;
import cliente.ws.sc.ETipoNivel;


@WebServlet(name = "AltaPatrocinioSvt", urlPatterns = {"/organizador-patrocinios-alta"})
public class AltaPatrocinioSvt extends HttpServlet {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
  
      throws ServletException, IOException {

    req.setCharacterEncoding("UTF-8");
	cliente.ws.sc.WebServicesService service = new cliente.ws.sc.WebServicesService();
    cliente.ws.sc.WebServices port = service.getWebServicesPort();
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
            DtTipoRegistro tr = port.consultaTipoRegistro(edicion, tipoRegistro);
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

    String evento  = req.getParameter("evento");
    String edicion = req.getParameter("edicion");

    if (isBlank(evento) || isBlank(edicion)) {
      req.setAttribute("msgError", "Faltan parámetros (evento y/o edición). Volvé desde la edición y reintentá.");
      forward(req, resp);
      return;
    }

    try {

      DtEdicion dto = port.consultaEdicionDeEvento(evento, edicion);
      if (dto == null) {
        throw new IllegalArgumentException("No se encontró la edición '" + edicion + "' del evento '" + evento + "'.");
      }

      req.setAttribute("EVENTO_SEL", evento);
      req.setAttribute("EDICION_SEL", edicion);

      List<String> nombresTR = (List<String>) port.listarNombresTiposRegistroDTO(edicion);
      req.setAttribute("TIPOS_REGISTRO",
          (nombresTR == null) ? new String[0] : nombresTR.toArray(new String[0]));

      cargarInstitucionesDTO(req);

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
      Float aporte   = (aporteStr   == null || aporteStr.isBlank())   ? null : Float.valueOf(aporteStr);
      Integer cantidad = (cantidadStr == null || cantidadStr.isBlank()) ? null : Integer.valueOf(cantidadStr);
      ETipoNivel nivel = parseNivel(nivelStr);

  		cliente.ws.sc.WebServicesService service = new cliente.ws.sc.WebServicesService();
  		cliente.ws.sc.WebServices port = service.getWebServicesPort();
  		java.time.LocalDate javaLocalDate = java.time.LocalDate.now();
  		String fechaString = javaLocalDate.toString();
      port.altaPatrocinio(
          codigo,
          fechaString,
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
      req.setAttribute("msgError", e.getMessage());
      req.setAttribute("EVENTO_SEL", evento);
      req.setAttribute("EDICION_SEL", edicion);
      req.setAttribute("form_institucion", institucion);
      req.setAttribute("form_nivel", nivelStr);
      req.setAttribute("form_aporte", aporteStr);
      req.setAttribute("form_tipoRegistro", tipoRegistro);
      req.setAttribute("form_cantidad", cantidadStr);
      req.setAttribute("form_codigo", codigo);

      try {
    	cliente.ws.sc.WebServicesService service = new cliente.ws.sc.WebServicesService();
      	cliente.ws.sc.WebServices port = service.getWebServicesPort();
        List<String> nombresTR = (List<String>) port.listarNombresTiposRegistroDTO(edicion);
        req.setAttribute("TIPOS_REGISTRO",
            (nombresTR == null) ? new String[0] : nombresTR.toArray(new String[0]));
      } catch (Exception ignore) {
        req.setAttribute("TIPOS_REGISTRO", new String[0]);
      }

      cargarInstitucionesDTO(req);
      forward(req, resp);
    }
  }


  private static void forward(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.getRequestDispatcher("/WEB-INF/views/AltaPatrocinio.jsp").forward(req, resp);
  }

  private static boolean isBlank(String s){ return s == null || s.trim().isEmpty(); }

  private static ETipoNivel parseNivel(String s){
    if (s == null) return ETipoNivel.PLATINO;
    switch (s) {
      case "Platino": return ETipoNivel.PLATINO;
      case "Oro":     return ETipoNivel.ORO;
      case "Plata":   return ETipoNivel.PLATA;
      case "Bronce":  return ETipoNivel.BRONCE;
      default:        return ETipoNivel.PLATINO;
    }
  }

  // SOLO DTOs
  private static void cargarInstitucionesDTO(HttpServletRequest req) {
    try {
      cliente.ws.sc.WebServicesService service = new cliente.ws.sc.WebServicesService();
      cliente.ws.sc.WebServices port = service.getWebServicesPort();
      DtInstitucionArray instDTA= port.listarDTInstituciones();
      List<DtInstitucion> dts=instDTA.getItem();

      String[] nombresIns = (dts == null) ? new String[0]
          : dts.stream().map(DtInstitucion::getNombre).toArray(String[]::new);
      req.setAttribute("INSTITUCIONES", nombresIns);
    } catch (Exception e) {
      req.setAttribute("INSTITUCIONES", new String[0]);
    }
  }

  private static String url(String s) throws java.io.UnsupportedEncodingException {
    return java.net.URLEncoder.encode((s==null?"":s), java.nio.charset.StandardCharsets.UTF_8.name());
  }
}

