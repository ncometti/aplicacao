package CamadaTransporte;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import EstruturasDados.Boleano;
import EstruturasDados.Inteiro;

public class SRSocket {

	int portOrigem;
	int portDestino;
	InetAddress IPDestino;

	DatagramSocket UDPSocket;
	static int socketBufferSizes = 200000;

	static int packetSize = 490; // in bytes, headers not included. supposing
									// the header will have 10 bytes
	static int headerSize = 10;

	static int windowSize = 10; // perguntar p o professor
	
	public SRSocket(DatagramSocket UDPSocket, InetAddress IPDestino,
			int portDestino, int clientID) throws Exception {

		this.UDPSocket = UDPSocket;

		UDPSocket.setReceiveBufferSize(socketBufferSizes);
		UDPSocket.setSendBufferSize(socketBufferSizes);

		this.portOrigem = UDPSocket.getLocalPort();
		this.IPDestino = IPDestino;
		this.portDestino = portDestino;

		byte[] synAckStream = new byte[2];
		DatagramPacket synAckPacket = new DatagramPacket(synAckStream,
				synAckStream.length, IPDestino, portDestino);

		UDPSocket.send(synAckPacket);
		
	}

	public SRSocket(InetAddress IPDestino, int portDestino, int id) throws Exception {
		
		UDPSocket = new DatagramSocket();

		UDPSocket.setReceiveBufferSize(socketBufferSizes);
		UDPSocket.setSendBufferSize(socketBufferSizes);

		this.portOrigem = UDPSocket.getLocalPort();

		System.out.println(portOrigem);
		
		
		byte[] synStream = new byte[2];
		synStream[0] = (byte) id;
		DatagramPacket synPacket = new DatagramPacket(synStream,
				synStream.length, IPDestino, portDestino);
		
		Boleano lossSuportSyn = new Boleano(false);
		TimeOutIndividualPacket timeOutSyn = new TimeOutIndividualPacket(synPacket, lossSuportSyn, UDPSocket, id);
		timeOutSyn.start();
		
		System.out.println("Porta: " + UDPSocket.getLocalPort());
		UDPSocket.send(synPacket);

		byte[] synAckStream = new byte[2];
		DatagramPacket synAckPacket = new DatagramPacket(synAckStream,
				synAckStream.length);
		UDPSocket.receive(synAckPacket);
		lossSuportSyn.setValue(true);
		timeOutSyn.join();
		System.out.println("passei por aki");
		
		this.portDestino = synAckPacket.getPort();
		this.IPDestino = IPDestino;
	}

	public void enviar(byte[] sendStream) throws Exception {

		DatagramPacket[] packetOutBuffer = intoPackets(sendStream);

		Inteiro windowBase = new Inteiro(0);
		Inteiro windowTop = new Inteiro(0);
		
		Inteiro time = new Inteiro(0);
		int[] timelimitPackets = new int[windowSize*2];
		
		boolean[] estadoChegadaPacotes = new boolean[packetOutBuffer.length];

		InputThreadDefault recepcaoAcks = new InputThreadDefault(windowBase,
				UDPSocket, packetOutBuffer.length, windowSize, estadoChegadaPacotes, windowTop);
		
		OutputThreadDefault envioPacotes = new OutputThreadDefault(
				windowBase, UDPSocket, packetOutBuffer, windowSize, windowTop, timelimitPackets, time);
		
		TimeOutThreadServidor timeoutThread = new TimeOutThreadServidor(packetOutBuffer, windowSize, UDPSocket,
				estadoChegadaPacotes, time, timelimitPackets, windowBase, windowTop, new ControladorDownload(), new Inteiro(0));
		
		envioPacotes.start();
		recepcaoAcks.start();
		timeoutThread.start();

		envioPacotes.join();
		recepcaoAcks.join();
		timeoutThread.join();
	}
	
	public void enviarFile(byte[] sendStream, int initialWindowBase, int portDestino, ControladorDownload controle) throws Exception {
		
		DatagramPacket[] packetOutBuffer = intoFilePackets(sendStream, portDestino, initialWindowBase);
		
		System.out.println("tamanho!          ! " + packetOutBuffer.length);

		Inteiro windowBase = new Inteiro(0);
		Inteiro windowTop = new Inteiro(0);
		
		Inteiro time = new Inteiro(0);
		int[] timelimitPackets = new int[windowSize*2];
		
		boolean[] estadoChegadaPacotes = new boolean[packetOutBuffer.length];

		Inteiro pacotesEnviados = new Inteiro(0);
		
		InputThreadServidor recepcaoAcks = new InputThreadServidor(windowBase,
				controle.getUDPSocket(), packetOutBuffer.length, windowSize, estadoChegadaPacotes, controle, windowTop);
		
		OutputThreadServidor envioPacotes = new OutputThreadServidor(
				windowBase, controle.getUDPSocket(), packetOutBuffer, windowSize, windowTop, timelimitPackets, time, controle, pacotesEnviados);
		
		TimeOutThreadServidor timeoutThread = new TimeOutThreadServidor(packetOutBuffer, windowSize, controle.getUDPSocket(),
				estadoChegadaPacotes, time, timelimitPackets, windowBase, windowTop, controle, pacotesEnviados);
		
		envioPacotes.start();
		recepcaoAcks.start();
		timeoutThread.start();

		envioPacotes.join();
		recepcaoAcks.join();
		timeoutThread.join();
	}

	public void receber(byte[] receiveStream) throws Exception {

		int size = receiveStream.length;
		int sizeInPackets;

		if (size > (size / packetSize) * packetSize) {
			sizeInPackets = size / packetSize + 1;
		} else {
			sizeInPackets = size / packetSize;
		}

		DatagramPacket[] packetInBuffer = new DatagramPacket[sizeInPackets];

		InputOutputThreadDefault envioAcks = new InputOutputThreadDefault(
				new Inteiro(0), UDPSocket, windowSize, packetInBuffer, size, 0, IPDestino, portDestino); //lembrar que o 0 no parametro é o percentual de perda.
		
		envioAcks.start();

		envioAcks.join();
		
		intoStream(packetInBuffer, size, receiveStream);

	}
	
	public void receberFile(byte[] receiveStream, ControladorDownload controle, int portDestino) throws Exception {

		int size = receiveStream.length;
		int sizeInPackets;

		if (size > (size / packetSize) * packetSize) {
			sizeInPackets = size / packetSize + 1;
		} else {
			sizeInPackets = size / packetSize;
		}

		DatagramPacket[] packetInBuffer = new DatagramPacket[sizeInPackets];

		InputOutputThreadCliente envioAcks = new InputOutputThreadCliente(
				controle.getWindowBase(), controle.getUDPSocket(), windowSize, packetInBuffer, size, 0, IPDestino, portDestino,
				controle, controle.getPacotesPerdidos(), controle.getPacotesRecebidos(), controle.getPacotesRecebidosCorretamente()); //lembrar que o 5 no parametro é o percentual de perda.
		
		envioAcks.start();

		envioAcks.join();
		
		intoStream(packetInBuffer, size, receiveStream);

	}

	private DatagramPacket makePacket(byte[] Segment, int sequenceNumber)
			throws Exception {

		byte[] num = convertToByte(sequenceNumber, 2);

		for (int j = 0; j < 2; j++) { // colocando no cabecalho no pacote
			Segment[j] = num[j];
		}

		DatagramPacket enviarPacote = new DatagramPacket(Segment,
				Segment.length, IPDestino, portDestino);

		return enviarPacote;
	}
	
	private DatagramPacket makeFilePacket(byte[] Segment, int sequenceNumber, int portDestino)
			throws Exception {

		byte[] num = convertToByte(sequenceNumber, 2);

		for (int j = 0; j < 2; j++) { // colocando no cabecalho no pacote
			Segment[j] = num[j];
		}

		DatagramPacket enviarPacote = new DatagramPacket(Segment,
				Segment.length, IPDestino, portDestino);

		return enviarPacote;
	}

	private byte[] convertToByte(int valor, int numeroBytes) {
		byte[] bytes = new byte[numeroBytes];
		for (int i = 0; i < numeroBytes; i++) {
			bytes[i] = (byte) (valor >>> (i * 8));
		}

		return bytes;
	}

	private DatagramPacket[] intoPackets(byte[] sendStream) throws Exception {

		DatagramPacket[] packetOutBuffer;

		if (sendStream.length > (sendStream.length / packetSize) * packetSize) {
			packetOutBuffer = new DatagramPacket[((int) (sendStream.length / packetSize)) + 1];
		} else {
			packetOutBuffer = new DatagramPacket[((int) (sendStream.length / packetSize))];
		}

		int i;
		for (i = 0; i < packetOutBuffer.length - 1; i++) {

			byte[] Segment = new byte[packetSize + headerSize];

			for (int j = 0; j < packetSize; j++) {
				Segment[j + headerSize] = sendStream[i * packetSize + j];
			}

			int numeroSequencia = i % (windowSize * 2);
			
			packetOutBuffer[i] = makePacket(Segment, numeroSequencia);
		}

		byte[] Segment = new byte[sendStream.length - i * packetSize
				+ headerSize];
		for (int j = 0; j < (sendStream.length - i * packetSize); j++) {

			Segment[j + headerSize] = sendStream[j + i * packetSize];
		}
		int numeroSequencia = i % (windowSize * 2);
		packetOutBuffer[i] = makePacket(Segment, numeroSequencia);

		return packetOutBuffer;
	}
	
	private DatagramPacket[] intoFilePackets(byte[] sendStream, int portDestino, int initialWindowBase) throws Exception {

		DatagramPacket[] packetOutBuffer;

		if (sendStream.length > (sendStream.length / packetSize) * packetSize) {
			packetOutBuffer = new DatagramPacket[((int) (sendStream.length / packetSize)) + 1 - initialWindowBase];
		} else {
			packetOutBuffer = new DatagramPacket[((int) (sendStream.length / packetSize)) - initialWindowBase];
		}

		int i;
		for (i = initialWindowBase; i < packetOutBuffer.length - 1 + initialWindowBase; i++) {

			byte[] Segment = new byte[packetSize + headerSize];

			for (int j = 0; j < packetSize; j++) {
				Segment[j + headerSize] = sendStream[i * packetSize + j];
			}

			int numeroSequencia = (i - initialWindowBase) % (windowSize * 2);
			
			packetOutBuffer[i - initialWindowBase] = makeFilePacket(Segment, numeroSequencia, portDestino);
		}

		byte[] Segment = new byte[sendStream.length - i * packetSize + headerSize];
		for (int j = 0; j < (sendStream.length - i * packetSize); j++) {

			Segment[j + headerSize] = sendStream[j + i * packetSize];
		}
		int numeroSequencia = (i - initialWindowBase) % (windowSize * 2);
		packetOutBuffer[i - initialWindowBase] = makeFilePacket(Segment, numeroSequencia, portDestino);

		return packetOutBuffer;
	}

	private byte[] intoStream(DatagramPacket[] receivePackets, int sizeInBytes,
			byte[] output) {

		for (int i = 0; i < receivePackets.length; i++) {
			for (int j = 0; j < receivePackets[i].getData().length - headerSize; j++) {
				output[i * packetSize + j] = receivePackets[i].getData()[j
						+ headerSize];
			}
		}

		return output;
	}
	
	public void Close(){
		UDPSocket.close();
	}
	
	static int byteToInt(byte[] b) {
		return ((b[3] & 0xff) << 24) | ((b[2] & 0xff) << 16) | ((b[1] & 0xff) << 8) | (b[0] & 0xff);
	}
}
