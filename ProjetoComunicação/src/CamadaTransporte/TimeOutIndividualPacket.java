package CamadaTransporte;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Timer;
import java.util.TimerTask;

import EstruturasDados.Boleano;

public class TimeOutIndividualPacket extends Thread{
	
	Timer temporizador;
	DatagramPacket sent;
	Boleano arrived;
	DatagramSocket UDPSocket;
	
	int i;
	
	public TimeOutIndividualPacket(DatagramPacket sent, Boleano arrived, DatagramSocket UDPSocket, int i){
		this.sent = sent;
		this.arrived = arrived;
		this.UDPSocket = UDPSocket;
		
		temporizador = new Timer(true);
		
		this.i = i;
	}
	
	public void run(){
		
		try{
			
			IndividualTask task = new IndividualTask(sent, arrived, UDPSocket, i);
			temporizador.schedule(task, 600, 1000);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}

class IndividualTask extends TimerTask{
	
	DatagramPacket sent;
	Boleano arrived;
	int time;
	DatagramSocket UDPSocket;
	
	int i;
	
	public IndividualTask(DatagramPacket sent, Boleano arrived, DatagramSocket UDPSocket, int i){
		this.sent = sent;
		this.arrived = arrived;
		time = 0;
		this.UDPSocket = UDPSocket;
		
		this.i = i;
	}
	
	public void run(){
		
		try{
			int lol = 0;
			while(!arrived.getValue()){
				time++;
				lol++;
				if(time == 5){
					System.out.println("precisaram de mim... o erro era aqui mermo. fique feliz! " + i);
					time = 0;
					UDPSocket.send(sent);
					
				}
				if(lol == 100){
					break;
				}
			}
			this.cancel();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}