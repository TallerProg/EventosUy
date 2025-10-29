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

import jakarta.xml.ws.BindingProvider;

import cliente.ws.sc.WebServices;
import cliente.ws.sc.WebServicesService;
import cliente.ws.sc.DtCategoria;
import cliente.ws.sc.DtCategoriaArray;
import cliente.ws.sc.DTevento;
import cliente.ws.sc.DTeventoArray;

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

    // nombres de context-param en web.xml
    private static final String PARAM_WS_ENDPOINT = "SERVIDOR_CENTRAL_URL";   // ej: http://localhost:9128/webservices
    private static final String PARAM_WS_WSDL     = "SERVIDOR_CENTRAL_WSDL";  // ej: http://localhost:9128/webservices?wsdl

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
        if (descripcion.isEmpty()) errores.add("La descripcion es obligatoria.");
        if (sigla.isEmpty()) errores.add("La sigla es obligatoria.");
        if (paramCats == null || paramCats.length == 0) errores.add("Debe seleccionar al menos una categoria.");

        try {
            if (!nombre.isEmpty() && nombreOcupadoWS(request, nombre)) {
                errores.add("Ya existe un evento con ese nombre.");
            }
        } catch (Exception e) {
            errores.add("No se pudo verificar si el nombre esta ocupado: " + safeMsg(e));
        }

        List<DtCategoria> categorias = Collections.emptyList();
        if (errores.isEmpty()) {
            try {
                categorias = resolveCategoriasWS(request, paramCats);
                if (categorias.isEmpty()) {
                    errores.add("Las categorias seleccionadas no existen en el sistema.");
                }
            } catch (Exception e) {
                errores.add("No se pudieron resolver las categorias: " + safeMsg(e));
            }
        }

        if (!errores.isEmpty()) {
            mirrorBack(request, nombre, descripcion, sigla, paramCats, errores);
            request.getRequestDispatcher(FORM_JSP).forward(request, response);
            return;
        }

        String imagenWebPath = null;
        try {
            Part imgPart = request.getPart("imagen");
            if (imgPart != null && imgPart.getSize() > 0) {
                String original = submittedFileName(imgPart);
                String ext = extensionOf(original);
                String safeBase = slug(nombre);
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
            errores.add("La imagen supera el tamano permitido (5 MB por archivo, 10 MB por solicitud).");
        } catch (Exception ex) {
            errores.add("No se pudo guardar la imagen: " + safeMsg(ex));
        }

        if (!errores.isEmpty()) {
            mirrorBack(request, nombre, descripcion, sigla, paramCats, errores);
            request.getRequestDispatcher(FORM_JSP).forward(request, response);
            return;
        }

        // invocar al WS para dar de alta el evento
        try {
            WebServices port = getPort(request);

            // IMPORTANTE:
            // Aca invoca el metodo real de tu WS para alta de evento.
            // Como tu interfaz pegada no muestra altaEvento/altaEventoDT,
            // dejamos un ejemplo generico. Cambia NOMBRE_DEL_METODO por el real
            // y ajusta los tipos si tu WSDL difiere (por ejemplo fechas).
            //
            // Ejemplo si tu WS tiene:
            // void altaEventoDT(String nombre, String descripcion, String fechaAltaISO, String sigla, DtCategoriaArray cats, String imgPath)
            //
            // Crea el wrapper de categorias:
            DtCategoriaArray arr = new DtCategoriaArray();
            arr.getItem().addAll(categorias);

            // fecha como ISO (si tu WS espera string; si espera otro tipo, ajusta)
            String fechaAlta = LocalDate.now().toString();

            // Reemplaza esta linea por la firma real de tu servicio:
            // port.altaEventoDT(nombre, descripcion, fechaAlta, sigla, arr, imagenWebPath);

            // Si tu metodo tiene otro nombre/orden, ajusta aca:
            // port.NOMBRE_DEL_METODO(nombre, sigla, descripcion, fechaAlta, arr, imagenWebPath);

            // fin ejemplo

            response.sendRedirect(request.getContextPath() + "/alta-evento?ok=1");
        } catch (UnsupportedOperationException uoe) {
            // mensaje util si aun no publicaste el metodo en el WS
            errores.add("Falta implementar/publicar el metodo de alta de evento en el Web Service.");
            mirrorBack(request, nombre, descripcion, sigla, paramCats, errores);
            request.getRequestDispatcher(FORM_JSP).forward(request, response);
        } catch (Exception e) {
            errores.add((e.getMessage() != null) ? e.getMessage() : "Error al invocar el Web Service.");
            mirrorBack(request, nombre, descripcion, sigla, paramCats, errores);
            request.getRequestDispatcher(FORM_JSP).forward(request, response);
        }
    }

    // ===== WS helpers =====

    private WebServices getPort(HttpServletRequest req) throws Exception {
        String wsdl = getInitParamOrDefault(req, PARAM_WS_WSDL, "http://localhost:9128/webservices?wsdl");
        String endpoint = getInitParamOrDefault(req, PARAM_WS_ENDPOINT, "http://localhost:9128/webservices");

        WebServicesService svc = new WebServicesService(new java.net.URL(wsdl));
        WebServices port = svc.getWebServicesPort();

        ((BindingProvider) port).getRequestContext().put(
            BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint
        );
        return port;
    }

    private boolean nombreOcupadoWS(HttpServletRequest req, String nombre) throws Exception {
        WebServices port = getPort(req);
        DTeventoArray arr = port.listarDTEventos();
        if (arr == null || arr.getItem() == null) return false;
        String n = normaliza(nombre);
        for (DTevento e : arr.getItem()) {
            if (e != null && e.getNombre() != null) {
                if (normaliza(e.getNombre()).equals(n)) return true;
            }
        }
        return false;
    }

    private List<DtCategoria> resolveCategoriasWS(HttpServletRequest req, String[] seleccionadas) throws Exception {
        WebServices port = getPort(req);
        DtCategoriaArray arr = port.listarDTCategorias();

        Map<String, DtCategoria> porNombre = new HashMap<>();
        if (arr != null && arr.getItem() != null) {
            for (DtCategoria c : arr.getItem()) {
                if (c != null && c.getNombre() != null) {
                    porNombre.put(normaliza(c.getNombre()), c);
                }
            }
        }

        Map<String, String> codigoANombre = defaultCodigoNombre();

        List<DtCategoria> res = new ArrayList<>();
        for (String raw : seleccionadas) {
            if (raw == null) continue;
            String v = raw.trim();

            DtCategoria byName = porNombre.get(normaliza(v));
            if (byName != null) {
                res.add(byName);
                continue;
            }

            String nombreCat = codigoANombre.get(v.toUpperCase(Locale.ROOT));
            if (nombreCat != null) {
                DtCategoria byCode = porNombre.get(normaliza(nombreCat));
                if (byCode != null) res.add(byCode);
            }
        }
        return res;
    }

    private static String getInitParamOrDefault(HttpServletRequest req, String name, String def) {
        String v = req.getServletContext().getInitParameter(name);
        return (v != null && !v.isBlank()) ? v.trim() : def;
    }

    // ===== util =====

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

    private static Map<String, String> defaultCodigoNombre() {
        Map<String, String> m = new HashMap<>();
        m.put("CA01", "Tecnologia");
        m.put("CA02", "Innovacion");
        m.put("CA03", "Literatura");
        m.put("CA04", "Cultura");
        m.put("CA05", "Musica");
        m.put("CA06", "Deporte");
        m.put("CA07", "Salud");
        m.put("CA08", "Entretenimiento");
        m.put("CA09", "Agro");
        m.put("CA10", "Negocios");
        m.put("CA11", "Moda");
        m.put("CA12", "Investigacion");
        return m;
    }

    private static String normaliza(String s) {
        if (s == null) return "";
        String t = s.toLowerCase(Locale.ROOT).trim();
        t = t.replace('á','a').replace('é','e').replace('í','i').replace('ó','o').replace('ú','u')
             .replace('ä','a').replace('ë','e').replace('ï','i').replace('ö','o').replace('ü','u')
             .replace('ñ','n');
        t = t.replaceAll("\\s+", " ");
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

    private static String safeMsg(Exception e) {
        String m = e.getMessage();
        return (m == null || m.isBlank()) ? (e.getClass().getSimpleName()) : m;
    }
}
