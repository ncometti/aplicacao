package negocios.threads.client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Vector;

import CamadaTransporte.In;
import CamadaTransporte.Out;
import CamadaTransporte.SRServerSocket;
import CamadaTransporte.SRSocket;



public class MainRoutineClient implements Runnable{

	static int SOCKET_SERVER_NUMBER = 600;

	private SRSocket socket;
	private int serverSocketNumber;

	private InetAddress serverIP;

	public MainRoutineClient(String serverIP, int serverSocketNumber) throws UnknownHostException {
		// TODO Auto-generated constructor stub
		this.serverIP = InetAddress.getByName(serverIP);
		this.serverSocketNumber = serverSocketNumber;
	}


	@Override
	public void run() {

		try {

			socket = new SRSocket(InetAddress.getByName("localhost"), SOCKET_SERVER_NUMBER, 0);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		
		
		try {
			In in = new In(socket);
			Out out = new Out(socket);

			int newSocket = in.receberTamanho();
			
			ClientServerRoutine newClient = new ClientServerRoutine(serverIP, newSocket);
			Thread threadClient = new Thread(newClient);
			threadClient.start();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
					
		}

	}


}
