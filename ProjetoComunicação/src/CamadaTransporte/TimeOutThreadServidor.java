package CamadaTransporte;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Timer;
import java.util.TimerTask;
import EstruturasDados.Inteiro;


public class TimeOutThreadServidor extends Thread{
	
	boolean[] estadoChegadaPacotes;
	Inteiro windowBase;
	Inteiro windowTop;
	
	DatagramSocket UDPSocket;
	int windowSize;
	DatagramPacket[] packetOutBuffer;
	
	Timer temporizador;
	Inteiro time;
	int[] timelimitPackets;
	
	ControladorDownload controle;
	Inteiro pacotesEnviados;
	
	
	public TimeOutThreadServidor(DatagramPacket[] packetOutBuffer, int windowSize, DatagramSocket socket, boolean[] estadoChegadaPacotes, 
			Inteiro time, int[] timelimitPackets, Inteiro windowBase, Inteiro windowTop, ControladorDownload controle, Inteiro pacotesEnviados){
		this.packetOutBuffer = packetOutBuffer;
		this.windowSize = windowSize;
		UDPSocket = socket;
		
		this.windowBase = windowBase;
		this.windowTop = windowTop;
		temporizador = new Timer(true);
		
		this.time = time;
		this.timelimitPackets = timelimitPackets;
		
		this.estadoChegadaPacotes = estadoChegadaPacotes;
		
		this.controle = controle;
		this.pacotesEnviados = pacotesEnviados;
	}
	
	public void run(){
		Task task = new Task(packetOutBuffer, windowSize, UDPSocket, estadoChegadaPacotes, windowBase, windowTop, time, 
				timelimitPackets, controle, pacotesEnviados);
		temporizador.schedule(task, 0, 14);
	}

}

class Task extends TimerTask{
	Inteiro time;
	int sequenceRange;
	int[] timelimitPackets;
	
	ControladorDownload controle;
	
	Inteiro windowBase;
	Inteiro windowTop;
	int windowSize;
	
	boolean[] estadoChegadaPacotes;
	DatagramSocket UDPSocket;
	DatagramPacket[] packetOutBuffer;
	
	Inteiro pacotesEnviados;
	
	public Task(DatagramPacket[] packetOutBuffer, int windowSize, DatagramSocket socket, boolean[] estadoChegadaPacotes, 
			Inteiro windowBase, Inteiro windowTop, Inteiro time, int[] timelimitPackets, ControladorDownload controle,
			Inteiro pacotesEnviados){
		this.time = time;
		this.windowSize = windowSize;
		this.windowBase = windowBase;
		this.windowTop = windowTop;
		
		this.controle = controle;
		
		sequenceRange = windowSize*2;
		this.timelimitPackets = timelimitPackets;
		this.estadoChegadaPacotes = estadoChegadaPacotes;
		this.packetOutBuffer = packetOutBuffer;
		
		UDPSocket = socket;
		
		this.pacotesEnviados = pacotesEnviados;
	}
	
	public void run(){
		
		try{
			
			if(windowBase.getValor() < estadoChegadaPacotes.length){
				
				time.incrementar();
				
				for(int i = windowBase.getValor(); i < windowTop.getValor(); i++){
					
					//System.out.println("checando o timeout do cara " + i);
					
					if(!estadoChegadaPacotes[i]){
						
//						System.out.println("ainda nao chegou o cara " + i +" "+ timelimitPackets[i] +" "+ time.getValor());
						
						if((time.getValor() - timelimitPackets[i%sequenceRange]) > 3){
							
//							System.out.println("deu time out no cara " + i +" "+ timelimitPackets[i%sequenceRange] +" "+ time.getValor());
							
							timelimitPackets[i%sequenceRange] = time.getValor();
//							System.out.println(packetOutBuffer[i].getData());
							UDPSocket.send(packetOutBuffer[i]);
//							System.out.println("O TIME OUT ENVIOU O PACOTE DE NUMERO " + i);
							pacotesEnviados.incrementar();
						}
					}
				}
				
				
			}else{
				System.out.println("TO AQUI, FELIPE!! A THREAD TO TIME OUT AINDA NAO ACABOU!");
				this.cancel();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}