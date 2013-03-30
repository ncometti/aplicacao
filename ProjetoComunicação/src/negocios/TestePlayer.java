package negocios;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Vector;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import org.farng.mp3.TagException;

import negocios.Gerenciador;

import dados.Musica;

import javafx.scene.media.MediaPlayer.Status;

public class TestePlayer {

	public static void main(String[] args) throws InterruptedException, IOException, TagException, UnsupportedAudioFileException {
		
		Gerenciador gerenciador = new Gerenciador("D:\\Users/Casa/Desktop/Comunicacao/TesteAplicacao/musicas"); // colocar o nome da pasta aqui
		
		final MP3Player player = new MP3Player((Vector<Musica>)gerenciador.getLista(), 1);
		Thread.sleep(100);
//		System.out.println("asdasdast "+t);
		final JProgressBar jPB = new JProgressBar(0,(int) player.getPlayer().getTotalDuration().toMillis());
		jPB.setSize(300, 100);
		final JFrame frame = new JFrame("Player");
		frame.setLocation(400, 300);
		JPanel cont = new JPanel(new java.awt.BorderLayout());
		frame.add(cont);
		Timer timerAtualizacao = new Timer (50, new ActionListener() {  
			@Override  
			public void actionPerformed(ActionEvent arg0) {  
//				System.out.println(player.getPlayer().getTotalDuration().toMillis()
//						+"\n"+player.getPlayer().getCurrentTime().toMillis());
				jPB.setValue((int)player.getPlayer().getCurrentTime().toMillis());
				frame.setTitle (player.getCurrentTime());  
			}  
		});  
		timerAtualizacao.start();


		final JButton teste1 = new JButton("segundo 5");

		teste1.addActionListener( new ActionListener() {
			@Override  
			public void actionPerformed(ActionEvent arg0) {  
				player.skip(5000);
				
			}  
		});  
		cont.add(teste1, BorderLayout.EAST);
		final JButton next = new JButton(">>>>");

		next.addActionListener( new ActionListener() {
			@Override  
			public void actionPerformed(ActionEvent arg0) {  
				player.next();
				
			}  
		});  
		cont.add(next, BorderLayout.NORTH);
		
		final JButton prev = new JButton("<<<<");

		prev.addActionListener( new ActionListener() {
			@Override  
			public void actionPerformed(ActionEvent arg0) {  
				player.previous();
				
			}  
		});  
		cont.add(prev, BorderLayout.SOUTH);

		final JButton teste2 = new JButton("STOP");

		teste2.addActionListener( new ActionListener() {
			@Override  
			public void actionPerformed(ActionEvent arg0) {  
				player.stop();
			}  
		});  

		cont.add(teste2, BorderLayout.WEST);

		final JButton button1 = new JButton("Play");
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (player.getPlayer().getStatus()==Status.PLAYING) {
					player.pause();
					button1.setText("resume");
				} else {
					player.play();
					button1.setText("play");
				}

			}
		});

		cont.add(button1, BorderLayout.CENTER);

		

//		cont.add(jPB, java.awt.BorderLayout.SOUTH);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
