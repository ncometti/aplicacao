package negocios.threads.server;

import CamadaTransporte.In;
import CamadaTransporte.Out;
import CamadaTransporte.SRServerSocket;
import CamadaTransporte.SRSocket;

public class ServerClientRoutine implements Runnable{
	
	private SRServerSocket serverSocket;
	private SRSocket socket;
	private int serverSocketNumber;
	
	public ServerClientRoutine(int serverSocketNumber) throws Exception {
		
		this.serverSocketNumber= serverSocketNumber;

		serverSocket = new SRServerSocket(serverSocketNumber);
		
	}
	
	public void run() {
		
		while(true){
			
			try {
				socket = serverSocket.acceptConnection();
				System.out.println("CONEXAO CLIENT ESTABELECIDA THREAD");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			In in = new In(socket);
			Out out = new Out(socket);
			
			try {
				out.enviarObjeto("JONAS TESTANDO ENVIO");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			
			
		}
		
	}

}
