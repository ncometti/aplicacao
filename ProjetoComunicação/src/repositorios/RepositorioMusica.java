package repositorios;

import java.text.Normalizer;
import java.util.Vector;

import dados.Musica;

public class RepositorioMusica implements Repositorio{

	private Vector<Musica> vector;

	public RepositorioMusica() {

		this.vector = new Vector<Musica>();
	}

	public void inserir(Musica musica) {

		this.vector.add(musica);

	}

	public void remover(String nomeArquivo) {
		int i = getIndice(nomeArquivo);
		this.vector.remove(i);
	}

	private int getIndice(String nomeArquivo) {
		String aux;
		boolean achou = false;
		int i = 0;
		while ((!achou) && (i < this.vector.size())) {
			aux = this.vector.get(i).getFile().getName();
			if (aux.equals(nomeArquivo)) {
				achou = true;
			} else {
				i++;
			}
		}
		return i;
	}

	public boolean existe(String nomeArquivo) {
		int i = this.getIndice(nomeArquivo);
		return (i != this.vector.size());
	}

	public Object procurarPorTitulo(String titulo) {

		titulo = Normalizer.normalize(titulo, Normalizer.Form.NFD);
		titulo = titulo.replaceAll("[^\\p{ASCII}]", "");

		Vector<Musica> vector = new Vector<Musica>();
		for (int i = 0; i < this.vector.size(); i++) {

			int j = 0;
			boolean achou = false;
			while ((j <= this.vector.get(i).getTitulo().length() - titulo.length()) && !achou) { 

				String nomeTmp = Normalizer.normalize(this.vector.get(i).getTitulo(), Normalizer.Form.NFD);
				nomeTmp = nomeTmp.replaceAll("[^\\p{ASCII}]", "");	

				if (titulo.equalsIgnoreCase( nomeTmp.substring( j, j + titulo.length()) )) { 
					vector.add(this.vector.get(i));
					achou = true;

				} else {
					j++;
				}

			}

		}
		return vector;
	}

	public Object procurarPorArtista(String artista) {
		artista = Normalizer.normalize(artista, Normalizer.Form.NFD);
		artista = artista.replaceAll("[^\\p{ASCII}]", "");

		Vector<Musica> vector = new Vector<Musica>();
		for (int i = 0; i < this.vector.size(); i++) {

			int j = 0;
			boolean achou = false;
			while ((j <= this.vector.get(i).getArtista().length() - artista.length()) && !achou) { 

				String nomeTmp = Normalizer.normalize(this.vector.get(i).getArtista(), Normalizer.Form.NFD);
				nomeTmp = nomeTmp.replaceAll("[^\\p{ASCII}]", "");	

				if (artista.equalsIgnoreCase( nomeTmp.substring( j, j + artista.length()) )) { 
					vector.add(this.vector.get(i));
					achou = true;

				} else {
					j++;
				}

			}

		}
		return vector;
	}

	public Object procurarPorAlbum(String album) {
		album = Normalizer.normalize(album, Normalizer.Form.NFD);
		album = album.replaceAll("[^\\p{ASCII}]", "");

		Vector<Musica> vector = new Vector<Musica>();
		for (int i = 0; i < this.vector.size(); i++) {

			int j = 0;
			boolean achou = false;
			while ((j <= this.vector.get(i).getAlbum().length() - album.length()) && !achou) { 

				String nomeTmp = Normalizer.normalize(this.vector.get(i).getAlbum(), Normalizer.Form.NFD);
				nomeTmp = nomeTmp.replaceAll("[^\\p{ASCII}]", "");	

				if (album.equalsIgnoreCase( nomeTmp.substring( j, j + album.length()) )) { 
					vector.add(this.vector.get(i));
					achou = true;

				} else {
					j++;
				}

			}

		}
		return vector;
	}

}