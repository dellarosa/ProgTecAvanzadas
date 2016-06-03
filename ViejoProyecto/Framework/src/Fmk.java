import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Annotation.Get;
import Annotation.Path;
import Annotation.Post;
import Annotation.Servicios;
import Delivery.DeliveryLogicmism;
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
	String PATHORIGINAL=null;
	String folderDest=null;
	String paqueteDest=null;
	
	public Fmk(String orig,String dest,String paqDest) {
		// TODO Auto-generated constructor stub
		PATHORIGINAL=orig;
		folderDest=dest;
		paqueteDest=paqDest;
	}


	public boolean superFrame( String folder)
	{
		int x=0;
		
		System.out.println(folder);
		String realPathConPaquete=null;
		
	//	FindFile ff=new FindFile();
	//	File[] files=ff.finder(folder);
		
		File folders = new File(folder);
		File[] files = folders.listFiles();
		
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
		
		System.out.println("FILES: "+files.length);
		
		while(x<files.length)
		{	
			if(files[x].isDirectory())
			{
				realPathConPaquete=null;
				realPathConPaquete=folder+"\\"+files[x].getName();
				System.out.println("REALPATH: "+realPathConPaquete);
				superFrame( realPathConPaquete);
			}	
			
			String classname= files[x].getName().substring(0, (files[x].getName().length()-6));
			
			System.out.println(" \nFILE: "+files[x].getName() + "   " + classname);
			
			if(!(classname.equals("DeliveryLogic")))
			{
				x++;
				continue;
			}
				
			//Class<?> cl = null;
			Class cl = null;
			System.out.println("\nPATH:"+System.getProperty("user.dir"));
					
			try {
				
				File fd=new File(PATHORIGINAL);
				URL[] cp = {fd.toURI().toURL()};				
				URLClassLoader urlcl = new URLClassLoader(cp);
				cl= urlcl.loadClass("Entidades."+classname);
				
				System.out.println(cl.toString());
				//cl=Class.forName( files[x].getName().substring(0, (files[x].getName().length()-5)));
				
				
			} catch (ClassNotFoundException | MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("Exception URL: "+e1);
				return false;
			}
			//------------- RECORRER PATH PARA BUSCAR CLASES
			
			//obj=Class.forName("Pedido");	//Harcodead, futuramente dinamico
			
			
			//String strClass="@Path(\"/Delivery\")public Class DeliveryService{ \n"+
			String strClass="package "+paqueteDest +";\n" +"public class DeliveryService{ \n"+
			"private "+ cl.getName()+" dl = new "+ cl.getName()+"(); \n";	
			
			
			
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
				{
					x++;
					continue;
				}
				
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
							try
							{
								System.out.println("Cantidad: "+f.getParameterCount());
								for(i=0;i<f.getParameterCount();i++)
								{
									
									Type[] tvs =f.getGenericParameterTypes();
									
									System.out.println("TipoParametro: "+f.getGenericParameterTypes());
									
									strClass+=tvs[i].getTypeName()+" a"+String.valueOf(i);
									if((i+1) <f.getParameterCount())  strClass+=", ";									
									
								}
							}catch(Exception ex)
							{
								System.out.println("Excetion Paramets: "+ex);
								return false;
							}
								
							strClass+="){\n";
						
							strClass+="return dl."+f.getName().toString()+"(";
							int j=0;
							for(j=0;j<i;j++)
							{
								if((j+1) <i) 
									strClass+="a"+String.valueOf(j)+", ";
								else
									strClass+="a"+String.valueOf(j);
								
							}
								
							strClass+=");";
							
							strClass+="\n}";
							
						//	lstJasonReq.add(f.getName().toString() + " = " + f.get(obj));
						//	System.out.println(f.getName().toString() + " = " + f.get(obj));
							
						}else if(a instanceof Get)
						{
							strClass+="";
						}
						
					}
					
				}
				/*
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
				*/
				//---------------------------- FORMATEAR TRAMA EN JASON...
				
				
				//------------------------- ENVIAR MEDIANTE REQUEST HTTP
				
				
				
				//------------------------ RECIBIR MEDIANTE RESPONSE HTTP
				strClass+="\n}";
			
				System.out.println("CLASS: "+strClass);
				
				try {
					//File file = new File("DeliveryService.java");
					File file = new File(folderDest+"\\"+"DeliveryService.java");
					FileWriter fileWriter = new FileWriter(file);
					fileWriter.write(strClass);					
					fileWriter.flush();
					fileWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
				
			}catch(Exception e)
			{
				System.out.println("Excetion Gral: "+e);
				
			}
		
			
			
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
