package negocios;

import java.io.FileInputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class MP3Player extends Thread {
	//status
	private final static int NOTSTARTED = 0;
	private final static int PLAYING = 1;
	private final static int PAUSED = 2;
	private final static int FINISHED = 3;

	private Player p;
	private int status;
	private Object lock = new Object();

	public MP3Player (FileInputStream in) throws JavaLayerException {
		p = new Player(in);
	}

	public void playSong() {
		synchronized (lock) {
			status = PLAYING;
			lock.notifyAll();
		}

	}
	public void resumeSong() {
		synchronized (lock) {
			if (status == PAUSED) {
				status = PLAYING;
				lock.notifyAll();
			}
		}
	}
	public void pauseSong() {
		if (status == PLAYING)
			status = PAUSED;
	}

	public void stopSong() {
		synchronized (lock) {
			status = FINISHED;
			lock.notifyAll();
		}
		
	}
	public void run() {
		try {
			synchronized (lock) {
				switch (status) {

				case NOTSTARTED:
					p.play();
					break;

				//case PAUSED:
				//	resumeSong();
				//	break;

				case FINISHED:
					p.close();
					break;

				}
			}
			
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
