package Archivos;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ArchivoLectura extends Archivo {
	protected FileReader fr = null;
	protected BufferedReader br = null;
	protected String linea = null;
	
	public boolean eof(){return (linea == null);}
	
	public void abrir() throws FileNotFoundException{
		try
		{
		
		fr = new FileReader(this.archivo);
		br = new BufferedReader(fr);
		
		//System.out.println("Size: "+fr.read());
		}catch(Exception e)
		{
			System.out.println("Exception: "+" no encontro archivo origen");
		}
	}
	
	public String getLineaActual(){return linea;}
	
	public String next() throws IOException{
		if (br != null)
			linea = br.readLine();
		return linea;
	}
	
	public void cerrar() throws IOException{
		fr.close();
		br.close();
	}

	public ArchivoLectura(String file) throws IOException {
		super(file);
		this.abrir();
		this.next();
	}
}
