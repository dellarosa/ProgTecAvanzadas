package Framework;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import Annotation.Get;
import Annotation.Path;
import Annotation.Post;
import Annotation.Servicio;
import Archivos.Archivo;
import Archivos.ArchivoJava;

public class AnalizadorJava {
	private String workspace = null;
	private String paquete = null;
	private String paqueteDir = null;
	private String nombre = null;
	private String proyecto = null;
	private ArchivoJava file = null;
	
	public AnalizadorJava(String archivo){
		int iAux = 0;
		int iAux2 = 0;
		
		System.out.println("\n\n\n*********************\n"+archivo);
		
		this.workspace = Configuracion.getInstancia().getWorkspace();
		this.proyecto = Configuracion.getInstancia().getProyectoOrigen();

		iAux  = archivo.lastIndexOf('\\');
		iAux2 = archivo.lastIndexOf('.');
		
		this.nombre = archivo.substring(iAux +1, iAux2);
		iAux2 = (Configuracion.getInstancia().carpetaOrigenBIN()).length();
	//	System.out.println("ARchivo: " + this.nombre + "LEN: "+ iAux + "LEN 2: "+iAux2);
		
		if(iAux>iAux2)
		{	
			this.paqueteDir = archivo.substring(iAux2, iAux);
			this.paquete = paqueteDir.replace('\\', '.');
		}
		else
		{
			this.paqueteDir = "";
			this.paquete = "";
		}
		
		
		System.out.println("Procesando archivo: " + this.nombre + " PAQUETE: "+paquete);		
	}

	public void compilar(){
		
		String archivoOUT=null;
		String archivoIN =null;
		
		try
		{
			
			if(!paqueteDir.equals(""))
			{
				archivoIN = Configuracion.getInstancia().carpetaOrigenSRC() + paqueteDir + "\\" + nombre + ".java";
			
				archivoOUT = Configuracion.getInstancia().carpetaOrigenBIN() + paqueteDir + "\\" + nombre + ".class";
				System.out.println("Compilando archivo: " + archivoIN + "\n a:" + archivoOUT);
			}else
			{
				archivoIN = Configuracion.getInstancia().carpetaOrigenSRC() +  nombre + ".java";
				
				archivoOUT = Configuracion.getInstancia().carpetaOrigenBIN() + nombre + ".class";
				System.out.println("Compilando archivo: " + archivoIN + "\n a: " + archivoOUT);
			}
		}catch(Exception e)
		{
			System.out.println("ERROR OBTENIENDO SRC: "+e);
		}
//		TODO ESTO NO ANDA!!!!!
//		
//		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
//		StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
//		Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromStrings(Arrays.asList("C:\\Ciudad.java"));
//		JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits);
//		boolean success = task.call();
//		try {
//			fileManager.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}


//TODO 	Esto solo copia el compilado del proyecto de origen hay que sacarlo
//		Si es que el proyecto Origen está compilado
		String temp=null;
		if(!paqueteDir.equals(""))
		{
		
			 temp= Configuracion.getInstancia().carpetaOrigenBIN() + paqueteDir + "\\" + nombre + ".class";
		}
		else
		{
			temp = Configuracion.getInstancia().carpetaOrigenBIN() + nombre + ".class";
		}
		
		Archivo file = new Archivo(temp);
		file.copiarArchivo(archivoOUT);
		
		//System.out.println("TEMP: "+temp);
	}
	
	//public Class<?> instanciar(){
	public Class instanciar(){
		//Class<?> ret = null;
		Class ret = null;
		URLClassLoader cl = null;
		URL[] urlList = new URL[1];
		try 
		{
//	TODO	Una vez que compile ... aca tiene que ir el proyecto de destino 
			String strFile=workspace + "\\" + proyecto;
			
			System.out.println("\n**STRFILE: "+strFile);
			
			File file = new File(strFile);
		/*
			if(file==null)
				System.out.println("FILE NULL");
			else
				System.out.println("URL: "+file.toURI().toURL());
			*/
			urlList[0] = new URL(file.toURI().toURL(),"bin\\");
			
			System.out.println("URL LIST: "+urlList[0]);
			
			cl = new URLClassLoader(urlList);
			if(cl==null)
				System.out.println("CL NULL");
			
			System.out.println("paquete: "+paquete);
			System.out.println("NOMBREPAC: "+nombre);
			
			System.out.println("GETFILE: "+urlList[0]+nombre+".class" );
			File file2=null;
			if(!paquete.equals(""))
				file2 = new File(strFile+"//"+"bin//"+paquete+"//"+nombre+".class");
			else
				file2= new File(strFile+"//"+"bin//"+nombre+".class");
				
			
			//File file2 = new File(urlList[0]+nombre+".class");
			System.out.println("\n**PATH: "+ file2.getAbsolutePath() +" -\nLEN: "+file2.length() );
			if(file2.length()<=0)
				return null;
			
			
			if(!paquete.equals(""))				
				ret = cl.loadClass(paquete + "." + nombre);
			else
			{	
				//ret = cl.loadClass("default package" +"." + nombre);
				ret = cl.loadClass(nombre);
			}
		} catch (MalformedURLException | ClassNotFoundException e) {
			System.out.println("****Error al instanciar la clase: ");
			e.printStackTrace();
		} finally{
			/*try {
				if (cl != null) 
				{	;//cl.close();					
				}
				
			} catch (IOException e) {
				System.out.println("EXCPTION INSTANCIA: "+e);
				e.printStackTrace();
			}
			*/
			System.out.println("PASEFIN");
		}
	
		return ret;
	}
	
	private void annotationPOST(Method metodo){
		System.out.println("POST");
		int iAux = 0;
		
		// Agregando Metodo
		file.addMetodo(file.visibilidadPublic(), metodo.getReturnType(), metodo.getName(),"@POST");
		
		// Agregando Argumentos
		iAux = 0;
		for (Class<?> args : metodo.getParameterTypes()){
			file.addAgrumento(metodo.getName(), args, ("arg" + iAux));
			iAux++;
		}
		// Agregando codigo
		String cod = "";
		if(metodo.getReturnType() != void.class) cod += "return ";
		
		cod += "original." + metodo.getName() + "(";
		cod += generarArgumentos(iAux-1, "arg");
		cod += ");";
		file.addCodigo(metodo.getName(),  cod);
		
	}
	
	public void annotationGet(Method metodo){
		System.out.println("GET");
		//TODO ver que hace
	}
	public void annotationPath(Method metodo){
		System.out.println("PATH");
		//TODO ver que hace
	}
	private void analizarMetodo(Method metodo){
		System.out.println("Analizando metodo: " + metodo.getName());
		for (Annotation anotacion : metodo.getDeclaredAnnotations()){
			if (anotacion instanceof Post)
				annotationPOST(metodo);
			else if (anotacion instanceof Get)
				annotationGet(metodo);
			else if (anotacion instanceof Path)
			{	
				annotationPath(metodo);
				System.out.println("PATH");
			}
			else 
				System.out.println("Anotacion desconocida: " + anotacion);
		}
	}
	
	private String generarArgumentos(int cantidad, String nombre){
//		genera ej.: "arg0, arg1, etc"
		String cod = "";
		boolean first = true;
		for(; cantidad >= 0; cantidad--){
			if (!first) cod = ", " + cod;
			else first = false;
			cod = nombre + cantidad + cod;
		}
		return cod;
	}

	private void generarConstructores(Constructor<?>[] constructores){
		for (Constructor<?> constr: constructores){
			file.addCreacional();
			int iAux = 0;
			for (Class<?> tipo: constr.getParameterTypes()){
				file.addAgrumento(file.getNombre(), tipo, "arg" + iAux);
				iAux++;
			}
			file.addCodigo(file.getNombre(), "original = new " + nombre + "(" + generarArgumentos(iAux-1, "arg") + ");");
		}
	}
	
	//public void procesar(Class<?> clase){
	public void procesar(Class clase){
//	TODO	Si no hay mas anotaciones en la clase mas que service se puede reemplazar
//			tanto el for como el if siguientes!!!!
//			if (clase.isAnnotationPresent(Servicio.class)){}
		
		System.out.println("NAMECLASS: "+clase.getName());
		
		for (Annotation anotacion : clase.getAnnotations()){
			if (anotacion instanceof Servicio){
				try
				{
					String strArchivojava=clase.getSimpleName() + "Serve";
					System.out.println("ARCHIVOJAVA: "+strArchivojava+" PAQUETE:"+Configuracion.getInstancia().getPaqueteGenerado());
					file = new ArchivoJava(strArchivojava, Configuracion.getInstancia().getPaqueteGenerado());
					
					file.addInclude(clase.getName());
					//------annotations lib
					file.addInclude("javax.ws.rs.GET");
					file.addInclude("javax.ws.rs.POST");
					file.addInclude("javax.ws.rs.Path");
					
					file.addInclude(clase.getName());
					file.addVariable(file.visibilidadPrivate(), clase, "original", null);
					file.addCreacional();				
					generarConstructores(clase.getConstructors());
				}catch(Exception exi)
				{
					System.out.println("ANOT EXCEPTION: "+exi);
				}
				
				try
				{
					System.out.println("FIELDS LEN: "+clase.getDeclaredFields().length);
					System.out.println("METODOS LEN: "+clase.getMethods().length);
				
					for (Method metodo: clase.getDeclaredMethods())
						analizarMetodo(metodo);
				}catch(Exception ex)
				{
					System.out.println("METODOS EXCEPTION: "+ex);
				}
				file.generarJava();
			}
			
		}
	}

	public void copiarArchivoDestino()
	{
		
		Configuracion conf = Configuracion.getInstancia();
		
		//System.out.println("{copiarArchivoDest} - PAK: "+paqueteDir + " - NOMBRE: "+nombre);		
		String origen = conf.carpetaOrigenSRC() + paqueteDir + "\\" + nombre + ".java";
		String destino = conf.carpetaDestinoSRC() + paqueteDir + "\\" + nombre + ".java";		
		//System.out.println("{copiarArchivoDest} - ORIGEN: "+origen + " - DESTIINO: "+destino);		
		Archivo file = new Archivo(origen);
		
		file.copiarArchivo(destino);
	}
}
