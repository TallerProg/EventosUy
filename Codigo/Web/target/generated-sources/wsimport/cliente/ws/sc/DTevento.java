
package cliente.ws.sc;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dTevento complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="dTevento">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="sigla" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="fAlta" type="{http://publicar/}localDate" minOccurs="0"/>
 *         <element name="fAltaS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="img" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="finalizado" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         <element name="dtCategorias" type="{http://publicar/}dtCategoria" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="dtEdiciones" type="{http://publicar/}dtEdicion" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dTevento", propOrder = {
    "nombre",
    "sigla",
    "descripcion",
    "fAlta",
    "fAltaS",
    "img",
    "finalizado",
    "dtCategorias",
    "dtEdiciones"
})
public class DTevento {

    protected String nombre;
    protected String sigla;
    protected String descripcion;
    protected LocalDate fAlta;
    protected String fAltaS;
    protected String img;
    protected Boolean finalizado;
    @XmlElement(nillable = true)
    protected List<DtCategoria> dtCategorias;
    @XmlElement(nillable = true)
    protected List<DtEdicion> dtEdiciones;

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
     * Obtiene el valor de la propiedad descripcion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Define el valor de la propiedad descripcion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcion(String value) {
        this.descripcion = value;
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
     * Obtiene el valor de la propiedad fAltaS.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFAltaS() {
        return fAltaS;
    }

    /**
     * Define el valor de la propiedad fAltaS.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFAltaS(String value) {
        this.fAltaS = value;
    }

    /**
     * Obtiene el valor de la propiedad img.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImg() {
        return img;
    }

    /**
     * Define el valor de la propiedad img.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImg(String value) {
        this.img = value;
    }

    /**
     * Obtiene el valor de la propiedad finalizado.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFinalizado() {
        return finalizado;
    }

    /**
     * Define el valor de la propiedad finalizado.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFinalizado(Boolean value) {
        this.finalizado = value;
    }

    /**
     * Gets the value of the dtCategorias property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the dtCategorias property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDtCategorias().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DtCategoria }
     * 
     * 
     * @return
     *     The value of the dtCategorias property.
     */
    public List<DtCategoria> getDtCategorias() {
        if (dtCategorias == null) {
            dtCategorias = new ArrayList<>();
        }
        return this.dtCategorias;
    }

    /**
     * Gets the value of the dtEdiciones property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the dtEdiciones property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDtEdiciones().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DtEdicion }
     * 
     * 
     * @return
     *     The value of the dtEdiciones property.
     */
    public List<DtEdicion> getDtEdiciones() {
        if (dtEdiciones == null) {
            dtEdiciones = new ArrayList<>();
        }
        return this.dtEdiciones;
    }

}
