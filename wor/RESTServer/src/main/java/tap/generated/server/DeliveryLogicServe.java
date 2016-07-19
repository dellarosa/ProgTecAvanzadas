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
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Produces;


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
@Path("/psj")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.TEXT_PLAIN)
	public String EnviarPedido(Pedido arg0) {
		return original.EnviarPedido(arg0);
	}

	@GET
@Path("/json")
@Produces(MediaType.APPLICATION_JSON)
public Pedido obtenerPedido() {
		return original.obtenerPedido();
	}

}
