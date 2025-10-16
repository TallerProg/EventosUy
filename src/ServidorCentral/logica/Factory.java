package ServidorCentral.logica;

public class Factory {

    private static Factory instance;

    private Factory() {
        // constructor privado
    }

    public static Factory getInstance() {
        if (instance == null) {
            instance = new Factory();
        }
        return instance;
    }

    // Métodos para devolver interfaces
    public IControllerUsuario getIControllerUsuario() {
        return new ControllerUsuario();
    }

    public IControllerEvento getIControllerEvento() {
        return new ControllerEvento();
    }
}
