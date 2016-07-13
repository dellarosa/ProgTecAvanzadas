package Serv;
public class DeliveryService{ 
private Entidades.DeliveryLogic dl = new Entidades.DeliveryLogic(); 

 public boolean hacerAlgo(Entidades.Pedido a0){
return dl.hacerAlgo(a0);
}
 
 
}