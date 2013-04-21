package CamadaTransporte;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import EstruturasDados.Inteiro;

public class InputOutputThreadDefault extends Thread {

	DatagramSocket serverSocket;
	Inteiro windowBase;
	int windowSize;
	int totalPacotes;
	int percentualEscolhido;
	DatagramSocket UDPSocket;
	DatagramPacket[] packetInBuffer;

	ControladorDownload controle;

	static int headerSize = 10;
	static int packetSize = 490;

	boolean[] confirmacoes;

	static int ackSize = 4;

	int sizeInBytes;

	InetAddress IPDestino;
	int portDestino;

	int contadorTeste;
	
	public InputOutputThreadDefault(Inteiro windowBase,
			DatagramSocket socket,
			int windowSize, DatagramPacket[] packetInBuffer, int sizeInBytes,
			int percentualEscolhido, InetAddress IPDestino, int portDestino) {

		this.windowBase = windowBase; 
		this.windowSize = windowSize;
		this.percentualEscolhido = percentualEscolhido;
		totalPacotes = packetInBuffer.length;

		this.UDPSocket = socket;

		confirmacoes = new boolean[totalPacotes];
		this.packetInBuffer = packetInBuffer;

		this.sizeInBytes = sizeInBytes;

		this.IPDestino = IPDestino;
		this.portDestino = portDestino;
		
		contadorTeste = 0;
	}

	public void run() {

		try {

			while (windowBase.getValor() < totalPacotes) {
				
				byte[] packetStream = new byte[packetSize + headerSize];
				
				DatagramPacket packet = new DatagramPacket(packetStream,
						packetStream.length);
				// System.out.println("to no loop do servidor...");
				UDPSocket.receive(packet);
				
				if (((int) (100 * Math.random())) >= percentualEscolhido) {
					
					if(packetStream[2] == 0){
						
						byte[] parametro = new byte[2];
						for (int i = 0; i < 2; i++) {
							parametro[i] = packetStream[i];
						}
						
						int sequenceNumber = byteToInt(parametro);
						int sequenceNumberBase = windowBase.getValor() % (windowSize * 2);
						
						int indicePacoteRecebido;
						if (sequenceNumber < sequenceNumberBase) {
							indicePacoteRecebido = windowBase.getValor()
									+ (windowSize * 2 - sequenceNumberBase)
									+ sequenceNumber;
						} else {
							indicePacoteRecebido = windowBase.getValor()
									+ (sequenceNumber - sequenceNumberBase);
						}
						if(indicePacoteRecebido >= windowBase.getValor() + windowSize){
							indicePacoteRecebido -= windowSize*2;
						}
						int lastPacketSize;
						
//						System.out.println(sequenceNumber + " NUMERO DE SEQUENCIA ..." +"ISSO AQUI APARECEU " + 
//						indicePacoteRecebido + " e a windowbase é " + windowBase.getValor());
						
						if ((indicePacoteRecebido >= windowBase.getValor())
								&& (indicePacoteRecebido < (windowSize + windowBase.getValor()))) {
							
//							System.out.println("o pacote tá na janela");
							
							confirmacoes[indicePacoteRecebido] = true;
							packetInBuffer[indicePacoteRecebido] = packet;
							
							if (indicePacoteRecebido == packetInBuffer.length - 1) {
								
								lastPacketSize = headerSize + sizeInBytes
										- (packetInBuffer.length - 1)
										* (packetSize);
								
								byte[] lastStream = new byte[lastPacketSize];
								for (int i = 0; i < lastPacketSize; i++) {
									lastStream[i] = packetStream[i];
								}
								
								DatagramPacket lastPacket = new DatagramPacket(
										lastStream, lastStream.length);
								packetInBuffer[indicePacoteRecebido] = lastPacket;
							}
							
							UDPSocket.send(makeAck(sequenceNumber, IPDestino, portDestino, 0));
							
							if (indicePacoteRecebido == windowBase.getValor()) {
								int i = indicePacoteRecebido;
								while (i < totalPacotes && confirmacoes[i]) {
									i++;
									windowBase.incrementar();
								}
							}
							
						}  else if ((indicePacoteRecebido < windowBase.getValor())
								&& (indicePacoteRecebido > (windowBase.getValor() - windowSize))){
							UDPSocket.send(makeAck(sequenceNumber,
									packet.getAddress(), packet.getPort(), 0));
						}
						
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int byteToInt(byte[] b) {
		return ((b[1] & 0xff) << 8) | (b[0] & 0xff);
	}

	private byte[] convertToByte(int valor, int numeroBytes) {
		byte[] bytes = new byte[numeroBytes];
		for (int i = 0; i < numeroBytes; i++) {
			bytes[i] = (byte) (valor >>> (i * 8));
		}

		return bytes;
	}

	private DatagramPacket makeAck(int indice, InetAddress IPDestino,
			int portDestino, int state) {

		int numeroSequencia = indice % (windowSize * 2);
		byte[] numeroAck = convertToByte(numeroSequencia, 2);
		byte[] ackStream = new byte[3];
		for (int i = 0; i < 2; i++) {
			ackStream[i] = numeroAck[i];
		}

		ackStream[2] = (byte) state;

		DatagramPacket ack = new DatagramPacket(ackStream, ackStream.length,
				IPDestino, portDestino);
		return ack;
	}
	
}
