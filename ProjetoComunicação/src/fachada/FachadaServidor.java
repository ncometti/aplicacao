package fachada;

import java.io.IOException;
import java.util.Vector;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.farng.mp3.TagException;

import dados.Musica;
import negocios.Gerenciador;
import negocios.GerenciadorServidor;

public class FachadaServidor implements GuiCoreServidor {

	private GerenciadorServidor gers;
	private Gerenciador ger;

	public FachadaServidor(String pasta) throws IOException, TagException,
			UnsupportedAudioFileException {
		gers = new GerenciadorServidor();
		ger = new Gerenciador(pasta);

	}

	@Override
	public Vector<Musica> atualizarListaMusicas() {
		Vector<Musica> musicas;

		try {
			musicas = (Vector<Musica>) ger.atualizarLista();
			return musicas;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TagException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}

		return null;
	}

}
