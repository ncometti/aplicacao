package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Vector;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Action;
import javax.swing.Timer;

import javazoom.jl.decoder.JavaLayerException;

import negocios.MP3Player;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import dados.Musica;

import fachada.GuiCoreCliente;
import fachada.FachadaCliente;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.farng.mp3.TagException;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.internal.Platform;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.ProgressBar;

public class MainClientWindow {

	protected Shell shlMuzika;
	private GuiCoreCliente gcc;
	private Text musicText;
	private Table tableServer;
	private Table tableMusic;
	private boolean playing = false;
	private MP3Player mp3;
	private Musica music;
	private String msc = "musicas";
	private Vector<Musica> musicas;
	private Thread thread;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainClientWindow window = new MainClientWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 * 
	 * @throws JavaLayerException
	 * @throws UnsupportedAudioFileException
	 * @throws TagException
	 * @throws IOException
	 */
	public void open() {
		try {
			gcc = new FachadaCliente(msc);
			mp3 = new MP3Player(gcc.getLista(), 0);
			Display display = Display.getDefault();
			createContents();
			InitialDialog inicio = new InitialDialog(shlMuzika, 0, gcc);
			Composite composite = new Composite(shlMuzika, SWT.NONE);
			composite.setBounds(260, 578, 244, 64);
			inicio.open();
			shlMuzika.open();
			shlMuzika.layout();
			while (!shlMuzika.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (JavaLayerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TagException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Create contents of the window.
	 * 
	 * @throws MalformedURLException
	 * 
	 * @wbp.parser.entryPoint
	 */
	protected void createContents() throws MalformedURLException {
		shlMuzika = new Shell();
		shlMuzika.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BORDER));
		shlMuzika.setImage(SWTResourceManager
				.getImage("C:\\Users\\Pedro\\Desktop\\Java\\eclipse\\workspace\\ProjetoGUI\\logoCIn.jpg"));
		shlMuzika.setSize(1000, 700);
		shlMuzika.setLocation(348, 74);
		shlMuzika.setText("MuziKa");
		shlMuzika.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent arg0) {
				System.exit(0);
				System.out.println("faloris");
			}
		});

		// ============================== DECLARAÇÃO DE VARIÁVEIS
		// ===============================
		final Button botaoPlay = new Button(shlMuzika, SWT.NONE);
		final Label lblTime = new Label(shlMuzika, SWT.NONE);
		final Scale slideMusica = new Scale(shlMuzika, SWT.NONE);
		final Scale slideVolume = new Scale(shlMuzika, SWT.NONE);
		final Label labelMusica = new Label(shlMuzika, SWT.CENTER);
		Button botaoVoltar = new Button(shlMuzika, SWT.NONE);
		Button botaoAvancar = new Button(shlMuzika, SWT.NONE);
		Label labelVolume = new Label(shlMuzika, SWT.NONE);
		TabFolder tabFolder = new TabFolder(shlMuzika, SWT.NONE);
		tableMusic = new Table(tabFolder, SWT.BORDER | SWT.FULL_SELECTION);
		tableServer = new Table(tabFolder, SWT.BORDER | SWT.FULL_SELECTION);
		final TableColumn clientNameCol = new TableColumn(tableMusic, SWT.NONE);
		Label label = new Label(shlMuzika, SWT.SEPARATOR | SWT.HORIZONTAL);
		TabItem tbtmMusicas = new TabItem(tabFolder, SWT.NONE);
		TableColumn clientGenderCol = new TableColumn(tableMusic, SWT.NONE);
		TableColumn tblclmnDurao = new TableColumn(tableMusic, SWT.NONE);
		TableColumn clientArtistCol = new TableColumn(tableMusic, SWT.NONE);
		TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		TableColumn serverNameCol = new TableColumn(tableServer, SWT.NONE);
		TableColumn serverArtistCol = new TableColumn(tableServer, SWT.NONE);
		Menu menu = new Menu(shlMuzika, SWT.BAR);
		TableColumn serverGenderCol = new TableColumn(tableServer, SWT.NONE);
		MenuItem mntmArquivo = new MenuItem(menu, SWT.CASCADE);
		Menu menu_1 = new Menu(mntmArquivo);
		MenuItem mntmAtualizarMsicas = new MenuItem(menu_1, SWT.NONE);
		MenuItem mntmAtualizarServidor = new MenuItem(menu_1, SWT.NONE);
		MenuItem mntmMudarNome = new MenuItem(menu_1, SWT.NONE);
		Button buscarMusica = new Button(shlMuzika, SWT.NONE);
		musicText = new Text(shlMuzika, SWT.BORDER);
		MenuItem mntmSair = new MenuItem(menu_1, SWT.NONE);
		MenuItem mntmInformaes = new MenuItem(menu, SWT.CASCADE);
		Menu menu_2 = new Menu(mntmInformaes);
		MenuItem mntmSobre = new MenuItem(menu_2, SWT.NONE);
		Label lblDownloadsAtivos = new Label(shlMuzika, SWT.NONE);
		Button stopButton = new Button(shlMuzika, SWT.NONE);
		Composite composite = new Composite(shlMuzika, SWT.NONE);
		// ============================== FIM DA DA DECLARAÇÃO
		// ==================================

		// ---------------------------------------------------------------------------------------------------------------

		// BOTÃO VOLTAR
		botaoVoltar
				.setImage(SWTResourceManager
						.getImage("C:\\Users\\Pedro\\Desktop\\Java\\eclipse\\workspace\\ProjetoGUI\\backwardButton.jpg"));
		botaoVoltar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				gcc.press_ant();
			}
		});

		botaoVoltar.setBounds(10, 25, 50, 25);
		// FIM BOTÃO VOLTAR

		// ---------------------------------------------------------------------------------------------------------------

		// BOTÃO AVANÇAR
		botaoAvancar
				.setImage(SWTResourceManager
						.getImage("C:\\Users\\Pedro\\Desktop\\Java\\eclipse\\workspace\\ProjetoGUI\\forwardButton.jpg"));
		botaoAvancar.setBounds(163, 25, 50, 25);
		// FIM BOTÃO AVANÇAR

		// ---------------------------------------------------------------------------------------------------------------

		// SLIDE VOLUME
		slideVolume.setMinimum(0);
		slideVolume.setMaximum(100);
		slideVolume.setSelection(30);
		mp3.setVolume(0.3);
		slideVolume.addListener(SWT.Selection, new Listener() {
			public void handleEvent(org.eclipse.swt.widgets.Event arg0) {
				double x = slideVolume.getMaximum()
						- slideVolume.getSelection() + slideVolume.getMinimum()
						- 100;
				x = (x * -1) / 100;
				mp3.setVolume(x);
			}
		});
		slideVolume.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BORDER));
		slideVolume.setBounds(219, 25, 120, 32);
		// FIM SLIDE VOLUME

		// ---------------------------------------------------------------------------------------------------------------

		// LABEL VOLUME
		labelVolume.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		labelVolume.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BORDER));
		labelVolume.setBounds(253, 10, 55, 15);
		labelVolume.setText("Volume");
		// FIM LABEL VOLUME

		// ---------------------------------------------------------------------------------------------------------------

		// LABEL MUSICA
		labelMusica.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BORDER));
		labelMusica.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		labelMusica.setBounds(373, 10, 259, 15);
		// FIM LABEL MUSICA

		// ---------------------------------------------------------------------------------------------------------------

		label.setBounds(0, 73, 984, 2); // LABEL ALEATORIA HUE HUE

		// TABFOLDER
		tabFolder.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BORDER));
		tabFolder.setBounds(0, 81, 984, 470);
		// FIM TABFOLDER

		// ---------------------------------------------------------------------------------------------------------------

		// TABELA DE MUSICAS
		tbtmMusicas.setText("M\u00FAsicas");

		tableMusic.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				if (!playing) {
					playing = true;
					botaoPlay.setImage(SWTResourceManager
							.getImage("C:\\Users\\Pedro\\Desktop\\Java\\eclipse\\workspace\\ProjetoGUI\\pauseButton.jpg"));
				}

				try {
					mp3.stop();
					mp3.setMusic(tableMusic.getSelectionIndex());
					mp3.play();
					slideMusica.setMaximum((int) mp3.getPlayer()
							.getTotalDuration().toMillis());
					double x = slideVolume.getMaximum()
							- slideVolume.getSelection()
							+ slideVolume.getMinimum() - 100;
					x = (x * -1) / 100;
					mp3.setVolume(x);
					music = mp3.getMusica();

					labelMusica.setText(music.getTitulo() + " - "
							+ music.getArtista());
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}
			}
		});
		tbtmMusicas.setControl(tableMusic);
		tableMusic.setHeaderVisible(true);
		tableMusic.setLinesVisible(true);

		clientNameCol.setWidth(200);
		clientNameCol.setText("Nome");

		clientArtistCol.setWidth(200);
		clientArtistCol.setText("Artista");

		tblclmnDurao.setWidth(100);
		tblclmnDurao.setText("Dura\u00E7\u00E3o");

		clientGenderCol.setWidth(150);
		clientGenderCol.setText("G\u00EAnero");
		atualizarMusicas();
		// FIM TABELA DE MUSICAS

		// ---------------------------------------------------------------------------------------------------------------
		tabItem.setText("Servidor");

		tabItem.setControl(tableServer);
		tableServer.setHeaderVisible(true);
		tableServer.setLinesVisible(true);

		serverNameCol.setWidth(200);
		serverNameCol.setText("Nome");

		serverArtistCol.setWidth(200);
		serverArtistCol.setText("Artista");

		serverGenderCol.setWidth(150);
		serverGenderCol.setText("G\u00EAnero");

		shlMuzika.setMenuBar(menu);

		mntmArquivo.setText("Arquivo");

		mntmArquivo.setMenu(menu_1);

		mntmAtualizarMsicas.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				atualizarMusicas();
			}
		});
		mntmAtualizarMsicas.setText("Atualizar M\u00FAsicas");

		mntmAtualizarServidor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				gcc.atualizarListaServidor();
				// CHAMA MÉTODO ATUALIZAR LISTA DE MUSICAS NO SERVIDOR
			}
		});
		mntmAtualizarServidor.setText("Atualizar Servidor");

		mntmMudarNome.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				NameDialog novoNome = new NameDialog(shlMuzika, 0, gcc);
				try {
					novoNome.open();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (JavaLayerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (TagException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnsupportedAudioFileException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mntmMudarNome.setText("Mudar Nome");

		mntmSair.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				mp3.stop();
				shlMuzika.close();
			}
		});
		mntmSair.setText("Sair");

		mntmInformaes.setText("Ajuda");

		mntmInformaes.setMenu(menu_2);

		mntmSobre.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SobreDialog sobre = new SobreDialog(shlMuzika, 0);
				sobre.open();
			}
		});
		mntmSobre.setText("Sobre");

		botaoPlay
				.setImage(SWTResourceManager
						.getImage("C:\\Users\\Pedro\\Desktop\\Java\\eclipse\\workspace\\ProjetoGUI\\playButton.jpg"));

		botaoPlay.setBounds(97, 7, 60, 60);

		lblDownloadsAtivos.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BORDER));
		lblDownloadsAtivos.setForeground(SWTResourceManager
				.getColor(SWT.COLOR_WHITE));
		lblDownloadsAtivos.setBounds(10, 557, 102, 15);
		lblDownloadsAtivos.setText("Downloads Ativos");

		composite.setBounds(10, 578, 244, 64);

		musicText.setBounds(812, 27, 156, 21);
		musicText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				buscarMusicas(musicText.getText());
			}
		});

		buscarMusica.setBounds(756, 27, 50, 21);
		buscarMusica.setText("Buscar");

		lblTime.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BORDER));
		lblTime.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblTime.setBounds(638, 10, 55, 15);

		slideMusica.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BORDER));
		slideMusica.setBounds(373, 25, 320, 32);
		slideMusica.setMinimum(0);

		slideMusica.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				long x = slideMusica.getSelection();
				mp3.skip(x);
			}
		});

		// BOTÃO PLAY
		botaoPlay.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!playing) {
					mp3.play();
					mp3.skip(slideMusica.getSelection());
					slideMusica.setMaximum((int) mp3.getPlayer()
							.getTotalDuration().toMillis());
					music = mp3.getMusica();

					labelMusica.setText(music.getTitulo() + " - "
							+ music.getArtista());
					botaoPlay.setImage(SWTResourceManager
							.getImage("C:\\Users\\Pedro\\Desktop\\Java\\eclipse\\workspace\\ProjetoGUI\\pauseButton.jpg"));
					playing = true;
				} else {
					mp3.pause();
					playing = false;
					botaoPlay.setImage(SWTResourceManager
							.getImage("C:\\Users\\Pedro\\Desktop\\Java\\eclipse\\workspace\\ProjetoGUI\\playButton.jpg"));
				}
			}
		});
		// FIM BOTÃO PLAY

		stopButton
				.setImage(SWTResourceManager
						.getImage("C:\\Users\\Pedro\\Desktop\\Java\\eclipse\\workspace\\ProjetoGUI\\stopButton.jpg"));
		stopButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				mp3.stop();
				playing = false;
				slideMusica.setSelection(0);
				botaoPlay.setImage(SWTResourceManager
						.getImage("C:\\Users\\Pedro\\Desktop\\Java\\eclipse\\workspace\\ProjetoGUI\\playButton.jpg"));
			}
		});
		stopButton.setBounds(66, 25, 25, 25);

		botaoAvancar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				mp3.next();
				slideMusica.setMaximum((int) mp3.getPlayer().getTotalDuration()
						.toMillis());
				double x = slideVolume.getMaximum()
						- slideVolume.getSelection() + slideVolume.getMinimum()
						- 100;
				x = (x * -1) / 100;
				mp3.setVolume(x);
				music = mp3.getMusica();
				labelMusica.setText(music.getTitulo() + " - "
						+ music.getArtista());
				if (playing) {
					mp3.play();
				}
			}
		});

		botaoVoltar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				mp3.previous();
				slideMusica.setMaximum((int) mp3.getPlayer().getTotalDuration()
						.toMillis());
				double x = slideVolume.getMaximum()
						- slideVolume.getSelection() + slideVolume.getMinimum()
						- 100;
				x = (x * -1) / 100;
				mp3.setVolume(x);
				music = mp3.getMusica();
				labelMusica.setText(music.getTitulo() + " - "
						+ music.getArtista());
				if (playing) {
					mp3.play();
				}
			}
		});
	}

	private void atualizarMusicas() {
		tableMusic.removeAll();
		musicas = gcc.atualizarListaCliente();
		mp3.setPlayList(musicas);
		System.out.println(musicas.size());
		for (int i = 0; i < musicas.size(); i++) {
			System.out.println(musicas.get(i).getTitulo());
			TableItem tableItem = new TableItem(tableMusic, SWT.NONE);
			tableItem.setText(0, musicas.get(i).getTitulo());
			tableItem.setText(1, musicas.get(i).getArtista());
			tableItem.setText(2, musicas.get(i).getDuracao());
			tableItem.setText(3, musicas.get(i).getGenero());
		}
		System.out.println("escrevi lista 1");
	}

	private void getLista() {
		tableMusic.removeAll();
		musicas = gcc.getLista();
		mp3.setPlayList(musicas);
		for (int i = 0; i < musicas.size(); i++) {
			TableItem tableItem = new TableItem(tableMusic, SWT.NONE);
			tableItem.setText(0, musicas.get(i).getTitulo());
			tableItem.setText(1, musicas.get(i).getArtista());
			tableItem.setText(2, musicas.get(i).getDuracao());
			tableItem.setText(3, musicas.get(i).getGenero());
		}
		System.out.println("escrevi lista 2");
	}

	private void buscarMusicas(String str) {
		tableMusic.removeAll();
		musicas = gcc.buscarListaCliente(str);
		mp3.setPlayList(musicas);
		for (int i = 0; i < musicas.size(); i++) {
			TableItem tableItem = new TableItem(tableMusic, SWT.NONE);
			tableItem.setText(0, musicas.get(i).getTitulo());
			tableItem.setText(1, musicas.get(i).getArtista());
			tableItem.setText(2, musicas.get(i).getDuracao());
			tableItem.setText(3, musicas.get(i).getGenero());
		}
		System.out.println("escrevi lista 3");
	}
}
