package negocios;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Map;
import java.util.Vector;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.UnsupportedAudioFileException;

import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;

import negocios.Gerenciador;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;

import dados.Musica;

public class GerenciadorTeste {

	/**
	 * @param args
	 * @throws UnsupportedAudioFileException 
	 */
	public static void main(String[] args) throws UnsupportedAudioFileException {
		

		try {
			Gerenciador gerenciador = new Gerenciador("musicas");
			
			Vector<Musica> tmpMusicaa= (Vector<Musica>) gerenciador.getLista();
			for (int i = 0; i < tmpMusicaa.size(); i++) {
				Musica tmpMusica = tmpMusicaa.get(i);
				System.out.println("---------------------------------------");
				System.out.println("Caminho "+tmpMusica.getFile());
				System.out.println("Artista: " +tmpMusica.getArtista());
				System.out.println("Titulo: "+tmpMusica.getTitulo());
				System.out.println("Album: "+tmpMusica.getAlbum());
				System.out.println("Faixa: " +tmpMusica.getFaixa());
				System.out.println("Duracao "+ tmpMusica.getDuracao());
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}