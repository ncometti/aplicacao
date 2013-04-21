package EstruturasDados;

//class doido{
//	
//	int valor;
//	doido(int valor){
//		this.valor = valor;
//	}
//}

public class Lista {
	
//	public static void main(String[] args){
//		
//		Lista lista = new Lista();
//		doido tal1 = new doido(10);
//		doido tal2 = new doido(20);
//		doido tal3 = new doido(30);
//		
//		lista.inserir(tal1);
//		lista.inserir(tal2);
//		lista.inserir(tal3);
//		System.out.println(((doido)lista.head.getConteudo()).valor);
//		lista.remover(tal2);
//		System.out.println(((doido)lista.head.getConteudo()).valor);
//		lista.remover(tal1);
//		System.out.println(((doido)lista.head.getConteudo()).valor);
//		
//	}
	
	public BlocoLista head;
	private int size;
	
	public Lista(){
		size = 0;
		head = null;
	}
	
	public void inserir(Object novo){
		if(size == 0){
			head = new BlocoLista(novo);
			size++;
		}else{
			BlocoLista temp = head;
			while(temp.getNext() != null) temp = temp.getNext();
			temp.setNext(new BlocoLista(novo));
			size++;
		}
	}
	
	public void remover(Object velho){
		if(size > 0){
			
			if(head.getConteudo() == velho){
				head = head.getNext();
			}else{
				BlocoLista temp = head;
				
				for(int i=1; i < size; i++){
					
					if(temp.getNext().getConteudo() == velho){
						temp.setNext(temp.getNext().getNext());
						size--;
						break;
					}
					temp = temp.getNext();
				}
			}
		}
	}
	
	public int getSize(){
		return this.size;
	}
	
	
}
