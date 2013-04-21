package CamadaTransporte;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class main {

	public static void main(String[] args) {

		for (int i = 0; i < 1; i++) {

			Object sharedLock = new Object();
			ThreadServidor servidor = new ThreadServidor();
			ClientThread cliente1 = new ClientThread(sharedLock, 1, i);
			// ClientThread cliente2 = new ClientThread(sharedLock, 2, i);
			// ClientThread cliente3 = new ClientThread(sharedLock, 3, i);
			// ClientThread cliente4 = new ClientThread(sharedLock, 4, i);

			servidor.start();
			cliente1.start();
			// cliente2.start();
			// cliente3.start();
			// cliente4.start();

			try {
				servidor.join();
				cliente1.join();
				// cliente2.join();
				// cliente3.join();
				// cliente4.join();

			} catch (Exception e) {
				System.out.println("opa...");
			}

		}

		System.out.println("ACABOU O MAIN!");

	}

	static int byteToInt(byte[] b) {
		return ((b[1] & 0xff) << 8) | (b[0] & 0xff);
	}

	static int byteToInt2(byte[] b) {
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

/*
 * Explica��o do erro na inicializa��o do Socket: �s vezes, a primeira mensagem
 * enviada pelo clientThread n�o chega ao servidor e por isso o c�digo n�o segue
 * adiante. Talvez seja necess�rio incluir um timeout na sincroniza��o do
 * ciente-servidor. Esse erro foi identificado ao notar que o cliente chega a
 * enviar o pedido de conex�o, mas mesmo as portas e IPs estando sincronizados,
 * a mensagem n�o chega ao servidor e esse continua esperando.
 */
