package negocios.threads.server;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import negocios.GerenciadorServidor;
import CamadaTransporte.ControladorDownload;
import CamadaTransporte.In;
import CamadaTransporte.Out;
import CamadaTransporte.SRServerSocket;
import CamadaTransporte.SRSocket;

public class ServerClientRoutine implements Runnable{

	private SRSocket socket;
	private In in;
	private Out out;
	private GerenciadorServidor gServidor;

	public ServerClientRoutine(SRSocket socket, GerenciadorServidor gServidor) throws Exception {
		this.gServidor= gServidor;
		this.socket= socket;
		this.in = new In(socket);
		this.out = new Out(socket);

	}

	public void run() {

		String request;

		while(true){

			
			try {
				
//				1 - Requisicao de lista
//				2 - Requisicao de download
				
				
				request = (String) in.receberObjeto();
				System.out.println(request);
				
				if(request.equals("1")){
					
					out.enviarObjeto(getServidor().getListaMusica());
					
				}else{
					// TODO LEMBRAR DE PARTICIONAR O ARQUIVO
					// TODO TESTAR COM A GUI
					String fileName=request.substring(1);
					
					int newSocket = in.receberPorta();
					
					File file = new File("I came 2 party.mp3"); // LEMBRAR DE MUDAR ISSO AQUI PRA FILENAME
					byte[] sendStream = new byte[(int) file.length()];
					System.out.println(file.length());
					
					FileInputStream fis = new FileInputStream(file);
					BufferedInputStream bis = new BufferedInputStream(fis);
					bis.read(sendStream, 0, sendStream.length);					
					
					ControladorDownload controle = new ControladorDownload();
					out.enviarArquivo(sendStream, controle);
					

					
				}
				

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}



		}

	}
	
	private synchronized GerenciadorServidor getServidor(){
		return gServidor;
	}

}
