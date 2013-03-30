package fachada;

import java.util.Vector;

import dados.Musica;

public interface GuiCoreCliente {

	// metodos relacionados ao player
	void press_play(); // inicia, pausa ou retoma musica

	void press_pause();

	void press_stop(); // para a musica

	void press_prox(); // passa para a proxima musica da lista

	void press_ant(); // volta para a musica anterior

	// metodos relacionados ao download
	void iniciarDownload();

	void pausarDownload();

	void reiniciarDownload(); // retoma o download apos dar pausa

	void cancelarDownload(); // cancela o download e apaga arquivo parcial

	// metodos relacionados a lista de musicas
	void atualizarListaServidor();

	Vector<Musica> atualizarListaCliente();

	Vector<Musica> getLista();

	void deletar(); // deleta musica da lista do cliente (deletando o arquivo)

	void ordenar(); // o cliente pode ordenar as musicas que baixou por data de
					// download ou por ordem alfabetica, etc

	Vector<Musica> buscarListaCliente(String str); // buscar musicas baixadas
													// por nome ou artista

	Vector<Musica> buscarListaServidor(String str); // buscar musicas do
													// servidor por nome ou
	// artista

	// metodo relacionado a taxa de perda de pacotes
	void setTolerancia(int tolerancia);

	// metodo relacionado ao nome do usuario
	void setUserName(String nome);

	// metodo relacionado ao IP do usuario
	void setServerIP(String ip);
}