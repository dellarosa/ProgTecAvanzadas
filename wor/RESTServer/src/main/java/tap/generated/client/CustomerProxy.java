package tap.generated.client;

import java.io.IOException;

import tap.exampleapp.client.HttpURLConnectionExample;

public class CustomerProxy {
	
	
	HttpURLConnectionExample hturl;
	int tipoProxy;
	
	public CustomerProxy(int proxyhttp)
	{
		
		tipoProxy=proxyhttp;
		
	}
	
	public String menuHacerPedido(String Pedido)
	{
		String res="";
		switch(tipoProxy)
		{
			case 0:
				hturl=new HttpURLConnectionExample();
				try {
					res=hturl.sendPOST(hturl.POST_PARAMS);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;
		}
		return res;
	}
	
	public String menuRecibirPedido()
	{
		String res="";
		switch(tipoProxy)
		{
			case 0:
				hturl=new HttpURLConnectionExample();
				try {
					
					res=hturl.sendGET();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;
		}
		return res;
			
	}
}
