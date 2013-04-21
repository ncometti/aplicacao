package CamadaTransporte;

import java.io.File;
import java.io.FileInputStream;

public class SRDataStreamOutTransfer {
	
	SRSocket socket;
	
	public SRDataStreamOutTransfer(SRSocket socket){
		this.socket = socket;
	}
	
	public void sendFile(File file) throws Exception {
//
//		FileInputStream fis = new FileInputStream(file);
//		OutputStream outoutStream = sock.getOutputStream();
//
//		byte[] buf = new byte[9310];
//		int len;
//		printMessage("Enviando...");
//
//		while ((len = fileInputStream.read(buf)) > 0) {
//		outoutStream.write(buf, 0, len);
//		}
//
//		fileInputStream.close();
//		printMessage("Arquivo enviado com sucesso!");
//		sock.close();
//		serverSocket.close();
//		
//		
//		byte[] sizeStream = convertToByte(sendStream.length, 4);
//		
//		socket.enviar(sizeStream);
		
		
		
	}

	public void TransferFile(String string) throws Exception {
		byte[] arrayBytesString = string.getBytes();
		//socket.enviar(arrayBytesString);
	}

	public void TransferByte(byte[] arrayByte) throws Exception {
		//socket.enviar(arrayByte);
	}
	
	static private byte[] convertToByte(int valor, int numeroBytes) {
		byte[] bytes = new byte[numeroBytes];
		for (int i = 0; i < numeroBytes; i++) {
			bytes[i] = (byte) (valor >>> (i * 8));
		}

		return bytes;
	}
}
