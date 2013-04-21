package EstruturasDados;

public class Inteiro {
	
	int valor;
	
	public Inteiro(int valor){
		this.valor = valor;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}
	
	public void incrementar(){
		valor++;
	}
}
