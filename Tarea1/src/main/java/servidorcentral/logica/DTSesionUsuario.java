package servidorcentral.logica;
import java.time.LocalDateTime;

public class DTSesionUsuario{
        private final String nickname;
        private final String correo;
        private final RolUsuario rol;
        private final LocalDateTime fechaHoraInicio; 

        public DTSesionUsuario(String nickname, String correo, RolUsuario rol, LocalDateTime fechaHoraInicio) {
            this.nickname = nickname;
            this.correo = correo;
            this.rol = rol;
            this.fechaHoraInicio = fechaHoraInicio;
        }
        public String getNickname() { return nickname; }
        public String getCorreo() { return correo; }
        public RolUsuario getRol() { return rol; }
        public LocalDateTime getFechaHoraInicio() { return fechaHoraInicio; }
    }
