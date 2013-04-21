package CamadaTransporte;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SRServerSocket {

	static int limiteConexoes = 4;

	int port;
	DatagramSocket UDPServerSocket;
	InetAddress IPServer = InetAddress.getByName("localhost");

	SRSocket[] connections;
	InetAddress[] clientIPs;
	int[] clientPorts;

	public SRServerSocket(int port) throws Exception {
		this.port = port;

		UDPServerSocket = new DatagramSocket(port);

		IPServer = InetAddress.getByName("localhost");

		connections = new SRSocket[limiteConexoes];
		
		clientIPs = new InetAddress[limiteConexoes];
		
		clientPorts = new int[limiteConexoes];
		
	}
	
	public SRSocket acceptConnection() throws Exception{

		/*
		 * Inicia conexão com o cliente e direciona a conexão do mesmo para um
		 * novo SRsocket. Tal socket encontra-se no array/lista de sockets.
		 */

		byte[] synStream = new byte[2];
		DatagramPacket synPacket = new DatagramPacket(synStream,
				synStream.length);

		boolean repeated = false;
//		System.out.println("llo");
		UDPServerSocket.receive(synPacket);
		
//		System.out.println(synStream[0] + " estamos recebendo esse cara...................");
		for(int i = 0; i < limiteConexoes; i++){
			if(clientIPs[i] != null && clientIPs[i] == synPacket.getAddress() && clientPorts[i] == synPacket.getPort()){
				repeated = true;
			}
		}
		if(!repeated){
			
			for (int i = 0; i < limiteConexoes; i++) {
				if (connections[i] == null) {
					
					connections[i] = new SRSocket(new DatagramSocket(), synPacket.getAddress(), synPacket.getPort(), i);
					
					return connections[i];
				}
			}
		}
		
		return null; // criar exception para numero de clientes excedido.
	}
	
	public void Close(){
		UDPServerSocket.close();
	}
}
