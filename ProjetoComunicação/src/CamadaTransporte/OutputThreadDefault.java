package CamadaTransporte;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import EstruturasDados.Inteiro;

public class OutputThreadDefault extends Thread{

	int windowSize;
	Inteiro windowBase;
	Inteiro windowTop;
	
	int actualPacket;

	DatagramPacket[] packetOutBuffer;
	
	int[] timelimitPackets;
	Inteiro time;
	
	DatagramSocket UDPSocket;
	
	public OutputThreadDefault(Inteiro windowBase, DatagramSocket socket, DatagramPacket[] packetOutBuffer, int windowSize, 
			Inteiro windowTop, int[] timelimitPackets, Inteiro time){ //lembrar de colocar o control p versao adiante.
		
		this.windowSize = windowSize;
		this.windowBase = windowBase;
		this.windowTop = windowTop;
		
		actualPacket = windowBase.getValor();
		this.packetOutBuffer = packetOutBuffer;

		this.timelimitPackets = timelimitPackets;
		this.time = time;
		
		UDPSocket = socket;
		
	}
	
	
	public void run(){
		
		try{
			while(windowTop.getValor() < packetOutBuffer.length){
				
				actualPacket = windowTop.getValor();
				
				System.out.print(""); // para pegar para mais de 2 clientes
				if(actualPacket < windowBase.getValor() + windowSize){
					
					UDPSocket.send(packetOutBuffer[actualPacket]);
					timelimitPackets[actualPacket%(windowSize*2)] = time.getValor();
					
//						System.out.println("O CARA NORMAL ENVIOU O PACOTE       " + actualPacket);
					
					windowTop.incrementar();
					
//					System.out.println("já enviei até o cara "+ actualPacket +" e o limite é o cara " + (windowBase.getValor() + windowSize));
				}
				
			}
		}catch(Exception e){
			System.out.println("erro outputThreadServidor");
			e.printStackTrace();
		}
	}
	
}
