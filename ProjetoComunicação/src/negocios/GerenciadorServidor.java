package negocios;

import java.util.Vector;

import negocios.threads.server.MainRoutineServer;

import dados.Cliente;
import dados.Download;
import repositorios.RepositorioCliente;


public class GerenciadorServidor {

	private RepositorioCliente repoCliente;
	
	private Thread mainRoutine;
	
	
	public GerenciadorServidor() {
		this.repoCliente = new RepositorioCliente();
	}

	public void runMainRoutine(){
		mainRoutine = new Thread(new MainRoutineServer());
		mainRoutine.start();
		
	}
	
	public void runServerService(){
		
		
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
