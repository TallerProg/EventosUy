package ServidorCentral.logica;

public class Organizador extends Usuario {

    private String descripcion;
    private String url;

    public Organizador(String nickname, String correo, String nombre, String descripcion, String url) {
        super(nickname, correo, nombre);
        this.descripcion = descripcion;
        this.url = url;
    }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }


}
