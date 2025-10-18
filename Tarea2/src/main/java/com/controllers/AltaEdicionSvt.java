package com.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import servidorcentral.logica.DTSesionUsuario;
import servidorcentral.logica.DTUsuarioListaConsulta;
import servidorcentral.logica.Factory;
import servidorcentral.logica.IControllerEvento;
import servidorcentral.logica.IControllerUsuario;
import servidorcentral.logica.RolUsuario;

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
        req.setAttribute("form_fechaAlta", LocalDate.now().toString()); // solo visual en la JSP
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

        // ---- Params
        String nombreEvento = req.getParameter("evento");
        String nombre       = req.getParameter("nombre");
        String sigla        = req.getParameter("sigla");
        String ciudad       = req.getParameter("ciudad");
        String pais         = req.getParameter("pais");
        String sIni         = req.getParameter("fechaIni");
        String sFin         = req.getParameter("fechaFin");
        LocalDate fAlta     = LocalDate.now();

        // ---- Persistencia de form (para la JSP)
        req.setAttribute("form_evento", nombreEvento);
        req.setAttribute("form_nombre", nombre);
        req.setAttribute("form_sigla", sigla);
        req.setAttribute("form_ciudad", ciudad);
        req.setAttribute("form_pais", pais);
        req.setAttribute("form_fechaIni", sIni);
        req.setAttribute("form_fechaFin", sFin);
        req.setAttribute("form_fechaAlta", fAlta.toString());

        // ---- Validaciones básicas
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
            // Solo interfaces y DTOs
            IControllerEvento  ce = Factory.getInstance().getIControllerEvento();
            IControllerUsuario cu = Factory.getInstance().getIControllerUsuario();

            DTUsuarioListaConsulta dtoOrg = cu.consultaDeUsuario(u.getNickname());
            if (dtoOrg == null) {
                req.setAttribute("msgError", "No se pudo resolver el Organizador a partir del usuario en sesión.");
                req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
                return;
            }
            String nickOrganizador = dtoOrg.getNickname();

            // Chequeos de unicidad (expuestos por la interfaz)
            if (ce.existeEdicionPorNombre(nombreEvento, nombre)) {
                req.setAttribute("msgError", "Ya existe una edición con ese nombre para este evento.");
                req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
                return;
            }
            if (ce.existeEdicionPorSiglaDTO(sigla)) {
                req.setAttribute("msgError", "Ya existe una edición con esa sigla.");
                req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
                return;
            }

            // ---- Imagen (opcional) con validación simple MIME/extensión
            String imagenWebPath = null;
            try {
                Part imagenPart = req.getPart("imagen");
                if (imagenPart != null && imagenPart.getSize() > 0) {
                    String contentType = imagenPart.getContentType(); // ej: image/jpeg, image/png
                    if (!isMimeImagenPermitido(contentType)) {
                        throw new IllegalArgumentException("Formato de imagen no permitido (solo JPG/PNG).");
                    }
                    String fileName = sanitizeFileName(getFileName(imagenPart));
                    if (!isBlank(fileName)) {
                        // Normalizar .jpeg -> .jpg
                        fileName = normalizarExt(fileName);

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

            // ---- Alta vía DTO (sin tocar entidades desde el servlet)
            ce.altaEdicionDeEventoDTO(
                nombreEvento,
                nickOrganizador,
                nombre,
                sigla,
                ciudad,
                pais,
                fIni,
                fFin,
                fAlta,
                imagenWebPath
            );

            req.setAttribute("msgOk", "Edición de evento creada exitosamente.");
            clearForm(req);

        } catch (Exception e) {
            req.setAttribute("msgError", "No se pudo crear la edición: " + e.getMessage());
        }

        // Misma vista que tu JSP
        req.getRequestDispatcher("/WEB-INF/views/AltaEdicion.jsp").forward(req, resp);
    }

    // ----------------- helpers -----------------

    private static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }

    private static boolean isMimeImagenPermitido(String mime) {
        if (mime == null) return false;
        String m = mime.toLowerCase();
        return m.equals("image/jpeg") || m.equals("image/jpg") || m.equals("image/png");
    }

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

    private static String normalizarExt(String fileName) {
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
}

