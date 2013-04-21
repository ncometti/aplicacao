package CamadaTransporte;


public class SRDataStreamInTransfer {
/*
	SRSocket socket;

	public SRDataStreamInTransfer(SRSocket socket) {
		this.socket = socket;
	}

	public void TransferFile() throws Exception {
		int bytesRead;
		int current = 0;
		
		byte[] arrayByte = new byte[6022386]; // checar tamanho
		arrayByte = socket.receber();
		FileOutputStream fos = new FileOutputStream("file.mp3"); 
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		bytesRead = is.read(arrayByte, 0, arrayByte.length);
		current = bytesRead;

		do {
			bytesRead = is.read(arrayByte, current,
					(arrayByte.length - current));
			if (bytesRead >= 0)
				current += bytesRead;
		} while (bytesRead > -1);

		bos.write(arrayByte, 0, current);
		bos.flush();
		bos.close();
	}

	public void TransferString() throws Exception {
		byte[] arrayBytesString = string.getBytes();
		socket.enviar(arrayBytesString);
	}

	public void TransferByte(byte[] arrayByte) throws Exception {
		socket.enviar(arrayByte);
	}
*/
}
