

import Archivos.Archivo;
import Framework.AnalizadorJava;
import Framework.Configuracion;

public class Inicio {

	public static void main(String[] args) {
		System.out.println("Inicio");
		procesar();
	}

	public static void procesar(){
		Configuracion conf = Configuracion.getInstancia();
//		Busco directorio
		String strDirO = conf.getWorkspace() + "\\" + conf.getProyectoOrigen() +
				"\\" + conf.getCarpetaJAVA();
		Archivo  fileDir = new Archivo(strDirO);
//		Listar todos los archivos .java
//		C/U
		for (String srt: fileDir.listarArchivos("java")){
			AnalizadorJava analizar = new  AnalizadorJava(srt);
//			copio a destino / java / paquete
			analizar.copiarArchivoDestino();

//TODO compilo a destino / class / paquete
// ESTO ES LO QUE NO PUEDO HACER!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			analizar.compilar();
			
//			levanto el archivo class y analizo
			Class<?> instancia = analizar.instanciar();
//				genero metodos y variables
//			genero el archivo java a proyecto destino / java / paquete generado
			analizar.procesar(instancia);
//(Podría compilar el archivo generado)
		}
//		copio el paquete generado a: proyecto frontend / java
//		genero servidor en el proyecto destino
//		genero cliente en el proyecto frontend
	}
}
