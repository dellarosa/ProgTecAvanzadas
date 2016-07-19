import tap.generated.client.CustomerProxy;


public class Main {

	static final int ProxyHttp=0;
	static final int ProxyHttpDifernte=1;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		CustomerProxy cp=new CustomerProxy(ProxyHttp);
		
		System.out.println("RESULT ENVIAR PEDIDO: "+cp.menuHacerPedido(""));
		
		System.out.println("RESULT RECIBIR PEDIDO: "+cp.menuRecibirPedido());
	}

}
