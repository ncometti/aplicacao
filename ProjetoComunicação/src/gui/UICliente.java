package gui;


import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;

import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.id3.AbstractID3v2;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumnBuilder;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class UICliente extends Application{
	private static final int UNKNOWN = -1;
	private static final int PLAYING = 0;
	private static final int PAUSED = 1;
	private static final int STOPPED = 2;
	private static final int SEEKING = 3;
	private int playState = UNKNOWN;
	private static final int LOW = 0;
	private static final int HIGH = 1;
	private static final int MUTE = 2;
	private int soundState = HIGH;	
	
	public static void main(String[] args) {
		launch();
	}
	@Override
	public void start(final Stage stage) throws Exception {
		stage.setTitle("Musique");
		//stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
		
		Group root = new Group();
		Scene scene = new Scene(root, 800, 600, Color.WHITE);
		
		TabPane tabs = new TabPane();
		BorderPane player = new BorderPane();
		final Repositorio rep = new Repositorio();
		final TableView<MP3> tableTab1 = new TableView<MP3>();
		
		
		//===================player=================================
		Tab tab1 = new Tab();
		tab1.setClosable(false);
		tab1.setText("Player");
		tab1.setStyle("-fx-font-size: 16;");
		
		//Vertical box
		VBox stuffTab1 = new VBox(5);
		stuffTab1.setPadding(new Insets(5,0,5,0));
		
		//horizontal box 1
		HBox playControls = new HBox();
		playControls.setAlignment(Pos.TOP_LEFT);
		playControls.setPadding(new Insets(5,10,5,5));
		playControls.setSpacing(5);
		
		final Image img1 = new Image(getClass().getResourceAsStream("previous.png"), 20, 20, false, false);
		final Image img2 = new Image(getClass().getResourceAsStream("play.png"), 20, 20, false, false);
		final Image img3 = new Image(getClass().getResourceAsStream("stop.png"), 20, 20, false, false);
		final Image img4 = new Image(getClass().getResourceAsStream("next.png"), 20, 20, false, false);
		final Image img5 = new Image(getClass().getResourceAsStream("pause.png"), 20, 20, false, false);
		final Image img6 = new Image(getClass().getResourceAsStream("mute.png"), 20, 20, false, false);
		final Image img7 = new Image(getClass().getResourceAsStream("low.png"), 20, 20, false, false);
		final Image img8 = new Image(getClass().getResourceAsStream("high.png"), 20, 20, false, false);
		
		final Button previous = new Button("", new ImageView(img1));
		previous.setStyle("-fx-background-color: transparent;");
		previous.setCursor(Cursor.HAND);
		
		final MediaPlayer mp = null;
		
		final Button play = new Button("", new ImageView(img2));
		play.setStyle("-fx-background-color: transparent;");
		play.setCursor(Cursor.HAND);
		play.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				if(playState != PLAYING){
					MP3 playing = (MP3) tableTab1.getSelectionModel().getSelectedItem();
					Media media = new Media(playing.getPath().toString());
					MediaPlayer mp = new MediaPlayer(media);
					mp.play();
					play.setGraphic(new ImageView(img5));
					playState = PLAYING;						
				}else{
					mp.pause();
					play.setGraphic(new ImageView(img2));
					playState = PAUSED;
				}
				
			}
		});
	
		final Button stop = new Button("", new ImageView(img3));
		stop.setStyle("-fx-background-color: transparent;");
		stop.setCursor(Cursor.HAND);
		stop.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				if(playState == PLAYING){
					play.setGraphic(new ImageView(img2));
					playState = STOPPED;
				}
			}
		});

		final Button next = new Button("", new ImageView(img4));
		next.setStyle("-fx-background-color: transparent;");
		next.setCursor(Cursor.HAND);

		TextField musicTab1 = new TextField();
		musicTab1.setPrefHeight(25);
		playControls.setHgrow(musicTab1, Priority.ALWAYS);
		musicTab1.setEditable(false);
		musicTab1.setText("nome da música");
		
		final Slider volume = new Slider();
		final Button mute = new Button("", new ImageView(img8));
		
		
		volume.setValue(50);
		volume.setPrefWidth(100);
		volume.setTranslateY(volume.getTranslateY()+5);
		volume.setTranslateX(volume.getTranslateX()+4);
	
		
		mute.setStyle("-fx-background-color: transparent;");
		mute.setCursor(Cursor.HAND);
		mute.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				if(soundState != MUTE){
					mute.setGraphic(new ImageView(img6));
					soundState = MUTE;
				}else{
					mute.setGraphic(new ImageView(img8));
					soundState = HIGH;
				}
			}
		});
		playControls.getChildren().addAll(previous, play, stop, next, musicTab1, mute, volume);	
		
		
		//Table
		tableTab1.setEditable(true);
		//tableTab1.setStyle("-fx-background-color: black;");
		TableColumn nameCol = new TableColumn("Nome");
		nameCol.setMinWidth(100);
		nameCol.prefWidthProperty().bind(tableTab1.widthProperty().divide(4));
		TableColumn timeCol = new TableColumn("Duração");
		timeCol.setMinWidth(40);
		timeCol.prefWidthProperty().bind(tableTab1.widthProperty().divide(10));
		TableColumn artistCol = new TableColumn("Artista");
		artistCol.setMinWidth(100);
		artistCol.prefWidthProperty().bind(tableTab1.widthProperty().divide(4));
		TableColumn albumCol = new TableColumn("Álbum");
		albumCol.setMinWidth(80);
		albumCol.prefWidthProperty().bind(tableTab1.widthProperty().divide(5));
		TableColumn generoCol = new TableColumn("Gênero");
		generoCol.setMinWidth(80);
		generoCol.prefWidthProperty().bind(tableTab1.widthProperty().divide(5));
		tableTab1.prefHeightProperty().bind(scene.heightProperty());
		tableTab1.prefWidthProperty().bind(scene.widthProperty());
		
		tableTab1.getColumns().addAll(nameCol, timeCol, artistCol, albumCol, generoCol);
		
		
		
		//Vertical box
		VBox bottomTab1 = new VBox();
		bottomTab1.setPadding(new Insets(0,15,0,15));
		
		//Slider
		Slider sliderTab1 = new Slider();
		
		bottomTab1.getChildren().add(sliderTab1);
		
		//Horizontal box 2
		HBox searchTab1 = new HBox();
		searchTab1.setAlignment(Pos.CENTER_LEFT);
		searchTab1.setPadding(new Insets(10,15,10,15));
		searchTab1.setSpacing(10);
		
		Label labelTempo = new Label();
		labelTempo.setText("00:00/00:00");
		labelTempo.setAlignment(Pos.CENTER_LEFT);
		labelTempo.prefWidthProperty().bind(searchTab1.widthProperty());
		searchTab1.setHgrow(labelTempo, Priority.ALWAYS);
		
		
		TextField textTab1 = new TextField("");
		textTab1.setMinWidth(160);
		textTab1.setTooltip(new Tooltip("Digite aqui o que deseja pesquisar."));
		Button buttonTab1 = new Button("Pesquisar");
		buttonTab1.setMinWidth(75);
		buttonTab1.setCursor(Cursor.HAND);
		
		searchTab1.getChildren().addAll(labelTempo, textTab1, buttonTab1);
		
		vertical box settings
		stuffTab1.getChildren().addAll(playControls, tableTab1, bottomTab1, searchTab1);
		tab1.setContent(stuffTab1);
		tabs.getTabs().add(tab1);
		//======================fim player========================
		
		
		//======================downloads================================
		//tab2
		Tab tab2 = new Tab();
		tab2.setClosable(false);
		tab2.setText("Download");
		tab2.setStyle("-fx-font-size: 16;");
		
		//Vertical Box
		VBox downloadTab2 = new VBox(15);
		downloadTab2.setPadding(new Insets(0,15,15,15));
		
		//Horizontal box 1
		HBox searchTab2 = new HBox();
		searchTab2.setAlignment(Pos.TOP_CENTER);
		searchTab2.setPadding(new Insets(15,0,15,0));
		searchTab2.setSpacing(10);
		
		TextField textTab2 = new TextField("");
		textTab2.setTooltip(new Tooltip("Digite aqui o que deseja pesquisar."));
		Button button1Tab2 = new Button("Pesquisar");
		Button button2Tab2 = new Button("Download");
		HBox.setHgrow(textTab2, Priority.ALWAYS);
		HBox.setHgrow(button1Tab2, Priority.ALWAYS);
		HBox.setHgrow(button2Tab2, Priority.ALWAYS);
		button2Tab2.setVisible(false);
		
		searchTab2.getChildren().addAll(textTab2, button1Tab2);
		
		//Table list
		TableView listTab2 = new TableView<>();
		
		//Horizontal box 2
		HBox moreButtonsTab1 = new HBox();
		moreButtonsTab1.setAlignment(Pos.TOP_CENTER);
		moreButtonsTab1.setPadding(new Insets(0,0,0,0));
		moreButtonsTab1.setSpacing(10);
		
		Button pauseTab2 = new Button("Pausar");
		Button cancelTab2 = new Button("Cancelar");
		HBox.setHgrow(pauseTab2, Priority.ALWAYS);
		HBox.setHgrow(cancelTab2, Priority.ALWAYS);
		pauseTab2.setMaxWidth(Double.MAX_VALUE);
		cancelTab2.setMaxWidth(Double.MAX_VALUE);
		moreButtonsTab1.getChildren().addAll(pauseTab2, cancelTab2);
		moreButtonsTab1.setVisible(false);
		
		
		//Table download
		TableView listdownloadsTab2 = new TableView<>();
		
		
		downloadTab2.getChildren().addAll(searchTab2, listTab2, moreButtonsTab1, listdownloadsTab2);
		tab2.setContent(downloadTab2);
		tabs.getTabs().add(tab2);
		//=========================fim download==========================
		
		
		//========================Settings===============================
		//tab3
		Tab tab3 = new Tab();
		tab3.setClosable(false);
		tab3.setText("Configurações");
		tab3.setStyle("-fx-font-size: 16;");
		
		//Vertical box
		VBox settingsTab3 = new VBox();
		settingsTab3.setPadding(new Insets(15,15,15,15));
		//button
		Button about = new Button("Sobre");
		about.setAlignment(Pos.BOTTOM_LEFT);
		about.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
          
                StackPane aboutLayout = new StackPane();
                
                Image image = new Image(getClass().getResourceAsStream("icon.png"), 75, 75, false, false);
                //Image image2 = new Image(getClass().getResourceAsStream("cin.jpg"), 50, 50, false, false);
                Image image3 = new Image(getClass().getResourceAsStream("ufpe.jpg"), 75, 125, false, false);
                
                HBox hbox = new HBox(20);
                hbox.setPadding(new Insets(15, 15, 15, 15));
                
                VBox vbox = new VBox();
                Label label1 = new Label("", new ImageView(image));
                // Label label2 = new Label("", new ImageView(image2));
                Label label3 = new Label("", new ImageView(image3));
                vbox.getChildren().addAll(label1,  label3);
                
                Label label4 = new Label();
                label4.setText("Musique\n\nVersion 1.0");
                hbox.getChildren().addAll(vbox, label4);
                
                
                aboutLayout.getChildren().addAll(hbox);
                 
                Scene secondScene = new Scene(aboutLayout, 300, 200, Color.WHITE);
 
                Stage secondStage = new Stage();
                secondStage.setTitle("Sobre");
                
                secondStage.setScene(secondScene);
                 
                //Set position of second window, related to primary window.
                secondStage.setX(stage.getX() + 250);
                secondStage.setY(stage.getY() + 100);
                
                secondStage.show();
            }
		});
		
		settingsTab3.getChildren().addAll(about);
		tab3.setContent(settingsTab3);
		tabs.getTabs().add(tab3);
		//========================fim settings==========================
		
		
		player.setCenter(tabs);
		player.prefHeightProperty().bind(scene.heightProperty());
		player.prefWidthProperty().bind(scene.widthProperty());
		
		root.getChildren().add(player);
		stage.setScene(scene);
		stage.show();
	}

}
