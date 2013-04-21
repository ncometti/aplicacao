package CamadaTransporte;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ResourceBundle.Control;

import EstruturasDados.Inteiro;

public class InputThreadServidor extends Thread {

	Inteiro windowBase;
	Inteiro windowTop;
	
	int windowSize;
	int totalPacotes;
	DatagramSocket UDPSocket;

	boolean[] confirmacoes;

	static int ackSize = 3;
	
	ControladorDownload controle;

	/*
	 * DEFINIÇÃO DO ACK
	 * 
	 * byte[0-1] sequenceNumber byte[2] Pause, play e stop
	 */

	public InputThreadServidor(Inteiro windowBase, DatagramSocket socket,
			int totalPacotes, int windowSize, boolean[] estadoChegadaPacotes, ControladorDownload controle, Inteiro windowTop) { //lembrar de add controle.
		this.windowBase = windowBase;
		this.windowTop = windowTop;
		this.windowSize = windowSize;
		this.totalPacotes = totalPacotes;
		UDPSocket = socket;

		confirmacoes = estadoChegadaPacotes;
		
		this.controle = controle;
	}

	public void run() {

		try {

			while (windowBase.getValor() < totalPacotes) {

				byte[] ackStream = new byte[ackSize];

				DatagramPacket ack = new DatagramPacket(ackStream,
						ackStream.length);

				UDPSocket.receive(ack);
				
				if(ackStream[2] == 1){
					controle.setPauseState(true);
					
//					System.out.println("PAUSOU NO CLIENTE AO MENOS.. " + windowBase.getValor());
					
					byte[] parametro = new byte[2];
					for (int i = 0; i < 2; i++) {
						parametro[i] = ackStream[i];
					}
					
					int sequenceNumber = byteToInt(parametro);
					int sequenceNumberBase = (windowBase.getValor())
							% (windowSize * 2);
					
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
					
					windowBase.setValor(indicePacoteRecebido);
					windowTop.setValor(indicePacoteRecebido);
					
					for(int i = windowBase.getValor(); i < windowBase.getValor() + windowSize; i++){
  
					}
					
				}else if(ackStream[2] == 2){
					controle.setCancelState(true);
					
				}else{
					
					if(controle.isPaused()){
						
//						windowTop.setValor(windowBase.getValor());
						
						controle.setPauseState(false);
						
//						System.out.println("DESPAUSEI.. VÁ ENTENDER!");
						
					}else{
						
						byte[] parametro = new byte[2];
						for (int i = 0; i < 2; i++) {
							parametro[i] = ackStream[i];
						}
						int sequenceNumber = byteToInt(parametro);
						int sequenceNumberBase = (windowBase.getValor())
								% (windowSize * 2);
						
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
						
//						System.out.println("ENTREI NESSA PARTE..." + indicePacoteRecebido);
						
						confirmacoes[indicePacoteRecebido] = true;
						if (indicePacoteRecebido == windowBase.getValor()) {
							int i = indicePacoteRecebido;
							while (i < confirmacoes.length && confirmacoes[i]) {
								i++;
								windowBase.incrementar();
//								System.out.println(windowBase.getValor());
								// System.out.println("this is the clientWindowBase being increased: "
								// + windowBase.getValor());
								
							}
						}
					}
				}
			}

		} catch (Exception e) {
			System.out.println("erro inputThreadServidor");
			e.printStackTrace();
		}
	}

	private int byteToInt(byte[] b) {
		return ((b[1] & 0xff) << 8) | (b[0] & 0xff);
	}

}
