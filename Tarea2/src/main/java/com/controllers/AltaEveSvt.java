package com.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import ServidorCentral.logica.Categoria;
import ServidorCentral.logica.IControllerEvento;
import ServidorCentral.logica.Factory; 

@WebServlet("/alta-evento")
@MultipartConfig(
    fileSizeThreshold = 256 * 1024,   // 256 KB
    maxFileSize = 5 * 1024 * 1024,    // 5 MB
    maxRequestSize = 10 * 1024 * 1024 // 10 MB
)
public class AltaEveSvt extends HttpServlet {

    private static final long serialVersionUID = 1L;


    private static final String FORM_JSP = "/WEB-INF/views/AltaEvento.jsp";


    private static final String EVENT_IMG_DIR = "/media/img/eventos";

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

        
        String imagenWebPath = null;
        try {
            Part imgPart = request.getPart("imagen");
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
            errores.add("La imagen supera el tamaño permitido (5 MB por archivo, 10 MB por solicitud).");
        } catch (Exception ex) {
            errores.add("No se pudo guardar la imagen: " + ex.getMessage());
        }

        if (!errores.isEmpty()) {
            mirrorBack(request, nombre, descripcion, sigla, paramCats, errores);
            request.getRequestDispatcher(FORM_JSP).forward(request, response);
            return;
        }


        List<Categoria> categorias = resolveCategorias(paramCats);

        if (categorias.isEmpty()) {
            errores.add("Las categorías seleccionadas no existen en el sistema.");
            mirrorBack(request, nombre, descripcion, sigla, paramCats, errores);
            request.getRequestDispatcher(FORM_JSP).forward(request, response);
            return;
        }

        try {

            getController().altaEvento(nombre, descripcion, LocalDate.now(), sigla, categorias,imagenWebPath);
            response.sendRedirect(request.getContextPath() + "/alta-evento?ok=1");
        } catch (Exception e) {
            errores.add(e.getMessage() != null ? e.getMessage() : "Error al dar de alta el evento.");
            mirrorBack(request, nombre, descripcion, sigla, paramCats, errores);
            request.getRequestDispatcher(FORM_JSP).forward(request, response);
        }
    }

    /* ======================== Helpers ======================== */

    private IControllerEvento getController() {
    	Factory fabrica = Factory.getInstance();
        return fabrica.getIControllerEvento();
    }

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

    private List<Categoria> resolveCategorias(String[] seleccionadas) {
        List<Categoria> todas = getController().getCategorias();
        Map<String, Categoria> porNombre = new HashMap<>();
        for (Categoria c : todas) {
            if (c != null && c.getNombre() != null) {
                porNombre.put(normaliza(c.getNombre()), c);
            }
        }


        Map<String, String> codigoANombre = defaultCodigoNombre();

        List<Categoria> res = new ArrayList<>();
        for (String raw : seleccionadas) {
            if (raw == null) continue;
            String v = raw.trim();


            Categoria byName = porNombre.get(normaliza(v));
            if (byName != null) {
                res.add(byName);
                continue;
            }


            String nombreCat = codigoANombre.get(v.toUpperCase(Locale.ROOT));
            if (nombreCat != null) {
                Categoria byCode = porNombre.get(normaliza(nombreCat));
                if (byCode != null) res.add(byCode);
            }
        }
        return res;
    }

    private static Map<String, String> defaultCodigoNombre() {
        Map<String, String> m = new HashMap<>();
        m.put("CA01", "Tecnología");
        m.put("CA02", "Innovación");
        m.put("CA03", "Literatura");
        m.put("CA04", "Cultura");
        m.put("CA05", "Música");
        m.put("CA06", "Deporte");
        m.put("CA07", "Salud");
        m.put("CA08", "Entretenimiento");
        m.put("CA09", "Agro");
        m.put("CA10", "Negocios");
        m.put("CA11", "Moda");
        m.put("CA12", "Investigación");
        return m;
    }

    private static String normaliza(String s) {
        if (s == null) return "";
        String t = s.toLowerCase(Locale.ROOT).trim();
        // Quitar tildes básicas
        t = t.replace('á','a').replace('é','e').replace('í','i').replace('ó','o').replace('ú','u')
             .replace('ä','a').replace('ë','e').replace('ï','i').replace('ö','o').replace('ü','u')
             .replace('ñ','n');
        return t;
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

