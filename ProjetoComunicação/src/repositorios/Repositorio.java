package repositorios;

import dados.Musica;

public interface Repositorio {
	// TODO Ainda ha muito o que adicionar aqui, mas por enquanto eh o necessario.

	void inserir(Musica musica);

	void remover(String nomeArquivo);

	boolean existe(String nomeArquivo);
	
	Object procurarPorTitulo(String titulo);
	
	Object procurarPorArtista(String artista);
	
	Object procurarPorAlbum(String album);
	
	Object getLista();

//	Object atualizarLista(); // Object porque nao sabemos qual estrutura usar ainda



}