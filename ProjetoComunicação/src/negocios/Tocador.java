package negocios;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Tocador {
public static void main (String args[]){
 FileInputStream in;
 try {
  //Inicializa o FileInputStream com o endereço do arquivo para tocar
  in = new FileInputStream("wonderwall.mp3");

  //Cria uma instancia da classe player passando para ele o InpuStream do arquivo
  Player p = new Player(in);

  //executa o som
  p.play();  
  
 } catch (FileNotFoundException e) {
  e.printStackTrace();
 } catch (JavaLayerException e) {
  e.printStackTrace();
 }
}
}