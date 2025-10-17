package com.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;

import servidorcentral.logica.*;
import servidorcentral.logica.ControllerUsuario.DTSesionUsuario;
import servidorcentral.logica.ControllerUsuario.RolUsuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/editarperfil")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,    // 1MB
    maxFileSize = 10 * 1024 * 1024,     // 10MB
    maxRequestSize = 20 * 1024 * 1024   // 20MB
)
public class EditarPerfilSvt extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	
    	boolean esAsis = false;
        HttpSession session = req.getSession(false);
        if (session != null) {
          Object a = session.getAttribute("usuario_logueado");
          if (a instanceof DTSesionUsuario u && u.getRol() == RolUsuario.ASISTENTE) {
            esAsis = true;
          }
        }
        req.setAttribute("ES_ASIS", esAsis);

        HttpSession session2 = req.getSession(false);
        if (session2 == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        DTSesionUsuario ses = (DTSesionUsuario) session.getAttribute("usuario_logueado");
        if (ses == null || ses.getNickname() == null || ses.getNickname().isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String nickUsuario = ses.getNickname();
        IControllerUsuario icu = Factory.getInstance().getIControllerUsuario();
        Usuario usuario = icu.getUsuario(nickUsuario);
        if (usuario == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado.");
            return;
        }
        DTUsuarioListaConsulta usu = icu.consultaDeUsuario(nickUsuario);

        IControllerInstitucion ici = Factory.getInstance().getIControllerInstitucion();
        req.setAttribute("LISTA_INSTITUCION", ici.getDTInstituciones().toArray(DTInstitucion[]::new));
        req.setAttribute("USUARIO", usu);
        req.setAttribute("TIPO_USUARIO", (usuario instanceof Organizador) ? "organizador" : "asistente");

        req.getRequestDispatcher("/WEB-INF/views/ModificarUsuario.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        HttpSession session2 = req.getSession(false);
        if (session2 == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        DTSesionUsuario ses = (DTSesionUsuario) session2.getAttribute("usuario_logueado");
        if (ses == null || ses.getNickname() == null || ses.getNickname().isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String nickname = ses.getNickname();
        IControllerUsuario icu = Factory.getInstance().getIControllerUsuario();
        Usuario user = icu.getUsuario(nickname);
        if (user == null) throw new ServletException("Usuario no encontrado.");

     // Campos comunes 
        String nombre = req.getParameter("nombre");
        String password = req.getParameter("password");
        String confirm  = req.getParameter("confirmPassword");

        if (nombre != null && !nombre.isBlank()) {
            user.setNombre(nombre);
        }

        // Validación server-side de contraseña
        if (password != null && !password.isBlank()) {
            if (confirm == null || !password.equals(confirm)) {
                // Preparar datos para re-render del formulario con error
                IControllerInstitucion ici = Factory.getInstance().getIControllerInstitucion();
                req.setAttribute("LISTA_INSTITUCION", ici.getInstituciones().toArray(Institucion[]::new));
                req.setAttribute("USUARIO", user);
                req.setAttribute("TIPO_USUARIO", (user instanceof Organizador) ? "organizador" : "asistente");
                req.setAttribute("msgError", "Las contraseñas no coinciden."); // mostrala en el JSP

                req.getRequestDispatcher("/WEB-INF/views/ModificarUsuario.jsp").forward(req, resp);
                return; // ¡importante!
            }
            // Coinciden -> aplicar cambio
            user.setContrasena(password);
        }


        // Es asistente 
        if (user instanceof Asistente a) {
            String apellido = req.getParameter("apellido");
            if (apellido != null) a.setApellido(apellido);

            String fechaStr = req.getParameter("fechaNacimiento");
            if (fechaStr != null && !fechaStr.isBlank()) {
                try { a.setfNacimiento(LocalDate.parse(fechaStr)); } catch (Exception ignore) {}
            }

            String inst = req.getParameter("institucion");
            if (inst != null && !inst.isBlank()) {
                IControllerInstitucion ici = Factory.getInstance().getIControllerInstitucion();
                Institucion i = ici.findInstitucion(inst);
                if (i != null) a.setInstitucion(i);
            }
        }

        // Imagen de perfil: nick fijo, sobrescribe, limpia extensiones previas 
        Part imagenPart = req.getPart("imagen");
        if (imagenPart != null && imagenPart.getSize() > 0) {

            String contentType = imagenPart.getContentType();
            if (contentType == null ||
               !(contentType.equals("image/jpeg")
                 || contentType.equals("image/png")
                 || contentType.equals("image/webp"))) {
                throw new ServletException("Formato de imagen no soportado. Use JPG/PNG/WEBP.");
            }

            String ext;
            switch (contentType) {
                case "image/jpeg": ext = ".jpg";  break; // normalizamos .jpeg a .jpg
                case "image/png":  ext = ".png";  break;
                case "image/webp": ext = ".webp"; break;
                default: ext = ".bin";
            }

            String fileName = nickname + ext;

            // Directorio en WAR desplegado
            String uploadDir = getServletContext().getRealPath("/media/img/usuarios");
            if (uploadDir == null) throw new ServletException("No se pudo resolver la ruta de subida.");
            File dirDeploy = new File(uploadDir);
            if (!dirDeploy.exists()) dirDeploy.mkdirs();

            // Borrar variantes previas
            String[] exts = {".jpg", ".jpeg", ".png", ".webp"};
            for (String e : exts) {
                new File(dirDeploy, nickname + e).delete();
            }

            // Guardar en WAR
            File destinoWAR = new File(dirDeploy, fileName);
            imagenPart.write(destinoWAR.getAbsolutePath());

            // Copia persistente al proyecto fuente (sobrevive Clean/Restart)
            String projectPath = System.getProperty("user.dir") + "/src/main/webapp/media/img/usuarios";
            File dirProyecto = new File(projectPath);
            if (!dirProyecto.exists()) dirProyecto.mkdirs();

            for (String e : exts) {
                new File(dirProyecto, nickname + e).delete();
            }

            try {
                Files.copy(destinoWAR.toPath(),
                           new File(dirProyecto, fileName).toPath(),
                           java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                throw new ServletException("No se pudo copiar la imagen al proyecto fuente", e);
            }

            // Persistir en el dominio
            boolean seteado = false;
            try {
                user.getClass().getMethod("setImg", String.class).invoke(user, fileName);
                seteado = true;
            } catch (NoSuchMethodException nsme) {
                // ignore y probamos setFoto
            } catch (Exception e) {
                throw new ServletException("Error seteando imagen (setImg)", e);
            }
            if (!seteado) {
                try {
                    user.getClass().getMethod("setFoto", String.class).invoke(user, fileName);
                    seteado = true;
                } catch (Exception e) {
                    throw new ServletException("Error seteando imagen (setFoto)", e);
                }
            }

            // Sesión para reflejo inmediato en el header
            session2.setAttribute("IMAGEN_LOGUEADO", "/media/img/usuarios/" + fileName);
        }

        // Guardar cambios en el repositorio/BD
        icu.modificarUsuario1(user);

        // Volver al perfil
        resp.sendRedirect(req.getContextPath() + "/perfil");
    }
}
