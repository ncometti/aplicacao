package negocios;

import java.util.Vector;

import dados.Cliente;
import dados.Download;
import repositorios.RepositorioCliente;


public class GerenciadorServidor {

	private RepositorioCliente repoCliente;
	
	
	
	public GerenciadorServidor() {
		this.repoCliente = new RepositorioCliente();
	}
	
	public void adicionarCliente(Cliente cliente) {
		
		repoCliente.inserirCliente(cliente);
		
	}
	
	public void adicionarDownload(Cliente cliente, Download download){
		
		repoCliente.inserirDownload(cliente, download);
				
	}
	
	public void removerCliente(Cliente cliente){
		repoCliente.removerCliente(cliente);
	}
	
	
	public Vector<Cliente> getLista(){
		return repoCliente.getLista();
	}
	

}
