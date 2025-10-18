package servidorcentral.logica;

import java.util.ArrayList;
import java.util.List;

public class DTOrganizadorDetallado {
    private final String nickname;
    private final String correo;
    private final String nombre;
    private final String descripcion;
    private final String url;
    private final String img;
    private final List<DTEdicion> ediciones; // nunca null

    public DTOrganizadorDetallado(String nickname,
                                String correo,
                                String nombre,
                                String descripcion,
                                String url,
                                String img,
                                List<DTEdicion> ediciones) {
        this.nickname = nickname;
        this.correo = correo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.url = url;
        this.img = img;
        // copia defensiva
        this.ediciones = (ediciones == null) ? new ArrayList<>() : new ArrayList<>(ediciones);
    }

    public String getNickname()   { return nickname; }
    public String getCorreo()     { return correo; }
    public String getNombre()     { return nombre; }
    public String getDescripcion(){ return descripcion; }
    public String getUrl()        { return url; }
    public String getImg()        { return img; }
    public List<DTEdicion> getEdiciones() { return new ArrayList<>(ediciones); }
}
