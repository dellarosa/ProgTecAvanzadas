package Delivery;

import Annotation.Get;
import Annotation.Post;
import Annotation.Servicios;


//@DatabaseTable(tableName = "delivery")
@Servicios(servicio = "")
public class DeliveryLogic {
	
	@Post(post="")
	public boolean hacerAlgo(Pedido p1)
	{
		
		return true;
	}

	@Get(get="")
	public boolean obtenerPedido(Pedido p1)
	{
		
		return true;
	}
}
