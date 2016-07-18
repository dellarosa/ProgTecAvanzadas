package tap.generated.server;

/****************************************************************
********************** C�DIGO AUTOGENERADO **********************
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
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


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
	@Produces(MediaType.TEXT_PLAIN)
	public String obtenerPedido(Pedido arg0) {
		return original.obtenerPedido(arg0);
	}

}
