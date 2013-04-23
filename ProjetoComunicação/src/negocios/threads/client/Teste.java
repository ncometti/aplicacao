package negocios.threads.client;

import java.io.File;
import java.net.UnknownHostException;

import negocios.threads.server.MainRoutineServer;

public class Teste {
	
	
	
	public static void main(String[] args) throws InterruptedException, UnknownHostException, Exception {
		
		

//		File file = new File("I came 2 party.mp3"); // LEMBRAR DE MUDAR ISSO AQUI PRA FILENAME
//		byte[] sendStream = new byte[(int) file.length()];
//		System.out.println(file.length());		
		Thread server = new Thread(new MainRoutineServer());
//		Thread client = new Thread(new MainRoutineClient("localhost", SERVER_NUMBER));
		
		server.start();
//		client.start();
//		Thread client1 = new Thread(new MainRoutineClient("localhost", SERVER_NUMBER));
//		client1.start();
		
		
		
	}

}
