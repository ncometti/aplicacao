package fachada;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import negocios.MP3Player;
public class fachadaCliente implements GuiCoreCliente{
	String caminho;
	FileInputStream in;
	Thread musica;																																
	public fachadaCliente() throws FileNotFoundException, JavaLayerException {
		in = new FileInputStream(caminho);
		musica = new MP3Player(in);
	}

	public void iniciarPlayer() {
		
		musica.start();
		
	}

	public void pausarPlayer() {
		//musica.pauseSong();
		
	}

	public void reiniciarPlayer() {
	
		
	}

	public void cancelarPlayer() {
		
		
	}

	public void atualizarListaServidor() {
		
		
	}

	public void atualizarListaCliente() {
		
		
	}

	public void deletar() {
	
		
	}

	public void ordenar() {

		
	}

	public void buscarListaCliente() {
	
		
	}

	public void buscarListaServidor() {
	
		
	}

	public void setTolerancia() {
		
		
	}

}
