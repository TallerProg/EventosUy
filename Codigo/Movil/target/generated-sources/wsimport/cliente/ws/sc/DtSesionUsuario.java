
package cliente.ws.sc;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dtSesionUsuario complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="dtSesionUsuario">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="nickname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="correo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="rol" type="{http://publicar/}RolUsuario" minOccurs="0"/>
 *         <element name="fechaHoraInicio" type="{http://publicar/}localDate" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtSesionUsuario", propOrder = {
    "nickname",
    "correo",
    "rol",
    "fechaHoraInicio"
})
public class DtSesionUsuario {

    protected String nickname;
    protected String correo;
    @XmlSchemaType(name = "string")
    protected RolUsuario rol;
    protected LocalDate fechaHoraInicio;

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
     * Obtiene el valor de la propiedad rol.
     * 
     * @return
     *     possible object is
     *     {@link RolUsuario }
     *     
     */
    public RolUsuario getRol() {
        return rol;
    }

    /**
     * Define el valor de la propiedad rol.
     * 
     * @param value
     *     allowed object is
     *     {@link RolUsuario }
     *     
     */
    public void setRol(RolUsuario value) {
        this.rol = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaHoraInicio.
     * 
     * @return
     *     possible object is
     *     {@link LocalDate }
     *     
     */
    public LocalDate getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    /**
     * Define el valor de la propiedad fechaHoraInicio.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalDate }
     *     
     */
    public void setFechaHoraInicio(LocalDate value) {
        this.fechaHoraInicio = value;
    }

}
