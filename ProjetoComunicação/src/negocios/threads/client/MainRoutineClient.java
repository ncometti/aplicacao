package negocios.threads.client;

import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Vector;

import dados.Musica;

import CamadaTransporte.ControladorDownload;
import CamadaTransporte.In;
import CamadaTransporte.Out;
import CamadaTransporte.SRServerSocket;
import CamadaTransporte.SRSocket;



public class MainRoutineClient{

	static int SOCKET_SERVER_NUMBER = 60000;

	private SRSocket socket;

	private InetAddress serverIP;
	
	In in;
	Out out;

	public MainRoutineClient(String serverIP) throws UnknownHostException {
		// TODO Auto-generated constructor stub
		this.serverIP = InetAddress.getByName(serverIP);
	}


	public void stablishConecction() throws UnknownHostException, Exception {

			
			socket = new SRSocket(serverIP, SOCKET_SERVER_NUMBER, 0);

			in = new In(socket);
			out = new Out(socket);

//			int newSocket = in.receberTamanho();
			
//			ClientServerRoutine newClient = new ClientServerRoutine(serverIP, newSocket);
//			Thread threadClient = new Thread(newClient);
//			threadClient.start();	

	}
	
//	1 - Requisicao de lista
//	2 - Requisicao de download
	
	
	public Vector<Musica> requestServerList() throws Exception{
		
		out.enviarObjeto("1");
		return (Vector<Musica>) in.receberObjeto();
		
	}
	
	public void requestDownload(String fileName) throws Exception { //@Warning nao eh pra ser com indice, apenas para teste
		
		// TODO LEMBRAR DE TESTAR COM A GUI
//		
//		out.enviarObjeto("2"+"I came 2 party.mp3");
//		ControladorDownload controle = new ControladorDownload();
//		
//		byte[] receiveStream = new byte[4873310];
//		in.receberArquivo(receiveStream, controle);
//		
//		FileOutputStream fos = new FileOutputStream("teste.mp3");
//		fos.write(receiveStream);
//		fos.close();

	}


}
