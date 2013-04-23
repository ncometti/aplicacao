package negocios.threads.server;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Vector;

import negocios.GerenciadorServidor;

import CamadaTransporte.ControladorDownload;
import CamadaTransporte.In;
import CamadaTransporte.Out;
import CamadaTransporte.SRServerSocket;
import CamadaTransporte.SRSocket;

public class MainRoutineServer implements Runnable{
	
	public static int SERVER_NUMBER = 60000;

	private SRServerSocket serverSocket;
	private SRSocket socket;

	
	GerenciadorServidor gServidor;

	public MainRoutineServer() throws Exception{


		serverSocket = new SRServerSocket(SERVER_NUMBER);
		
		gServidor = new GerenciadorServidor();
	}


	public void run() {

		// TODO Auto-generated method stub

		while(true){
			
			try {
				System.out.println("NOVA REQUISIÇÃO___");

				socket = serverSocket.acceptConnection();

				// se nao deu erro ate aqui eh pq a conexão foi realizada com sucesso

				
//				ControladorDownload controle = new ControladorDownload();
//				System.out.println("Porta asdjasoidjoiasjdajsdoas:"+controle.getUDPSocket().getLocalPort());
				
				
			} catch (Exception e) {
				
				e.printStackTrace();
			
			}
			
			System.out.println("cheguei aqui");
			
			try {
				
				ServerClientRoutine newClient = new ServerClientRoutine(socket, gServidor);
				Thread threadClient = new Thread(newClient);
				threadClient.start();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			System.out.println("TERMINOU REQUISIÇÃO___");
		}

	}
	
	byte[] convertToByte(int valor, int numeroBytes){
		byte[] bytes = new byte[numeroBytes];
		for (int i = 0; i < numeroBytes; i++) {
			bytes[i] = (byte)(valor >>> (i * 8));
		}

		return bytes;
	}



}
