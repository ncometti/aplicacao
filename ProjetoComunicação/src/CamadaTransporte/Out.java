package CamadaTransporte;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Out {
	SRSocket UDPSocket;

	public Out(SRSocket UDPSocket) {
		this.UDPSocket = UDPSocket;
	}

	public void enviarPorta(int novaPorta) throws Exception {
		byte[] send = convertToByte(novaPorta, 4);
		UDPSocket.enviar(send);
	}

	public void enviarTamanho(int tamanhoArquivo) throws Exception {
		byte[] send = convertToByte(tamanhoArquivo, 4);
		UDPSocket.enviar(send);
	}

	public void enviarObjeto(Object objeto) throws Exception {
		byte[] send = serialize(objeto);
		enviarTamanho(send.length);
		UDPSocket.enviar(send);
	}
	
	public void enviarArquivo (byte[] sendStream, ControladorDownload controle) throws Exception {
		In in= new In(UDPSocket);
		int newPort = in.receberPorta();
		enviarPorta(controle.getUDPSocket().getLocalPort());
		enviarTamanho(sendStream.length);
		UDPSocket.enviarFile(sendStream, 0, newPort, controle);
	}
	
	static byte[] convertToByte(int valor, int numeroBytes) {
		byte[] bytes = new byte[numeroBytes];
		for (int i = 0; i < numeroBytes; i++) {
			bytes[i] = (byte) (valor >>> (i * 8));
		}

		return bytes;
	}

	static byte[] serialize(Object obj) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(obj);
		return out.toByteArray();
	}
}
