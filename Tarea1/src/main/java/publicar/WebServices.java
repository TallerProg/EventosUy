/**
 * 
 */
package publicar;

/**
 * @author efviodo
 *
 */


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;
import jakarta.jws.soap.SOAPBinding.Style;
import jakarta.xml.ws.Endpoint;
import jakarta.xml.ws.soap.MTOM;
import servidorcentral.excepciones.CredencialesInvalidasException;
import servidorcentral.excepciones.UsuarioNoExisteException;
import servidorcentral.excepciones.UsuarioRepetidoException;
import servidorcentral.excepciones.NombreTRUsadoException;
import servidorcentral.logica.Edicion;
import servidorcentral.logica.DTCategoria;
import servidorcentral.logica.DTSesionUsuario;
import servidorcentral.logica.DTUsuarioListaConsulta;
import servidorcentral.logica.DTevento;
import servidorcentral.logica.DTRegistro;
import servidorcentral.logica.DTEdicion;
import servidorcentral.logica.Factory;
import servidorcentral.logica.IControllerEvento;
import servidorcentral.logica.IControllerUsuario;
import servidorcentral.logica.Institucion;

@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class WebServices {
	



    private Endpoint endpoint = null;
    public WebServices(){}


    @WebMethod(exclude = true)
    public void publicar(){
    	if (!WSConfig.getBool("ws.publish.enabled", true)) return;
        String url = WSConfig.get("ws.publish.url", "http://127.0.0.1:9128/noPropieties");
        this.endpoint = jakarta.xml.ws.Endpoint.publish(url, this);
        System.out.println("[WebServices] Publicado en: " + url + " (WSDL: " + url + "?wsdl)");
    }

    @WebMethod(exclude = true)
    public Endpoint getEndpoint() {
            return endpoint;
    }


    @WebMethod(exclude = true)
    private IControllerEvento getControllerEvento() {
        return Factory.getInstance().getIControllerEvento();
    }
    
    @WebMethod(exclude = true)
	private IControllerUsuario getControllerUsuario() {
		return Factory.getInstance().getIControllerUsuario();
	}

    @WebMethod
    public DTCategoria[] listarDTCategorias() {
        List<DTCategoria> lista = getControllerEvento().listarDTCategorias();
        return (lista == null || lista.isEmpty())
                ? new DTCategoria[0]
                : lista.toArray(new DTCategoria[0]);
    }
    
    @WebMethod
    public DTevento[] listarDTEventos() {
		List<DTevento> lista = getControllerEvento().listarDTEventos();
		return (lista == null || lista.isEmpty())
				? new DTevento[0]
				: lista.toArray(new DTevento[0]);
	}
    
    @WebMethod
    public DTUsuarioListaConsulta[] listarDTAsistentes() {
		List<DTUsuarioListaConsulta> lista = getControllerUsuario().getDTAsistentes();
		return (lista == null || lista.isEmpty())
				? new DTUsuarioListaConsulta[0]
				: lista.toArray(new DTUsuarioListaConsulta[0]);
	}
    
    @WebMethod
    public DTUsuarioListaConsulta[] listarDTOrganizadores() {
		List<DTUsuarioListaConsulta> lista = getControllerUsuario().getDTOrganizadores();
		return (lista == null || lista.isEmpty())
				? new DTUsuarioListaConsulta[0]
				: lista.toArray(new DTUsuarioListaConsulta[0]);
	}
    
    @WebMethod
    public void altaAsistente(String nicknameUsu, String correo, String nombre, String apellido, LocalDate fNacimiento,
    		Institucion ins, String contrasena, String img) throws UsuarioRepetidoException {
    	getControllerUsuario().altaAsistente(nicknameUsu, correo, nombre, apellido, fNacimiento, ins, contrasena, img);
    }
    
    @WebMethod
	public void altaOrganizador(String nicknameUsu, String correo, String nombre, String descripcion, String url,
			String contrasena, String img) throws UsuarioRepetidoException {
		getControllerUsuario().altaOrganizador(nicknameUsu, correo, nombre, descripcion, url, contrasena, img);
	}
    @WebMethod
    public boolean existeEvento(String nombre) {
    	return getControllerEvento().existeEvento(nombre);
    }
    
    @WebMethod
    public DTCategoria[] resolverCategoriasPorNombreOCodigo(String[] seleccionadas) {
        if (seleccionadas == null || seleccionadas.length == 0) return new DTCategoria[0];

        List<DTCategoria> todas = getControllerEvento().listarDTCategorias();
        Map<String, DTCategoria> porNombreNorm = new HashMap<>();
        for (DTCategoria c : todas) {
            if (c != null && c.getNombre() != null) {
                porNombreNorm.put(normaliza(c.getNombre()), c);
            }
        }
        Map<String, String> codigoANombre = defaultCodigoNombre();

        List<DTCategoria> res = new ArrayList<>();
        for (String raw : seleccionadas) {
            if (raw == null) continue;
            String v = raw.trim();
            DTCategoria byName = porNombreNorm.get(normaliza(v));
            if (byName != null) { res.add(byName); continue; }
            String nombreCat = codigoANombre.get(v.toUpperCase(Locale.ROOT));
            if (nombreCat != null) {
                DTCategoria byCode = porNombreNorm.get(normaliza(nombreCat));
                if (byCode != null) res.add(byCode);
            }
        }
        return res.toArray(new DTCategoria[0]);
    }

    @MTOM(enabled = true)
    @WebMethod
    public String subirImagenEvento(
            @WebParam(name = "nombreSugerido") String nombreSugerido,
            @WebParam(name = "originalFileName") String originalFileName,
            @WebParam(name = "contenido") byte[] contenido) throws IOException {

        if (contenido == null || contenido.length == 0) return null;

        String ext = extensionOf(originalFileName);
        String safeBase = slug(nombreSugerido);
        String stamp = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
        String fileName = safeBase + "_" + stamp + (ext.isEmpty() ? "" : "." + ext);

        String realDir = WSConfig.get("images.events.real_dir",
                System.getProperty("java.io.tmpdir") + File.separator + "eventuy-img");
        String webDir  = WSConfig.get("images.events.web_dir", "/media/img/eventos");

        File dir = new File(realDir);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("No se pudo crear el directorio de imágenes: " + realDir);
        }

        File destino = new File(dir, fileName);
        try (FileOutputStream fos = new FileOutputStream(destino)) {
            fos.write(contenido);
        }
        return (webDir.endsWith("/") ? webDir.substring(0, webDir.length()-1) : webDir) + "/" + fileName;
    }

    @WebMethod
    public void altaEvento(
            @WebParam(name="nombre") String nombre,
            @WebParam(name="descripcion") String descripcion,
            @WebParam(name="sigla") String sigla,
            @WebParam(name="categoriasNombreOCodigo") String[] categoriasNombreOCodigo,
            @WebParam(name="imagenBytes") byte[] imagenBytes,           
            @WebParam(name="imagenFileName") String imagenFileName      
    ) throws Exception {

        List<String> errores = new ArrayList<>();
        if (isBlank(nombre)) errores.add("El nombre es obligatorio.");
        if (isBlank(descripcion)) errores.add("La descripción es obligatoria.");
        if (isBlank(sigla)) errores.add("La sigla es obligatoria.");
        if (categoriasNombreOCodigo == null || categoriasNombreOCodigo.length == 0)
            errores.add("Debe seleccionar al menos una categoría.");
        if (!isBlank(nombre) && getControllerEvento().existeEvento(nombre))
            errores.add("Ya existe un evento con ese nombre.");
        if (!errores.isEmpty()) throw new IllegalArgumentException(String.join(" | ", errores));

        DTCategoria[] dtCats = resolverCategoriasPorNombreOCodigo(categoriasNombreOCodigo);
        if (dtCats.length == 0) throw new IllegalArgumentException("Las categorías seleccionadas no existen en el sistema.");

        String imagenWebPath = null;
        if (imagenBytes != null && imagenBytes.length > 0) {
            imagenWebPath = subirImagenEvento(nombre, imagenFileName, imagenBytes);
        }

        getControllerEvento().altaEventoDT(
                nombre, descripcion, java.time.LocalDate.now(), sigla,
                Arrays.asList(dtCats),
                imagenWebPath
        );
    }
    @WebMethod
    public DTUsuarioListaConsulta consultarUsuarioPorNickname(String nicknameUsu) {
        return getControllerUsuario().consultaDeUsuario(nicknameUsu);
    }

    
    
 // ===== Helpers=====
    @WebMethod(exclude = true)
    private static String normaliza(String s) {
        if (s == null) return "";
        String t = s.toLowerCase(java.util.Locale.ROOT).trim();

        t = t.replace('á', 'a').replace('é', 'e').replace('í', 'i').replace('ó', 'o').replace('ú', 'u')
             .replace('ä', 'a').replace('ë', 'e').replace('ï', 'i').replace('ö', 'o').replace('ü', 'u')
             .replace('ñ', 'n');

        return t.replaceAll("\\s+", " ");
    }

    @jakarta.jws.WebMethod(exclude = true)
    private static java.util.Map<String, String> defaultCodigoNombre() {
        java.util.Map<String, String> m = new java.util.HashMap<>();
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
    @WebMethod(exclude = true)
    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    @WebMethod(exclude = true)
    private static String extensionOf(String filename) {
        if (filename == null) return "";
        int dot = filename.lastIndexOf('.');
        if (dot < 0 || dot == filename.length() - 1) return "";
        return filename.substring(dot + 1).toLowerCase(java.util.Locale.ROOT);
    }

    @WebMethod(exclude = true)
    private static String slug(String s) {
        String base = (s == null || s.isEmpty()) ? "evento" : s.toLowerCase(java.util.Locale.ROOT);
        base = base.replaceAll("[^a-z0-9-_]+", "-");
        base = base.replaceAll("-{2,}", "-");
        return base.replaceAll("^-|-$", "");
    }

    
    @WebMethod
    public servidorcentral.logica.DTSesionUsuario iniciarSesion(String identifier, String password)
            throws servidorcentral.excepciones.UsuarioNoExisteException,
                   servidorcentral.excepciones.CredencialesInvalidasException {
        return getControllerUsuario().iniciarSesion(identifier, password);
    }
    
    @WebMethod
    public servidorcentral.logica.DTUsuarioListaConsulta consultaDeUsuario(String nickname)
            throws servidorcentral.excepciones.UsuarioNoExisteException {
        return getControllerUsuario().consultaDeUsuario(nickname);
    }

    @WebMethod
    public boolean existeEdicionPorNombre(String evento, String nombreEdicion) {
        return getControllerEvento().existeEdicionPorNombre(evento, nombreEdicion);
    }

    @WebMethod
    public boolean existeEdicionPorSigla(String sigla) {
        return getControllerEvento().existeEdicionPorSiglaDTO(sigla);
    }

    @MTOM(enabled = true)
    @WebMethod
    public String subirImagenEdicion(
            @WebParam(name = "evento") String evento,
            @WebParam(name = "nombreEdicion") String nombreEdicion,
            @WebParam(name = "originalFileName") String originalFileName,
            @WebParam(name = "contenido") byte[] contenido) throws IOException {

        if (contenido == null || contenido.length == 0) return null;

        String ext = extensionOf(originalFileName);
        String safeBase = slug(evento + "-" + nombreEdicion);
        String stamp = java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
                .format(java.time.LocalDateTime.now());
        String fileName = safeBase + "_" + stamp + (ext.isEmpty() ? "" : "." + ext);

        String realDir = WSConfig.get("images.editions.real_dir",
                System.getProperty("java.io.tmpdir") + java.io.File.separator + "eventuy-ediciones");
        String webDir  = WSConfig.get("images.editions.web_dir", "/media/img/ediciones");

        java.io.File dir = new java.io.File(realDir);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("No se pudo crear el directorio de imágenes: " + realDir);
        }

        java.io.File destino = new java.io.File(dir, fileName);
        try (java.io.FileOutputStream fos = new java.io.FileOutputStream(destino)) {
            fos.write(contenido);
        }
        return (webDir.endsWith("/") ? webDir.substring(0, webDir.length()-1) : webDir) + "/" + fileName;
    }

    @WebMethod
    public void altaEdicionDeEventoDTO(String nombreEvento, String nickOrganizador, String nombreEdicion,
                                       String sigla, String ciudad, String pais,
                                       java.time.LocalDate fInicio, java.time.LocalDate fFin,
                                       java.time.LocalDate fAlta, String imagenWebPath) throws Exception {
        getControllerEvento().altaEdicionDeEventoDTO(
            nombreEvento, nickOrganizador, nombreEdicion, sigla, ciudad, pais, fInicio, fFin, fAlta, imagenWebPath
        );
    }
    
    @WebMethod
    public DTRegistro[] listarRegistrosDeAsistente(
            @jakarta.jws.WebParam(name = "nickname") String nickname) {

        if (nickname == null || nickname.isBlank()) return new DTRegistro[0];

        var asis = getControllerUsuario().getDTAsistente(nickname);
        if (asis == null || asis.getRegistros() == null || asis.getRegistros().isEmpty())
            return new DTRegistro[0];

        java.util.List<DTRegistro> regs = asis.getRegistros();
        return regs.toArray(new DTRegistro[0]);
    }

    @WebMethod
    public DTEdicion[] listarEdicionesDeOrganizador(
            @jakarta.jws.WebParam(name = "nickname") String nickname) {

        if (nickname == null || nickname.isBlank()) return new DTEdicion[0];

        var org = getControllerUsuario().getDTOrganizadorDetallado(nickname);
        if (org == null || org.getEdiciones() == null || org.getEdiciones().isEmpty())
            return new DTEdicion[0];

        java.util.List<DTEdicion> eds = org.getEdiciones();
        return eds.toArray(new DTEdicion[0]);
    }

//AltaTipoRegistro
    
    @WebMethod
    public void altaTipoRegistroDT(String nombreTR, String descripcion, Float costo, Integer cupo, String edicion) throws NombreTRUsadoException{
    	getControllerEvento().altaTipoRegistroDT(nombreTR, descripcion, costo, cupo, edicion);
    }
  
    @WebMethod
    public boolean existeTRNombre(String edicion, String nombre) {
    	return getControllerEvento().existeTRNombre(edicion, nombre);
    }

    
}

