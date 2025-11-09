
package cliente.ws.sc;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para RolUsuario.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <pre>{@code
 * <simpleType name="RolUsuario">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="ASISTENTE"/>
 *     <enumeration value="ORGANIZADOR"/>
 *     <enumeration value="VISITANTE"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "RolUsuario")
@XmlEnum
public enum RolUsuario {

    ASISTENTE,
    ORGANIZADOR,
    VISITANTE;

    public String value() {
        return name();
    }

    public static RolUsuario fromValue(String v) {
        return valueOf(v);
    }

}
