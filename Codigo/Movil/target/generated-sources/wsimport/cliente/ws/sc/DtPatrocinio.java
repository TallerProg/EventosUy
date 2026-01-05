
package cliente.ws.sc;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dtPatrocinio complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="dtPatrocinio">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="fInicio" type="{http://publicar/}localDate" minOccurs="0"/>
 *         <element name="fInicioS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="registroGratuito" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="monto" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         <element name="nivel" type="{http://publicar/}ETipoNivel" minOccurs="0"/>
 *         <element name="institucion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="edicion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="tipoRegistro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtPatrocinio", propOrder = {
    "codigo",
    "fInicio",
    "fInicioS",
    "registroGratuito",
    "monto",
    "nivel",
    "institucion",
    "edicion",
    "tipoRegistro"
})
public class DtPatrocinio {

    protected String codigo;
    protected LocalDate fInicio;
    protected String fInicioS;
    protected int registroGratuito;
    protected Float monto;
    @XmlSchemaType(name = "string")
    protected ETipoNivel nivel;
    protected String institucion;
    protected String edicion;
    protected String tipoRegistro;

    /**
     * Obtiene el valor de la propiedad codigo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Define el valor de la propiedad codigo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigo(String value) {
        this.codigo = value;
    }

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
     * Obtiene el valor de la propiedad registroGratuito.
     * 
     */
    public int getRegistroGratuito() {
        return registroGratuito;
    }

    /**
     * Define el valor de la propiedad registroGratuito.
     * 
     */
    public void setRegistroGratuito(int value) {
        this.registroGratuito = value;
    }

    /**
     * Obtiene el valor de la propiedad monto.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getMonto() {
        return monto;
    }

    /**
     * Define el valor de la propiedad monto.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setMonto(Float value) {
        this.monto = value;
    }

    /**
     * Obtiene el valor de la propiedad nivel.
     * 
     * @return
     *     possible object is
     *     {@link ETipoNivel }
     *     
     */
    public ETipoNivel getNivel() {
        return nivel;
    }

    /**
     * Define el valor de la propiedad nivel.
     * 
     * @param value
     *     allowed object is
     *     {@link ETipoNivel }
     *     
     */
    public void setNivel(ETipoNivel value) {
        this.nivel = value;
    }

    /**
     * Obtiene el valor de la propiedad institucion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstitucion() {
        return institucion;
    }

    /**
     * Define el valor de la propiedad institucion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstitucion(String value) {
        this.institucion = value;
    }

    /**
     * Obtiene el valor de la propiedad edicion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEdicion() {
        return edicion;
    }

    /**
     * Define el valor de la propiedad edicion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEdicion(String value) {
        this.edicion = value;
    }

    /**
     * Obtiene el valor de la propiedad tipoRegistro.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoRegistro() {
        return tipoRegistro;
    }

    /**
     * Define el valor de la propiedad tipoRegistro.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoRegistro(String value) {
        this.tipoRegistro = value;
    }

}
