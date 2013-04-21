package CamadaTransporte;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.InetAddress;

public class ClientThread extends Thread {

	Object mutex;
	int id;
	int iteracao;

	public ClientThread(Object mutex, int numero, int iteracao) {
		this.mutex = mutex;
		id = numero;
		this.iteracao = iteracao;
	}

	public void run() {

		try {

			InetAddress IPDestino = InetAddress.getByName("localhost");

			SRSocket sock;

			System.out.println("só eu entrei.." + id);

			sock = new SRSocket(IPDestino, 60000, id);

			System.out.println(sock.portOrigem + " origem c destino "
					+ sock.portDestino + " " + id);

			File file = new File("wonderwall.mp3");
			byte[] preSendStream = new byte[(int) file.length()];
			

			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			bis.read(preSendStream, 0, preSendStream.length);
			
			byte[] sendStream = new byte[49000];
			for(int p = 0; p < 49000; p++){
				sendStream[p] = preSendStream[p];
			}

			File file2 = new File("wonderwall.mp3");
			byte[] sendStream2 = new byte[(int) file2.length()];

			FileInputStream fis2 = new FileInputStream(file2);
			BufferedInputStream bis2 = new BufferedInputStream(fis2);
			bis2.read(sendStream2, 0, sendStream2.length);

			ControladorDownload controle = new ControladorDownload();

			byte[] newDestPort = new byte[4];

			sock.receber(newDestPort);

			int destPort = byteToInt(newDestPort);

			System.out.println("destino port do cliente: " + destPort);

			byte[] newLocalPort = convertToByte(controle.getUDPSocket()
					.getLocalPort(), 4);

			sock.enviar(newLocalPort);

			System.out.println("local port no cliente: "
					+ byteToInt(newLocalPort));

			sock.enviarFile(sendStream, 0, destPort, controle);

			ControladorDownload controle2 = new ControladorDownload();

			byte[] newDestPort2 = new byte[4];

			sock.receber(newDestPort2);

			int destPort2 = byteToInt(newDestPort2);

			System.out.println("destino port do cliente: " + destPort2);

			byte[] newLocalPort2 = convertToByte(controle2.getUDPSocket()
					.getLocalPort(), 4);

			sock.enviar(newLocalPort2);

			System.out.println("local port no cliente: "
					+ byteToInt(newLocalPort2));

			sock.enviarFile(sendStream2, 100, destPort2, controle2);

			
			
			
			fis.close();
			fis2.close();
			sock.Close();

			//
			System.out
					.println("passei, meu velho. a bronca é com o mutex. sou o cara "
							+ id);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	static int byteToInt(byte[] b) {
		return ((b[3] & 0xff) << 24) | ((b[2] & 0xff) << 16)
				| ((b[1] & 0xff) << 8) | (b[0] & 0xff);
	}

	static byte[] convertToByte(int valor, int numeroBytes) {
		byte[] bytes = new byte[numeroBytes];
		for (int i = 0; i < numeroBytes; i++) {
			bytes[i] = (byte) (valor >>> (i * 8));
		}

		return bytes;
	}
}
