package publicar.dto;

import jakarta.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AltaEventoWSResponse")
public class AltaEventoWSResp {
    public boolean ok;

    @XmlElementWrapper(name = "errores")
    @XmlElement(name = "mensaje")
    public List<String> errores;

    /** Ruta web donde quedo accesible la imagen (si se guardo). */
    public String imagenWebPath;
}
