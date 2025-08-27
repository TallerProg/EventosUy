package ServidorCentral.logica;

import java.util.Date;

public class Evento {

    private String nombre;
    private String sigla;
    private String descripcion;
    private Date fAlta;
    private Categoria categoria; // referencia a Categoria

    public Evento(String nombre, String sigla, String descripcion, Date fAlta, Categoria categoria) {
        this.nombre = nombre;
        this.sigla = sigla;
        this.descripcion = descripcion;
        this.fAlta = fAlta;
        this.categoria = categoria;
    }

    // Getters y setters temporales
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFAlta() {
        return fAlta;
    }

    public void setFAlta(Date fAlta) {
        this.fAlta = fAlta;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    // Método de ejemplo
    public void method(String type) {
        // placeholder para que compile
    }
}
