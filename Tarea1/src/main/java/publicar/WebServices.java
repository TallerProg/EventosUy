/**
 * 
 */
package publicar;

/**
 * @author efviodo
 *
 */


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;
import jakarta.jws.soap.SOAPBinding.Style;
import jakarta.xml.ws.Endpoint;
import servidorcentral.logica.DTCategoria;
import servidorcentral.logica.Factory;
import servidorcentral.logica.IControllerEvento;

@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class WebServices {
	



    private Endpoint endpoint = null;
    //Constructor
    public WebServices(){}

    //Operaciones las cuales quiero publicar

    @WebMethod(exclude = true)
    public void publicar(){
         endpoint = Endpoint.publish("http://localhost:9128/webservices", this);
    }

    @WebMethod(exclude = true)
    public Endpoint getEndpoint() {
            return endpoint;
    }


    @WebMethod(exclude = true)
    private IControllerEvento getControllerEvento() {
        return Factory.getInstance().getIControllerEvento();
    }

    // === MÉTODO PUBLICADO ===
    @WebMethod
    public DTCategoria[] listarCategorias() {
        List<DTCategoria> lista = getControllerEvento().listarDTCategorias();
        return (lista == null || lista.isEmpty())
                ? new DTCategoria[0]
                : lista.toArray(new DTCategoria[0]);
    }
}

