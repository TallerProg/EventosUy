
package cliente.ws.sc;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dtRegistro complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="dtRegistro">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="fInicio" type="{http://publicar/}localDate" minOccurs="0"/>
 *         <element name="fInicioS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="costo" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         <element name="tipoRegistroNombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="asistenteNickname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="patrocinioCodigo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="nombreEdicion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="nombreEvento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="asistio" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtRegistro", propOrder = {
    "fInicio",
    "fInicioS",
    "costo",
    "tipoRegistroNombre",
    "asistenteNickname",
    "patrocinioCodigo",
    "nombreEdicion",
    "nombreEvento",
    "asistio"
})
public class DtRegistro {

    protected LocalDate fInicio;
    protected String fInicioS;
    protected Float costo;
    protected String tipoRegistroNombre;
    protected String asistenteNickname;
    protected String patrocinioCodigo;
    protected String nombreEdicion;
    protected String nombreEvento;
    protected boolean asistio;

    /**
     * Obtiene el valor de la propiedad fInicio.
     * 
     * @return
     *     possible object is
     *     {@link LocalDate }
     *     
     */
    public LocalDate getFInicio() {
        return fInicio;
    }

    /**
     * Define el valor de la propiedad fInicio.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalDate }
     *     
     */
    public void setFInicio(LocalDate value) {
        this.fInicio = value;
    }

    /**
     * Obtiene el valor de la propiedad fInicioS.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFInicioS() {
        return fInicioS;
    }

    /**
     * Define el valor de la propiedad fInicioS.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFInicioS(String value) {
        this.fInicioS = value;
    }

    /**
     * Obtiene el valor de la propiedad costo.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getCosto() {
        return costo;
    }

    /**
     * Define el valor de la propiedad costo.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setCosto(Float value) {
        this.costo = value;
    }

    /**
     * Obtiene el valor de la propiedad tipoRegistroNombre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoRegistroNombre() {
        return tipoRegistroNombre;
    }

    /**
     * Define el valor de la propiedad tipoRegistroNombre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoRegistroNombre(String value) {
        this.tipoRegistroNombre = value;
    }

    /**
     * Obtiene el valor de la propiedad asistenteNickname.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsistenteNickname() {
        return asistenteNickname;
    }

    /**
     * Define el valor de la propiedad asistenteNickname.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsistenteNickname(String value) {
        this.asistenteNickname = value;
    }

    /**
     * Obtiene el valor de la propiedad patrocinioCodigo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatrocinioCodigo() {
        return patrocinioCodigo;
    }

    /**
     * Define el valor de la propiedad patrocinioCodigo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatrocinioCodigo(String value) {
        this.patrocinioCodigo = value;
    }

    /**
     * Obtiene el valor de la propiedad nombreEdicion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreEdicion() {
        return nombreEdicion;
    }

    /**
     * Define el valor de la propiedad nombreEdicion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreEdicion(String value) {
        this.nombreEdicion = value;
    }

    /**
     * Obtiene el valor de la propiedad nombreEvento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreEvento() {
        return nombreEvento;
    }

    /**
     * Define el valor de la propiedad nombreEvento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreEvento(String value) {
        this.nombreEvento = value;
    }

    /**
     * Obtiene el valor de la propiedad asistio.
     * 
     */
    public boolean isAsistio() {
        return asistio;
    }

    /**
     * Define el valor de la propiedad asistio.
     * 
     */
    public void setAsistio(boolean value) {
        this.asistio = value;
    }

}
