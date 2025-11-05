	package com.controllers;
	
	import java.io.File;
	import java.io.IOException;
	import java.nio.file.Files;
	
	import cliente.ws.sc.DtInstitucion;
	import cliente.ws.sc.DtSesionUsuario;
	import cliente.ws.sc.RolUsuario;
	import cliente.ws.sc.DtUsuarioListaConsulta;
	
	import jakarta.servlet.ServletException;
	import jakarta.servlet.annotation.MultipartConfig;
	import jakarta.servlet.annotation.WebServlet;
	import jakarta.servlet.http.*;
	
	@WebServlet("/editarperfil")
	@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024,    // 1MB
	    maxRequestSize = 20 * 1024 * 1024   // 20MB
	)
	public class EditarPerfilSvt extends HttpServlet {
	    private static final long serialVersionUID = 1L;
	
	    @Override
	    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	            throws ServletException, IOException {
	    	
	    	 cliente.ws.sc.WebServicesService service = new cliente.ws.sc.WebServicesService();
	         cliente.ws.sc.WebServices port = service.getWebServicesPort();
	    	
	    	boolean esAsis = false;
	        HttpSession session = req.getSession(false);
	        if (session != null) {
	          Object a = session.getAttribute("usuario_logueado");
	          if (a instanceof DtSesionUsuario u && (u.getRol() == RolUsuario.ASISTENTE)) {
	            esAsis = true;
	          }
	        }
	        req.setAttribute("ES_ASIS", esAsis);
	
	        HttpSession session2 = req.getSession(false);
	        if (session2 == null) {
	            resp.sendRedirect(req.getContextPath() + "/login");
	            return;
	        }
	
	        DtSesionUsuario ses = (DtSesionUsuario) session.getAttribute("usuario_logueado");
	        if (ses == null || ses.getNickname() == null || ses.getNickname().isBlank()) {
	            resp.sendRedirect(req.getContextPath() + "/login");
	            return;
	        }
	
	        String nickUsuario = ses.getNickname();
	        DtUsuarioListaConsulta usuario = null;
	        try {	
	         usuario = port.consultaDeUsuario(nickUsuario);
	        } catch (Exception e) {
	        				
	        }
	        if (usuario == null) {
	            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado.");
	            return;
	        }
	        DtUsuarioListaConsulta usu = null;
	        try {
	         usu = port.consultaDeUsuario(nickUsuario);
			}
	        catch (Exception e) {
							
			}
	        req.setAttribute("USUARIO", usu);
	        req.setAttribute("TIPO_USUARIO", (ses.getRol() == RolUsuario.ORGANIZADOR) ? "organizador" : "asistente");
	
	        req.getRequestDispatcher("/WEB-INF/views/ModificarUsuario.jsp").forward(req, resp);
	    }
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException {
		cliente.ws.sc.WebServicesService service = new cliente.ws.sc.WebServicesService();
	    cliente.ws.sc.WebServices port = service.getWebServicesPort();
	
	    req.setCharacterEncoding("UTF-8");
	
	    HttpSession sesHttp = req.getSession(false);
	    if (sesHttp == null) { resp.sendRedirect(req.getContextPath()+"/login"); return; }
	
	    DtSesionUsuario ses = (DtSesionUsuario) sesHttp.getAttribute("usuario_logueado");
	    if (ses == null || ses.getNickname() == null || ses.getNickname().isBlank()) {
	        resp.sendRedirect(req.getContextPath() + "/login");
	        return;
	    }
	
	    // --- Leer parámetros (pero NO persistir aún) ---
	    String tipoForm = req.getParameter("tipoUsuario"); 
	    boolean esAsis = "asistente".equalsIgnoreCase(tipoForm) || ses.getRol() == RolUsuario.ASISTENTE;
	
	    String nombre = trimOrNull(req.getParameter("nombre"));
	    String apellido = trimOrNull(req.getParameter("apellido"));
	    String fechaStr = req.getParameter("fechaNacimiento");
	    String url = trimOrNull(req.getParameter("uRL")); 
	    String descripcion = trimOrNull(req.getParameter("descripcion"));
	    String password = req.getParameter("password");
	    String confirm  = req.getParameter("confirmPassword");
	
	    // --- Validación de contraseña ---
	    if (password != null && !password.isBlank() && (confirm == null || !password.equals(confirm))) {
	
	        req.setAttribute("form_nombre", nombre);
	        req.setAttribute("form_apellido", apellido);
	        req.setAttribute("form_fechaNacimiento", fechaStr);
	        req.setAttribute("form_url", url);
	        req.setAttribute("form_descripcion", descripcion);
	
	        req.setAttribute("TIPO_USUARIO", esAsis ? "asistente" : "organizador");
	        req.setAttribute("ES_ASIS", esAsis);
	
	
	        req.setAttribute("USUARIO", port.consultarUsuarioPorNickname(ses.getNickname()));
	
	        req.setAttribute("msgError", "Las contraseñas no coinciden.");
	        req.getRequestDispatcher("/WEB-INF/views/ModificarUsuario.jsp").forward(req, resp);
	        return; 
	    }
	
	
	    if (esAsis) {
	        try {
	        port.modificarUsuario(ses.getNickname(), nombre, apellido, fechaStr, "", "");
	        } catch (Exception e) {
						e.printStackTrace();
			}
	    } else {
	    	try {
	    		port.modificarUsuario(ses.getNickname(), nombre, "", "", descripcion, url);

	    } catch (Exception e) {
			e.printStackTrace();
	}
	    }
	
	    if (password != null && !password.isBlank()) {
	    	try {
	        port.setContrasena(ses.getNickname(), password);
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    }
	    }
	
	    Part imagenPart = req.getPart("imagen");
	    if (imagenPart != null && imagenPart.getSize() > 0) {
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
	
	        String warDir = getServletContext().getRealPath("/media/img/usuarios");
	        if (warDir == null) throw new ServletException("No se pudo resolver ruta del WAR para imágenes.");
	        File dirWar = new File(warDir);
	        if (!dirWar.exists() && !dirWar.mkdirs()) {
	            throw new ServletException("No se pudo crear el directorio de imágenes en WAR.");
	        }
	
	        File dirSrc = new File(System.getProperty("user.dir"), "src/main/webapp/media/img/usuarios");
	        if (!dirSrc.exists() && !dirSrc.mkdirs()) {
	            throw new ServletException("No se pudo crear el directorio de imágenes en el proyecto.");
	        }
	
	        for (String e : extsToClean) {
	            new File(dirWar, nickname + e).delete();
	            new File(dirSrc, nickname + e).delete();
	        }
	
	        File destinoWar = new File(dirWar, fileName);
	        imagenPart.write(destinoWar.getAbsolutePath());
	
	        File destinoSrc = new File(dirSrc, fileName);
	        try {
	            Files.copy(destinoWar.toPath(), destinoSrc.toPath(),
	                       java.nio.file.StandardCopyOption.REPLACE_EXISTING);
	        } catch (IOException io) {
	            throw new ServletException("No se pudo copiar la imagen al proyecto fuente.", io);
	        }
	
	        String rutaWeb = "/media/img/usuarios/" + fileName;
	
	        sesHttp.setAttribute("IMAGEN_LOGUEADO", rutaWeb);
	
	        try {
	            port.actualizarImagenUsuario(nickname, rutaWeb);
	        } catch (Exception e) {
	          
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
