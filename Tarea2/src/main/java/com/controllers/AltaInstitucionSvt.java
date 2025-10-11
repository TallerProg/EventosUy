package com.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import ServidorCentral.logica.ControllerInstitucion;

@WebServlet(name = "AltaInstitucionSvt", urlPatterns = {"/AltaInstitucion"})
@MultipartConfig(
    fileSizeThreshold = 1 * 1024 * 1024,
    maxFileSize = 10 * 1024 * 1024, 
    maxRequestSize = 15 * 1024 * 1024
)
public class AltaInstitucionSvt extends HttpServlet {
    private static final String EVENT_IMG_DIR = "/media/img/eventos";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Mostrar el formulario HTML (GET)
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/AltaInstitucion.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String url = request.getParameter("url");
        Part imgPart = request.getPart("imagen");

        // Validaciones mínimas
        if (isBlank(nombre)) {
            setErrorMessage("El nombre es obligatorio.", request);
            forwardToForm(request, response);
            return;
        }
        if (isBlank(descripcion)) {
            setErrorMessage("La descripcion es obligatoria", request);
            forwardToForm(request, response);
            return;
        }
        if (isBlank(url)) {
            setErrorMessage("El sitio web es obligatorio.", request);
            forwardToForm(request, response);
            return;
        }

        String imagenWebPath = null;

        // Verificar si hay un archivo
        try {
        if (imgPart != null && imgPart.getSize() > 0) {
            	String original = submittedFileName(imgPart);
                String ext = extensionOf(original);
                String safeBase = slug(nombre.isEmpty() ? "evento" : nombre);
                String stamp = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
                String fileName = safeBase + "_" + stamp + (ext.isEmpty() ? "" : "." + ext);

                String realDir = getServletContext().getRealPath(EVENT_IMG_DIR);
                if (realDir == null) {
                    realDir = System.getProperty("java.io.tmpdir") + File.separator + "eventuy-img";
                }
                File dir = new File(realDir);
                if (!dir.exists()) dir.mkdirs();

                File destino = new File(dir, fileName);
                imgPart.write(destino.getAbsolutePath());

                imagenWebPath = EVENT_IMG_DIR + "/" + fileName;
        }
        } catch (IllegalStateException ise) {
        	setErrorMessage("La imagen supera el tamaño permitido (5 MB por archivo, 10 MB por solicitud).",request);
        } catch (Exception ex) {
        	setErrorMessage("No se pudo guardar la imagen: " + ex.getMessage(),request);
        }

        ControllerInstitucion ctrl = new ControllerInstitucion();

        try {
            // Registrar la institución
            ctrl.altaInstitucion(nombre, url, descripcion);

            // Si hay una imagen, almacEnar su ruta
            if (imagenWebPath != null) {
                // Aquí puedes hacEr lo que necEsites con la imagenWebPath, como guardarla en la base de datos
            }

            setSuccEssMessage("Institución registrada con éxito.", request);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            if (errorMessage.contains("La institución ya existe")) {
                setErrorMessage("Ya existe una institución con ese nombre.", request);
            } else {
                setErrorMessage("No se pudo completar el Alta de institución: " + errorMessage, request);
            }
        }

        // Después de procEsar, reenviar la solicitud para mostrar los mensajes
        forwardToForm(request, response);
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private void setErrorMessage(String message, HttpServletRequest request) {
        // Guardamos el mensaje de error en la sesión
        request.getSession().setAttribute("error_message", message);
    }

    private void setSuccEssMessage(String message, HttpServletRequest request) {
        // Guardamos el mensaje de éxito en la sesión
        request.getSession().setAttribute("succEss_message", message);
    }

    private void forwardToForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirigir (reenviar) al mismo formulario
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/AltaInstitucion.jsp");
        dispatcher.forward(request, response);
    }

    private String getFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    private String sanitizeFileName(String fileName) {
        // Reemplazar caracteres no alfanuméricos (excEpto . y -) por _
        return fileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
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


