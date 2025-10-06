package src.ServidorCentral.logica;

import java.util.ArrayList;
import java.util.List;

public class ManejadorUsuario {

	private List<Usuario> usuarios = new ArrayList<>();
	private List<Organizador> organizadores = new ArrayList<>();
	private List<Asistente> asistentes = new ArrayList<>();
	private static ManejadorUsuario instancia = null;

	private ManejadorUsuario() {
	}

	public static ManejadorUsuario getinstance() {
		if (instancia == null)
			instancia = new ManejadorUsuario();
		return instancia;
	}
	public void limpiar() {
		organizadores.clear();
		usuarios.clear();
		asistentes.clear();
	}
	public List<Usuario> listarUsuarios() {
		return new ArrayList<>(usuarios);
	}

	public List<Organizador> listarOrganizadores() {
		return new ArrayList<>(organizadores);
	}

	public List<Asistente> listarAsistentes() {
		return new ArrayList<>(asistentes);
	}

	public Usuario findUsuario(String nickname) {
		for (Usuario u : usuarios) {
			if (u.getNickname().equalsIgnoreCase(nickname)) {
				return u;
			}
		}
		return null;
	}

	public Usuario findCorreo(String correo) {
		for (Usuario u : usuarios) {
			if (u.getCorreo().equalsIgnoreCase(correo)) {
				return u;
			}
		}
		return null;
	}

	public Asistente findAsistente(String nickname) {
		for (Asistente a : asistentes) {
			if (a.getNickname().equalsIgnoreCase(nickname)) {
				return a;
			}
		}
		return null;
	}

	public Organizador findOrganizador(String nicknameOrg) {
		for (Organizador o : organizadores) {
			if (o.getNickname().equalsIgnoreCase(nicknameOrg)) {
				return o;
			}
		}
		return null;
	}

	public boolean existeAsistente(String nickname) {
		for (Asistente u : asistentes) {
			if (u.getNickname().equalsIgnoreCase(nickname)) {
				return true;
			}
		}
		return false;
	}

	public void agregarUsuario(Usuario u) {
		usuarios.add(u);
	}

	public void agregarAsistente(Asistente a) {
		asistentes.add(a);
	}

	public void agregarOrganizador(Organizador o) {
		organizadores.add(o);
	}

	public void limpiarUsuarios() {
		usuarios.clear();
		asistentes.clear();
		organizadores.clear();
	}

}
