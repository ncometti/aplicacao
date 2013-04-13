package dados;

import java.util.Vector;

public class Cliente {
	String nome;
	String IP;
	Vector<Download> downloads;


	public Cliente(String nome, String IP, Vector<Download> downloads) {
		this.nome = nome;
		this.IP = IP;
		this.downloads = downloads;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getIP() {
		return IP;
	}


	public void setIP(String IP) {
		this.IP =IP;
	}


	public Vector<Download> getDownloads() {
		return downloads;
	}


	public void setDownloads(Vector<Download> downloads) {
		this.downloads = downloads;
	}
	
	
	
	
}