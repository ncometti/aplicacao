package dados;

public class Musica {
	
	// Mais Atributos ainda a definir

	// TODO Pesquisar como pegar as informacoes da musica pra usa-las como parametro 
	
	private String artista;
	private String caminho;
	
	public Musica(String artista, String caminho) {
		this.artista = artista;
		this.caminho = caminho;
	}
	
	public String getArtista() {
		return artista;
	}
	
	public void setArtista(String artista) {
		this.artista = artista;
	}
	
	public String getCaminho() {
		return caminho;
	}
	
	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}	
	
}
