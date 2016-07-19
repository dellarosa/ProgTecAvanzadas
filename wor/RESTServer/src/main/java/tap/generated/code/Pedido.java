package tap.generated.code;


//@DatabaseTable(tableName = "pedidos")
//@XmlRootElement
public class Pedido {

	public Pedido()
	{}
	public Pedido(String name,String cant)
	{
		setCantidad(cant);
		setCliente(name);
	}
	//@DatabaseField(canBeNull = false)
	//@XmlElement(name="id")
	public String id="1";	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	//@Seri(name="cantidad")
	//@XmlElement(name="cantidad")
	public String cantidad;
	public String getCantidad() {
		return cantidad;
	}
	public void setCantidad(String cant) {
		cantidad = cant;
	}
	
	//@Seri(name="cliente")
	 //@XmlElement(name="cliente") 
	 public String cliente;
	 //@XmlElement(name="king") 
	// public String name;	 
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	
}
