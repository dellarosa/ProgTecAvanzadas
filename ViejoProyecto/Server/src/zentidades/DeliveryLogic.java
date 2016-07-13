package zentidades;

import com.j256.ormlite.table.DatabaseTable;

import Annotation.Get;
import Annotation.Post;
import Annotation.Servicio;




//@DatabaseTable(tableName = "delivery")
@Servicio(servicio = "")
public class DeliveryLogic {
	
	@Post(post="")
	public boolean hacerAlgo(Pedido p1)
	//public boolean hacerAlgo()
	{
		System.out.println("HICE ALGO");
		return true;
	}

	@Get(get="")
	public boolean obtenerPedido(Pedido p1)
	//public boolean obtenerPedido()
	{
		
		return true;
	}
}
