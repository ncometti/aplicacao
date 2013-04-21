package CamadaTransporte;

import EstruturasDados.Inteiro;

public class TimeCounter extends Thread{

	Inteiro time;
	boolean keepRunning;
	
	public TimeCounter(Inteiro time){
		this.time = time;
		keepRunning = true;
	}
	
	public void run(){
		
		while(keepRunning){
			int actualTime = (int) System.currentTimeMillis();
			if(actualTime - time.getValor() > 10000){
				keepRunning = false;
			}else{
				time.setValor(actualTime);
			}
		}
	}
	
	public boolean keepOn(){
		return keepRunning;
	}
}
