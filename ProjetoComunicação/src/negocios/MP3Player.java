package negocios;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class MP3Player extends Thread {

	// Objeto para nosso arquivo MP3 a ser tocado
	private File mp3;
	// Objeto Player da biblioteca jLayer. Ele tocará o arquivo MP3
	private Player player;
	private boolean playing;
	private boolean paused;
	private Object lock = new Object();
	public static boolean musicaAtiva = false;

	public static boolean isMusicaAtiva() {
		return musicaAtiva;
	}

	public void pressPlayPause() throws JavaLayerException { //o que vai acontecer ao clicar o botao play/pause
			if (playing) { //se estiver tocando, vai dar pause
				paused = true;
				playing = false;
				try {
					lock.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (paused) { //se estiver no pause, vai dar play
				playing = true;
				paused = false;
				lock.notifyAll();
			} else { //se nao estiver tocando nem pausado, eh porque nao comecou, entao vai comecar
				playing = true;
				paused = false;
				player.play();
			}
		
	}

	public MP3Player(File mp3) {
		this.mp3 = mp3;
		//this.playing = estado;
		this.paused = false;
	}




	public void retomar() {

	}
	public void run() {
		try {
			FileInputStream fis     = new FileInputStream(mp3);
			BufferedInputStream bis = new BufferedInputStream(fis);
			this.player = new Player(bis);
			synchronized (lock) {
				
		

			}
			/*System.out.println("Tocando!");
			musicaAtiva = true;
			this.player.play();
			System.out.println("Terminado!");
			musicaAtiva = false;*/
		} catch (Exception e) {
			System.out.println("Problema ao tocar a música: " + mp3);
			//e.printStackTrace();
		}
	}
}
