package tap.generated.server;

/****************************************************************
********************** CÓDIGO AUTOGENERADO **********************
*****************************************************************
*
* Creado por: 
*	Barzini, Sebastian
*	Dellarosa Fabio
*/

// IMPORTS 
import tap.generated.code.*;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;


@Path("/service")
public class DeliveryLogicServe{
// CONSTANTES


// VARIABLES
	private DeliveryLogic original = null;


	public DeliveryLogicServe() {
		original = new DeliveryLogic();
	}

// METODOS
	@POST
	public boolean hacerAlgo(Pedido arg0) {
		return original.hacerAlgo(arg0);
	}

	@GET
	public String obtenerPedido(Pedido arg0) {
		return original.obtenerPedido(arg0);
	}

}
