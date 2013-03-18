package negocios;

import java.io.FileInputStream;

public class testePlayer {

	public static void main(String[] args) {
		try {
            FileInputStream input = new FileInputStream("wonderwall.mp3"); 
            PausablePlayer player = new PausablePlayer(input);

            // start playing
            player.play();

            // after 5 secs, pause
            Thread.sleep(5000);
            player.pause();     

            // after 5 secs, resume
            Thread.sleep(5000);
            player.resume();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }

	}

}
