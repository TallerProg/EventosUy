package com.controllers;

import com.config.WSClientProvider;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.xml.ws.Binding;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.soap.SOAPBinding;
import jakarta.xml.ws.soap.SOAPFaultException;

import cliente.ws.sc.DtSesionUsuario;
import cliente.ws.sc.DtUsuarioListaConsulta;
import cliente.ws.sc.WebServices;

@WebServlet(name = "AltaEdicionSvt", urlPatterns = {"/ediciones-alta"})
@MultipartConfig(
    fileSizeThreshold = 1 * 1024 * 1024,
    maxFileSize = 10 * 1024 * 1024,
    maxRequestSize = 15 * 1024 * 1024
)
public class AltaEdicionSvt extends HttpServlet {
  private static final long serialVersionUID = 1L;
	private static final String INST_IMG_DIR = "/media/img/ediciones";



  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    HttpSession ses = req.getSession(false);
    if (ses == null || ses.getAttribute("usuario_logueado") == null) {
      req.setAttribute("msgError", "Debés iniciar sesión como Organizador para dar de alta una edición.");
      req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
      return;
    }
    String evento = req.getParameter("evento");
    if (isBlank(evento)) {
      req.setAttribute("msgError", "Falta el parámetro 'evento'.");
      req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
      return;
    }
    req.setAttribute("form_evento", evento);
    req.setAttribute("form_fechaAlta", LocalDate.now().toString());
    req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");

    HttpSession ses = req.getSession(false);
    if (ses == null || ses.getAttribute("usuario_logueado") == null) {
      req.setAttribute("msgError", "Debés iniciar sesión como Organizador para dar de alta una edición.");
      req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
      return;
    }

    String nicknameSesion = (String) ses.getAttribute("nickname");
    if (isBlank(nicknameSesion)) {
      try {
        DtSesionUsuario dto = (DtSesionUsuario) ses.getAttribute("usuario_logueado");
        if (dto != null) nicknameSesion = dto.getNickname();
      } catch (ClassCastException ignore) {}
    }
    if (isBlank(nicknameSesion)) {
      req.setAttribute("msgError", "No se pudo determinar el usuario en sesión.");
      req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
      return;
    }

    String evento = trim(req.getParameter("evento"));
    String nombre = trim(req.getParameter("nombre"));
    String sigla  = trim(req.getParameter("sigla"));
    String ciudad = trim(req.getParameter("ciudad"));
    String pais   = trim(req.getParameter("pais"));
    String sIni   = trim(req.getParameter("fechaIni"));
    String sFin   = trim(req.getParameter("fechaFin"));
    LocalDate fAlta = LocalDate.now();

    req.setAttribute("form_evento", evento);
    req.setAttribute("form_nombre", nombre);
    req.setAttribute("form_sigla", sigla);
    req.setAttribute("form_ciudad", ciudad);
    req.setAttribute("form_pais", pais);
    req.setAttribute("form_fechaIni", sIni);
    req.setAttribute("form_fechaFin", sFin);
    req.setAttribute("form_fechaAlta", fAlta.toString());

    if (isBlank(evento) || isBlank(nombre) || isBlank(sigla) || isBlank(ciudad) ||
        isBlank(pais) || isBlank(sIni) || isBlank(sFin)) {
      req.setAttribute("msgError", "Todos los campos son obligatorios.");
      req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
      return;
    }

    LocalDate fIni, fFin;
    try {
      fIni = LocalDate.parse(sIni);
      fFin = LocalDate.parse(sFin);
    } catch (DateTimeParseException ex) {
      req.setAttribute("msgError", "Formato de fecha inválido.");
      req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
      return;
    }
    if (fFin.isBefore(fIni)) {
      req.setAttribute("msgError", "La fecha de fin no puede ser anterior a la fecha de inicio.");
      req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
      return;
    }
	if (fFin.isBefore(LocalDate.now())) {
		req.setAttribute("msgError", "La fecha de fin no puede ser anterior a la fecha actual.");
		req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
		return;
	}

    try {
    	cliente.ws.sc.WebServicesService service = WSClientProvider.newService();
        cliente.ws.sc.WebServices port = service.getWebServicesPort();

      DtUsuarioListaConsulta dtOrg = port.consultaDeUsuario(nicknameSesion);
      if (dtOrg == null || isBlank(dtOrg.getNickname())) {
        req.setAttribute("msgError", "No se pudo resolver el Organizador a partir del usuario en sesión.");
        req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
        return;
      }
      String nickOrganizador = dtOrg.getNickname();

      if (port.existeEdicionPorNombre(evento, nombre)) {
        req.setAttribute("msgError", "Ya existe una edición con ese nombre para este evento.");
        req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
        return;
      }
      if (port.existeEdicionPorSigla(sigla)) {
        req.setAttribute("msgError", "Ya existe una edición con esa sigla.");
        req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
        return;
      }

      String imagenWebPath = null;
      Part imgPart = req.getPart("imagen");
      if (imgPart != null && imgPart.getSize() > 0) {
      	String original = submittedFileName(imgPart);
          String ext = extensionOf(original);
          String safeBase = slug(nombre.isEmpty() ? "evento" : nombre);
          String stamp = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
          String fileName = safeBase + "_" + stamp + (ext.isEmpty() ? "" : "." + ext);

          String realDir = getServletContext().getRealPath(INST_IMG_DIR);
          if (realDir == null) {
              realDir = System.getProperty("java.io.tmpdir") + File.separator + "eventuy-img";
          }
          File dir = new File(realDir);
          if (!dir.exists()) dir.mkdirs();

          File destino = new File(dir, fileName);
          imgPart.write(destino.getAbsolutePath());

          imagenWebPath = INST_IMG_DIR + "/" + fileName;
  }

      try {
    	    port.altaEdicionDeEventoDTO(
    	        evento,
    	        nickOrganizador,
    	        nombre,
    	        sigla,
    	        ciudad,
    	        pais,
    	        fIni.toString(),           // "yyyy-MM-dd"
    	        fFin.toString(),           // "yyyy-MM-dd"
    	        fAlta.toString(),          // "yyyy-MM-dd"
    	        (imagenWebPath == null ? "" : imagenWebPath) // opcional: "" -> servidor lo convierte a null
    	    );
    	} catch (SOAPFaultException sfe) {
    	    req.setAttribute("msgError", faultMsg(sfe));
    	    req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
    	    return;
    	} catch (Exception e) {
    	    req.setAttribute("msgError", rootMsg(e));
    	    req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
    	    return;
    	}


      req.setAttribute("msgOk", "Edición de evento creada exitosamente.");
      clearForm(req);

    } catch (Exception e) {
      req.setAttribute("msgError", rootMsg(e));
    }

    req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
  }

  // ===== helpers =====
  private static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
  private static String trim(String s) { return (s == null) ? "" : s.trim(); }

  private static boolean isMimeImagenPermitido(String mime) {
    if (mime == null) return false;
    String m = mime.toLowerCase();
    return m.equals("image/jpeg") || m.equals("image/jpg") || m.equals("image/png");
  }

  private static String getFileName(Part part) {
    String cd = part.getHeader("content-disposition");
    if (cd != null) {
      for (String token : cd.split(";")) {
        String t = token.trim();
        if (t.startsWith("filename")) {
          String fn = t.substring(t.indexOf('=') + 1).trim().replace("\"", "");
          return new java.io.File(fn).getName();
        }
      }
    }
    try { return part.getSubmittedFileName(); } catch (Throwable ignore) {}
    return null;
  }

  private static String sanitizeFileName(String fileName) {
    if (fileName == null) return null;
    fileName = fileName.replace("\\", "/");
    fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
    return fileName.replaceAll("[^a-zA-Z0-9._-]", "_");
  }

  private static String normalizarExt(String fileName) {
    if (fileName == null) return null;
    String lower = fileName.toLowerCase();
    if (lower.endsWith(".jpeg")) return fileName.substring(0, fileName.length() - 5) + ".jpg";
    return fileName;
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

  private static String faultMsg(SOAPFaultException sfe) {
    try {
      String m = sfe.getFault() != null ? sfe.getFault().getFaultString() : sfe.getMessage();
      return (m == null || m.isBlank()) ? "Error remoto" : m;
    } catch (Throwable t) {
      return rootMsg(sfe);
    }
  }

  private static String rootMsg(Throwable t) {
    if (t == null) return "Error";
    String m = t.getMessage();
    if (m != null && !m.isBlank()) return m;
    Throwable c = t.getCause();
    return (c != null && c != t) ? rootMsg(c) : t.getClass().getSimpleName();
  }
  
  private static String submittedFileName(Part part) {
      String cd = part.getHeader("content-disposition");
      if (cd != null) {
          for (String token : cd.split(";")) {
              String t = token.trim();
              if (t.startsWith("filename")) {
                  String fn = t.substring(t.indexOf('=') + 1).trim().replace("\"", "");
                  return new File(fn).getName();
              }
          }
      }
      try { return part.getSubmittedFileName(); } catch (Throwable ignore) {}
      return "upload";
  }
  
  private static String extensionOf(String filename) {
      if (filename == null) return "";
      int dot = filename.lastIndexOf('.');
      if (dot < 0 || dot == filename.length() - 1) return "";
      return filename.substring(dot + 1).toLowerCase(Locale.ROOT);
  }
  
  private static String slug(String s) {
      String base = (s == null || s.isEmpty()) ? "evento" : s.toLowerCase(Locale.ROOT);
      base = base.replaceAll("[^a-z0-9-_]+", "-");
      base = base.replaceAll("-{2,}", "-");
      return base.replaceAll("^-|-$", "");
  }


}

