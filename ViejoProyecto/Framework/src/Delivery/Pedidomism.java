package Delivery;

import Serialize.Seri;


//@DatabaseTable(tableName = "pedidos")
public class Pedidomism {

	public Pedidomism(String name,int cant)
	{
		setCantidad(cant);
		setCliente(name);
	}
	//@DatabaseField(canBeNull = false)
	public int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Seri(name="cantidad")	
	public int cantidad;

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cant) {
		cantidad = cant;
	}
	
	@Seri(name="cliente")
	public String cliente;

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	
}
