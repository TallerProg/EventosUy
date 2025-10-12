package com.controllers;

import java.io.File;
import java.io.IOException;
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
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import ServidorCentral.logica.ControllerInstitucion;
import ServidorCentral.logica.ControllerUsuario;
import ServidorCentral.logica.ControllerUsuario.DTSesionUsuario;
import ServidorCentral.logica.ControllerUsuario.RolUsuario;

@WebServlet(name = "AltaInstitucionSvt", urlPatterns = {"/AltaInstitucion"})
@MultipartConfig(
    fileSizeThreshold = 1 * 1024 * 1024,
    maxFileSize = 10 * 1024 * 1024, 
    maxRequestSize = 15 * 1024 * 1024
)
public class AltaInstitucionSvt extends HttpServlet {
    private static final String INST_IMG_DIR = "/media/img/institucion";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession(false); 
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login"); 
            return;
        }else {
        	 DTSesionUsuario usuario = (DTSesionUsuario) session.getAttribute("usuario_logueado");
             if (usuario != null) {
           	  	 RolUsuario rol = usuario.getRol(); // enum
                 boolean esOrg  = rol == ControllerUsuario.RolUsuario.ORGANIZADOR;
                 if(!esOrg) {
                	 response.sendRedirect(request.getContextPath() + "/login"); 
                     return;
                 }

             }else {
            	 response.sendRedirect(request.getContextPath() + "/login"); 
                 return;
             }
        }
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
        
        if (!isValidURL(url)) {
            setErrorMessage("La URL debe ser una dirección web válida (ej: https://ejemplo.com).", request);
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
        } catch (IllegalStateException ise) {
        	setErrorMessage("La imagen supera el tamaño permitido (5 MB por archivo, 10 MB por solicitud).",request);
        } catch (Exception ex) {
        	setErrorMessage("No se pudo guardar la imagen: " + ex.getMessage(),request);
        }

        ControllerInstitucion ctrl = new ControllerInstitucion();

        try {
            // Registrar la institución
            ctrl.altaInstitucion(nombre, url, descripcion,imagenWebPath);

            setSuccessMessage("Institución registrada con éxito.", request);
            response.sendRedirect(request.getContextPath() + "/Instituciones"); // Redirige a la página de la lista de instituciones

        } catch (Exception e) {
            String errorMessage = e.getMessage();
            if (errorMessage.contains("La institución ya existe")) {
                setErrorMessage("Ya existe una institución con ese nombre.", request);
            } else {
                setErrorMessage("No se pudo completar el Alta de institución: " + errorMessage, request);
            }
            forwardToForm(request, response);

        }

    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private void setErrorMessage(String message, HttpServletRequest request) {
        // Guardamos el mensaje de error en la sesión
        request.getSession().setAttribute("error_message", message);
    }

    private void setSuccessMessage(String message, HttpServletRequest request) {
        // Guardamos el mensaje de éxito en la sesión
        request.getSession().setAttribute("success_message", message);
    }

    private void forwardToForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirigir (reenviar) al mismo formulario
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/AltaInstitucion.jsp");
        dispatcher.forward(request, response);
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
    
    private static boolean isValidURL(String url) {
        try {
        	java.net.URL parsed = new java.net.URL(url);
            parsed.toURI(); 

            String host = parsed.getHost();
            if (host == null || host.isEmpty()) return false;

            if (!host.matches("^[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                return false;
            }
            // Solo aceptar http o https
            String protocol = parsed.getProtocol().toLowerCase(Locale.ROOT);
            return protocol.equals("http") || protocol.equals("https");
        } catch (Exception e) {
            return false;
        }
    }
}


