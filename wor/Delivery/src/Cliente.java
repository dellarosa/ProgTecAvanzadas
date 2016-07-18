

//import com.j256.ormlite.field.DatabaseField;

//@DatabaseTable(tableName = "clientes")
public class Cliente {

	public Cliente(String name)
	{
		Nombre=name;
		
	}
	
	//@DatabaseField(id = true,canBeNull=false)
	public String Nombre;

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	
	
}
