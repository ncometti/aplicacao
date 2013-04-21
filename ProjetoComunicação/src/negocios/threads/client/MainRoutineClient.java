package negocios.threads.client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Vector;

import dados.Musica;

import CamadaTransporte.In;
import CamadaTransporte.Out;
import CamadaTransporte.SRServerSocket;
import CamadaTransporte.SRSocket;



public class MainRoutineClient{

	static int SOCKET_SERVER_NUMBER = 600;

	private SRSocket socket;
	private int serverSocketNumber;

	private InetAddress serverIP;
	
	In in;
	Out out;

	public MainRoutineClient(String serverIP, int serverSocketNumber) throws UnknownHostException {
		// TODO Auto-generated constructor stub
		this.serverIP = InetAddress.getByName(serverIP);
		this.serverSocketNumber = serverSocketNumber;
	}


	public void stablishConecction() throws UnknownHostException, Exception {


			socket = new SRSocket(InetAddress.getByName("localhost"), SOCKET_SERVER_NUMBER, 0);

			in = new In(socket);
			out = new Out(socket);

//			int newSocket = in.receberTamanho();
			
//			ClientServerRoutine newClient = new ClientServerRoutine(serverIP, newSocket);
//			Thread threadClient = new Thread(newClient);
//			threadClient.start();	

	}
	
	public Vector<Musica> requestServerList() throws Exception{
		
		out.enviarObjeto("Lista");
		
		return (Vector<Musica>) in.receberObjeto();
		
	}


}
