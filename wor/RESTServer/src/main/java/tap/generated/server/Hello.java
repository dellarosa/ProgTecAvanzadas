package tap.generated.server;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tap.generated.code.Pedido;

// Plain old Java Object it does not extend as class or implements 
// an interface

// The class registers its methods for the HTTP GET request using the @GET annotation. 
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML and HTML. 

// The browser requests per default the HTML MIME type.

//Sets the path to base URL + /hello
@Path("/hello")
public class Hello {

  // This method is called if TEXT_PLAIN is request
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String sayPlainTextHello() {
    return "Hello Jersey";
  }

  // This method is called if XML is request
  @GET
  @Produces(MediaType.TEXT_XML)
  public String sayXMLHello() {
    return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey" + "</hello>";
  }

  // This method is called if HTML is request
  @GET
  @Produces(MediaType.TEXT_HTML)
  public String sayHtmlHello() {
	  
    return "<html> " + "<title>" + "Hello Jersey" + "</title>"
        + "<body><h1>" + "Hello Jersey" + "</body></h1>" + "</html> ";
  }
  
  // This method is called if Jason is request
  @GET
  @Path("/json")
  //@Produces("application/json")
  @Produces(MediaType.APPLICATION_JSON)
  public Pedido sayJasonHello() {
	  
	  Pedido p=new Pedido("fab","25");
    //return "<html> " + "<title>" + "Hello Jersey" + "</title>"    + "<body><h1>" + "Hello Jersey" + "</body></h1>" + "</html> ";
	  return p;
  }
  
	@POST
	@Path("/psj")
	@Consumes(MediaType.APPLICATION_JSON)
	//@Consumes("application/json")
    @Produces({MediaType.TEXT_PLAIN})
	public Response createTrackInJSON(Pedido p) {
	//public String createTrackInJSON(Pedido p) throws Exception {
		System.out.println("DENTRO POST");
		String result="";
		try
		{
			result = "cliente : " + p.cliente;
		}catch(Exception e)
		{
			System.out.println("ERROR POST");
			return null;
		}
		
		return Response.status(201).entity(result).build();
		//return "OK";
	}
	
} 