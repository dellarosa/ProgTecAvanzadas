package Archivos;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ArchivoEscritura extends Archivo {
	protected BufferedWriter bw = null;
	protected FileWriter fw = null;

	private void cargar(boolean append) throws IOException{
		fw = new FileWriter(archivo, append);
		bw = new BufferedWriter(fw);
	}
	
	public ArchivoEscritura(String nuevo, boolean append) throws IOException {
		super(nuevo);
		Archivo dir = new Archivo(this.getPath());
		if ((!dir.existe()) || (!dir.esDirectorio()))
			dir.crear();
		this.cargar(append);
	}

	public void escribir(String linea) throws IOException{
		bw.write(linea);
		bw.flush();
	}
	
	public void cerrar() throws IOException{
		fw.close();
		bw.close();
	}
}
