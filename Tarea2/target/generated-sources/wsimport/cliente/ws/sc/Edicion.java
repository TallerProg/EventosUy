
package cliente.ws.sc;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para edicion complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="edicion">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="estado" type="{http://publicar/}estadoEdicion" minOccurs="0"/>
 *         <element name="fechaAlta" type="{http://publicar/}localDate" minOccurs="0"/>
 *         <element name="imagenWebPath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "edicion", propOrder = {
    "estado",
    "fechaAlta",
    "imagenWebPath"
})
public class Edicion {

    @XmlSchemaType(name = "string")
    protected EstadoEdicion estado;
    protected LocalDate fechaAlta;
    protected String imagenWebPath;

    /**
     * Obtiene el valor de la propiedad estado.
     * 
     * @return
     *     possible object is
     *     {@link EstadoEdicion }
     *     
     */
    public EstadoEdicion getEstado() {
        return estado;
    }

    /**
     * Define el valor de la propiedad estado.
     * 
     * @param value
     *     allowed object is
     *     {@link EstadoEdicion }
     *     
     */
    public void setEstado(EstadoEdicion value) {
        this.estado = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaAlta.
     * 
     * @return
     *     possible object is
     *     {@link LocalDate }
     *     
     */
    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    /**
     * Define el valor de la propiedad fechaAlta.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalDate }
     *     
     */
    public void setFechaAlta(LocalDate value) {
        this.fechaAlta = value;
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

}
