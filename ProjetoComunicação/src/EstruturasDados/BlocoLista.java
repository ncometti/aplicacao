package EstruturasDados;

public class BlocoLista {

	private BlocoLista next;
	private Object conteudo;
	
	public BlocoLista(Object conteudo){
		next = null;
		this.conteudo = conteudo;
	}

	public BlocoLista getNext() {
		return next;
	}

	public void setNext(BlocoLista next) {
		this.next = next;
	}

	public Object getConteudo() {
		return conteudo;
	}

	public void setConteudo(Object conteudo) {
		this.conteudo = conteudo;
	}
}
