package paquete;


public class Calculadora {
	
	private int numero;
	protected int resultado;
	int memoria;
	public int otro;
	static int unomas;
	final int yyy = 11;
	private final int xxx=2;
	
	public Calculadora() {
		
	}
	public Calculadora(int inicial) {
		this.resultado = inicial;
	}
	
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	
	@MyAnnotation(usable = true)
	public int getResultado() {
		return resultado;
	}

	public void setResultado(int resultado) {
		this.resultado = resultado;
	}
	
	@MyAnnotation(usable = true)
	public int sumar(int numero) {
		resultado = resultado + numero;
		this.numero = numero;
		return resultado;
	}
	
	public void limpiar() {
		resultado = 0;
		numero = 0;
	}
	
	private void escondido() {
		System.out.println("Me encontraron!!!");
	}

}

class MiniCalc extends Calculadora {
	public int getResultado() {
		return super.resultado;
	}
}
