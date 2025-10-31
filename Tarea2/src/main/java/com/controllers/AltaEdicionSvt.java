package com.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import publicar.WebServices;
import publicar.WebServicesService;
import publicar.DTUsuarioListaConsulta; 

@WebServlet(name = "AltaEdicionSvt", urlPatterns = {"/ediciones-alta"})
@MultipartConfig(
    fileSizeThreshold = 1 * 1024 * 1024,
    maxFileSize = 10 * 1024 * 1024,
    maxRequestSize = 15 * 1024 * 1024
)
public class AltaEdicionSvt extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private WebServices getPort() throws Exception {
        WebServicesService svc = new WebServicesService();
        WebServices port = svc.getWebServicesPort();
        // Habilitar MTOM del lado cliente
        javax.xml.ws.Binding b = ((javax.xml.ws.BindingProvider) port).getBinding();
        if (b instanceof javax.xml.ws.soap.SOAPBinding) {
            ((javax.xml.ws.soap.SOAPBinding) b).setMTOMEnabled(true);
        }
        return port;
    }

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
            req.setAttribute("msgError", "Falta el parámetro 'evento'. Volvé a la página del evento y elegí 'Dar de alta edición'.");
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

        // nickname en sesión (ajustá si guardás otro objeto)
        String nicknameSesion = (String) ses.getAttribute("nickname");
        if (isBlank(nicknameSesion)) {
            // fallback si guardaste un DTO con getNickname()
            try {
                Object dto = ses.getAttribute("usuario_logueado");
                nicknameSesion = (String) dto.getClass().getMethod("getNickname").invoke(dto);
            } catch (Exception e) {
                req.setAttribute("msgError", "No se pudo determinar el usuario en sesión.");
                req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
                return;
            }
        }

        // Params
        String evento = trim(req.getParameter("evento"));
        String nombre = trim(req.getParameter("nombre"));
        String sigla  = trim(req.getParameter("sigla"));
        String ciudad = trim(req.getParameter("ciudad"));
        String pais   = trim(req.getParameter("pais"));
        String sIni   = trim(req.getParameter("fechaIni"));
        String sFin   = trim(req.getParameter("fechaFin"));

        LocalDate fAlta = LocalDate.now();

        // Persistencia para la JSP
        req.setAttribute("form_evento", evento);
        req.setAttribute("form_nombre", nombre);
        req.setAttribute("form_sigla", sigla);
        req.setAttribute("form_ciudad", ciudad);
        req.setAttribute("form_pais", pais);
        req.setAttribute("form_fechaIni", sIni);
        req.setAttribute("form_fechaFin", sFin);
        req.setAttribute("form_fechaAlta", fAlta.toString());

        // Validaciones
        if (isBlank(evento) || isBlank(nombre) || isBlank(sigla) || isBlank(ciudad)
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
            WebServices port = getPort();

            DTUsuarioListaConsulta dtOrg = port.consultaDeUsuario(nicknameSesion);
            if (dtOrg == null || isBlank(dtOrg.getNickname())) {
                req.setAttribute("msgError", "No se pudo resolver el Organizador a partir del usuario en sesión.");
                req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
                return;
            }
            String nickOrganizador = dtOrg.getNickname();

            // 2) Unicidad
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
            try {
                Part imagenPart = req.getPart("imagen");
                if (imagenPart != null && imagenPart.getSize() > 0) {
                    String ct = imagenPart.getContentType();
                    if (!isMimeImagenPermitido(ct)) {
                        throw new IllegalArgumentException("Formato de imagen no permitido (solo JPG/PNG).");
                    }
                    String fileName = sanitizeFileName(getFileName(imagenPart));
                    if (fileName != null && !fileName.isBlank()) {
                        fileName = normalizarExt(fileName);
                        byte[] bytes = imagenPart.getInputStream().readAllBytes();
                        imagenWebPath = port.subirImagenEdicion(evento, nombre, fileName, bytes);
                    }
                }
            } catch (Exception e) {
                req.setAttribute("msgError", "No se pudo subir la imagen: " + safeMsg(e));
                req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
                return;
            }

            // 4) Alta
            port.altaEdicionDeEvento(
                    evento,
                    nickOrganizador,
                    nombre,
                    sigla,
                    ciudad,
                    pais,
                    fIni.toString(),
                    fFin.toString(),
                    fAlta.toString(),
                    (imagenWebPath == null ? "" : imagenWebPath)
            );

            req.setAttribute("msgOk", "Edición de evento creada exitosamente.");
            clearForm(req);

        } catch (Exception e) {
            req.setAttribute("msgError", "No se pudo crear la edición: " + safeMsg(e));
        }

        req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
    }

    // ===== Helpers chicos =====

    private static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }

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

    private static String trim(String s) { 
        return (s == null) ? "" : s.trim(); 
    }

    private static String safeMsg(Throwable t) {
        String m = (t == null ? null : t.getMessage());
        return (m == null || m.isBlank()) ? t.getClass().getSimpleName() : m;
    }
}
