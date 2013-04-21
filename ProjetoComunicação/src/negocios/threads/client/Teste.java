package negocios.threads.client;

import java.net.UnknownHostException;

import negocios.threads.server.MainRoutineServer;

public class Teste {
	public static int SERVER_NUMBER = 600;
	
	
	public static void main(String[] args) throws InterruptedException, UnknownHostException, Exception {
		
		
		
		
		Thread server = new Thread(new MainRoutineServer(SERVER_NUMBER));
//		Thread client = new Thread(new MainRoutineClient("localhost", SERVER_NUMBER));
		
		server.start();
		Thread.sleep(100);
//		client.start();
//		Thread client1 = new Thread(new MainRoutineClient("localhost", SERVER_NUMBER));
//		client1.start();
		
		
		
	}

}
