package CamadaTransporte;

import java.net.DatagramSocket;

import EstruturasDados.Inteiro;

public class ControladorDownload {

	boolean paused;
	boolean canceled;
	
	Inteiro pacotesPerdidos; //cliente
	Inteiro pacotesRecebidos; //cliente
	Inteiro pacotesRecebidosCorretamente; //cliente
	
	Inteiro pacotesEnviados; //servidor
	
	Inteiro windowBase;
	
	DatagramSocket UDPSocket;
	
	public ControladorDownload() throws Exception{
		paused = false;
		canceled = false;
		
		pacotesPerdidos =  new Inteiro(0);
		pacotesRecebidos = new Inteiro(0);
		pacotesRecebidosCorretamente = new Inteiro(0);
		
		pacotesEnviados = new Inteiro(0); //servidor
		
		windowBase = new Inteiro(0);
		
		UDPSocket = new DatagramSocket();
	}
	
	public DatagramSocket getUDPSocket() {
		return UDPSocket;
	}

	public void setUDPSocket(DatagramSocket uDPSocket) {
		UDPSocket = uDPSocket;
	}

	public Inteiro getPacotesPerdidos() {
		return pacotesPerdidos;
	}

	public void setPacotesPerdidos(Inteiro pacotesPerdidos) {
		this.pacotesPerdidos = pacotesPerdidos;
	}

	public Inteiro getPacotesRecebidos() {
		return pacotesRecebidos;
	}

	public void setPacotesRecebidos(Inteiro pacotesRecebidos) {
		this.pacotesRecebidos = pacotesRecebidos;
	}

	public Inteiro getPacotesRecebidosCorretamente() {
		return pacotesRecebidosCorretamente;
	}

	public void setPacotesRecebidosCorretamente(Inteiro pacotesRecebidosCorretamente) {
		this.pacotesRecebidosCorretamente = pacotesRecebidosCorretamente;
	}

	public Inteiro getWindowBase() {
		return windowBase;
	}

	public void setWindowBase(Inteiro windowBase) {
		this.windowBase = windowBase;
	}

	public boolean isPaused(){
		
		return paused;
	}
	
	public boolean isCanceled(){
		
		return canceled;
	}

	public void setPauseState(boolean paused) {
		this.paused = paused;
	}

	public void setCancelState(boolean canceled) {
		this.canceled = canceled;
	}
	
}
