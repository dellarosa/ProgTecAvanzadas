package Framework;
import java.io.IOException;

import Archivos.ArchivoLectura;

public class Configuracion {
	//static final String ARCHIVO_DE_CONFIGURACION = "D:\\workspace\\Frame\\src\\conf.ini";
	static final String ARCHIVO_DE_CONFIGURACION = "C:\\Documents and Settings\\"
			+ "f.dellarosa.PPAR-PC12\\Mis documentos\\_DropboxOLD\\Public\\UP\\4 Año 1 Cuatri\\Tecnicas avanzadas de prog\\Rposi\\ProgTecAvanzadas\\trunk\\NuevoProyecto\\Frame\\src\\conf.ini";
	private static Configuracion instancia = null;
	
	private String proyectoOrigen;
	private String proyectoDestino;
	private String workspace;
	private String carpetaJAVA;
	private String carpetaCLASS;
	private String paqueteGenerado;
	private String proyectoFE;
	
	public String getProyectoFE() {return proyectoFE;}
	public String getWorkspace() {return workspace;}
	public String getCarpetaCLASS() {return carpetaCLASS;}
	public String getPaqueteGenerado() {return paqueteGenerado;}
	public String getCarpetaJAVA() {return carpetaJAVA;}
	public String getProyectoOrigen() {return this.proyectoOrigen;}
	public String getProyectoDestino() {return this.proyectoDestino;}
	
	public static Configuracion getInstancia(){
		if(instancia == null) instancia = new Configuracion();
		return instancia;
	}
	
	public Configuracion(){
		cargarConfiguracion(ARCHIVO_DE_CONFIGURACION);
	}
	
	public String carpetaOrigenSRC(){
		return workspace + "\\" + proyectoOrigen + "\\" + carpetaJAVA + "\\";
	}
	
	public String carpetaDestinoSRC(){
		return workspace + "\\"  + proyectoDestino + "\\" + carpetaJAVA + "\\";
	}
	
	public String carpetaOrigenBIN(){
		return workspace + "\\"  + proyectoOrigen + "\\" + carpetaCLASS + "\\";
	}
	
	public String carpetaDestinoBIN(){
		return workspace + "\\"  + proyectoDestino + "\\" + carpetaCLASS + "\\";
	}
	
	public Configuracion(String arch){
		cargarConfiguracion(arch);
	}
	
	private void cargarConfiguracion(String arch){
		String linea = null;
		ArchivoLectura conf = null;
		int iAux;
		try {
			conf = new ArchivoLectura(arch);

			//leer el archivo
			while((linea = conf.next()) != null){
			
				// Ver que no sea una linea vacia
				if ((linea != null) && (linea.length() > 0)){
					//Ver que no sea un comentario sobre el archivo de configuracion
					if (linea.charAt(0) != '%'){
						//buscar el separador valor clave y separar
						iAux = linea.indexOf(" = ");
						if (iAux >= 0){
							System.out.print("Clave: ");
							System.out.print(linea.substring(0, iAux));
							System.out.print(", valor: ");
							System.out.println(linea.substring(iAux+3));
							
							//guardar el valor en la clave
							if (linea.startsWith("Carpeta origen")){
								this.proyectoOrigen = linea.substring(iAux+3);
							} else if (linea.startsWith("Carpeta destino")){
								this.proyectoDestino = linea.substring(iAux+3);
							}else if (linea.startsWith("WorkSapce")){
								this.workspace = linea.substring(iAux+3);
							}else if (linea.startsWith("CarpetaJava")){
								this.carpetaJAVA = linea.substring(iAux+3);
							}else if (linea.startsWith("CarpetaClass")){
								this.carpetaCLASS = linea.substring(iAux+3);
							}else if (linea.startsWith("proyecto FE")){
								this.proyectoFE = linea.substring(iAux+3);
							}else if (linea.startsWith("paquete generado")){
								this.paqueteGenerado = linea.substring(iAux+3);
							}
						}
						else{
							System.out.println("Error en la configuracion: ");
							System.out.println(linea);
						}
				}
				}
			}
			
		} catch (IOException e) {
			System.out.println("Error cargando configuración");
			e.printStackTrace();
		}finally{
			try {conf.cerrar();}
			catch (IOException e) {e.printStackTrace();}
		}
		
	}

}
