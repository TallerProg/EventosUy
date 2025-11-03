package servidorcentral.logica;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;

@XmlEnum
@XmlType(name = "RolUsuario")
public enum RolUsuario { 
	ASISTENTE, ORGANIZADOR, VISITANTE 
	}