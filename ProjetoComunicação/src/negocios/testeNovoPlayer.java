package negocios;

public class testeNovoPlayer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Thread musica = new NovoPlayer();
		((NovoPlayer) musica).setFile("wonderwall.mp3");
		musica.start();
		

	}

}
