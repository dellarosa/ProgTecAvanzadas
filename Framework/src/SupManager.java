import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import Serialize.Seri;




public class SupManager {

	public Request r=new Request(); 
	private List<String> lstJasonReq=null; 
	//public static  <D extends Dao<T, ?>, T> D
	//public boolean enviar( Class<T> clazz)
	public boolean enviar( Object obj)
	{
		lstJasonReq=new ArrayList<String>();
		try
		{
			System.out.println("ENTRE");
			for(Annotation a : obj.getClass().getAnnotations())
			{
				//a.annotationType();
				System.out.println(a);
				
			}
			Field field=null;
			for(Field f : obj.getClass().getDeclaredFields())
			{
				System.out.println("Fields: "+ f.getName().toString());
				System.out.println("Value: "+ f.get(obj));
				
//				Annotation[] annotations = f.;
				
			
				for(Annotation a : f.getDeclaredAnnotations())
				{
					//System.out.println("anotatnion: "+ ((Seri) a).());
					//System.out.println(a);
					
					if(a instanceof Seri)					
					{
						System.out.println("Entre");
						
						lstJasonReq.add(f.getName().toString() + " = " + f.get(obj));
						System.out.println(f.getName().toString() + " = " + f.get(obj));
						
					}
					
				}
				
			}
		}catch(Exception e)
		{
			System.out.println("Excetion: "+e);
			
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
