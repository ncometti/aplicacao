package gui;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import javazoom.jl.decoder.JavaLayerException;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.farng.mp3.TagException;

import fachada.FachadaCliente;
import fachada.GuiCoreCliente;

public class NameDialog extends Dialog {

	protected Object result;
	protected Shell shlMudarNome;
	protected Shell shell;
	private Text campoTexto;
	private Button btnPronto;
	private GuiCoreCliente gcc;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public NameDialog(Shell parent, int style, GuiCoreCliente gcc) {
		super(parent, style);
		this.gcc = gcc;
		// this.shell = parent;
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 * @throws JavaLayerException
	 * @throws UnsupportedAudioFileException 
	 * @throws TagException 
	 * @throws IOException 
	 */
	public Object open() throws JavaLayerException, IOException, TagException, UnsupportedAudioFileException {
		createContents();
		shlMudarNome.open();
		shlMudarNome.layout();
		Display display = getParent().getDisplay();
		while (!shlMudarNome.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shlMudarNome = new Shell(getParent(), getStyle());
		shlMudarNome.setSize(340, 150);
		shlMudarNome.setLocation(518, 309);
		shlMudarNome.setText("Mudar Nome");

		Label lblDigiteSeuNome = new Label(shlMudarNome, SWT.NONE);
		lblDigiteSeuNome.setFont(SWTResourceManager.getFont("Segoe UI", 12,
				SWT.NORMAL));
		lblDigiteSeuNome.setBounds(10, 10, 118, 23);
		lblDigiteSeuNome.setText("Digite seu nome");

		campoTexto = new Text(shlMudarNome, SWT.BORDER);
		campoTexto.setFont(SWTResourceManager.getFont("Segoe UI", 12,
				SWT.NORMAL));
		campoTexto.setBounds(10, 39, 314, 35);

		btnPronto = new Button(shlMudarNome, SWT.NONE);
		btnPronto.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String nome = campoTexto.getText();
				gcc.setUserName(nome);
				shlMudarNome.close();
			}
		});
		btnPronto.setBounds(249, 80, 75, 25);
		btnPronto.setText("Pronto");

	}
}
