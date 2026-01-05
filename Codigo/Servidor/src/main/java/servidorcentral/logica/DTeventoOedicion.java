package servidorcentral.logica;
import java.time.LocalDate;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
public class DTeventoOedicion {
    private String tipo;  
    private String nombreEvento;
    private String nombreEdicion;
    private String descripcion;
    private LocalDate fechaAlta;  
    private Boolean finalizado;
    private String img;
    private String estado;
	private List<DTOrganizador> organizadores;


    public DTeventoOedicion() {
	}
    // Constructor para evento
    public DTeventoOedicion(DTevento evento) {
        this.tipo = "evento";
        this.nombreEvento = evento.getNombre();
        this.nombreEdicion =null;
        this.descripcion = evento.getDescripcion();
        this.fechaAlta = evento.getFAlta();
        this.finalizado=evento.getFinalizado();
        this.img=evento.getImg();
        this.organizadores=null;
        this.estado="evento";
    }

    // Constructor para edición
    public DTeventoOedicion(DTEdicion edicion) {
        this.tipo = "edicion";
        this.nombreEdicion = edicion.getNombre();
        this.nombreEvento = edicion.getEvento().getNombre();
        this.descripcion = null;
        this.fechaAlta = edicion.getfAlta();
        this.finalizado=null;
        this.img=edicion.getImagenWebPath();
        this.organizadores=edicion.getOrganizadores();
        this.estado=edicion.getEstado();
    }

    // Getters
    public String getTipo() {
        return tipo;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }
    public String getNombreEdicion() {
        return nombreEdicion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Boolean getFinalizado() {
        return finalizado;
    }
    
    public LocalDate getFechaAlta() {
        return fechaAlta;
    }
    
    public String getImg() {
        return img;
    }

    public List<DTOrganizador> getOrganizadores() {
        return organizadores;
    }
    
    public String getEstado() {
        return estado;
    }


}