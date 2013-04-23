package CamadaTransporte;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class In {
	SRSocket UDPSocket;
	

	public In(SRSocket UDPSocket) {
		this.UDPSocket = UDPSocket;
	}

	public int receberPorta() throws Exception {
		byte[] recive = new byte[4];
		UDPSocket.receber(recive);

		return byteToInt2(recive);
	}

	public int receberTamanho() throws Exception {
		byte[] recive = new byte[4];
		UDPSocket.receber(recive);

		return byteToInt2(recive);
	}
	
	public Object receberObjeto() throws Exception {
		int tamanho = receberTamanho();
		byte[] receive = new byte[tamanho];
		UDPSocket.receber(receive);
		return deserialize(receive);
	}
	
	public void receberArquivo (byte[] receiveStream, ControladorDownload controle) throws Exception {
		Out out = new Out(UDPSocket);

		out.enviarPorta(controle.getUDPSocket().getLocalPort());
		int newPort = receberPorta();
		int tamanho = receberTamanho();
		UDPSocket.receberFile(receiveStream, controle, newPort);
	}

	static int byteToInt2(byte[] b) {
		return ((b[3] & 0xff) << 24) | ((b[2] & 0xff) << 16)
				| ((b[1] & 0xff) << 8) | (b[0] & 0xff);
	}

	static Object deserialize(byte[] data) throws IOException,
			ClassNotFoundException {
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		ObjectInputStream is = new ObjectInputStream(in);
		return is.readObject();
	}
}
