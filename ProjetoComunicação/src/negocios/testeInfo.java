package negocios;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.UnsupportedAudioFileException;

import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;

public class testeInfo {

	/**
	 * @param args
	 * @throws UnsupportedAudioFileException 
	 */
	public static void main(String[] args) throws UnsupportedAudioFileException {
		File file = new File("wonderwall.mp3");
		try {
			MP3File mp3File = new MP3File(file);
	
			AudioFileFormat baseFileFormat = new MpegAudioFileReader().getAudioFileFormat(file);
			Map<?,?> properties = baseFileFormat.properties();
			Long duration = (Long) properties.get("duration");
			System.out.println("TAGS:");
			System.out.println(duration);
			System.out.println(mp3File.getID3v1Tag().getAlbum());
			System.out.println(mp3File.getID3v1Tag().getArtist());
			System.out.println(mp3File.getID3v1Tag().getSize());
			//System.out.println(mp3File.
			System.out.println("FIM TAGS");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
