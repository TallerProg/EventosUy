package publicar.dto;

import jakarta.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AltaEventoWSRequest")
public class AltaEventoWSReq {
    public String nombre;
    public String descripcion;
    public String sigla;

    /** Puede enviar nombres de categoria o codigos (CA01, CA02, ...). */
    @XmlElementWrapper(name = "categorias")
    @XmlElement(name = "valor")
    public List<String> categorias;

    /** Imagen opcional en Base64. Si no se envia, el evento queda sin imagen. */
    public String imagenNombreOriginal; // ej. "foto.png"
    public String imagenBase64;         // ej. "iVBORw0K..." (Base64)
}
