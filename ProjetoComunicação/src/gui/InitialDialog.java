package gui;

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

public class InitialDialog extends Dialog {

	protected Object result;
	protected Shell shlCintunes;
	private Text textName;
	private Text textIP;
	private Button btnOk;
	private Label lblTaxaDeErro;
	private Text textErro;
	private GuiCoreCliente gcc;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public InitialDialog(Shell parent, int style, GuiCoreCliente gcc) {
		super(parent, style);
		this.gcc = gcc;
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
	public Object open() throws JavaLayerException, IOException, TagException,
			UnsupportedAudioFileException {
		createContents();
		shlCintunes.open();
		shlCintunes.layout();
		Display display = getParent().getDisplay();
		while (!shlCintunes.isDisposed()) {
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
		shlCintunes = new Shell(getParent(), getStyle());
		shlCintunes.setSize(240, 200);
		shlCintunes.setLocation(460, 230);
//		shlCintunes.setText("CInTunes");
//		shlCintunes
//				.setImage(SWTResourceManager
//						.getImage("C:\\Users\\Pedro\\Desktop\\Java\\eclipse\\workspace\\ProjetoGUI\\logoCIn.jpg"));

		Label lblDigiteOSeu = new Label(shlCintunes, SWT.NONE);
		lblDigiteOSeu.setBounds(10, 10, 42, 15);
		lblDigiteOSeu.setText("Nome:");

		textName = new Text(shlCintunes, SWT.BORDER);
		textName.setBounds(10, 31, 215, 21);

		Label lblNewLabel = new Label(shlCintunes, SWT.NONE);
		lblNewLabel.setBounds(10, 58, 55, 15);
		lblNewLabel.setText("IP");

		textIP = new Text(shlCintunes, SWT.BORDER);
		textIP.setBounds(10, 79, 215, 21);

		btnOk = new Button(shlCintunes, SWT.NONE);
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int tolerancia = Integer.parseInt(textErro.getText());
				gcc.setTolerancia(tolerancia);
				gcc.setServerIP(textIP.getText());
				gcc.setUserName(textName.getText());
				shlCintunes.close();
			}
		});
		btnOk.setBounds(149, 137, 75, 25);
		btnOk.setText("OK");

		lblTaxaDeErro = new Label(shlCintunes, SWT.NONE);
		lblTaxaDeErro.setBounds(10, 106, 101, 15);
		lblTaxaDeErro.setText("Taxa de Erro (0~99)");

		textErro = new Text(shlCintunes, SWT.BORDER);
		textErro.setBounds(10, 127, 27, 21);
		textErro.setText("0");

	}
}
