package servidorcentral.logica;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;

@XmlEnum
@XmlType(name = "ETipoNivel")
public enum ETipoNivel {
	Platino, Oro, Plata, Bronce
}