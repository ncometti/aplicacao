package dados;

public class Download {
	private Musica musica;
	private int progresso;
	
	public Download(Musica musica) {
		super();
		this.musica = musica;
		this.progresso = 0;
	}

	public int getProgresso() {
		return progresso;
	}
	
	public void setProgresso(int progresso) {
		this.progresso = progresso;
	}

	public Musica getMusica() {
		return musica;
	}
	
	public void setMusica(Musica musica) {
		this.musica = musica;
	}
		
}