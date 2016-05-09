import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Annotation.Get;
import Annotation.Path;
import Annotation.Post;
import Annotation.Servicios;
import Delivery.DeliveryLogic;
import Serialize.Seri;

//@Path(path = "/Delivery")
public class Fmk {

	public Request r=new Request(); 
	private List<String> lstJasonReq=null; 
	//public static  <D extends Dao<T, ?>, T> D
	//public boolean enviar( Class<T> clazz)
	Object obj=null;
	boolean encontre=false;
	//@Path(path = "/")
	public boolean superFrame( String folder)
	{
		int x=0;
		
		System.out.println(folder);
		
		FindFile ff=new FindFile();
		File[] files=ff.finder(folder);
		
		/*	
		bl.saveCustomer(customer);
		
		String s = "Se grabo el cliente: ";
		s += customer.getFirstName() + " " + customer.getLastName();
		
		return Response.status(201).entity(s).build();	//201 - Created
		*/
		if(files==null)
		{
			System.out.println("NULLLLLLLLLLL");
			return false;
		}
		while(x<files.length)
		{
			
			//System.out.println("FILE: "+files[x].getName() + "   " + files[x].getName().substring(0, (files[x].getName().length()-5)));
			
			Class<?> cl = null;
				
			try {
				//cl=Class.forName("D:\\workspace\\Framework\\src\\Delivery\\DeliveryLogic.java");
				//cl=Class.forName("Delivery"+"DeliveryLogic");
				cl=Class.forName("Delivery."+files[x].getName().substring(0, (files[x].getName().length()-5)));
				//cl=Class.forName( files[x].getName().substring(0, (files[x].getName().length()-5)));
				
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return false;
			}
			//------------- RECORRER PATH PARA BUSCAR CLASES
			
			//obj=Class.forName("Pedido");	//Harcodead, futuramente dinamico
			
			
			String strClass="@Path(\"/Delivery\")public Class DeliveryService{ \n"+ 
			"private "+ cl.getName()+" dl = new"+ cl.getName()+"(); ";	
			
			
			
			lstJasonReq=new ArrayList<String>();
			
			try
			{
				encontre=false;
			//	System.out.println("ENTRE");
				for(Annotation a : cl.getAnnotations())
				{
					//a.annotationType();
					if(a instanceof Servicios)
					{
						encontre=true;
						break;
					}
					System.out.println(a);
					
				}
				if(encontre==false)
					continue;
				
				//------------------------------------------------------------
				for(Method f : cl.getDeclaredMethods())
				{
					System.out.println("Fields: "+ f.getName().toString());					
					for(Annotation a : f.getDeclaredAnnotations())
					{
						//System.out.println("anotatnion: "+ ((Seri) a).());
						//System.out.println(a);						
						if(a instanceof Post)					
						{
							strClass+="\n public "+f.getReturnType().getName()+" "+f.getName()+"(";
							
							int i=0;
							
							for(i=0;i<f.getParameterCount();i++)
							{
								TypeVariable<Method>[] tvs =f.getTypeParameters();
								
								strClass+=tvs[i].getName()+" a"+String.valueOf(i);
								
							}
							
							strClass+="){";
							
						//	lstJasonReq.add(f.getName().toString() + " = " + f.get(obj));
						//	System.out.println(f.getName().toString() + " = " + f.get(obj));
							
						}else if(a instanceof Get)
						{
							strClass+="";
						}
						strClass+="dl."+f.getName().toString();
						strClass+="}";
					}
					
				}
				//-------------------------------------------------------------------
				//Field field=null;
				for(Field f : cl.getDeclaredFields())
				{
					System.out.println("Fields: "+ f.getName().toString());
					//System.out.println("Value: "+ f.get(obj));
					
	//				Annotation[] annotations = f.;
					
				
					for(Annotation a : f.getDeclaredAnnotations())
					{
						//System.out.println("anotatnion: "+ ((Seri) a).());
						//System.out.println(a);
						
						if(a instanceof Seri)					
						{
							//System.out.println("Entre");
							
						//	lstJasonReq.add(f.getName().toString() + " = " + f.get(obj));
						//	System.out.println(f.getName().toString() + " = " + f.get(obj));
							
						}
						
					}
					
				}
				//---------------------------- FORMATEAR TRAMA EN JASON...
				
				
				//------------------------- ENVIAR MEDIANTE REQUEST HTTP
				
				
				
				//------------------------ RECIBIR MEDIANTE RESPONSE HTTP
				
			}catch(Exception e)
			{
				System.out.println("Excetion: "+e);
				
			}
		
			System.out.println("CLASS: "+strClass);
			
			/*
			try {
			    // Create the empty file with default permissions, etc.
			    Files.createFile(file);
			} catch (FileAlreadyExistsException x) {
			    System.err.format("file named %s" +
			        " already exists%n", file);
			} catch (IOException x) {
			    // Some other sort of failure, such as permissions.
			    System.err.format("createFile error: %s%n", x);
			}
			*/
			x++;
		}
		
		/*
		Method enviar= null;		//Tiene que tener el nombre exacto sino no funciona
				
		for(Method m : clazz.getMethods())
		{
			
			if(m.getName().equals("enviando"))
			{
				enviar=m;
			}
		}
		
		for(Annotation a : enviar.getDeclaredAnnotations())
		{
			System.out.println(a);
			
		}
		*/
		return true;
	}

}
