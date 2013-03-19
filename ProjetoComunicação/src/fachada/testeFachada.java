package fachada;

import java.io.File;

import negocios.MP3Player;

public class testeFachada {

	
	public static void main(String[] args) throws InterruptedException {
		String path = "wonderwall.mp3";
	    File mp3File = new File(path);
	    MP3Player musica = new MP3Player(mp3File);
	   if(MP3Player.isMusicaAtiva() == false) musica.start();	    
	}

}
