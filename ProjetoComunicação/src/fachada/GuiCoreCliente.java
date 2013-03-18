package fachada;

import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;

public interface GuiCoreCliente {

    // metodos relacionados ao player
    void iniciarPlayer() throws FileNotFoundException, JavaLayerException;
    void pausarPlayer();
    void reiniciarPlayer(); //retoma o download apos dar pausa
    void cancelarPlayer(); //cancela o dowload e apaga arquivo parcial
    
    //metodos relacionados a lista de musicas
    void atualizarListaServidor();
    void atualizarListaCliente();
    void deletar(); //deleta musica da lista do cliente (deletando o arquivo)
    void ordenar(); //o cliente pode ordenar as musicas que baixou por data de download ou por ordem alfabetica, etc
    void buscarListaCliente(); //buscar musicas baixadas por nome ou artista
    void buscarListaServidor(); //buscar musicas do servidor por nome ou artista
    
    // metodo relacionado a taxa de perda de pacotes
    void setTolerancia();
    
}