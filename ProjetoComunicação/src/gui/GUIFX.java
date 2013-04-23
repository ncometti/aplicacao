package gui;

import org.eclipse.swt.widgets.Shell;

import negocios.MP3Player;
import fachada.FachadaCliente;
import fachada.GuiCoreCliente;
import gui.GUIjavaFX.MediaControl;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.SliderBuilder;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;

public class GUIFX extends Application {

	private GuiCoreCliente gcc;
	private String pastaMusicas = "musicas";
	private MP3Player mp3;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage stage) throws Exception {
		gcc = new FachadaCliente(pastaMusicas);
		mp3 = new MP3Player(gcc.getLista(), 0);
		stage.setTitle("muzika");

		Group raiz = new Group();
		Scene cena = new Scene(raiz, 800, 600, Color.BLACK);

		MenuBar menuBar = new MenuBar();
		menuBar.setFocusTraversable(false);
		menuBar.prefWidthProperty().bind(cena.widthProperty());
		final Menu arquivoMenu = new Menu("Arquivo");
		MenuItem attMusicas = new MenuItem("Atualizar Músicas");
		MenuItem attServidor = new MenuItem("Atualizar Servidor");
		MenuItem sair = new MenuItem("Sair");
		sair.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				System.exit(0);
			}

		});
		arquivoMenu.getItems().addAll(attMusicas, attServidor, sair);

		final Menu editarMenu = new Menu("Editar");
		MenuItem editarTaxaErro = new MenuItem("Mudar Taxa de Erro");
		editarMenu.getItems().add(editarTaxaErro);
		final Menu helpMenu = new Menu("Ajuda");
		MenuItem sobreHelp = new MenuItem("Sobre");
		helpMenu.getItems().add(sobreHelp);
		menuBar.getMenus().addAll(arquivoMenu, editarMenu, helpMenu);
		raiz.getChildren().add(menuBar);

		TabPane tabs = new TabPane();
		tabs.setSide(Side.LEFT);
		BorderPane musicas = new BorderPane();
		TableView<MP3Player> tabelaMusicas = new TableView<MP3Player>();
		tabelaMusicas.setEditable(false);

		// Tab de musicas
		Tab tabMusicas = new Tab();
		tabMusicas.setClosable(false);
		tabMusicas.setText("Player");

		// Vertical Box
		VBox tabStuff = new VBox(5);

		TableColumn<MP3Player, String> nameCol = new TableColumn<MP3Player, String>(
				"Nome");
		nameCol.setMinWidth(200);
		nameCol.setResizable(false);
		// nameCol.prefWidthProperty().bind(tabelaMusicas.prefWidthProperty().divide(4));
		TableColumn<MP3Player, String> artistCol = new TableColumn<MP3Player, String>(
				"Artista");
		artistCol.setMinWidth(190);
		artistCol.setResizable(false);
		// artistCol.prefWidthProperty().bind(tabelaMusicas.prefWidthProperty().divide(4));
		TableColumn<MP3Player, String> albumCol = new TableColumn<MP3Player, String>(
				"Album");
		albumCol.setMaxWidth(180);
		albumCol.setResizable(false);
		// albumCol.prefWidthProperty().bind(tabelaMusicas.prefWidthProperty().divide(5));
		TableColumn<MP3Player, String> timeCol = new TableColumn<MP3Player, String>(
				"Duração");
		timeCol.setMinWidth(100);
		timeCol.setResizable(false);
		// timeCol.prefWidthProperty().bind(tabelaMusicas.prefWidthProperty().divide(10));

		// tabelaMusicas.prefHeightProperty().bind(cena.heightProperty());
		// tabelaMusicas.prefWidthProperty().bind(cena.widthProperty());

		tabelaMusicas.getColumns()
				.addAll(nameCol, artistCol, albumCol, timeCol);

		tabStuff.getChildren().addAll(tabelaMusicas);

		tabMusicas.setContent(tabStuff);
		tabs.getTabs().add(tabMusicas);

		musicas.setCenter(tabs);
		musicas.setLayoutY(24);
		musicas.prefWidthProperty().bind(cena.widthProperty());
		raiz.getChildren().addAll(musicas);
		stage.setScene(cena);
		stage.show();
	}

}
