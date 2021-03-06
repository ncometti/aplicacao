package fachada;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Vector;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Timer;

import org.farng.mp3.TagException;


import dados.Musica;

import negocios.Gerenciador;
import negocios.MP3Player;

public class FachadaCliente implements GuiCoreCliente {
	private String caminho;
	private File in;
	private Thread musica;
	private Gerenciador ger;

	public FachadaCliente(String pasta) throws IOException,
			TagException, UnsupportedAudioFileException {

		ger = new Gerenciador(pasta);

	}

	public void conectClientToServer(String serverIP) {
		try {
			ger.conectClientToServer(serverIP);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	

	public Vector<Musica> atualizarListaServidor() {
		try {
			return ger.atualizaListaServidor();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public Vector<Musica> atualizarListaCliente() {

		Vector<Musica> tmpMusica;
		try {
			tmpMusica = (Vector<Musica>) ger.atualizarLista();
			return tmpMusica;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TagException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}

		return null;
	}

	public void deletar() {

	}

	public void ordenar() {

	}



	public void setTolerancia() {

	}

	public void press_stop() {
	}

	public void press_prox() {
		// TODO Auto-generated method stub

	}

	public void press_ant() {
		// TODO Auto-generated method stub

	}

	public void iniciarDownload(String nomeMusica) {
		

	}

	public void pausarDownload() {
		// TODO Auto-generated method stub

	}

	public void reiniciarDownload() {
		// TODO Auto-generated method stub

	}

	public void cancelarDownload() {
		// TODO Auto-generated method stub

	}

	public Vector<Musica> buscarListaCliente(String str) {
		// TODO Auto-generated method stub
		return (Vector<Musica>) ger.procurar(str);
	}

	public Vector<Musica> buscarListaServidor(String str) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setUserName(String nome) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTolerancia(int tolerancia) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setServerIP(String ip) {
		// TODO Auto-generated method stub

	}

	@Override
	public Vector<Musica> getLista() {
		return (Vector<Musica>) ger.getLista();
	}

}
