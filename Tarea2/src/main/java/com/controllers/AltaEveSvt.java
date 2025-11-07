package com.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

        WebServicesService service = new WebServicesService();
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
        if (errores.isEmpty()) {
            try {
                Part imgPart = request.getPart("imagen");
                if (imgPart != null && imgPart.getSize() > 0) {
                    byte[] bytes = imgPart.getInputStream().readAllBytes();
                    String original = submittedFileName(imgPart);
                    imagenWebPath = port.subirImagenEvento(nombre, original, bytes);
                }
            } catch (IllegalStateException ise) {
                errores.add("La imagen supera el tamaño permitido (5 MB por archivo, 10 MB por solicitud).");
            } catch (Exception ex) {
                errores.add("No se pudo guardar la imagen: " + safeMsg(ex));
            }
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
        	
        	
            port.altaEvento(nombre, descripcion, sigla, catsWrap, 
                                        (imagenWebPath == null ? new byte[0] : new byte[0]), 
                                        (imagenWebPath == null ? "" : ""));                  


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
}

