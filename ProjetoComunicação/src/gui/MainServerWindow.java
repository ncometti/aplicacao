package gui;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Vector;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.farng.mp3.TagException;

import dados.Musica;

import fachada.FachadaServidor;
import fachada.GuiCoreServidor;

public class MainServerWindow {

	protected Shell shell;
	private GuiCoreServidor gcs;
	private String pasta = "musicas servidor";
	private List musicList;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainServerWindow window = new MainServerWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		try {
			gcs = new FachadaServidor(this.pasta);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TagException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setImage(SWTResourceManager
				.getImage("C:\\Users\\Pedro\\Desktop\\Java\\eclipse\\workspace\\ProjetoGUI\\logoCIn.jpg"));
		shell.setSize(680, 610);
		shell.setLocation(348, 74);
		shell.setText("CInTunes Server");

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(10, 31, 232, 420);

		musicList = new List(composite, SWT.BORDER);
		musicList.setBounds(0, 0, 232, 420);
		atualizarLista();

		Label lblListaDeMsicas = new Label(shell, SWT.NONE);
		lblListaDeMsicas.setBounds(10, 10, 86, 15);
		lblListaDeMsicas.setText("Lista de M\u00FAsicas");

		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setBounds(248, 31, 210, 420);

		List clientsList = new List(composite_1, SWT.BORDER);
		clientsList.setBounds(0, 0, 210, 420);

		Label lblClientesAtivos = new Label(shell, SWT.NONE);
		lblClientesAtivos.setBounds(250, 10, 86, 15);
		lblClientesAtivos.setText("Clientes Ativos");

		Composite composite_2 = new Composite(shell, SWT.NONE);
		composite_2.setBounds(464, 31, 190, 517);

		Label lblDownloadsPorCliente = new Label(shell, SWT.NONE);
		lblDownloadsPorCliente.setBounds(462, 10, 128, 15);
		lblDownloadsPorCliente.setText("Downloads por Cliente");

		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);

		MenuItem mntmArquivo = new MenuItem(menu, SWT.CASCADE);
		mntmArquivo.setText("Arquivo");

		Menu menu_1 = new Menu(mntmArquivo);
		mntmArquivo.setMenu(menu_1);

		MenuItem mntmAtualizarMsicas = new MenuItem(menu_1, SWT.NONE);
		mntmAtualizarMsicas.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				atualizarLista();
			}
		});
		mntmAtualizarMsicas.setText("Atualizar M\u00FAsicas");

		MenuItem mntmSair = new MenuItem(menu_1, SWT.NONE);
		mntmSair.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		mntmSair.setText("Sair");

		MenuItem mntmAjuda = new MenuItem(menu, SWT.CASCADE);
		mntmAjuda.setText("Ajuda");

		Menu menu_2 = new Menu(mntmAjuda);
		mntmAjuda.setMenu(menu_2);

		MenuItem mntmSobre = new MenuItem(menu_2, SWT.NONE);
		mntmSobre.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SobreDialog sobre = new SobreDialog(shell, 0);
				sobre.open();
			}
		});
		mntmSobre.setText("Sobre");

		Label lblIp = new Label(shell, SWT.NONE);
		lblIp.setBounds(0, 533, 13, 15);
		lblIp.setText("IP:");

		Label lblIP = new Label(shell, SWT.NONE);
		lblIP.setBounds(19, 533, 120, 15);
		try {
			String ip = InetAddress.getLocalHost().toString();
			lblIP.setText(ip.substring(ip.lastIndexOf("/") + 1, ip.length()));
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}

	}

	private void atualizarLista() {
		Vector<Musica> musicas = gcs.atualizarListaMusicas();
		this.musicList.removeAll();
		for (int i = 0; i < musicas.size(); i++) {
			musicList.add(musicas.get(i).getTitulo() + " - "
					+ musicas.get(i).getArtista());
		}
	}
}
