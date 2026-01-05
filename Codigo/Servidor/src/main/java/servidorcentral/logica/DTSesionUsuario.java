package servidorcentral.logica;

import java.time.LocalDate;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;


@XmlAccessorType(XmlAccessType.FIELD)
public class DTSesionUsuario{
        private String nickname;
        private String correo;
        private RolUsuario rol;
        private LocalDate fechaHoraInicio; 
        
        public DTSesionUsuario() {
        }

        public DTSesionUsuario(String nickname, String correo, RolUsuario rol, LocalDate fechaHoraInicio) {
            this.nickname = nickname;
            this.correo = correo;
            this.rol = rol;
            this.fechaHoraInicio = fechaHoraInicio;
        }
        public String getNickname() { return nickname; }
        public String getCorreo() { return correo; }
        public RolUsuario getRol() { return rol; }
        public String getRolString() { return rol.toString(); }
        public LocalDate getFechaHoraInicio() { return fechaHoraInicio; }

		public void setFechaHoraInicio(LocalDate fechaHoraInicio) {
			this.fechaHoraInicio = fechaHoraInicio;
			
		}
		public void setNickname(String nickname) {
			this.nickname = nickname;
		}
		public void setCorreo(String correo) {
			this.correo = correo;
		}
		public void setRol(RolUsuario rol) {
			this.rol = rol;
		}
        
    }
