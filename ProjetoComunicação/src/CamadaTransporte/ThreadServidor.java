package CamadaTransporte;

import java.io.FileOutputStream;

public class ThreadServidor extends Thread{

	public ThreadServidor(){
		
	}
	
	public void run(){
		
		try{
			
			SRServerSocket serverSock = new SRServerSocket(60000);
			for(int i = 0; i < 1; i++){

				SRSocket sock = serverSock.acceptConnection();
				System.out.println(sock.portOrigem + " origem s destino " + sock.portDestino);
				
				ControladorDownload controle = new ControladorDownload();
				byte[] newPortLocal = convertToByte(controle.getUDPSocket().getLocalPort(), 4);
				
				System.out.println("local port do servidor: " + controle.getUDPSocket().getLocalPort());
				
				sock.enviar(newPortLocal);
				
				byte[] newPortDest = new byte[4];
				
				sock.receber(newPortDest);
				
				int portDest = byteToInt(newPortDest);
				
				System.out.println("dest port do servidor: " + portDest);
				
				byte[] receiveStream = new byte[49000]; // so mudei 14 por tam
				
				sock.receberFile(receiveStream, controle, portDest);
				
				
				ControladorDownload controle2 = new ControladorDownload();
				byte[] newPortLocal2 = convertToByte(controle2.getUDPSocket().getLocalPort(), 4);
				
				System.out.println("local port do servidor: " + controle2.getUDPSocket().getLocalPort());
				
				sock.enviar(newPortLocal2);
				
				byte[] newPortDest2 = new byte[4];
				
				sock.receber(newPortDest2);
				
				int portDest2 = byteToInt(newPortDest2);
				
				System.out.println("dest port do servidor: " + portDest2);
				
				byte[] receiveStream2 = new byte[99345]; // so mudei 14 por tam
				
				sock.receberFile(receiveStream2, controle2, portDest2);
				
				
				byte[] result = new byte[148345];
				
				for(int p = 0; p < 49000; p++){
					result[p] = receiveStream[p];
				}
				for(int p = 0; p < 99345; p++){
					result[p + 49000] = receiveStream2[p];
				}
				
				
				System.out.println("aqui");
				FileOutputStream fos = new FileOutputStream("teste.mp3");
				fos.write(result);
				fos.close();
				
				
//				FileOutputStream fos2 = new FileOutputStream("flor" + i + ".png");
//				fos2.write(receiveStream2);
//				fos2.close();
				
				
				sock.Close();
				System.out.println("cheguei aqui");
				
			}
			
			serverSock.Close();
			
			System.out.println("done... server");
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	static int byteToInt(byte[] b) {
		return ((b[3] & 0xff) << 24) | ((b[2] & 0xff) << 16) | ((b[1] & 0xff) << 8) | (b[0] & 0xff);
	}
	
	static byte[] convertToByte(int valor, int numeroBytes){
		byte[] bytes = new byte[numeroBytes];
		for (int i = 0; i < numeroBytes; i++) {
		    bytes[i] = (byte)(valor >>> (i * 8));
		}
		
		return bytes;
	}
	
}
