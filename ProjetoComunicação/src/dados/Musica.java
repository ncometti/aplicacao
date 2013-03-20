package dados;

import java.io.File;

public class Musica {

	// Mais Atributos ainda a definir

	// TODO Pesquisar como pegar as informacoes da musica pra usa-las como parametro 

	private File file; // o file disponibiliza varios atributos que seriam feitos aquis
	private String artista;
	private String titulo;
	private String album;
	private String faixa;
	private String data;
	private String copyright;
	private String comentarios;
	

	public Musica(File file, String artista, String titulo, String album,
			String faixa, String data, String copyright, String comentarios) {
		super();
		this.file = file;
		this.artista = artista;
		this.titulo = titulo;
		this.album = album;
		this.faixa = faixa;
		this.data = data;
		this.copyright = copyright;
		this.comentarios = comentarios;
	}

	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getAlbum() {
		return album;
	}
	
	public void setAlbum(String album) {
		this.album = album;
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public String getCopyright() {
		return copyright;
	}
	
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	
	public String getComentarios() {
		return comentarios;
	}
	
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public String getArtista() {
		return artista;
	}

	public void setArtista(String artista) {
		this.artista = artista;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}



	public String getFaixa() {
		return faixa;
	}



	public void setFaixa(String faixa) {
		this.faixa = faixa;
	}	

}