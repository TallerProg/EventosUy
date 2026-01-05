package servidorcentral.logica;

public final class Factory {

    private static final Factory instance = new Factory();

    private Factory() { }

    public static Factory getInstance() {
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
