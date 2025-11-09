
package cliente.ws.sc;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dtUsuarioListaConsulta complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="dtUsuarioListaConsulta">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="nickname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="correo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="apellido" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="fNacimiento" type="{http://publicar/}localDate" minOccurs="0"/>
 *         <element name="fNacimientoS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="url" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="dtEdiciones" type="{http://publicar/}dtEdicion" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="dtRegistros" type="{http://publicar/}dtRegistro" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="seguidores" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="seguidos" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="img" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="ins" type="{http://publicar/}dtInstitucion" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtUsuarioListaConsulta", propOrder = {
    "nickname",
    "correo",
    "nombre",
    "apellido",
    "fNacimiento",
    "fNacimientoS",
    "descripcion",
    "url",
    "dtEdiciones",
    "dtRegistros",
    "seguidores",
    "seguidos",
    "img",
    "ins"
})
public class DtUsuarioListaConsulta {

    protected String nickname;
    protected String correo;
    protected String nombre;
    protected String apellido;
    protected LocalDate fNacimiento;
    protected String fNacimientoS;
    protected String descripcion;
    protected String url;
    @XmlElement(nillable = true)
    protected List<DtEdicion> dtEdiciones;
    @XmlElement(nillable = true)
    protected List<DtRegistro> dtRegistros;
    @XmlElement(nillable = true)
    protected List<String> seguidores;
    @XmlElement(nillable = true)
    protected List<String> seguidos;
    protected String img;
    protected DtInstitucion ins;

    /**
     * Obtiene el valor de la propiedad nickname.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Define el valor de la propiedad nickname.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNickname(String value) {
        this.nickname = value;
    }

    /**
     * Obtiene el valor de la propiedad correo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Define el valor de la propiedad correo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorreo(String value) {
        this.correo = value;
    }

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
     * Obtiene el valor de la propiedad apellido.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Define el valor de la propiedad apellido.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApellido(String value) {
        this.apellido = value;
    }

    /**
     * Obtiene el valor de la propiedad fNacimiento.
     * 
     * @return
     *     possible object is
     *     {@link LocalDate }
     *     
     */
    public LocalDate getFNacimiento() {
        return fNacimiento;
    }

    /**
     * Define el valor de la propiedad fNacimiento.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalDate }
     *     
     */
    public void setFNacimiento(LocalDate value) {
        this.fNacimiento = value;
    }

    /**
     * Obtiene el valor de la propiedad fNacimientoS.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFNacimientoS() {
        return fNacimientoS;
    }

    /**
     * Define el valor de la propiedad fNacimientoS.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFNacimientoS(String value) {
        this.fNacimientoS = value;
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
     * Obtiene el valor de la propiedad url.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrl() {
        return url;
    }

    /**
     * Define el valor de la propiedad url.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrl(String value) {
        this.url = value;
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

    /**
     * Gets the value of the dtRegistros property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the dtRegistros property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDtRegistros().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DtRegistro }
     * 
     * 
     * @return
     *     The value of the dtRegistros property.
     */
    public List<DtRegistro> getDtRegistros() {
        if (dtRegistros == null) {
            dtRegistros = new ArrayList<>();
        }
        return this.dtRegistros;
    }

    /**
     * Gets the value of the seguidores property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the seguidores property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSeguidores().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     * @return
     *     The value of the seguidores property.
     */
    public List<String> getSeguidores() {
        if (seguidores == null) {
            seguidores = new ArrayList<>();
        }
        return this.seguidores;
    }

    /**
     * Gets the value of the seguidos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the seguidos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSeguidos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     * @return
     *     The value of the seguidos property.
     */
    public List<String> getSeguidos() {
        if (seguidos == null) {
            seguidos = new ArrayList<>();
        }
        return this.seguidos;
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
     * Obtiene el valor de la propiedad ins.
     * 
     * @return
     *     possible object is
     *     {@link DtInstitucion }
     *     
     */
    public DtInstitucion getIns() {
        return ins;
    }

    /**
     * Define el valor de la propiedad ins.
     * 
     * @param value
     *     allowed object is
     *     {@link DtInstitucion }
     *     
     */
    public void setIns(DtInstitucion value) {
        this.ins = value;
    }

}
