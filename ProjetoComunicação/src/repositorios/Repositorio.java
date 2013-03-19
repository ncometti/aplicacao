package repositorios;

import dados.Musica;

public interface Repositorio {
	// TODO Ainda ha muito o que adicionar aqui, mas por enquanto eh o necessario.

	void inserir(Musica musica);

	void remover(String musica);

	boolean existe(String musica);

	Object atualizarLista(); // Object porque nao sabemos qual estrutura usar ainda



}