package com.controllers;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import ServidorCentral.logica.*;
import ServidorCentral.logica.ControllerUsuario.DTSesionUsuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/editarperfil")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1MB
    maxFileSize = 10 * 1024 * 1024,  // 10MB
    maxRequestSize = 20 * 1024 * 1024 // 20MB
)
public class EditarPerfilSvt extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // ---------- GET: mostrar datos del usuario ----------
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        DTSesionUsuario sesUser = (session != null) ? (DTSesionUsuario) session.getAttribute("usuario_logueado"): null;
      
        if (sesUser == null ) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Obtener datos del usuario
        IControllerUsuario icu = Factory.getInstance().getIControllerUsuario();
        Usuario usuario = icu.getUsuario(sesUser.getNickname());
        if (usuario == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado.");
            return;
        }

        // Instituciones (para asistentes)
        IControllerInstitucion ici = Factory.getInstance().getIControllerInstitucion();
        req.setAttribute("LISTA_INSTITUCION", ici.getInstituciones().toArray(Institucion[]::new));

        req.setAttribute("USUARIO", usuario);
        req.setAttribute("TIPO_USUARIO", (usuario instanceof Organizador) ? "organizador" : "asistente");

        req.getRequestDispatcher("/WEB-INF/views/ModificarUsuario.jsp").forward(req, resp);
    }

    // ---------- POST: guardar cambios ----------
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuario_logueado") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        DTSesionUsuario dtu = (session != null) ? (DTSesionUsuario) session.getAttribute("usuario_logueado"): null;
        String nickname = (String) dtu.getNickname();
        IControllerUsuario icu = Factory.getInstance().getIControllerUsuario();
        Usuario user = icu.getUsuario(nickname);

        if (user == null) {
            throw new ServletException("Usuario no encontrado.");
        }

        // Campos comunes
        String nombre = req.getParameter("nombre");
        String password = req.getParameter("password");
        if (nombre != null && !nombre.isBlank()) user.setNombre(nombre);
        if (password != null && !password.isBlank()) user.setContrasena(password);

        // Si es asistente, actualizar datos específicos
        if (user instanceof Asistente) {
            Asistente a = (Asistente) user;
            String apellido = req.getParameter("apellido");
            if (apellido != null) a.setApellido(apellido);

            String fechaStr = req.getParameter("fechaNacimiento");
            if (fechaStr != null && !fechaStr.isBlank()) {
                try {
                    a.setfNacimiento(LocalDate.parse(fechaStr));
                } catch (Exception ignore) {}
            }

            String inst = req.getParameter("institucion");
            if (inst != null && !inst.isBlank()) {
                IControllerInstitucion ici = Factory.getInstance().getIControllerInstitucion();
                Institucion i = ici.findInstitucion(inst);
                if (i != null) a.setInstitucion(i);
            }
        }

        // Imagen de perfil
        Part imagenPart = req.getPart("imagen");
        if (imagenPart != null && imagenPart.getSize() > 0) {
            String uploadDir = getServletContext().getRealPath("/media/img/usuarios");
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String fileName = nickname + "_" + System.currentTimeMillis() + ".jpg";
            File destino = new File(dir, fileName);
            imagenPart.write(destino.getAbsolutePath());

            // Si la clase Usuario tiene un campo o método setFoto
            try {
                user.getClass().getMethod("setFoto", String.class).invoke(user, fileName);
            } catch (Exception ignore) {}
        }

        // Guardar cambios
        icu.modificarUsuario1(user);

        // Redirigir al perfil (refrescado)
        resp.sendRedirect(req.getContextPath() + "/perfil");
    }
}
