package CamadaTransporte;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import EstruturasDados.Inteiro;

public class OutputThreadServidor extends Thread{

	int windowSize;
	Inteiro windowBase;
	Inteiro windowTop;
	
	int actualPacket;

	DatagramPacket[] packetOutBuffer;
	
	int[] timelimitPackets;
	Inteiro time;
	
	DatagramSocket UDPSocket;
	
	ControladorDownload controle;
	Inteiro pacotesEnviados;
	
	public OutputThreadServidor(Inteiro windowBase, DatagramSocket socket, DatagramPacket[] packetOutBuffer, int windowSize, 
			Inteiro windowTop, int[] timelimitPackets, Inteiro time, ControladorDownload controle, Inteiro pacotesEnviados){ //lembrar de colocar o control p versao adiante.
		
		this.windowSize = windowSize;
		this.windowBase = windowBase;
		this.windowTop = windowTop;
		
		actualPacket = windowBase.getValor();
		this.packetOutBuffer = packetOutBuffer;

		this.timelimitPackets = timelimitPackets;
		this.time = time;
		
		UDPSocket = socket;
		
		this.controle = controle;
		
		this.pacotesEnviados = pacotesEnviados;
		
	}
	
	public void run(){
		
		try{
			while(windowTop.getValor() < packetOutBuffer.length && !controle.isCanceled()){
				
				actualPacket = windowTop.getValor();
				
				if(!controle.isPaused()){
					
					System.out.print(""); // para pegar para mais de 2 clientes
					if(actualPacket < windowBase.getValor() + windowSize){
						
						UDPSocket.send(packetOutBuffer[actualPacket]);
						timelimitPackets[actualPacket%(windowSize*2)] = time.getValor();
						
//						System.out.println("O CARA NORMAL ENVIOU O PACOTE       " + actualPacket);
						
						windowTop.incrementar();
						pacotesEnviados.incrementar();
						
//					System.out.println("já enviei até o cara "+ actualPacket +" e o limite é o cara " + (windowBase.getValor() + windowSize));
					}
				}else{
//					System.out.println("CONTROL IS PAUSED AT LEAST... AND THE WINDOW BASE IS " + windowBase.getValor());
					byte[] confirmacaoPausa = new byte[3];
					confirmacaoPausa[2] = 1;
					DatagramPacket pacoteConfirmacao = new DatagramPacket(confirmacaoPausa, confirmacaoPausa.length,
							packetOutBuffer[0].getAddress(), packetOutBuffer[0].getPort());
					UDPSocket.send(pacoteConfirmacao);
					this.sleep(1000);
				}
				
				
			}
		}catch(Exception e){
			System.out.println("erro outputThreadServidor");
			e.printStackTrace();
		}
	}
	
}
