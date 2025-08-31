package ServidorCentral.logica;

public class Factory {

	private static Factory instance;

	private Factory() {
	}

	public static Factory getInstance() {
		if (instance == null) {
			instance = new Factory();
		}
		return instance;
	}

	public IControllerUsuario getIControllerUsuario() {
		return new ControllerUsuario();
	}

	public IControllerEvento getIControllerEvento() {
		return new ControllerEvento();
	}
	
	public IControllerInstitucion getIControllerInstitucion() {
		return new ControllerInstitucion();
	}
}
