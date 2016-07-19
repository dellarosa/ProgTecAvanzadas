

//import com.j256.ormlite.table.DatabaseTable;

import Annotation.Get;
import Annotation.Post;
import Annotation.Servicio;
import Annotation.Consumes;

//@DatabaseTable(tableName = "delivery")
@Servicio(servicio = "")
public class DeliveryLogic {
	
	@Post(post="")
	//@Path("/psj")
	//@Consumes(MediaType.APPLICATION_JSON)
	//@Consumes("application/json")
    //@Produces(MediaType.TEXT_PLAIN)
	public String EnviarPedido(Pedido p1)
	//public boolean hacerAlgo()
	{	String result="";
		try
		{
			result = "cliente : " + p1.cliente;
		}catch(Exception e)
		{
			System.out.println("ERROR POST");
			return "ERROR";
		}
		System.out.println("HICE ALGO");
		return "OK";
	}

	@Get(get="")
	//@Path("/json")
	//@Produces("application/json")
    //@Produces(MediaType.APPLICATION_JSON)
	public Pedido obtenerPedido()
	//public boolean obtenerPedido()
	{
		Pedido p=new Pedido("fab","25");
		
		return p;
		//return "UN PAR DE LECHUGAS";
	}
}
