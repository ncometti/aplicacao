package gui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class ActiveDownload extends Composite {

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public ActiveDownload(Composite parent, int style) {
		super(parent, style);

		Label lblNomeDaMsica = new Label(this, SWT.NONE);
		lblNomeDaMsica.setBounds(10, 10, 99, 15);
		lblNomeDaMsica.setText("Nome da M\u00FAsica");

		Label lblBanda = new Label(this, SWT.NONE);
		lblBanda.setBounds(10, 31, 38, 15);
		lblBanda.setText("Banda");

		Label lblStr = new Label(this, SWT.NONE);
		lblStr.setBounds(179, 31, 55, 15);
		lblStr.setText("STR");

		ProgressBar progressBar = new ProgressBar(this, SWT.NONE);
		progressBar.setBounds(10, 48, 224, 17);
		progressBar.setSelection(37);

		Button button = new Button(this, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// CHAMA FUNÇÃO PARA PAUSAR DOWNLOAD
			}
		});
		button.setBounds(202, 0, 18, 25);
		button.setText("||");

		Button button_1 = new Button(this, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// CHAMA FUNÇÃO PARAR CANCELAR DOWNLOAD
			}
		});
		button_1.setBounds(226, 0, 18, 25);
		button_1.setText("[]");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
