
package cliente.ws.sc;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ETipoNivel.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <pre>{@code
 * <simpleType name="ETipoNivel">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="Platino"/>
 *     <enumeration value="Oro"/>
 *     <enumeration value="Plata"/>
 *     <enumeration value="Bronce"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "ETipoNivel")
@XmlEnum
public enum ETipoNivel {

    @XmlEnumValue("Platino")
    PLATINO("Platino"),
    @XmlEnumValue("Oro")
    ORO("Oro"),
    @XmlEnumValue("Plata")
    PLATA("Plata"),
    @XmlEnumValue("Bronce")
    BRONCE("Bronce");
    private final String value;

    ETipoNivel(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ETipoNivel fromValue(String v) {
        for (ETipoNivel c: ETipoNivel.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
