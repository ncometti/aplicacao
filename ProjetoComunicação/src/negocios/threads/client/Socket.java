package negocios.threads.client;

import java.net.InetAddress;
import java.net.UnknownHostException;

import CamadaTransporte.SRSocket;

public class Socket {

	
	private SRSocket socket;
	
	public Socket(String ip, int socketNumber) throws UnknownHostException, Exception {
		// TODO Auto-generated constructor stub
		socket = new SRSocket(InetAddress.getByName(ip), socketNumber, 0);
		
	}
	
}
