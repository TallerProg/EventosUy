package com.controllers;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;

import servidorcentral.logica.ControllerUsuario;
import servidorcentral.logica.Factory;
import servidorcentral.logica.IControllerInstitucion;
import servidorcentral.logica.Institucion;
import servidorcentral.logica.DTInstitucion;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import servidorcentral.excepciones.UsuarioRepetidoException;

@WebServlet("/Registrarse")
@MultipartConfig(
    fileSizeThreshold = 1024 * 64,
    maxFileSize = 1024 * 1024 * 5,
    maxRequestSize = 1024 * 1024 * 10
)
public class RegistrarSvt extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String USR_IMG_DIR = "/media/img/usuarios";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";


    private IControllerInstitucion ci;

    @Override
    public void init() {
        Factory f = Factory.getInstance();
        this.ci = f.getIControllerInstitucion();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        cargarInstitucionesYHoy(req);
        req.getRequestDispatcher("/WEB-INF/views/Registrarse.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String tipo       = trim(request.getParameter("tipoUsuario"));
        String nickname   = trim(request.getParameter("nickname"));
        String email      = trim(request.getParameter("email"));
        String nombre     = trim(request.getParameter("nombre"));
        String apellido   = trim(request.getParameter("apellido"));
        String password   = trim(request.getParameter("password"));
        String confirm    = trim(request.getParameter("confirmPassword"));

        String descripcion = trim(request.getParameter("descripcion"));
        String sitioWeb    = trim(request.getParameter("sitioWeb"));

        String fechaNacStr     = trim(request.getParameter("fechaNacimiento"));
        String institucionName = trim(request.getParameter("institucion")); 
        Part imgPart = request.getPart("imagen");

        String imagenWebPath = null;

        // Verificar si hay un archivo
        try {
        if (imgPart != null && imgPart.getSize() > 0) {
            	String original = submittedFileName(imgPart);
                String ext = extensionOf(original);
                String safeBase = slug(nombre.isEmpty() ? "evento" : nombre);
                String stamp = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
                String fileName = safeBase + "_" + stamp + (ext.isEmpty() ? "" : "." + ext);

                String realDir = getServletContext().getRealPath(USR_IMG_DIR);
                if (realDir == null) {
                    realDir = System.getProperty("java.io.tmpdir") + File.separator + "eventuy-img";
                }
                File dir = new File(realDir);
                if (!dir.exists()) dir.mkdirs();

                File destino = new File(dir, fileName);
                imgPart.write(destino.getAbsolutePath());

                imagenWebPath = USR_IMG_DIR + "/" + fileName;
        }
        } catch (IllegalStateException ise) {
        	setErrorMessage("La imagen supera el tamaño permitido (5 MB por archivo, 10 MB por solicitud).",request);
        } catch (Exception ex) {
        	setErrorMessage("No se pudo guardar la imagen: " + ex.getMessage(),request);
        }

        if (isBlank(tipo))       { setErrorAndForward("Seleccioná un tipo de usuario.", request, response); return; }
        if (isBlank(nickname))   { setErrorAndForward("El nickname es obligatorio.", request, response);     return; }
        if (isBlank(email))      { setErrorAndForward("El correo electrónico es obligatorio.", request, response); return; }
        if (isBlank(nombre))     { setErrorAndForward("El nombre es obligatorio.", request, response);       return; }
        if (isBlank(password) || isBlank(confirm)) {
            setErrorAndForward("Debés ingresar y confirmar la contraseña.", request, response);
            return;
        }
        if (!email.matches(EMAIL_REGEX)) {
            setErrorAndForward("El correo electrónico no tiene un formato válido.", request, response);
            return;
        }
        if (!password.equals(confirm)) {
            setErrorAndForward("Las contraseñas no coinciden.", request, response);
            return;
        }

        ControllerUsuario ctrl = new ControllerUsuario();

        try {
            if ("asistente".equalsIgnoreCase(tipo)) {
                if (isBlank(apellido)) {
                    setErrorAndForward("El apellido es obligatorio para asistentes.", request, response);
                    return;
                }
                LocalDate fechaNac = parseFecha(fechaNacStr);
                if (fechaNac == null) {
                    setErrorAndForward("La fecha de nacimiento no es válida.", request, response);
                    return;
                }
                if (fechaNac.isAfter(LocalDate.now())) {
                    setErrorAndForward("La fecha de nacimiento no puede ser futura.", request, response);
                    return;
                }

                ctrl.altaAsistente(
                        nickname, email, nombre, apellido, fechaNac, ci.findInstitucion(institucionName), password,imagenWebPath
                );

            } else if ("organizador".equalsIgnoreCase(tipo)) {
                if (isBlank(descripcion)) {
                    setErrorAndForward("La descripción es obligatoria para organizadores.", request, response);
                    return;
                }

                ctrl.altaOrganizador(
                        nickname, email, nombre, descripcion, sitioWeb, password,imagenWebPath
                );

            } else {
                setErrorAndForward("Tipo de usuario inválido.", request, response);
                return;
            }

            request.getSession(true).setAttribute("flash_ok", "Usuario registrado con éxito. Iniciá sesión.");
            response.sendRedirect(request.getContextPath() + "/login");

        } catch (UsuarioRepetidoException e) {
            setErrorAndForward("Ya existe un usuario con ese nickname o correo.", request, response);
        } catch (Exception e) {
            setErrorAndForward("No se pudo completar el registro: " + e.getMessage(), request, response);
        }
    }

    private void cargarInstitucionesYHoy(HttpServletRequest req) {
        List<DTInstitucion> instituciones = ci.getDTInstituciones();
        req.setAttribute("instituciones", instituciones);
        req.setAttribute("hoy", LocalDate.now().toString());
    }

    private Institucion obtenerInstitucionPorNombre(String nombre) {
        try { return ci.findInstitucion(nombre); } catch (Throwable ignored) {}
        return null;
    }

    private static String trim(String s) { return (s == null) ? "" : s.trim(); }
    private static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
    private static LocalDate parseFecha(String iso) {
        if (isBlank(iso)) return null;
        try { return LocalDate.parse(iso); } catch (DateTimeParseException e) { return null; }
    }

    private void setErrorAndForward(String msg, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("error", msg);
        cargarInstitucionesYHoy(req);
        req.getRequestDispatcher("/WEB-INF/views/Registrarse.jsp").forward(req, resp);
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
    private void setErrorMessage(String message, HttpServletRequest request) {
        // Guardamos el mensaje de error en la sesión
        request.getSession().setAttribute("error_message", message);
    }
}

