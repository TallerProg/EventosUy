package com.controllers;

import com.config.WSClientProvider;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.net.URL;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import cliente.ws.sc.WebServices;
import cliente.ws.sc.WebServicesService;
import cliente.ws.sc.DtCategoria;
import cliente.ws.sc.DtCategoriaArray;
import cliente.ws.sc.StringArray;

@WebServlet("/alta-evento")
@MultipartConfig(
    fileSizeThreshold = 256 * 1024,   // 256 KB
    maxFileSize = 5 * 1024 * 1024,    // 5 MB
    maxRequestSize = 10 * 1024 * 1024 // 10 MB
)
public class AltaEveSvt extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String FORM_JSP = "/WEB-INF/views/AltaEvento.jsp";
	private static final String INST_IMG_DIR = "/media/img/eventos";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher(FORM_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String nombre = trim(request.getParameter("nombre"));
        String descripcion = trim(request.getParameter("descripcion"));
        String sigla = trim(request.getParameter("sigla"));
        String[] paramCats = request.getParameterValues("categorias");

        List<String> errores = new ArrayList<>();

        if (nombre.isEmpty()) errores.add("El nombre es obligatorio.");
        if (descripcion.isEmpty()) errores.add("La descripción es obligatoria.");
        if (sigla.isEmpty()) errores.add("La sigla es obligatoria.");
        if (paramCats == null || paramCats.length == 0) errores.add("Debe seleccionar al menos una categoría.");

        WebServicesService service = WSClientProvider.newService();
        WebServices port = service.getWebServicesPort();

        try {
            if (!nombre.isEmpty() && port.existeEvento(nombre)) {
                errores.add("Ya existe un evento con ese nombre.");
            }
        } catch (Exception e) {
            errores.add("No se pudo validar la existencia del evento: " + safeMsg(e));
        }

        DtCategoria[] categoriasWs = new DtCategoria[0];
        if (errores.isEmpty()) {
            try {
            	StringArray paramCatsArray = new StringArray();
            	for(String cat : paramCats) {
            		paramCatsArray.getItem().add(cat);
            	}
                DtCategoriaArray catsArr = port.resolverCategoriasPorNombreOCodigo(paramCatsArray);
				for (DtCategoria cat : catsArr.getItem()) {
					if (cat != null) {
						categoriasWs = Arrays.copyOf(categoriasWs, categoriasWs.length + 1);
						categoriasWs[categoriasWs.length - 1] = cat;
					}
				}
                if (categoriasWs == null || categoriasWs.length == 0) {
                    errores.add("Las categorías seleccionadas no existen en el sistema.");
                }
            } catch (Exception e) {
                errores.add("Error resolviendo categorías: " + safeMsg(e));
            }
        }


        String imagenWebPath = null;
        Part imgPart = request.getPart("imagen");
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

        if (!errores.isEmpty()) {
            mirrorBack(request, nombre, descripcion, sigla, paramCats, errores);
            request.getRequestDispatcher(FORM_JSP).forward(request, response);
            return;
        }


        try {
        	StringArray catsWrap = new StringArray();
        	List<String> items = catsWrap.getItem();
        	if (paramCats != null) {
        	    Collections.addAll(items, paramCats);
        	}
        	
        	if(imagenWebPath == null) {
        		imagenWebPath = " ";
        	}
            port.altaEvento(nombre, descripcion, sigla, catsWrap,imagenWebPath);                  


            response.sendRedirect(request.getContextPath() + "/alta-evento?ok=1");
        } catch (Exception e) {
            errores.add(e.getMessage() != null ? e.getMessage() : "Error al dar de alta el evento.");
            mirrorBack(request, nombre, descripcion, sigla, paramCats, errores);
            request.getRequestDispatcher(FORM_JSP).forward(request, response);
        }
    }

    // ===== Helpers =======================================================

    private static String trim(String s) { return (s == null) ? "" : s.trim(); }

    private static void mirrorBack(HttpServletRequest req,
                                   String nombre, String desc, String sigla,
                                   String[] cats, List<String> errores) {
        req.setAttribute("nombre", nombre);
        req.setAttribute("descripcion", desc);
        req.setAttribute("sigla", sigla);
        req.setAttribute("categoriasSeleccionadas",
                (cats == null) ? Collections.emptySet() : new HashSet<>(Arrays.asList(cats)));
        req.setAttribute("errores", errores);
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

    private static String safeMsg(Throwable t) {
        String m = (t == null ? null : t.getMessage());
        return (m == null || m.isBlank()) ? t.getClass().getSimpleName() : m;
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

