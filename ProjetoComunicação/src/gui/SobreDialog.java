package gui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class SobreDialog extends Dialog {

	protected Object result;
	protected Shell shlSobre;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public SobreDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlSobre.open();
		shlSobre.layout();
		Display display = getParent().getDisplay();
		while (!shlSobre.isDisposed()) {
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
		shlSobre = new Shell(getParent(), getStyle());
		shlSobre.setSize(388, 280);
		shlSobre.setLocation(514, 244);
		shlSobre.setText("Sobre");

		Composite composite = new Composite(shlSobre, SWT.NONE);
		composite
				.setBackgroundImage(SWTResourceManager
						.getImage("imagens\\logoCIn.png"));
		composite.setBounds(10, 20, 102, 130);

		Button okButton = new Button(shlSobre, SWT.NONE);
		okButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlSobre.close();
			}
		});
		okButton.setBounds(153, 222, 75, 25);
		okButton.setText("OK");

		Label lblInfraestruturaDeComunicao = new Label(shlSobre, SWT.NONE);
		lblInfraestruturaDeComunicao.setFont(SWTResourceManager.getFont(
				"Segoe UI", 12, SWT.NORMAL));
		lblInfraestruturaDeComunicao.setBounds(10, 191, 362, 25);
		lblInfraestruturaDeComunicao
				.setText("Infra-Estrutura de Comunica\u00E7\u00E3o 2012.2   CIn - UFPE");

		Label lblProjetadoEDesenvolvido = new Label(shlSobre, SWT.NONE);
		lblProjetadoEDesenvolvido.setBounds(118, 52, 164, 15);
		lblProjetadoEDesenvolvido.setText("Projetado e desenvolvido por:");

		Label lblNewLabel = new Label(shlSobre, SWT.NONE);
		lblNewLabel.setBounds(118, 73, 180, 15);
		lblNewLabel.setText("Felipe do Couto Farias - fcf4");

		Label lblNewLabel_1 = new Label(shlSobre, SWT.NONE);
		lblNewLabel_1.setBounds(118, 94, 141, 15);
		lblNewLabel_1.setText("Jonas de Ara\u00FAjo Lins - jal3");

		Label lblNewLabel_2 = new Label(shlSobre, SWT.NONE);
		lblNewLabel_2.setBounds(118, 115, 194, 15);
		lblNewLabel_2.setText("Marina de Meira Lins Haak - mmlh");

		Label lblNewLabel_3 = new Label(shlSobre, SWT.NONE);
		lblNewLabel_3.setBounds(118, 136, 254, 15);
		lblNewLabel_3
				.setText("Nat\u00E1lia Paola de Vasconcelos Cometti - npvc");

		Label lblNewLabel_4 = new Label(shlSobre, SWT.NONE);
		lblNewLabel_4.setBounds(118, 157, 254, 15);
		lblNewLabel_4
				.setText("Pedro Tiago de V. S. R. Ara\u00FAjo - ptvsr");

		Label lblCintunes = new Label(shlSobre, SWT.NONE);
		lblCintunes.setFont(SWTResourceManager.getFont("Segoe UI", 10,
				SWT.NORMAL));
		lblCintunes.setBounds(118, 10, 75, 15);
		lblCintunes.setText("MuziKa 1.0");

		Label label = new Label(shlSobre, SWT.NONE);
		label.setBounds(118, 31, 55, 15);
		label.setText("\u00A92013");

	}

}
