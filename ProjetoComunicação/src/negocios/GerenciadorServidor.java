package negocios;

import java.io.IOException;
import java.util.Vector;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.farng.mp3.TagException;

import negocios.threads.server.MainRoutineServer;

import dados.Cliente;
import dados.Download;
import dados.Musica;
import repositorios.RepositorioCliente;


public class GerenciadorServidor {

	private RepositorioCliente repoCliente;
	private Gerenciador gerenciador;
	private Thread mainRoutine;
	
	
	public GerenciadorServidor() {
		this.repoCliente = new RepositorioCliente();
		try {
			gerenciador = new Gerenciador("musicasServidor");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void runMainRoutine() throws Exception{
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
	
	public Vector<Musica> getListaMusica(){
		try {
			return (Vector<Musica>) gerenciador.atualizarLista();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Vector<Cliente> getLista(){
		return repoCliente.getLista();
	}
	

}
