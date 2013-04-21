package gui;

import fachada.FachadaCliente;
import fachada.GuiCoreCliente;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Vector;

import javax.sound.sampled.UnsupportedAudioFileException;

import javazoom.jl.decoder.JavaLayerException;

//import org.eclipse.swt.widgets.Shell;
import org.farng.mp3.TagException;

import dados.Musica;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.control.Labeled;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.SliderBuilder;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import negocios.MP3Player;

public class GUIjavaFX extends Application {
	private MP3Player mp3;
	private GuiCoreCliente gcc;
	private String pastaMusicas = "musicas";
	private TableView<Musica> tabelaMusicas;
	private TableColumn<Musica, String> nameCol;
	private TableColumn<Musica, String> artistCol;
	private TableColumn<Musica, String> albumCol;
	private TableColumn<Musica, String> genderCol;
	private TableColumn<Musica, String> timeCol;
	private final ObservableList<Musica> dataMusicas = FXCollections
			.observableArrayList();
	private TableView<Musica> tabelaServidor;
	private TableColumn<Musica, String> nameColServer;
	private TableColumn<Musica, String> artistColServer;
	private TableColumn<Musica, String> albumColServer;
	private TableColumn<Musica, String> genderColServer;
	private TableColumn<Musica, String> timeColServer;
	private final ObservableList<Musica> dataServer = FXCollections
			.observableArrayList();

	public class MediaControl extends BorderPane {
		private MediaPlayer mp;
		private Vector<Musica> playList;
		private int currentMP3;
		private MediaView mediaView;
		private final boolean repeat = false;
		private boolean stopRequested = false;
		private boolean atEndOfMedia = false;
		private boolean isMuted = false;
		private boolean isPlaying = false;
		private double volumeAtual;
		private Duration duration;
		private Slider timeSlider;
		private Label playTime;
		private Label musicName;
		private Slider volumeSlider;
		private HBox mediaBar;
		private final Image PlayButtonImage = new Image(
				GUIjavaFX.class.getResourceAsStream("imagens\\play.png"));
		private final Image PauseButtonImage = new Image(
				GUIjavaFX.class.getResourceAsStream("imagens\\pause.png"));
		ImageView imgPlay = new ImageView(PlayButtonImage);
		ImageView imgPause = new ImageView(PauseButtonImage);
		private Pane mvPane;
		private final Button playButton;

		@Override
		protected void layoutChildren() {
			if (mediaView != null && getBottom() != null) {
				mediaView.setFitWidth(getWidth());
				mediaView
						.setFitHeight(getHeight() - getBottom().prefHeight(-1));
			}
			super.layoutChildren();
			if (mediaView != null && getCenter() != null) {
				mediaView
						.setTranslateX((((Pane) getCenter()).getWidth() - mediaView
								.prefWidth(-1)) / 2);
				mediaView
						.setTranslateY((((Pane) getCenter()).getHeight() - mediaView
								.prefHeight(-1)) / 2);
			}
		}

		@Override
		protected double computeMinWidth(double height) {
			return mediaBar.prefWidth(-1);
		}

		@Override
		protected double computeMinHeight(double width) {
			return 200;
		}

		@Override
		protected double computePrefWidth(double height) {
			return Math.max(mp.getMedia().getWidth(),
					mediaBar.prefWidth(height));
		}

		@Override
		protected double computePrefHeight(double width) {
			return mp.getMedia().getHeight() + mediaBar.prefHeight(width);
		}

		@Override
		protected double computeMaxWidth(double height) {
			return Double.MAX_VALUE;
		}

		@Override
		protected double computeMaxHeight(double width) {
			return Double.MAX_VALUE;
		}

		@SuppressWarnings("rawtypes")
		public MediaControl(Vector<Musica> playList, int start) {
			this.playList = playList;
			try {
				setMusic(playList.get(start).getFile());
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
			setStyle("-fx-background-color: #bfc2c7;");
			mediaView = new MediaView(mp);
			mvPane = new Pane();
			mvPane.getChildren().add(mediaView);

			mvPane.setStyle("-fx-background-color: black;");
			setCenter(mvPane);
			mediaBar = new HBox(5);
			mediaBar.setPadding(new Insets(5, 10, 5, 10));
			mediaBar.setAlignment(Pos.CENTER_LEFT);
			BorderPane.setAlignment(mediaBar, Pos.CENTER);

			playButton = ButtonBuilder.create().minWidth(Control.USE_PREF_SIZE)
					.build();
			playButton.setStyle("-fx-background-color: transparent;");
			Image backwardButtonImage = new Image(
					GUIjavaFX.class.getResourceAsStream("imagens\\reverse.png"));
			final ImageView imgVoltar = new ImageView(backwardButtonImage);
			Image forwardButtonImage = new Image(
					GUIjavaFX.class.getResourceAsStream("imagens\\forward.png"));
			final ImageView imgAvancar = new ImageView(forwardButtonImage);
			Image stopButtonImage = new Image(
					GUIjavaFX.class.getResourceAsStream("imagens\\stop.png"));
			final ImageView imgStop = new ImageView(stopButtonImage);

			Button backwardButton = ButtonBuilder.create()
					.minWidth(Control.USE_PREF_SIZE).build();
			backwardButton.setGraphic(imgVoltar);
			backwardButton.setStyle("-fx-background-color: transparent;");
			Button forwardButton = ButtonBuilder.create()
					.minWidth(Control.USE_PREF_SIZE).build();
			forwardButton.setGraphic(imgAvancar);
			forwardButton.setStyle("-fx-background-color: transparent;");
			Button stopButton = ButtonBuilder.create()
					.minWidth(Control.USE_PREF_SIZE).build();
			stopButton.setGraphic(imgStop);
			stopButton.setStyle("-fx-background-color: transparent;");

			playButton.setGraphic(imgPlay);
			playButton.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					updateValues();
					play();
				}
			});

			backwardButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					duration = mp.getMedia().getDuration();
					updateValues();
					previous();
					play();
				}
			});

			forwardButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					duration = mp.getMedia().getDuration();
					updateValues();
					next();
					play();
				}
			});

			stopButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					stop();
					isPlaying = false;
					playButton.setGraphic(imgPlay);
				}
			});

			mediaBar.getChildren().addAll(stopButton, backwardButton,
					playButton, forwardButton);

			// NOME MUSICA
			musicName = new Label();
			musicName.setMinWidth(Control.USE_PREF_SIZE);
			mediaBar.getChildren().add(musicName);
			musicName.setText(playList.get(currentMP3).getTitulo());

			// SLIDE MUSICA
			timeSlider = SliderBuilder.create().minWidth(30)
					.maxWidth(Double.MAX_VALUE).build();
			HBox.setHgrow(timeSlider, Priority.ALWAYS);
			timeSlider.valueProperty().addListener(new InvalidationListener() {
				public void invalidated(Observable ov) {
					if (timeSlider.isValueChanging()) {
						if (duration != null) {
							mp.seek(duration.multiply(timeSlider.getValue() / 100.0));
						}
						updateValues();
					}
				}
			});
			mediaBar.getChildren().add(timeSlider);

			// TEMPO MUSICA
			playTime = LabelBuilder.create().minWidth(Control.USE_PREF_SIZE)
					.build();
			mediaBar.getChildren().add(playTime);

			// BOTÃO VOLUME
			Image volumeButtonImage = new Image(
					GUIjavaFX.class.getResourceAsStream("imagens\\volume.png"));
			final ImageView imgVolume = new ImageView(volumeButtonImage);
			Image muteButtonImage = new Image(
					GUIjavaFX.class.getResourceAsStream("imagens\\mute.png"));
			final ImageView imgMute = new ImageView(muteButtonImage);

			final Button volumeButton = ButtonBuilder.create()
					.minWidth(Control.USE_PREF_SIZE).build();
			volumeButton.setGraphic(imgVolume);
			volumeButton.setStyle("-fx-background-color: transparent;");

			volumeButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					updateValues();

					if (!isMuted) {
						isMuted = true;
						volumeButton.setGraphic(imgMute);
						volumeAtual = volumeSlider.getValue();
						mp.setVolume(0);
					} else {
						isMuted = false;
						volumeButton.setGraphic(imgVolume);
						mp.setVolume(volumeAtual);
					}

				}
			});

			mediaBar.getChildren().add(volumeButton);

			// SLIDE VOLUME
			volumeSlider = SliderBuilder.create().prefWidth(70).minWidth(30)
					.maxWidth(Region.USE_PREF_SIZE).build();
			volumeSlider.valueProperty().addListener(
					new InvalidationListener() {
						public void invalidated(Observable ov) {
						}
					});
			volumeSlider.valueProperty().addListener(
					new ChangeListener<Number>() {
						@Override
						public void changed(
								ObservableValue<? extends Number> observable,
								Number oldValue, Number newValue) {
							if (volumeSlider.isValueChanging()) {
								volumeButton.setGraphic(imgVolume);
								isMuted = false;
								if (volumeSlider.getValue() == 0) {
									volumeButton.setGraphic(imgMute);
									isMuted = true;
								}
								mp.setVolume(volumeSlider.getValue() / 100.0);
							}
						}
					});

			mediaBar.getChildren().add(volumeSlider);

			setBottom(mediaBar);

		}

		protected void updateValues() {
			if (playTime != null && timeSlider != null && volumeSlider != null
					&& duration != null) {
				Platform.runLater(new Runnable() {
					public void run() {
						Duration currentTime = mp.getCurrentTime();
						playTime.setText(formatTime(currentTime, duration));
						timeSlider.setDisable(duration.isUnknown());
						if (!timeSlider.isDisabled()
								&& duration.greaterThan(Duration.ZERO)
								&& !timeSlider.isValueChanging()) {
							timeSlider.setValue(currentTime.divide(duration)
									.toMillis() * 100.0);
						}
						if (!volumeSlider.isValueChanging()) {
							volumeSlider.setValue((int) Math.round(mp
									.getVolume() * 100));
						}
						musicName.setText(playList.get(currentMP3).getTitulo());
					}
				});
			}
		}

		private String formatTime(Duration elapsed, Duration duration) {
			int intElapsed = (int) Math.floor(elapsed.toSeconds());
			int elapsedHours = intElapsed / (60 * 60);
			if (elapsedHours > 0) {
				intElapsed -= elapsedHours * 60 * 60;
			}
			int elapsedMinutes = intElapsed / 60;
			int elapsedSeconds = intElapsed - elapsedHours * 60 * 60
					- elapsedMinutes * 60;

			if (duration.greaterThan(Duration.ZERO)) {
				int intDuration = (int) Math.floor(duration.toSeconds());
				int durationHours = intDuration / (60 * 60);
				if (durationHours > 0) {
					intDuration -= durationHours * 60 * 60;
				}
				int durationMinutes = intDuration / 60;
				int durationSeconds = intDuration - durationHours * 60 * 60
						- durationMinutes * 60;

				if (durationHours > 0) {
					return String.format("%d:%02d:%02d/%d:%02d:%02d",
							elapsedHours, elapsedMinutes, elapsedSeconds,
							durationHours, durationMinutes, durationSeconds);
				} else {
					return String.format("%02d:%02d/%02d:%02d", elapsedMinutes,
							elapsedSeconds, durationMinutes, durationSeconds);
				}
			} else {
				if (elapsedHours > 0) {
					return String.format("%d:%02d:%02d", elapsedHours,
							elapsedMinutes, elapsedSeconds);
				} else {
					return String.format("%02d:%02d", elapsedMinutes,
							elapsedSeconds);
				}
			}
		}

		private void setMusic(File file) throws MalformedURLException {

			final String mediaLocation = file.toURI().toURL().toExternalForm();
			Media media = new Media(mediaLocation);
			mp = new MediaPlayer(media);
			mp.setOnEndOfMedia(new Runnable() {

				@Override
				public void run() {
					if (playList != null) {
						try {
							currentMP3 = (++currentMP3) % playList.size();
							setMusic(playList.get(currentMP3).getFile());
							isPlaying = false;
							play();
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
					}
				}
			});

			mp.currentTimeProperty().addListener(
					new ChangeListener<Duration>() {
						@Override
						public void changed(
								ObservableValue<? extends Duration> observable,
								Duration oldValue, Duration newValue) {
							updateValues();
						}
					});
			// mp.setOnPlaying(new Runnable() {
			// public void run() {
			//
			// if (stopRequested) {
			// pause();
			// stopRequested = false;
			// } else {
			// // playButton.setGraphic(imageViewPause);
			// }
			// }
			// });

			// mp.setOnPaused(new Runnable() {
			// public void run() {
			//
			// // playButton.setGraphic(imageViewPlay);
			// }
			// });

			mp.setOnReady(new Runnable() {
				public void run() {
					duration = mp.getMedia().getDuration();
					updateValues();
				}
			});

			// mp.setOnEndOfMedia(new Runnable() {
			// public void run() {
			// if (!repeat) {
			// //playButton.setGraphic(imageViewPlay);
			// stopRequested = true;
			// atEndOfMedia = true;
			// }
			// }
			// });
		}

		public void next() {
			if (playList != null) {
				stop();
				try {
					updateValues();
					currentMP3 = (++currentMP3) % playList.size();
					setMusic(playList.get(currentMP3).getFile());
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}

		}

		public void previous() {
			if (playList != null) {
				try {
					stop();
					updateValues();
					if (currentMP3 == 0) {
						currentMP3 = playList.size() - 1;
					} else {
						--currentMP3;
					}
					setMusic(playList.get(currentMP3).getFile());
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		}

		public void play() {
			if (!isPlaying) {
				isPlaying = true;
				mp.play();
				updateValues();
				playButton.setGraphic(imgPause);
			} else {
				isPlaying = false;
				playButton.setGraphic(imgPlay);
				mp.pause();
				updateValues();
			}

		}

		public void stop() {
			mp.stop();
			isPlaying = false;
			updateValues();
		}

		public void atualizarLista(Vector<Musica> playList) {
			this.playList = playList;
			currentMP3 = 0;
		}

		public void setMusic(int index) {
			currentMP3 = index;
			try {
				stop();
				setMusic(playList.get(index).getFile());
				play();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

	}

	private void atualizarLista(MediaControl mediaControl) {
		dataMusicas.clear();
		tabelaMusicas.setItems(dataMusicas);
		Vector<Musica> vector = gcc.atualizarListaCliente();
		mediaControl.atualizarLista(vector);

		for (int i = 0; i < vector.size(); i++) {
			dataMusicas.add(vector.get(i));
		}

		tabelaMusicas.setItems(dataMusicas);
	}

	private void atualizarListaServidor() {
		dataServer.clear();
		Vector<Musica> vector; // recebe lista do servidor

		// for(int i = 0; i < vector.size(); i++) {
		// insere o que tem no vector no dataServer
		// dataServer.add(vector.get(i));
		// }
	}

	@Override
	public void start(Stage stage) throws Exception {
		try {
			gcc = new FachadaCliente(pastaMusicas);
		} catch (JavaLayerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TagException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}

		// InitialDialog inicio = new InitialDialog(new Shell(), 0, gcc);
		// inicio.open();

		Group raiz = new Group();
		Scene cena = new Scene(raiz, 885, 590, Color.BLACK);
		final MediaControl mediaControl = new MediaControl(gcc.getLista(), 0);
		mediaControl.prefWidthProperty().bind(cena.widthProperty());
		mediaControl.prefHeightProperty().bind(cena.heightProperty());

		// ================== MENU ==========================
		MenuBar menuBar = new MenuBar();
		menuBar.setFocusTraversable(false);
		menuBar.prefWidthProperty().bind(cena.widthProperty());
		final Menu arquivoMenu = new Menu("Arquivo");
		MenuItem attMusicas = new MenuItem("Atualizar Músicas");
		attMusicas.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				atualizarLista(mediaControl);
			}
		});
		MenuItem attServidor = new MenuItem("Atualizar Servidor");
		MenuItem sair = new MenuItem("Sair");
		sair.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				System.exit(0);
			}

		});
		arquivoMenu.getItems().addAll(attMusicas, attServidor, sair);

		final Menu editarMenu = new Menu("Editar");
		MenuItem editarTaxaErro = new MenuItem("Mudar Taxa de Erro");
		editarMenu.getItems().add(editarTaxaErro);
		final Menu helpMenu = new Menu("Ajuda");
		MenuItem sobreHelp = new MenuItem("Sobre");
		sobreHelp.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
//				SobreDialog sobreDialog = new SobreDialog(new Shell(), 0);
//				sobreDialog.open();
			}
		});
		helpMenu.getItems().add(sobreHelp);
		menuBar.getMenus().addAll(arquivoMenu, editarMenu, helpMenu);

		// =====================================================

		// =================== TABPANE =========================
		TabPane tabs = new TabPane();
		tabs.setLayoutY(24);
		tabs.setPrefHeight(520);
		tabs.prefWidthProperty().bind(cena.widthProperty());
		tabs.setSide(Side.LEFT);

		Tab tabMusicas = new Tab("Musicas");
		tabMusicas.setClosable(false);
		Tab tabServer = new Tab("Servidor");
		tabServer.setClosable(false);

		tabs.getTabs().addAll(tabMusicas, tabServer);

		tabelaMusicas = new TableView<Musica>();
		tabelaMusicas.setEditable(false);

		nameCol = new TableColumn<Musica, String>("Nome");
		nameCol.setMinWidth(200);
		nameCol.setResizable(true);
		nameCol.setCellValueFactory(new PropertyValueFactory<Musica, String>(
				"tituloProp"));

		artistCol = new TableColumn<Musica, String>("Artista");
		artistCol.setMinWidth(190);
		artistCol.setResizable(true);
		artistCol.setCellValueFactory(new PropertyValueFactory<Musica, String>(
				"artistaProp"));

		albumCol = new TableColumn<Musica, String>("Album");
		albumCol.setMinWidth(180);
		albumCol.setResizable(true);
		albumCol.setCellValueFactory(new PropertyValueFactory<Musica, String>(
				"albumProp"));

		genderCol = new TableColumn<Musica, String>("Gênero");
		genderCol.setMinWidth(180);
		genderCol.setResizable(true);
		genderCol.setCellValueFactory(new PropertyValueFactory<Musica, String>(
				"generoProp"));

		timeCol = new TableColumn<Musica, String>("Duração");
		timeCol.setMinWidth(100);
		timeCol.setResizable(true);
		timeCol.setCellValueFactory(new PropertyValueFactory<Musica, String>(
				"duracaoProp"));

		atualizarLista(mediaControl);
		tabelaMusicas.getColumns().add(nameCol);
		tabelaMusicas.getColumns().add(artistCol);
		tabelaMusicas.getColumns().add(albumCol);
		tabelaMusicas.getColumns().add(genderCol);
		tabelaMusicas.getColumns().add(timeCol);

		tabelaMusicas.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 2) {
						if (tabelaMusicas.getSelectionModel().getSelectedItem() != null) {
							mediaControl.setMusic(tabelaMusicas
									.getSelectionModel().getSelectedIndex());
						}
					}
				}
			}
		});
		tabMusicas.setContent(tabelaMusicas);

		tabelaServidor = new TableView<Musica>();

		nameColServer = new TableColumn<Musica, String>("Nome");
		nameColServer.setMinWidth(200);
		nameColServer.setResizable(true);
		nameColServer
				.setCellValueFactory(new PropertyValueFactory<Musica, String>(
						"tituloProp"));

		artistColServer = new TableColumn<Musica, String>("Artista");
		artistColServer.setMinWidth(190);
		artistColServer.setResizable(true);
		artistColServer
				.setCellValueFactory(new PropertyValueFactory<Musica, String>(
						"artistaProp"));

		albumColServer = new TableColumn<Musica, String>("Album");
		albumColServer.setMinWidth(180);
		albumColServer
				.setCellValueFactory(new PropertyValueFactory<Musica, String>(
						"albumProp"));

		genderColServer = new TableColumn<Musica, String>("Gênero");
		genderColServer.setMinWidth(180);
		genderColServer.setResizable(true);
		genderColServer
				.setCellValueFactory(new PropertyValueFactory<Musica, String>(
						"generoProp"));

		timeColServer = new TableColumn<Musica, String>("Duração");
		timeColServer.setMinWidth(100);
		timeColServer.setResizable(true);
		timeColServer
				.setCellValueFactory(new PropertyValueFactory<Musica, String>(
						"duracaoProp"));

		tabelaServidor.getColumns().addAll(nameColServer, artistColServer,
				albumColServer, genderColServer, timeColServer);

		tabServer.setContent(tabelaServidor);

		raiz.getChildren().add(mediaControl);
		raiz.getChildren().add(menuBar);
		raiz.getChildren().add(tabs);
		stage.setScene(cena);
		stage.setTitle("muzika");
		stage.getIcons()
				.add(new Image(getClass().getResourceAsStream(
						"imagens\\clave.png")));
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
