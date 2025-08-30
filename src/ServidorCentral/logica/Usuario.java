package ServidorCentral.logica;

public class Usuario {
    private String nickname;
    private String correo;
    private String nombre;

    public Usuario(String nick, String correo, String nom) {
        this.nickname = nick;
        this.correo = correo;
        this.nombre = nom;
    }

    public String getNickname() {
        return nickname;
    }

    public String getCorreo() {
        return correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
  
    //SOBRECARGO TOSTRING PARA MOSTRAR NOMBRE EN COMBOBOX
    public String toString() { return nickname; }
	
}
