

import java.sql.SQLException;





import zentidades.DeliveryLogic;
import zentidades.Pedido;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;



public class Main {

	public final static String TAG="[·MAIN·] ";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	//	SupManager sp=new SupManager();
		
		Pedido p1=new Pedido("Juan",3);
		
		DeliveryLogic dl=new DeliveryLogic();
		dl.hacerAlgo(p1);
		//sp.enviar(p1);
		//DeliveryLogic.enviar(p1);
		
		/*
		// this uses h2 but you can change it to match your database
		String databaseUrl = "jdbc:h2:mem:account";
		// create a connection source to our database
		//ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl);
		ConnectionSource connectionSource = null;
		try {
			connectionSource = new JdbcConnectionSource(databaseUrl);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(TAG+"ERror JDBC: " + e);
			
			return;
		}
		
		try
		{
		// instantiate the DAO to handle Account with String id
		Dao<Cliente,String> clienteDao =  DaoManager.createDao(connectionSource, Cliente.class);

		// if you need to create the 'accounts' table make this call
		TableUtils.createTable(connectionSource, Cliente.class);

		// create an instance of Account		
		Cliente cliente= new Cliente("Juancho");

		// persist the account object to the database
		clienteDao.create(cliente);

		// retrieve the account
		Cliente cliente2 = clienteDao.queryForId("Juancho");
		// show its password
		System.out.println("Cliente: " + cliente2.getNombre());

		// close the connection source
		connectionSource.close();
		
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		*/
		
	}

}
