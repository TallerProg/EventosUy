package com.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;

import servidorcentral.logica.DTInstitucion;
import servidorcentral.logica.DTSesionUsuario;
import servidorcentral.logica.DTUsuarioListaConsulta;
import servidorcentral.logica.IControllerInstitucion;
import servidorcentral.logica.IControllerUsuario;
import servidorcentral.logica.Factory;


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
          if (a instanceof DTSesionUsuario u && u.getRolString().equals("ASISTENTE")) {
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
        DTUsuarioListaConsulta usuario = icu.consultaDeUsuario(nickUsuario);
        if (usuario == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado.");
            return;
        }
        DTUsuarioListaConsulta usu = icu.consultaDeUsuario(nickUsuario);

        IControllerInstitucion ici = Factory.getInstance().getIControllerInstitucion();
        req.setAttribute("LISTA_INSTITUCION", ici.getDTInstituciones().toArray(DTInstitucion[]::new));
        req.setAttribute("USUARIO", usu);
        req.setAttribute("TIPO_USUARIO", (ses.getRolString().equals("ORGANIZADOR")) ? "organizador" : "asistente");

        req.getRequestDispatcher("/WEB-INF/views/ModificarUsuario.jsp").forward(req, resp);
    }
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

    req.setCharacterEncoding("UTF-8");

    HttpSession sesHttp = req.getSession(false);
    if (sesHttp == null) { resp.sendRedirect(req.getContextPath()+"/login"); return; }

    DTSesionUsuario ses = (DTSesionUsuario) sesHttp.getAttribute("usuario_logueado");
    if (ses == null || ses.getNickname() == null || ses.getNickname().isBlank()) {
        resp.sendRedirect(req.getContextPath() + "/login");
        return;
    }

    // --- Leer parámetros (pero NO persistir aún) ---
    String tipoForm = req.getParameter("tipoUsuario"); // "organizador" o "asistente" del hidden
    boolean esAsis = "asistente".equalsIgnoreCase(tipoForm) || "ASISTENTE".equals(ses.getRolString());

    String nombre = trimOrNull(req.getParameter("nombre"));
    String apellido = trimOrNull(req.getParameter("apellido"));
    String fechaStr = req.getParameter("fechaNacimiento");
    String inst = trimOrNull(req.getParameter("institucion"));
    String url = trimOrNull(req.getParameter("uRL")); // si podés, renombrá a "url" en el JSP
    String descripcion = trimOrNull(req.getParameter("descripcion"));
    String password = req.getParameter("password");
    String confirm  = req.getParameter("confirmPassword");

    // --- Validación de contraseña ANTES de tocar la lógica ---
    if (password != null && !password.isBlank() && (confirm == null || !password.equals(confirm))) {

        // Sticky values para re-render
        req.setAttribute("form_nombre", nombre);
        req.setAttribute("form_apellido", apellido);
        req.setAttribute("form_fechaNacimiento", fechaStr);
        req.setAttribute("form_institucion", inst);
        req.setAttribute("form_url", url);
        req.setAttribute("form_descripcion", descripcion);

        // Flags para que el JSP muestre el bloque correcto
        req.setAttribute("TIPO_USUARIO", esAsis ? "asistente" : "organizador");
        req.setAttribute("ES_ASIS", esAsis);

        // Lista instituciones (para el <select>)
        IControllerInstitucion ici = Factory.getInstance().getIControllerInstitucion();
        req.setAttribute("LISTA_INSTITUCION", ici.getDTInstituciones().toArray(DTInstitucion[]::new));

        // DTO actual (para fallback de campos que no posteaste)
        IControllerUsuario icu = Factory.getInstance().getIControllerUsuario();
        req.setAttribute("USUARIO", icu.consultaDeUsuario(ses.getNickname()));

        req.setAttribute("msgError", "Las contraseñas no coinciden.");
        req.getRequestDispatcher("/WEB-INF/views/ModificarUsuario.jsp").forward(req, resp);
        return; // ⛔ Nada persistido si hay error
    }

    // --- Desde acá, TODO valida: ahora sí persistimos ---
    IControllerUsuario icu = Factory.getInstance().getIControllerUsuario();

    if (esAsis) {
        // Fecha (nullable)
        java.time.LocalDate fNac = (fechaStr == null || fechaStr.isBlank()) ? null : java.time.LocalDate.parse(fechaStr);
        try {
        icu.modificarUsuario(ses.getNickname(), nombre, apellido, fNac, null, null);
        } catch (Exception e) {
					e.printStackTrace();
		}
        if (inst != null) icu.aneadirInstitucion(ses.getNickname(), inst);
    } else {
    	try {
        icu.modificarUsuario(ses.getNickname(), nombre, null, null, url, descripcion);
    } catch (Exception e) {
		e.printStackTrace();
}
    }

    if (password != null && !password.isBlank()) {
    	try {
        icu.setContrasena(ses.getNickname(), password);
    	} catch (Exception e) {
    		e.printStackTrace();
    }
    }

 // Manejo de la Imagen SOLO cuando todo lo demás ya validó
    Part imagenPart = req.getPart("imagen");
    if (imagenPart != null && imagenPart.getSize() > 0) {
        // Validar tipo
        String contentType = imagenPart.getContentType();
        if (contentType == null) {
            throw new ServletException("No se pudo determinar el tipo de imagen.");
        }
        String ext;
        switch (contentType) {
            case "image/jpeg": ext = ".jpg";  break; // normalizamos .jpeg a .jpg
            case "image/png":  ext = ".png";  break;
            case "image/webp": ext = ".webp"; break;
            default:
                throw new ServletException("Formato no soportado. Use JPG/PNG/WEBP.");
        }

        String nickname = ses.getNickname();
        String fileName = nickname + ext;
        String[] extsToClean = {".jpg", ".jpeg", ".png", ".webp"};

        // 1) Directorio del WAR desplegado
        String warDir = getServletContext().getRealPath("/media/img/usuarios");
        if (warDir == null) throw new ServletException("No se pudo resolver ruta del WAR para imágenes.");
        File dirWar = new File(warDir);
        if (!dirWar.exists() && !dirWar.mkdirs()) {
            throw new ServletException("No se pudo crear el directorio de imágenes en WAR.");
        }

        // 2) Directorio en el proyecto fuente (persistente)
        File dirSrc = new File(System.getProperty("user.dir"), "src/main/webapp/media/img/usuarios");
        if (!dirSrc.exists() && !dirSrc.mkdirs()) {
            throw new ServletException("No se pudo crear el directorio de imágenes en el proyecto.");
        }

        // 3) Borrar variantes previas en WAR y en SRC
        for (String e : extsToClean) {
            new File(dirWar, nickname + e).delete();
            new File(dirSrc, nickname + e).delete();
        }

        // 4) Guardar en WAR (para que se sirva inmediatamente)
        File destinoWar = new File(dirWar, fileName);
        imagenPart.write(destinoWar.getAbsolutePath());

        // 5) Copiar al proyecto fuente (sobrevive a clean/redeploy)
        File destinoSrc = new File(dirSrc, fileName);
        try {
            Files.copy(destinoWar.toPath(), destinoSrc.toPath(),
                       java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException io) {
            throw new ServletException("No se pudo copiar la imagen al proyecto fuente.", io);
        }

        // 6) Ruta web (para sesión y dominio)
        String rutaWeb = "/media/img/usuarios/" + fileName;

        // 7) Actualizar sesión (para que el header muestre la nueva)
        sesHttp.setAttribute("IMAGEN_LOGUEADO", rutaWeb);

        // 8) Persistir en dominio
        try {
            icu.actualizarImagenUsuario(nickname, rutaWeb);
        } catch (Exception e) {
            // Si fallara persistencia de dominio, al menos dejamos la imagen en disco y la sesión actualizada.
            // Podés decidir si querés revertir archivos; aquí solo informamos.
            throw new ServletException("La imagen se guardó pero no se pudo actualizar en el dominio.", e);
        }
    }

        
    

    resp.sendRedirect(req.getContextPath() + "/perfil");
}

private static String trimOrNull(String s) {
    if (s == null) return null;
    s = s.trim();
    return s.isEmpty() ? null : s;
}

}
