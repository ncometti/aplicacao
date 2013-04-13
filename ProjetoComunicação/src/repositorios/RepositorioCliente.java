package repositorios;
import java.text.Normalizer;
import java.util.Vector;

import dados.Cliente;
import dados.Download;

public class RepositorioCliente {


	private Vector<Cliente> vector;

	public RepositorioCliente() {

		this.vector = new Vector<Cliente>();
	}

	public void inserirCliente(Cliente cliente) {

		this.vector.add(cliente);

	}
	
	public void inserirDownload(Cliente cliente, Download download){
		int i = getIndice(cliente);
		vector.get(i).getDownloads().add(download);
	}

	public void removerCliente(Cliente cliente) {
		int i = getIndice(cliente);
		this.vector.remove(i);
	}

	private int getIndice(Cliente cliente) {
		String aux;
		boolean achou = false;
		int i = 0;
		while ((!achou) && (i < this.vector.size())) {
			aux = this.vector.get(i).getIP();
			if (aux.equals(cliente.getIP())) {
				achou = true;
			} else {
				i++;
			}
		}
		return i;
	}

	public Vector<Cliente> getLista() {
		// TODO Auto-generated method stub
		return vector;
	}

}

