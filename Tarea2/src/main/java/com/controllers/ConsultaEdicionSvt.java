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
import ServidorCentral.logica.DTEdicion;
import ServidorCentral.logica.DTTipoRegistro;
import ServidorCentral.logica.DTRegistro;
import ServidorCentral.logica.DTPatrocinio;

@WebServlet(name = "ConsultaEdicionSvt", urlPatterns = {"/ediciones-consulta"})
public class ConsultaEdicionSvt extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    String nombreEvento  = req.getParameter("evento");
    String nombreEdicion = req.getParameter("edicion");

    if (isBlank(nombreEvento) || isBlank(nombreEdicion)) {
      req.setAttribute("msgError", "Faltan parámetros: evento y/o edición.");
      forward(req, resp);
      return;
    }

    try {
      IControllerEvento ctrl = Factory.getInstance().getIControllerEvento();
      DTEdicion ed = ctrl.consultaEdicionDeEvento(nombreEvento, nombreEdicion);
      if (ed == null) {
        req.setAttribute("msgError", "No se encontró la edición solicitada.");
        forward(req, resp);
        return;
      }

      // Roles de sesión (ajustá nombres de atributos si difieren)
      HttpSession session = req.getSession(false);
      boolean esOrganizador = false;
      boolean esAsistente   = false;
      String  nombreAsistenteSesion = null;

      if (session != null) {
        esOrganizador = (session.getAttribute("usuarioOrganizador") != null);
        Object asi = session.getAttribute("usuarioAsistente");
        if (asi != null) {
          esAsistente = true;
          nombreAsistenteSesion = tryToString(asi, "getNombre", "getNickname", "getNick", "getId");
        }
      }

      // ===== Armar VM que la JSP espera =====
      Map<String, Object> vm = new HashMap<>();

      vm.put("nombre", tryToString(ed, "getNombre"));
      vm.put("sigla",  tryToString(ed, "getSigla"));

      Object orgDTO = tryCall(ed, "getOrganizador", "getDTOrganizador", "getOrganizadorDTO");
      vm.put("organizadorNombre", tryToString(orgDTO, "getNombre", "getNickname", "getNick"));

      DateTimeFormatter out = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      Object fIni = tryCall(ed, "getFechaInicio", "getFechaIni", "getFIni", "getInicio");
      Object fFin = tryCall(ed, "getFechaFin",    "getFechaFinal", "getFFin", "getFin");
      vm.put("fechaIni", toDateString(fIni, out));
      vm.put("fechaFin", toDateString(fFin, out));

      vm.put("ciudad", tryToString(ed, "getCiudad"));
      vm.put("pais",   tryToString(ed, "getPais"));
      vm.put("imagen", tryToString(ed, "getImagenWebPath", "getImagenURL", "getUrlImagen", "getImagen"));

      // Tipos de registro
      List<Map<String, String>> tiposVM = new ArrayList<>();
      @SuppressWarnings("unchecked")
      List<DTTipoRegistro> tregs = (List<DTTipoRegistro>) tryCall(ed, "getTiposRegistro", "getDTTiposRegistro", "listarTiposRegistro");
      if (tregs != null) {
        for (DTTipoRegistro tr : tregs) {
          Map<String, String> row = new LinkedHashMap<>();
          row.put("nombre", tryToString(tr, "getNombre"));
          row.put("costo",  tryToString(tr, "getCosto", "getPrecio"));         // puede venir null
          row.put("cupos",  tryToString(tr, "getCupos", "getCupo", "getCapacidad"));
          tiposVM.add(row);
        }
      }
      vm.put("tipos", tiposVM);

      // Registros
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

      // Mi registro (si corresponde)
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
     
      @SuppressWarnings("unchecked")
      List<DTPatrocinio> pats = (List<DTPatrocinio>) tryCall(ed, "getPatrocinios", "getDTPatrocinios", "listarPatrocinios");
      req.setAttribute("patrocinios", pats);

      // Atributos para JSP
      req.setAttribute("VM", vm);
      req.setAttribute("ES_ORGANIZADOR", esOrganizador);
      req.setAttribute("ES_ASISTENTE",   esAsistente);

      forward(req, resp);

    } catch (Exception e) {
      req.setAttribute("msgError", "Error al consultar la edición: " + e.getMessage());
      forward(req, resp);
    }
  }

  private void forward(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.getRequestDispatcher("/WEB-INF/views/ConsultaEdicion.jsp").forward(req, resp);
  }

  // ===== Utils =====
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
    try {
      if (dateLike instanceof java.time.LocalDate ld) {
        return ld.format(outFmt);
      }
      if (dateLike instanceof java.time.LocalDateTime ldt) {
        return ldt.toLocalDate().format(outFmt);
      }
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



