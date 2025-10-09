package com.controllers;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.format.DateTimeFormatter;
import java.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import ServidorCentral.logica.Factory;
import ServidorCentral.logica.IControllerEvento;

// DTOs (ajustá nombres de paquetes si difieren)
import ServidorCentral.logica.DTEdicion;
import ServidorCentral.logica.DTTipoRegistro;
import ServidorCentral.logica.DTRegistro;
import ServidorCentral.logica.DTPatrocinio;

@WebServlet(name = "ConsultaEdicionSvt", urlPatterns = {"/ediciones/consulta"})
public class ConsultaEdicionSvt extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    String nombreEvento  = req.getParameter("evento");
    String nombreEdicion = req.getParameter("edicion");

    if (isBlank(nombreEvento) || isBlank(nombreEdicion)) {
      req.setAttribute("msgError", "Faltan parámetros: evento y/o edición.");
      req.getRequestDispatcher("/WEB-INF/views/ConsultaEdicion.jsp").forward(req, resp);
      return;
    }

    try {
      IControllerEvento ctrl = Factory.getInstance().getIControllerEvento();
      DTEdicion ed = ctrl.consultaEdicionDeEvento(nombreEvento, nombreEdicion);

      if (ed == null) {
        req.setAttribute("msgError", "No se encontró la edición solicitada.");
        req.getRequestDispatcher("/WEB-INF/views/ConsultaEdicion.jsp").forward(req, resp);
        return;
      }

      // ===== Roles (organizador / asistente en sesión) =====
      HttpSession session = req.getSession(false);
      boolean esOrganizador = false;
      boolean esAsistente   = false;
      String  nombreAsistenteSesion = null;

      if (session != null) {
        if (session.getAttribute("usuarioOrganizador") != null) {
          esOrganizador = true;
        }
        Object asi = session.getAttribute("usuarioAsistente");
        if (asi != null) {
          esAsistente = true;
          // Ajustá el getter real del Asistente de sesión:
          // nombreAsistenteSesion = ((ServidorCentral.logica.Asistente) asi).getNombre();
          nombreAsistenteSesion = tryToString(asi, "getNombre", "getNickname", "getNick", "getId");
        }
      }

      // ===== Aplanamos DTEdicion a un “view model” =====
      Map<String, Object> vm = new HashMap<>();

      vm.put("nombre", tryToString(ed, "getNombre"));
      vm.put("sigla",  tryToString(ed, "getSigla"));

      // Organizador
      Object orgDTO = tryCall(ed, "getOrganizador", "getDTOrganizador", "getOrganizadorDTO");
      vm.put("organizadorNombre", tryToString(orgDTO, "getNombre", "getNickname", "getNick"));

      // Fechas
      Object fIni = tryCall(ed, "getFechaInicio", "getFechaIni", "getFIni", "getInicio");
      Object fFin = tryCall(ed, "getFechaFin",    "getFechaFinal", "getFFin", "getFin");
      DateTimeFormatter out = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      vm.put("fechaIni", toDateString(fIni, out));
      vm.put("fechaFin", toDateString(fFin, out));

      // Ciudad / País
      vm.put("ciudad", tryToString(ed, "getCiudad"));
      vm.put("pais",   tryToString(ed, "getPais"));

      // Imagen (si tu DTO no tiene nada de imagen, dejará null)
      String imagen = tryToString(ed, "getImagenWebPath", "getImagenURL", "getUrlImagen", "getImagen");
      vm.put("imagen", imagen);

      // Tipos de Registro
      List<Map<String, String>> tiposVM = new ArrayList<>();
      @SuppressWarnings("unchecked")
      List<DTTipoRegistro> tregs = (List<DTTipoRegistro>) tryCall(ed, "getTiposRegistro", "getDTTiposRegistro", "listarTiposRegistro");
      if (tregs != null) {
        for (DTTipoRegistro tr : tregs) {
          Map<String, String> row = new LinkedHashMap<>();
          row.put("nombre", tryToString(tr, "getNombre"));

          // costo/cupos podrían ser primitivos => los convierto a String
          String costo = tryToString(tr, "getCosto", "getPrecio");
          String cupos = tryToString(tr, "getCupos", "getCupo", "getCapacidad");
          row.put("costo", costo);  // puede ser null o ""
          row.put("cupos", cupos);  // puede ser null o ""

          tiposVM.add(row);
        }
      }
      vm.put("tipos", tiposVM);

      // Registros (para tabla del organizador)
      List<Map<String, String>> regsVM = new ArrayList<>();
      @SuppressWarnings("unchecked")
      List<DTRegistro> regs = (List<DTRegistro>) tryCall(ed, "getRegistros", "getDTRegistros", "listarRegistros");
      if (regs != null) {
        for (DTRegistro r : regs) {
          Map<String, String> row = new LinkedHashMap<>();
          Object a = tryCall(r, "getAsistente", "getDTAsistente", "getAsistenteDTO");
          row.put("asistente", tryToString(a, "getNombre", "getNickname", "getNick"));
          row.put("tipo",      tryToString(r, "getTipoRegistroNombre", "getTipo", "getNombreTipo"));
          row.put("fecha",     tryToString(r, "getFecha", "getFechaRegistro", "getAlta"));
          row.put("estado",    tryToString(r, "getEstado", "getEstadoRegistro", "name"));
          regsVM.add(row);
        }
      }
      vm.put("registros", regsVM);

      // “Mi registro” (si es asistente)
      Map<String, String> miRegVM = null;
      if (esAsistente && regs != null && nombreAsistenteSesion != null) {
        for (DTRegistro r : regs) {
          Object a = tryCall(r, "getAsistente", "getDTAsistente", "getAsistenteDTO");
          String nomA = tryToString(a, "getNombre", "getNickname", "getNick");
          if (nombreAsistenteSesion.equals(nomA)) {
            miRegVM = new LinkedHashMap<>();
            miRegVM.put("tipo",   tryToString(r, "getTipoRegistroNombre", "getTipo", "getNombreTipo"));
            miRegVM.put("fecha",  tryToString(r, "getFecha", "getFechaRegistro", "getAlta"));
            miRegVM.put("estado", tryToString(r, "getEstado", "getEstadoRegistro", "name"));
            break;
          }
        }
      }
      vm.put("miRegistro", miRegVM);

      // Patrocinios
      List<String> patsVM = new ArrayList<>();
      @SuppressWarnings("unchecked")
      List<DTPatrocinio> pats = (List<DTPatrocinio>) tryCall(ed, "getPatrocinios", "getDTPatrocinios", "listarPatrocinios");
      if (pats != null) {
        for (DTPatrocinio p : pats) {
          // nombre de patrocinio o de la institución asociada
          String pn = tryToString(p, "getNombre", "getInstitucionNombre", "getNombreInstitucion");
          if (pn != null) patsVM.add(pn);
        }
      }
      vm.put("patrocinios", patsVM);

      // ===== Atributos para el JSP =====
      req.setAttribute("EVENTO_NOMBRE", nombreEvento);
      req.setAttribute("VM", vm);
      req.setAttribute("ES_ORGANIZADOR", esOrganizador);
      req.setAttribute("ES_ASISTENTE",   esAsistente);

      req.getRequestDispatcher("/WEB-INF/views/ConsultaEdicion.jsp").forward(req, resp);

    } catch (Exception e) {
      req.setAttribute("msgError", "Error al consultar la edición: " + e.getMessage());
      req.getRequestDispatcher("/WEB-INF/views/ConsultaEdicion.jsp").forward(req, resp);
    }
  }

  // ===== Utilidades =====
  private static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }

  private static Object tryCall(Object target, String... methodNames) {
    if (target == null) return null;
    Class<?> c = target.getClass();
    for (String m : methodNames) {
      try {
        Method mm = c.getMethod(m);
        return mm.invoke(target);
      } catch (Exception ignore) {}
    }
    return null;
  }

  private static String tryToString(Object target, String... methodNames) {
    Object val = tryCall(target, methodNames);
    return (val == null) ? null : String.valueOf(val);
  }

  private static String toDateString(Object dateLike, DateTimeFormatter outFmt) {
    if (dateLike == null) return null;
    // Soporta LocalDate / LocalDateTime / String
    try {
      // java.time.LocalDate
      if (dateLike instanceof java.time.LocalDate ld) {
        return ld.format(outFmt);
      }
      // java.time.LocalDateTime
      if (dateLike instanceof java.time.LocalDateTime ldt) {
        return ldt.toLocalDate().format(outFmt);
      }
      // String “yyyy-MM-dd”
      String s = String.valueOf(dateLike);
      if (s.matches("\\d{4}-\\d{2}-\\d{2}.*")) {
        return java.time.LocalDate.parse(s.substring(0, 10)).format(outFmt);
      }
      return s;
    } catch (Exception e) {
      return String.valueOf(dateLike);
    }
  }
}
