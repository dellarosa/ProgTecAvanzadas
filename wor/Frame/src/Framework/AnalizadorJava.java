package Framework;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import Annotation.Consumes;
import Annotation.Get;
import Annotation.Path;
import Annotation.Post;
import Annotation.Servicio;
import Archivos.ArchivoEscritura;
import Archivos.ArchivoJava;
import Archivos.ArchivoLectura;

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
		this.proyecto = Configuracion.getInstancia().getProyectoDestino();

		iAux  = archivo.lastIndexOf(File.separatorChar);
		iAux2 = archivo.lastIndexOf('.');
		
		this.nombre = archivo.substring(iAux +1, iAux2);
		iAux2 = (Configuracion.getInstancia().carpetaOrigenBIN()).length();
	//	System.out.println("ARchivo: " + this.nombre + "LEN: "+ iAux + "LEN 2: "+iAux2);
		
		if(iAux>iAux2)
		{	
			this.paqueteDir = archivo.substring(iAux2, iAux);
			this.paquete = Configuracion.getInstancia().Carpeta2Pack(paqueteDir);
		}
		else
		{
			this.paqueteDir = "";
			this.paquete = "";
		}
		
		
		System.out.println("Procesando archivo: " + this.nombre + " PAQUETE: "+paquete);		
	}

	public static void compilar(ArrayList<String> javaSTRFiles, String destino){
//		boolean success = false;
		
		System.out.println("PASE Compilar Lista: "+javaSTRFiles.size());
		System.out.println("PATH DESTINO CLASS:  "+destino);
		try {
			ArrayList<String> compileOptions = new ArrayList<String>();
			compileOptions.add("-d");
			compileOptions.add(destino);
			
			Iterable<String> compilationOptions = compileOptions;
			
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			//System.out.println("PASE Java Compiler");
			DiagnosticCollector<JavaFileObject> diagnostics=null;
			diagnostics = new DiagnosticCollector<>();
			//System.out.println("PASE Java Diagnostic");
			StandardJavaFileManager fileManager=null;
			
			if(compiler==null)
				System.out.println("COMPILER Diagnostic NULL");
			else
				fileManager = compiler.getStandardFileManager(diagnostics, null, null);
			
			//System.out.println("PASE file manager");
			
			Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromStrings(javaSTRFiles);
			JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, compilationOptions, null, compilationUnits);

//			success = task.call();
			task.call();
			fileManager.close();
			System.out.println("Compilado terminado");
	
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Fallo compilar: "+e.toString());
		}
		
	}
	
	@SuppressWarnings({ "resource"})
	public Class<?> instanciar(){
		Class<?> ret = null;
		URLClassLoader cl = null;
		URL[] urlList = new URL[1];
		Class<?> urlClass = null;
		
		try
		{
			String strFile=workspace + File.separatorChar + proyecto+"/src/main/java/";
			System.out.println("\n**STRFILE: "+strFile);
			
			File file = new File(strFile);
			
			URL url = file.toURI().toURL();          // file:/c:/myclasses/
		    URL[] urls = new URL[]{url};
	
		    // Create a new class loader with the directory
		    ClassLoader cll = new URLClassLoader(urls);
	
		    // Load in the class; MyClass.class should be located in
		    // the directory file:/c:/myclasses/com/mycompany
		    Class<?> cls = cll.loadClass("tap.generated.code."+nombre);
		    return cls;
		} catch (MalformedURLException | ClassNotFoundException e) {
					
					System.out.println("****Error al instanciar la clase: ");
					e.printStackTrace();
		}
		return null;
		/*
		try 
		{
			paquete="/src/main/java/tap/generated/code";	//hardcodeado
			//paquete="/tap/generated/code";	//hardcodeado
			//String strFile=workspace + File.separatorChar + proyecto+"\\src\\main\\java\\";
			
			//System.out.println("\n**FILESEPARATOR: "+File.separatorChar);
			String strFile=workspace + File.separatorChar + proyecto+"/src/main/java/";
			System.out.println("\n**STRFILE: "+strFile);
			
			File file = new File(strFile);
		  
			//paquete="src/main/java";	//hardcodeado
			//urlList[0] = new URL(file.toURI().toURL(),"bin" + File.separatorChar);
			
			//urlList[0] = new URL(file.toURI().toURL(),paquete + File.separatorChar);
			//urlList[0] = new URL(file.toURI().toURL(),paquete+File.separatorChar);
			urlList[0] = file.toURI().toURL();
			System.out.println("URL LIST: "+urlList[0]);			
			cl = new URLClassLoader(urlList);
			//cl = new URLClassLoader(urlList, getClass().getClassLoader());
//			if (cl == null) System.out.println("Class loader NULL");
			
			System.out.println("paquete: "+paquete);
			System.out.println("NOMBREPAC: "+nombre);
			
			System.out.println("GETFILE: "+urlList[0]+nombre+".class" );
			File file2=null;
			char sep = File.separatorChar;
			if(!paquete.equals(""))
				//file2 = new File(strFile + sep + "bin" + sep + paquete + sep + nombre + ".class");
				file2 = new File(strFile + sep  + paquete + sep + nombre + ".class");
			else
				file2= new File(strFile + sep +  nombre + ".class");
				//file2= new File(strFile + sep + "bin" + sep + nombre + ".class");
				
			
			//File file2 = new File(urlList[0]+nombre+".class");
			System.out.println("\n**PATH: "+ file2.getAbsolutePath() +" -\nLEN: "+file2.length() );
			if(file2.length()<=0)
			{
				System.out.println("****EL .CLASS ES 0 ");
				return null;
			}
			
			paquete="tap.generated.code";
						
			System.out.println("POR BUSCAR LOAD CLASS: "+paquete + "." + nombre);
			if(!paquete.equals(""))				
			{	
				ret = cl.loadClass(paquete + "." + nombre);
				//ret = cl.loadClass(paquete + "." + nombre+".class");
				//ret = cl.loadClass(paquete + "." + "Pedido");
				//ret = cl.loadClass("C:\\Documents and Settings\\f.dellarosa.PPAR-PC12\\Mis documentos\\_DropboxOLD\\Public\\UP\\4 Año 1 Cuatri\\Tecnicas avanzadas de prog\\Rposi\\ProgTecAvanzadas\\trunk\\wor\\RESTServer\\src\\main\\java\\tap\\generated\\code" + "\\Pedido.class");
				//ret = cl.loadClass(nombre);
			}
			else
			{	
				ret = cl.loadClass(nombre);
			}
		} catch (MalformedURLException | ClassNotFoundException e) {
			
			System.out.println("****Error al instanciar la clase: ");
			e.printStackTrace();
		} finally{			
			System.out.println("PASEFIN");
		}
	
		//return urlClass;
		return ret;
		*/
	}
	
	private void annotationPOST(Method metodo){
		System.out.println("POST");
		int iAux = 0;
		
		// Agregando Metodo
		//file.addMetodo(file.visibilidadPublic(), metodo.getReturnType(), metodo.getName(),"@POST");
		file.addMetodo(file.visibilidadPublic(), metodo.getReturnType(), metodo.getName(),"@POST\n@Path(\"/psj\")\n@Consumes(MediaType.APPLICATION_JSON)\n@Produces(MediaType.TEXT_PLAIN)");
		
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
		int iAux = 0;
		
		// Agregando Metodo
		file.addMetodo(file.visibilidadPublic(), metodo.getReturnType(), metodo.getName(),"@GET\n@Path(\"/json\")\n@Produces(MediaType.APPLICATION_JSON)\n");
		
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
			//for (Class<?> tipo: constr.getParameterTypes()){
			for (Class<?> tipo: constr.getParameterTypes()){
				file.addAgrumento(file.getNombre(), tipo, "arg" + iAux);
				iAux++;
			}
			file.addCodigo(file.getNombre(), "original = new " + nombre + "(" + generarArgumentos(iAux-1, "arg") + ");");
		}
	}
	
	//public void procesar(Class<?> clase){
	public void procesar(Class<?> clase){
//	TODO	Si no hay mas anotaciones en la clase mas que service se puede reemplazar
//			tanto el for como el if siguientes!!!!
//			if (clase.isAnnotationPresent(Servicio.class)){}
		
		System.out.println("NAMECLASS: "+clase.getName());
		
		for (Annotation anotacion : clase.getAnnotations()){
			if (anotacion instanceof Servicio){
				try
				{
					Configuracion Conf = Configuracion.getInstancia();
					String strArchivojava=clase.getSimpleName() + "Serve";
					System.out.println("ARCHIVOJAVA: "+strArchivojava+" PAQUETE:"+Conf.getPaqueteGenerado());
					file = new ArchivoJava(strArchivojava, Conf.getPaqueteGenerado());
					//file = new ArchivoJava(strArchivojava, "tap.generated.server");
					
					//file.addInclude(Conf.Carpeta2Pack(Conf.carpetaDestinoSRC()));
					//file.addInclude(clase.getName());
					//------annotations lib
					file.addInclude("tap.generated.code.*");
					file.addInclude("javax.ws.rs.GET");
					file.addInclude("javax.ws.rs.POST");
					file.addInclude("javax.ws.rs.Path");
					file.addInclude("javax.ws.rs.Consumes");
					file.addInclude("javax.ws.rs.core.MediaType");
					file.addInclude("javax.ws.rs.Produces");
					
					//file.addInclude(clase.getName());
					
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

	public static void copiarArchivosDestino(ArrayList<String> lista, String carpetaDestino){
		String destino = null;
		String linea = null;
		String paquete = null;
		System.out.println("Destino: "+carpetaDestino);
		//System.out.println("PATH COPIAR: "+lista.get(1));
		for (String origen: lista){
			try{
				System.out.println("Origen: "+origen);
				ArchivoLectura in = new ArchivoLectura(origen);
				
				destino = carpetaDestino + File.separatorChar + in.getNombreCompleto();				
				
				//System.out.println("COPIANDO ARCHIVO con (" + (linea.substring(0, 7)) + ") de "+ origen + "-->" + destino);
				System.out.println("COPIANDO ARCHIVO "+ destino);
				ArchivoEscritura out = new ArchivoEscritura(destino, false);
				
				out.escribir("package " + "tap.generated.code"+";");
				while(!in.eof()){
					linea = in.getLineaActual();
					
					if(linea.length()>7)
					{
						//System.out.println("LINEA: "+ linea);
						if ((linea.substring(0, 7)).equalsIgnoreCase("package")) 
							//linea = "package " + "tap.generated.code"+";";
						//linea = "package " + paquete+";";
							linea = " ";
						
						
					}
					linea+="\n";
					out.escribir(linea);
					in.next();
				}
				in.cerrar();
				out.cerrar();
			}catch(IOException e){
//				TODO ver que va aca!!!!
				System.out.println("NO ENCONTRE ALGO");
			}

		}
	}
}
