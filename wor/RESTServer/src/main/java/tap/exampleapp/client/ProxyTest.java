package tap.exampleapp.client;

import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

public class ProxyTest 
{
	HttpURLConnectionExample htp=new HttpURLConnectionExample();
	
	public ProxyTest()
	{
		
	}
	
	@Test
	public void testEnviarPedidoFallido()
	{
		String esperado="FALLO POST";
		String res="";
		try {
			res=htp.sendPOST(" ");			
			//System.out.println("RES: "+res);
			Assert.assertEquals(esperado,res);
			//Assert.assertNotSame(esperado,res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}

	@Test
	public void testEnviarPedido()
	{
		String esperado="OK";
		String res="";
		try {
			res=htp.sendPOST(htp.POST_PARAMS);			
			//System.out.println("RES: "+res);
		
			Assert.assertEquals(esperado,res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}

	@Test
	public void testRecibirPedido()
	{
		String esperado="{\"cantidad\":\"25\",\"cliente\":\"fab\",\"id\":\"1\"}";
			
		try {
			Assert.assertEquals(esperado,htp.sendGET());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	@Test
	public void testRecibirPedidoFallido()
	{
		String esperado="{\"id\":\"1\"}";
			
		try {
			Assert.assertNotSame(esperado,htp.sendGET());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}


