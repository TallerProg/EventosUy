package com.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import ServidorCentral.excepciones.UsuarioRepetidoException;
import ServidorCentral.logica.ControllerUsuario;
import ServidorCentral.logica.Factory;
import ServidorCentral.logica.IControllerInstitucion;
import ServidorCentral.logica.Institucion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/Registrarse")
@MultipartConfig(
    fileSizeThreshold = 1024 * 64,
    maxFileSize = 1024 * 1024 * 5,
    maxRequestSize = 1024 * 1024 * 10
)
public class RegistrarSvt extends HttpServlet {
    private static final long serialVersionUID = 1L;

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

        Part imagenPart = null;
        try {
            imagenPart = request.getPart("imagen");
        } catch (IllegalStateException ex) {
            setErrorAndForward("El archivo enviado es demasiado grande.", request, response);
            return;
        }

        if (isBlank(tipo))       { setErrorAndForward("Seleccioná un tipo de usuario.", request, response); return; }
        if (isBlank(nickname))   { setErrorAndForward("El nickname es obligatorio.", request, response);     return; }
        if (isBlank(email))      { setErrorAndForward("El correo electrónico es obligatorio.", request, response); return; }
        if (isBlank(nombre))     { setErrorAndForward("El nombre es obligatorio.", request, response);       return; }
        if (isBlank(password) || isBlank(confirm)) {
            setErrorAndForward("Debés ingresar y confirmar la contraseña.", request, response);
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

                Institucion institucion = null;
                if (!isBlank(institucionName)) {
                    institucion = obtenerInstitucionPorNombre(institucionName); 
                    if (institucion == null) {
                        setErrorAndForward("La institución seleccionada no existe.", request, response);
                        return;
                    }
                }

                ctrl.altaAsistente(
                        nickname, email, nombre, apellido, fechaNac, institucion, password
                );

            } else if ("organizador".equalsIgnoreCase(tipo)) {
                if (isBlank(descripcion)) {
                    setErrorAndForward("La descripción es obligatoria para organizadores.", request, response);
                    return;
                }

                ctrl.altaOrganizador(
                        nickname, email, nombre, descripcion, sitioWeb, password
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
        List<Institucion> instituciones = ci.getInstituciones(); 
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
}

