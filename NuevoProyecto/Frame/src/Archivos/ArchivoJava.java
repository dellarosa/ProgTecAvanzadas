package Archivos;
import java.io.IOException;
import java.util.ArrayList;

import Framework.Configuracion;

public class ArchivoJava {
	public enum visibilidad {Tprivate, Tpublic, Tprotected}
	protected class tipoArg {
		public Class<?> tipo = null;
		public String nomre = null;
		public String toString(){return this.tipo.getName() + " " + this.nomre;}
	}
	protected class tipoCons {
		public Class<?> tipo =null;
		public String nombre = null;
		public Object valor = null;
	}
	protected class tipoVariable {
		public visibilidad visible = visibilidad.Tpublic;
		public Class<?> tipo = null;
		public String nombre = null;
		public Object valor = null;
	}
	protected class tipoMetodo {
		public visibilidad visible = visibilidad.Tpublic;
		public Class<?> tipo = null;
		public String nombre = null;
		public ArrayList<String> codigo = null;
		public ArrayList<tipoArg> argumentos = null;
		public String toString(){return nombre;}
	}

	protected String workspace = null;
	protected String paquete = null;
	protected String paqueteDIR = null;
	protected String nombre = null;
	protected String proyecto = null;

	public ArchivoJava(String nombre, String paquete){
		workspace = Configuracion.getInstancia().getWorkspace();
		proyecto = Configuracion.getInstancia().getProyectoDestino();
		this.nombre = nombre;
		this.setPaquete(paquete); 
		System.out.println(nombre);
	}
	
	private ArrayList<String> includes = null;
	private ArrayList<tipoCons> constantes = null;
	private ArrayList<tipoVariable> variables = null;
	private ArrayList<tipoMetodo> metodos = null;
	private ArrayList<tipoMetodo> creacionales = null;
	private tipoMetodo lastCreacional = null;
	
	public String getProyecto() {return proyecto;}
	public void setProyecto(String proyecto) {this.proyecto = proyecto;}
	public String getWorkspace() {return workspace;}
	public void setWorkspace(String workspace) {this.workspace = workspace;}
	public String getPaquete() {return paquete;}
	public String getPaqueteDIR() {return paqueteDIR;}
	public String getNombre() {return nombre;}
	public void setNombre(String nombre) {this.nombre = nombre;}
	public visibilidad visibilidadPrivate(){return visibilidad.Tprivate;}
	public visibilidad visibilidadPublic(){return visibilidad.Tpublic;}
	public visibilidad visibilidadProtected(){return visibilidad.Tprotected;}

	public void setPaquete(String paquete) {
		this.paquete = paquete;
		paquete = paquete.replace('.', '\\');
		this.paqueteDIR = paquete;
		}
	
	public void setPaqueteDIR(String paqueteDIR) {
		this.paqueteDIR = paqueteDIR;
		paqueteDIR = paqueteDIR.replace('\\', '.');
		this.paquete = paqueteDIR;
		}
	
	public void addInclude(String nuevo){
		if (includes == null) includes = new ArrayList<String>();
		includes.add(nuevo);
	}
	
	private String getIncludes(){
		String ret = "// IMPORTS \n";
		if (this.includes != null)
				for(String aux: this.includes)
					ret += "import " + aux + ";\n";
		ret += "\n\n";
		return ret;
	}
	
	private void addConstante(tipoCons nuevo){
		if (constantes == null) constantes = new ArrayList<tipoCons>();
		constantes.add(nuevo);
	}
	
	public void addConstante(Class<?> clase, String nombre, Object valor){
		tipoCons nuevo = new tipoCons();
		nuevo.tipo = clase;
		nuevo.nombre = nombre;
		nuevo.valor = valor;
		this.addConstante(nuevo);
	}
	
	private String getConstantes(){
		String ret = "// CONSTANTES\n";
		if (this.constantes != null)
			for(tipoCons aux: constantes){
				ret += "\tstatic final " + aux.tipo.getName() + " " + aux.nombre;
				if (aux.tipo == String.class)
					ret += " = \"" + aux.valor + "\";\n";
				else ret += " = " + aux.valor + ";\n";
		}
		ret += "\n\n";
		return ret;
	}
	
	private void addVariable(tipoVariable nuevo){
		if (variables == null) variables = new ArrayList<tipoVariable>();
		variables.add(nuevo);
	}

	public void addVariable(visibilidad newVisibilidad, Class<?> clase, String nombre, Object valor){
		tipoVariable nuevo = new tipoVariable();
		nuevo.visible = newVisibilidad;
		nuevo.tipo = clase;
		nuevo.nombre = nombre;
		nuevo.valor = valor;
		this.addVariable(nuevo);
	}

	private String getVariables(){
		String ret = "// VARIABLES\n";
		if(this.variables != null)
			for(tipoVariable aux: this.variables){
				if (aux.visible == visibilidad.Tprivate)   ret += "\tprivate";
				if (aux.visible == visibilidad.Tprotected) ret += "\tprotected";
				if (aux.visible == visibilidad.Tpublic)    ret += "\tpublic";
				ret += " " + aux.tipo.getName() + " " + aux.nombre;
				if (aux.tipo == String.class)
					ret += " = \"" + aux.valor + "\";\n";
				else ret += " = " + aux.valor + ";\n";
			}
		ret+= "\n\n";
		return ret;
	}
	
	private void addMetodo(tipoMetodo nuevo){
		if (metodos == null) metodos = new ArrayList<tipoMetodo>();
		metodos.add(nuevo);
	}
	
	private void addCreacional(tipoMetodo nuevo){
		if (creacionales == null) creacionales = new ArrayList<tipoMetodo>();
		creacionales.add(nuevo);
		lastCreacional = nuevo;
	}
	
	public void addCreacional(){
		System.out.println(nombre);
		tipoMetodo nuevo = new tipoMetodo();
		nuevo.visible = this.visibilidadPublic();
		nuevo.tipo = void.class;
		nuevo.nombre = this.nombre;
		nuevo.codigo = new ArrayList<String>();
		nuevo.argumentos = new ArrayList<tipoArg>();
		this.addCreacional(nuevo);
	}
	
	private String getCreacional(){
		String ret = "";
		System.out.println("Creando metodo: " + nombre);
		
		for (tipoMetodo crear: this.creacionales){
			ret = "\tpublic " + this.nombre + "(";
			
			boolean first = true;
			for (tipoArg aux: crear.argumentos){
				if (!first) ret+= ", ";
				else first = false;
				ret+= aux;
			}
			ret += ") {\n";
			for (String aux2: crear.codigo){
				ret += "\t\t" + aux2 + "\n";
			}
			ret += "\t}\n\n";
		}
		return ret;
	}
		
	public void addMetodo(visibilidad newVisibilidad, Class<?> clase, String nombre){
		tipoMetodo nuevo = new tipoMetodo();
		nuevo.visible = newVisibilidad;
		nuevo.tipo = clase;
		nuevo.nombre = nombre;
		nuevo.codigo = new ArrayList<String>();
		nuevo.argumentos = new ArrayList<tipoArg>();
		this.addMetodo(nuevo);
	}
	
	public void addAgrumento(String metodo, Class<?> tipo, String nombre){
		tipoMetodo meto = null;
		if (metodo == lastCreacional.nombre)
			meto = lastCreacional;
		else 
			meto = this.buscarMetodo(metodo);

		System.out.println(meto.nombre);
		if (meto.argumentos == null) meto.argumentos = new ArrayList<tipoArg>();
		
		meto.argumentos.add(argumentoNuevo(tipo, nombre));
	}

	public void addCodigo(String metodo, String instr){
		tipoMetodo meto = null;
		if (metodo == lastCreacional.nombre)
			meto = lastCreacional;
		else 
			meto = this.buscarMetodo(metodo);
		
		if (meto.codigo == null) meto.codigo = new ArrayList<String>();
		System.out.println(instr);
		meto.codigo.add(instr);
	}
	
	private tipoMetodo buscarMetodo(String buscar){
		if (metodos != null)
			for (tipoMetodo aux: this.metodos)
				if (aux.nombre == buscar) return aux;

		return null;
	}
	
	private tipoArg argumentoNuevo(Class<?> tipo, String nombre){
		tipoArg ret = new tipoArg();
		ret.nomre = nombre;
		ret.tipo = tipo;
		return ret;
	}
	
	private String getMetodo(tipoMetodo proc){
		String ret = "";
		if (proc.visible == visibilidad.Tprivate)   ret += "\tprivate";
		if (proc.visible == visibilidad.Tprotected) ret += "\tprotected";
		if (proc.visible == visibilidad.Tpublic)    ret += "\tpublic";
		
		if (proc.tipo != null) ret += " " + proc.tipo.getName();
		ret += " " + proc.nombre + "(";
		
		System.out.println("Creando metodo: " + proc.nombre);
		
		boolean first = true;
		for (tipoArg aux: proc.argumentos){
			if (!first) ret+= ", ";
			else first = false;
			ret+= aux;
		}
		
		ret += ") {\n";
		
		for (String aux2: proc.codigo){
			ret += "\t\t" + aux2 + "\n";
		}
		ret += "\t}\n\n";
		return ret;
	}
	
	private String getMetodos(){
		String ret = "// METODOS\n";
		System.out.println("Generando metodos");
		if (this.metodos != null){
			for (tipoMetodo aux : this.metodos){
				ret += getMetodo(aux);
			}
		}
		return ret;
	}

	private String getComentarioAgregado(){
		return "/****************************************************************\n"
				+ "********************** CÓDIGO AUTOGENERADO **********************\n"
				+ "*****************************************************************\n"
				+ "*\n* Creado por: \n*\tBarzini, Sebastian\n*\tDellarosa Fabio\n*/\n\n";
	}
	
	
	public void generarJava(){
		ArchivoEscritura javaFile = null;
		String arch = null;
		try {
			System.out.println("Generando clase JAVA");
			// TODO ver que pasa cuando esta en un paquete dentro de otro paquete
			arch = this.workspace + "\\" + this.proyecto + "\\src\\";
			if (this.paqueteDIR != null)
				if (this.paqueteDIR != "")
					arch+= this.paqueteDIR + "\\";
			
			
			arch +=  this.nombre + ".java";
			javaFile = new ArchivoEscritura(arch, false);
			
			if (this.paqueteDIR != null)
				if (this.paqueteDIR != "")
					javaFile.escribir("package " + this.paquete + ";\n\n");
					javaFile.escribir(this.getComentarioAgregado());
					javaFile.escribir(getIncludes());
					javaFile.escribir("public class " + this.nombre + "{\n");
					javaFile.escribir(getConstantes());
					javaFile.escribir(getVariables());
					javaFile.escribir(getCreacional());
					javaFile.escribir(getMetodos());
					javaFile.escribir("}\n");
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				javaFile.cerrar();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
