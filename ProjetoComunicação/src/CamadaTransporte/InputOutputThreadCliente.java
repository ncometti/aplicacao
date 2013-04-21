package CamadaTransporte;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import EstruturasDados.Inteiro;

public class InputOutputThreadCliente extends Thread {

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

	Inteiro pacotesPerdidos;
	Inteiro pacotesRecebidos;
	Inteiro pacotesRecebidosCorretamente;
	
	int contadorTeste;
	
	public InputOutputThreadCliente(Inteiro windowBase,
			DatagramSocket socket,
			int windowSize, DatagramPacket[] packetInBuffer, int sizeInBytes,
			int percentualEscolhido, InetAddress IPDestino, int portDestino, ControladorDownload controle,
			Inteiro pacotesPerdidos, Inteiro pacotesRecebidos, Inteiro pacotesRecebidosCorretamente) {
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
		
		this.pacotesPerdidos = pacotesPerdidos;
		this.pacotesRecebidos = pacotesRecebidos;
		this.pacotesRecebidosCorretamente = pacotesRecebidosCorretamente;
		
		this.controle = controle;
		
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
				contadorTeste++;
				if(contadorTeste == 1){
					controle.setPauseState(true);
					System.out.println("PAUSEI O DOWNLOAD");
				}
				
				if(contadorTeste == 11){
					controle.setPauseState(false);
				}
				if (((int) (100 * Math.random())) >= percentualEscolhido) {
					
					pacotesRecebidos.incrementar();
					
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
							
							pacotesRecebidosCorretamente.incrementar();
							
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
							
							if(!controle.isCanceled() && !controle.isPaused()){
								UDPSocket.send(makeAck(sequenceNumber, IPDestino, portDestino, 0));
								
								if (indicePacoteRecebido == windowBase.getValor()) {
									int i = indicePacoteRecebido;
									while (i < totalPacotes && confirmacoes[i]) {
										i++;
										windowBase.incrementar();
									}
								}
								
							}else if(controle.isPaused()){
								UDPSocket.send(makeAck(windowBase.getValor(), IPDestino, portDestino, 1));
								pacotesRecebidosCorretamente.setValor(windowBase.getValor());
//								System.out.println("O CONTROLE FOI PAUSADO.. NADA AINDA.. MAS A WINDOW BASE IS " + windowBase.getValor());
								
								for(int i = windowBase.getValor(); i < windowBase.getValor() + windowSize; i++){
									confirmacoes[i] = false;
								}
								
								
							}else{
								UDPSocket.send(makeAck(windowBase.getValor(), IPDestino, portDestino, 2));
							}
							
							// System.out.println("this is the POINT 2: " +
							// indicePacoteRecebido + " recebendo pacotes");
							
							
						} else if ((indicePacoteRecebido < windowBase.getValor())
								&& (indicePacoteRecebido > (windowBase.getValor() - windowSize))){
							UDPSocket.send(makeAck(sequenceNumber,
									packet.getAddress(), packet.getPort(), 0));
						}
						
					}else if(packetStream[2] == 1){
						
						if(controle.isPaused()){
							UDPSocket.send(makeAck(windowBase.getValor(), IPDestino, portDestino, 1));
//							System.out.println("RECEBI UM PACOTE DE CONFIRMACAO DE PAUSA...");
						}else{
							UDPSocket.send(makeAck(0, IPDestino, portDestino, 0));
//							System.out.println("DESPAUSOU NO CLIENTE..");
						}
					}else{
						UDPSocket.send(makeAck(0, IPDestino, portDestino, 2));
						
						//esse pacote é enviado e o cliente já pode livrar-se da conexão. haverá um contador no servidor. após tanto tempo
						//sem receber acks, ele cancela o envio pois considera a conexão perdida.
					}
					
				} else {
					pacotesPerdidos.incrementar();
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
