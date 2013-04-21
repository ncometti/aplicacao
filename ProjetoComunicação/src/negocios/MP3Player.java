package negocios;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Vector;

import dados.Musica;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class MP3Player {

	private MediaPlayer player;
	private Vector<Musica> playList;


	private int currentMP3;
	private JFXPanel fxPanel;

	public MP3Player(Vector<Musica> playList, int start)
			throws MalformedURLException {
		this.playList = playList;
		fxPanel = new JFXPanel();
		setMusic(playList.get(start).getFile());
		currentMP3 = start;

	}
	
	public void atualizarLista(Vector<Musica> playList) {
		this.playList = playList;
		currentMP3 = 0;
	}

	public Vector<Musica> getPlayList() {
		return playList;
	}
	
	
	
	public void setPlayList(Vector<Musica> playList) {
		this.playList = playList;
	}
	
	
	private void setMusic(File file) throws MalformedURLException {

		final String mediaLocation = file.toURI().toURL().toExternalForm();
		Media media = new Media(mediaLocation);
		player = new MediaPlayer(media);
		player.setOnEndOfMedia(new Runnable() {

			@Override
			public void run() {
				if (playList != null) {
					try {
						currentMP3 = (++currentMP3) % playList.size();
						setMusic(playList.get(currentMP3).getFile());
						play();
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	public void setMusic(int index) throws MalformedURLException{
		currentMP3 = index;
		setMusic(playList.get(index).getFile());
	}

	public void next() {
		if (playList != null) {
			stop();
			try {
				currentMP3 = (++currentMP3) % playList.size();
				setMusic(playList.get(currentMP3).getFile());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

	}

	public void previous() {
		if (playList != null) {
			try {
				stop();

				if (currentMP3 == 0)
					currentMP3 = playList.size() - 1;
				else
					--currentMP3;

				setMusic(playList.get(currentMP3).getFile());
				//play();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void play() {
		player.play();
		
	}

	public void stop() {
		player.stop();
	}

	public void pause() {
		player.pause();
	}
	
	public Duration getTempoAtual() {
		return player.getCurrentTime();
	}
	
	public double getVolume() {
		return player.getVolume();
	}

	public String getCurrentTime() {
		Duration tmp = player.getCurrentTime();
		int horas = (int) tmp.toHours();
		int minutos = (int) tmp.toMinutes() % 60;
		int segundos = (int) tmp.toSeconds() % 60;
		String min = minutos + "";
		String sec = segundos + "";
		String duracao = "";
		if (segundos < 10) {
			sec = "0" + sec;
		}
		if (horas > 0) {
			if (minutos < 10) {
				min = "0" + min;
			}
			duracao = horas + ":" + min + ":" + sec;

		} else {
			duracao = min + ":" + sec;
		}
		return duracao;
	}

	public void skip(long mil) {
		player.seek(new Duration(mil));
	}

	public MediaPlayer getPlayer() {
		return player;
	}

	public JFXPanel getFxPanel() {
		return fxPanel;
	}

	public void setVolume(double value) {
		player.setVolume(value);
	}
	
	public Musica getMusica() {
		return playList.get(currentMP3);
	}
	
	public void seek(Duration seek) {
		player.seek(seek);
	}

}
