package negocios;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Map;
import java.util.Vector;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.UnsupportedAudioFileException;

import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.id3.AbstractID3v2;

import repositorios.Repositorio;
import repositorios.RepositorioMusica;

import dados.Musica;

public class Gerenciador {

	/*
	 * Essa classe tem o intuito de gerenciar alguns recursos e gerar os
	 * exceptions necessarios da camada relatorios
	 */

	// TODO
	
	
	private File dir;
	private Repositorio repositorio;

	// A Classe recebe a caminho da pasta ja correto pela GUI.
	// Na criacao da classe gerenciador eh feita a primeira atualizacao do
	// repositorio de acordo com o que ta na pasta

	// OBS.: se a pasta for na raiz do projeto apenas precisa colocar o nome da
	// pasta, ex.: musica ou musica/QualquerOutraPasta

	public Gerenciador(String dir) throws IOException, TagException,
			UnsupportedAudioFileException {

		this.dir = new File(dir);

		this.repositorio = new RepositorioMusica();
		atualizarLista();

	}
	
	public void runClientService(){
		
		
		
		
	}
	
	@SuppressWarnings("unchecked")
	public Object atualizarLista() throws IOException, TagException,
			UnsupportedAudioFileException {

		((Vector<Musica>) repositorio.getLista()).removeAllElements();

		File[] arquivos;

		FilenameFilter filter = new FilenameFilter() {

			public boolean accept(File dir, String name) {
				if (name.length() > 3) {
					return !name
							.substring(name.length() - 4, name.length() - 1)
							.equalsIgnoreCase(".mp3");
				}
				return true;
			}
		};
		arquivos = dir.listFiles(filter);

		if (arquivos != null) {

			for (int i = 0; i < arquivos.length; i++) {
				MP3File tmpMp3File = new MP3File(arquivos[i]);
				AbstractID3v2 tag = tmpMp3File.getID3v2Tag(); // classe com
				// metodos que
				// pegam as
				// informacoes
				// das musicas
				AudioFileFormat baseFileFormat = new MpegAudioFileReader()
						.getAudioFileFormat(arquivos[i]);

				Map<String, Object> properties = baseFileFormat.properties();

				Long duration = (Long) properties.get("duration");
				long minutos = duration / 60000000;
				long segundos = (duration % 60000000) / 1000000;
				String duracao = minutos + ":" + segundos;

				Musica tmpMusica = new Musica(arquivos[i], tag.getLeadArtist(),
						tag.getSongTitle(), tag.getAlbumTitle(),
						tag.getSongGenre(), tag.getTrackNumberOnAlbum(),
						duracao, tag.getYearReleased(), tag.getSongComment(),
						tag.getSongComment());

				repositorio.inserir(tmpMusica);

			}
		}
		return repositorio.getLista();
	}

	// @@ OBS.: esse metodo APENAS retorna a lista de musicas referente a ULTIMA
	// ATUALIZACAO
	public Object getLista() {

		return repositorio.getLista();
	}

	public Object procurar(String string) {
		return repositorio.procurarPorTitulo(string);
	}

	public Object procurarPorAlbum(String album) {
		return repositorio.procurarPorAlbum(album);
	}

	public Object procuraPorArtista(String artista) {
		return repositorio.procurarPorArtista(artista);
	}

}