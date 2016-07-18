package Archivos;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.net.*;

public class Archivo {
	protected File archivo;
	private FileInputStream fileInputStream;
	private FileOutputStream fileOutputStream;

	public String getPath(){return archivo.getParentFile().getAbsolutePath();}
	public String getNombreCompleto(){return archivo.getName();}
	public String getCarpeta(){return archivo.getParentFile().getName();}
	public boolean existe(){return archivo.exists();}
	public boolean esDirectorio(){return archivo.isDirectory();}
	public boolean esArchivo(){return archivo.isFile();}
	public void borrar(){archivo.delete();}
	public void crear(){archivo.mkdirs();}
	
	public List<Archivo> listaDeArchivos(){
		File[] temp = archivo.listFiles();
		List<Archivo> ret = new ArrayList<Archivo>();
		for(File aux: temp ) ret.add(new Archivo(aux));
		return ret;
	}

	public Archivo(File nuevo){
		this.archivo = nuevo;
	}
	
	public Archivo(String file){
		this.archivo = new File(file);
	}

	public String getExtencion(){
		String aux = archivo.getName();
		String ret = aux.substring(aux.indexOf('.') + 1);
		return 	ret;
	}
	
	public String getNombre(){
		String ret = archivo.getName();
		if (archivo.isFile())
			if (ret.indexOf('.') != 0)
				ret = ret.substring(0, ret.indexOf('.'));
		return 	ret;
	}
	
	public boolean moverArchivo(String nuevo){
		File destino = new File(nuevo);
		boolean ret = this.archivo.renameTo(destino);
		archivo = destino;
		return ret;
	}

	public Archivo copiarArchivo(String destino){
		Archivo ret = new Archivo(destino);
		copiarFile(ret.archivo);
		return ret;
	}

	private void copiarFile(File destinoFile){
		FileChannel origen = null;
		FileChannel destino = null;
	 
	    try {
	    	
	    	//System.out.println("****" + destinoFile.getAbsolutePath());
	    	if(!destinoFile.exists()) 
	    		destinoFile.createNewFile();

	    	fileInputStream = new FileInputStream(archivo);
	        fileOutputStream = new FileOutputStream(destinoFile);
			
	        origen = fileInputStream.getChannel();
			destino = fileOutputStream.getChannel();
	 
	        long count = 0;
	        long size = origen.size();              
	        while((count += destino.transferFrom(origen, count, size-count))<size);
	    }
	    catch(IOException e){
			e.printStackTrace();
	    }
	    finally {
			try {
		        if (origen != null)origen.close();
		        if (destino != null) destino.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    
	}
	
    public URL[] geturl() throws MalformedURLException{
    	System.out.println(archivo.toURI().toURL().toString());
    	URL[] ret = {archivo.toURI().toURL()};
    	return ret;
    }

    public ArrayList<String> listarArchivos(String extencion){
    	ArrayList<String> ret = null;
    	String sAux = null;
    	
    	if (archivo.isDirectory()){
    		ret = new ArrayList<String>();
    		
    		for (File arch: archivo.listFiles()){
    			if (arch.isDirectory()){
    				Archivo dir = new Archivo(arch.getAbsolutePath());
    				ret.addAll(dir.listarArchivos(extencion));
    			}
    			else{
    				sAux = arch.getAbsolutePath();
    				int iAux =sAux.lastIndexOf(".");
    				if (sAux.substring(iAux+1).endsWith(extencion))
   					ret.add(arch.getAbsolutePath());
    			}
    		}
    		
    	}
    	
    	
    	return ret;
    }

}