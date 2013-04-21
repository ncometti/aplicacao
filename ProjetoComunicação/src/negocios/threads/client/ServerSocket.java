package negocios.threads.client;

import CamadaTransporte.SRServerSocket;

public class ServerSocket {
	
	private SRServerSocket sock;

	
	public ServerSocket(int socket) throws Exception {
		// TODO Auto-generated constructor stub
		sock = new SRServerSocket(socket);
		
	}
	
	public void accept() throws Exception{
		sock.acceptConnection();
	
	}
	
	
	

}
