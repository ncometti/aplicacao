package fachada;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import negocios.MP3Player;
public class fachadaCliente implements GuiCoreCliente{
	String caminho;
	File in;
	Thread musica;
	public fachadaCliente() throws FileNotFoundException, JavaLayerException {
		in = new File(caminho);
		musica = new MP3Player(in);
	}

	public void iniciarPlayer() {
		if (MP3Player.musicaAtiva == false) {
			musica.start();
		}
		
		
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
