package Entidades;

import com.j256.ormlite.table.DatabaseTable;

import Annotation.Get;
import Annotation.Post;
import Annotation.Servicios;


//@DatabaseTable(tableName = "delivery")
@Servicios(servicio = "")
public class DeliveryLogic {
	
	@Post(post="")
	public boolean hacerAlgo(Pedido p1)
	{
		System.out.println("HICE ALGO");
		return true;
	}

	@Get(get="")
	public boolean obtenerPedido(Pedido p1)
	{
		
		return true;
	}
}
