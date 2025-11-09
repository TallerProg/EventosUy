
package cliente.ws.sc;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dtEdicion complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="dtEdicion">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="sigla" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="fInicio" type="{http://publicar/}localDate" minOccurs="0"/>
 *         <element name="fInicioS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="fFin" type="{http://publicar/}localDate" minOccurs="0"/>
 *         <element name="fFinS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="fAlta" type="{http://publicar/}localDate" minOccurs="0"/>
 *         <element name="ciudad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="pais" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="estado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="imagenWebPath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="nombreEvento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="tipoRegistros" type="{http://publicar/}dtTipoRegistro" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="organizadores" type="{http://publicar/}dtOrganizador" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="registros" type="{http://publicar/}dtRegistro" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="patrocinios" type="{http://publicar/}dtPatrocinio" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtEdicion", propOrder = {
    "nombre",
    "sigla",
    "fInicio",
    "fInicioS",
    "fFin",
    "fFinS",
    "fAlta",
    "ciudad",
    "pais",
    "estado",
    "imagenWebPath",
    "nombreEvento",
    "tipoRegistros",
    "organizadores",
    "registros",
    "patrocinios"
})
public class DtEdicion {

    protected String nombre;
    protected String sigla;
    protected LocalDate fInicio;
    protected String fInicioS;
    protected LocalDate fFin;
    protected String fFinS;
    protected LocalDate fAlta;
    protected String ciudad;
    protected String pais;
    protected String estado;
    protected String imagenWebPath;
    protected String nombreEvento;
    @XmlElement(nillable = true)
    protected List<DtTipoRegistro> tipoRegistros;
    @XmlElement(nillable = true)
    protected List<DtOrganizador> organizadores;
    @XmlElement(nillable = true)
    protected List<DtRegistro> registros;
    @XmlElement(nillable = true)
    protected List<DtPatrocinio> patrocinios;

    /**
     * Obtiene el valor de la propiedad nombre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Define el valor de la propiedad nombre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombre(String value) {
        this.nombre = value;
    }

    /**
     * Obtiene el valor de la propiedad sigla.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSigla() {
        return sigla;
    }

    /**
     * Define el valor de la propiedad sigla.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSigla(String value) {
        this.sigla = value;
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
     * Obtiene el valor de la propiedad fFin.
     * 
     * @return
     *     possible object is
     *     {@link LocalDate }
     *     
     */
    public LocalDate getFFin() {
        return fFin;
    }

    /**
     * Define el valor de la propiedad fFin.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalDate }
     *     
     */
    public void setFFin(LocalDate value) {
        this.fFin = value;
    }

    /**
     * Obtiene el valor de la propiedad fFinS.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFFinS() {
        return fFinS;
    }

    /**
     * Define el valor de la propiedad fFinS.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFFinS(String value) {
        this.fFinS = value;
    }

    /**
     * Obtiene el valor de la propiedad fAlta.
     * 
     * @return
     *     possible object is
     *     {@link LocalDate }
     *     
     */
    public LocalDate getFAlta() {
        return fAlta;
    }

    /**
     * Define el valor de la propiedad fAlta.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalDate }
     *     
     */
    public void setFAlta(LocalDate value) {
        this.fAlta = value;
    }

    /**
     * Obtiene el valor de la propiedad ciudad.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * Define el valor de la propiedad ciudad.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCiudad(String value) {
        this.ciudad = value;
    }

    /**
     * Obtiene el valor de la propiedad pais.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPais() {
        return pais;
    }

    /**
     * Define el valor de la propiedad pais.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPais(String value) {
        this.pais = value;
    }

    /**
     * Obtiene el valor de la propiedad estado.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Define el valor de la propiedad estado.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstado(String value) {
        this.estado = value;
    }

    /**
     * Obtiene el valor de la propiedad imagenWebPath.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImagenWebPath() {
        return imagenWebPath;
    }

    /**
     * Define el valor de la propiedad imagenWebPath.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImagenWebPath(String value) {
        this.imagenWebPath = value;
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
     * Gets the value of the tipoRegistros property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the tipoRegistros property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTipoRegistros().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DtTipoRegistro }
     * 
     * 
     * @return
     *     The value of the tipoRegistros property.
     */
    public List<DtTipoRegistro> getTipoRegistros() {
        if (tipoRegistros == null) {
            tipoRegistros = new ArrayList<>();
        }
        return this.tipoRegistros;
    }

    /**
     * Gets the value of the organizadores property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the organizadores property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrganizadores().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DtOrganizador }
     * 
     * 
     * @return
     *     The value of the organizadores property.
     */
    public List<DtOrganizador> getOrganizadores() {
        if (organizadores == null) {
            organizadores = new ArrayList<>();
        }
        return this.organizadores;
    }

    /**
     * Gets the value of the registros property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the registros property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRegistros().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DtRegistro }
     * 
     * 
     * @return
     *     The value of the registros property.
     */
    public List<DtRegistro> getRegistros() {
        if (registros == null) {
            registros = new ArrayList<>();
        }
        return this.registros;
    }

    /**
     * Gets the value of the patrocinios property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the patrocinios property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPatrocinios().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DtPatrocinio }
     * 
     * 
     * @return
     *     The value of the patrocinios property.
     */
    public List<DtPatrocinio> getPatrocinios() {
        if (patrocinios == null) {
            patrocinios = new ArrayList<>();
        }
        return this.patrocinios;
    }

}
