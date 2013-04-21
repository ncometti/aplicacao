package dados;

import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;

public class MusicaSSP {
	
	private final SimpleStringProperty tituloProp;
	private final SimpleStringProperty artistaProp;
	private final SimpleStringProperty albumProp;
	private final SimpleStringProperty generoProp;
	private final SimpleStringProperty duracaoProp;
	
	public MusicaSSP(Musica musica) {
		this.tituloProp = new SimpleStringProperty(musica.getTitulo());
		this.artistaProp = new SimpleStringProperty(musica.getArtista());
		this.generoProp = new SimpleStringProperty(musica.getGenero());
		this.duracaoProp = new SimpleStringProperty(musica.getDuracao());
		this.albumProp = new SimpleStringProperty(musica.getAlbum());
	}
	

	public String getTituloProp() {
		return tituloProp.get();
	}

	public String getArtistaProp() {
		return artistaProp.get();
	}

	public String getGeneroProp() {
		return generoProp.get();
	}

	public String getDuracaoProp() {
		return duracaoProp.get();
	}

	public String getAlbumProp() {
		return albumProp.get();
	}
}
