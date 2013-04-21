package negocios.threads.client;

import java.net.InetAddress;
import java.net.UnknownHostException;

import CamadaTransporte.In;
import CamadaTransporte.Out;
import CamadaTransporte.SRSocket;

public class ClientServerRoutine implements Runnable{
	private SRSocket socket;
	private int serverSocketNumber;

	private InetAddress serverIP;

	public ClientServerRoutine(InetAddress serverIP, int serverSocketNumber) throws UnknownHostException {
		// TODO Auto-generated constructor stub
		this.serverIP = serverIP;
		this.serverSocketNumber = serverSocketNumber;
	}
	
	
	public void run() {
		// TODO Auto-generated method stub
		try {

			socket = new SRSocket(InetAddress.getByName("localhost"), serverSocketNumber, 0);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		
		
		try {
			In in = new In(socket);
			Out out = new Out(socket);
			
			String teste = (String)in.receberObjeto();
			System.out.println("Teste realizado com sucesso: "+teste);
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
					
		}
		
		System.out.println("TREMINEI THREAD CLIENT REQUISIÇÃO");
	}

}
