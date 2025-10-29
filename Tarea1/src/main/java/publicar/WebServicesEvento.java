package publicar;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.Style;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;

import servidorcentral.logica.DTCategoria;
import servidorcentral.logica.Factory;
import servidorcentral.logica.IControllerEvento;
import publicar.dto.AltaEventoWSReq;
import publicar.dto.AltaEventoWSResp;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@WebService
@SOAPBinding(style = Style.DOCUMENT) // DOCUMENT-literal wrapped
@XmlAccessorType(XmlAccessType.FIELD)
public class WebServicesEvento {

    /** Debe coincidir con tu estructura publica de estaticos del web. */
    private static final String EVENT_IMG_DIR = "/media/img/eventos";

    /** Directorio real en disco para guardar imagenes (no hay ServletContext aca). */
    private static final String REAL_IMG_BASEDIR = System.getProperty("java.io.tmpdir")
            + File.separator + "eventuy-img";

    @WebMethod
    public AltaEventoWSResp altaEvento(AltaEventoWSReq req) {
        AltaEventoWSResp res = new AltaEventoWSResp();
        List<String> errores = new ArrayList<>();

        // -------- Normalizacion basica ----------
        String nombre      = trim(req.nombre);
        String descripcion = trim(req.descripcion);
        String sigla       = trim(req.sigla);
        List<String> paramCats = (req.categorias == null) ? List.of() : req.categorias;

        // -------- Validaciones (igual que en tu Servlet) ----------
        if (nombre.isEmpty())      errores.add("El nombre es obligatorio.");
        if (descripcion.isEmpty()) errores.add("La descripcion es obligatoria.");
        if (sigla.isEmpty())       errores.add("La sigla es obligatoria.");
        if (paramCats.isEmpty())   errores.add("Debe seleccionar al menos una categoria.");

        if (!nombre.isEmpty() && nombreOcupado(nombre)) {
            errores.add("Ya existe un evento con ese nombre.");
        }

        List<DTCategoria> categorias = List.of();
        if (errores.isEmpty()) {
            categorias = resolveCategorias(paramCats);
            if (categorias == null || categorias.isEmpty()) {
                errores.add("Las categorias seleccionadas no existen en el sistema.");
            }
        }

        if (!errores.isEmpty()) {
            res.ok = false;
            res.errores = errores;
            return res;
        }

        // -------- Imagen opcional en Base64 (equivalente a Part del Servlet) ----------
        String imagenWebPath = null;
        if (req.imagenBase64 != null && !req.imagenBase64.isBlank()) {
            try {
                String original = (req.imagenNombreOriginal == null) ? "upload" : req.imagenNombreOriginal;
                String ext = extensionOf(original);
                String safeBase = slug(nombre);
                String stamp = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
                String fileName = safeBase + "_" + stamp + (ext.isEmpty() ? "" : "." + ext);

                File dir = new File(REAL_IMG_BASEDIR);
                if (!dir.exists()) dir.mkdirs();

                byte[] data = Base64.getDecoder().decode(req.imagenBase64.getBytes(StandardCharsets.ISO_8859_1));
                try (FileOutputStream fos = new FileOutputStream(new File(dir, fileName))) {
                    fos.write(data);
                }
                // Lo expones por tu app web sirviendo EVENT_IMG_DIR apuntando a ese file si corresponde.
                imagenWebPath = EVENT_IMG_DIR + "/" + fileName;
            } catch (IllegalArgumentException iae) {
                errores.add("La imagen enviada no es Base64 valido.");
            } catch (Exception ex) {
                errores.add("No se pudo guardar la imagen: " + ex.getMessage());
            }
        }

        if (!errores.isEmpty()) {
            res.ok = false;
            res.errores = errores;
            return res;
        }

        try {
            getController().altaEventoDT(
                nombre,
                descripcion,
                LocalDate.now(),
                sigla,
                categorias,
                imagenWebPath
            );
            res.ok = true;
            res.errores = List.of();
            res.imagenWebPath = imagenWebPath;
            return res;
        } catch (Exception e) {
            res.ok = false;
            res.errores = List.of(
                (e.getMessage() != null && !e.getMessage().isBlank())
                    ? e.getMessage()
                    : "Error al dar de alta el evento."
            );
            return res;
        }
    }

    // ---------------- Helpers equivalentes a tu Servlet ----------------

    private IControllerEvento getController() {
        Factory fabrica = Factory.getInstance();
        return fabrica.getIControllerEvento();
    }

    private boolean nombreOcupado(String nombre) {
        try {
            return getController().existeEvento(nombre);
        } catch (Exception ex) {
            return false;
        }
    }

    private List<DTCategoria> resolveCategorias(List<String> seleccionadas) {
        List<DTCategoria> todas = getController().listarDTCategorias();
        Map<String, DTCategoria> porNombre = new HashMap<>();
        for (DTCategoria c : todas) {
            if (c != null && c.getNombre() != null) {
                porNombre.put(normaliza(c.getNombre()), c);
            }
        }

        Map<String, String> codigoANombre = defaultCodigoNombre();

        List<DTCategoria> res = new ArrayList<>();
        for (String raw : seleccionadas) {
            if (raw == null) continue;
            String v = raw.trim();

            // Permitir nombre o codigo (CA01, ...)
            DTCategoria byName = porNombre.get(normaliza(v));
            if (byName != null) { res.add(byName); continue; }

            String nombreCat = codigoANombre.get(v.toUpperCase(Locale.ROOT));
            if (nombreCat != null) {
                DTCategoria byCode = porNombre.get(normaliza(nombreCat));
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

    private static String trim(String s) { return (s == null) ? "" : s.trim(); }

    private static String normaliza(String s) {
        if (s == null) return "";
        String t = s.toLowerCase(Locale.ROOT).trim();
        t = t.replace('á','a').replace('é','e').replace('í','i').replace('ó','o').replace('ú','u')
             .replace('ä','a').replace('ë','e').replace('ï','i').replace('ö','o').replace('ü','u')
             .replace('ñ','n');
        t = t.replaceAll("\\s+", " ");
        return t;
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
