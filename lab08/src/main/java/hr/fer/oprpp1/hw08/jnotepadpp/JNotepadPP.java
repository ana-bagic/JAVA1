package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;

import hr.fer.oprpp1.hw08.jnotepadpp.document.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.document.listeners.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.document.models.SingleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationListener;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.utils.AllActions;

/**
 * Program pokreće aplikaciju za uređivanje teksta Notepad++.
 * 
 * @author Ana Bagić
 *
 */
public class JNotepadPP extends JFrame {

	private static final long serialVersionUID = 1L;
	/**
	 * Model koji sadrži sve modele otvorenih dokumenata
	 */
	private DefaultMultipleDocumentModel documentModel;
	/**
	 * Sve akcije koje se mogu izvesti u aplikaciji.
	 */
	private AllActions actions;
	/**
	 * Label koji prikazuje duljinu teksta.
	 */
	private JLabel length;
	/**
	 * Label koji prikazuje broj trenutnog reda.
	 */
	private JLabel ln;
	/**
	 * Label koj prikazuje broj trenutnog stupca.
	 */
	private JLabel col;
	/**
	 * Label koj prikazuje duljinu trenutno označenog teksta.
	 */
	private JLabel sel;
	/**
	 * Menu za datoteke.
	 */
	private JMenu fileMenu;
	/**
	 * Menu za uređivanje.
	 */
	private JMenu editMenu;
	/**
	 * Menu za alate.
	 */
	private JMenu toolsMenu;
	/**
	 * Menu za promijeniti veličinu slova.
	 */
	private JMenu changeCaseMenu;
	/**
	 * Menu za sortiranje teksta.
	 */
	private JMenu sortMenu;
	/**
	 * Menuza promjenu jezika.
	 */
	private JMenu languagesMenu;
	/**
	 * Form localization provider.
	 */
	private FormLocalizationProvider flp;

	/**
	 * Stvara novi primjerak razreda JNotepadPP te mu postavlja grafičko korisničko sučelje.
	 */
	public JNotepadPP() {
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		flp.addLocalizationListener(new DefaultLocalizationListener());
		
		documentModel = new DefaultMultipleDocumentModel();
		documentModel.addMultipleDocumentListener(new DefaultMultipleDocumentListener());
		actions = new AllActions(this, documentModel, new DefaultCaretListener());
		addWindowListener(new DefaultWindowListener());
		
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle("Notepad++");
		setLocation(0, 0);
		setSize(600, 600);
		
		initGUI();
	}

	/**
	 * Inicijalizira grafičko korisničko sučelje.
	 */
	private void initGUI() {
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(documentModel, BorderLayout.CENTER);

		createMenuBar();
		createToolBar();
		createStatusBar();
		
		actions.getToUppercaseAction().setEnabled(false);
		actions.getToLowercaseAction().setEnabled(false);
		actions.getInvertCaseAction().setEnabled(false);
	}

	/**
	 * Stvara izbornik.
	 */
	private void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		fileMenu = new JMenu("Datoteka");

		fileMenu.add(new JMenuItem(actions.getOpenNewDocumentAction()));
		fileMenu.add(new JMenuItem(actions.getLoadDocumentAction()));
		fileMenu.add(new JMenuItem(actions.getSaveDocumentAction()));
		fileMenu.add(new JMenuItem(actions.getSaveAsDocumentAction()));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(actions.getStatsInfoAction()));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(actions.getCloseDocumentAction()));
		fileMenu.add(new JMenuItem(actions.getExitAction()));
		menuBar.add(fileMenu);

		editMenu = new JMenu("Uredi");

		editMenu.add(new JMenuItem(actions.getCutAction()));
		editMenu.add(new JMenuItem(actions.getCopyAction()));
		editMenu.add(new JMenuItem(actions.getPasteAction()));
		menuBar.add(editMenu);

		toolsMenu = new JMenu("Alati");

		changeCaseMenu = new JMenu("Promijeni veličinu slova");
		changeCaseMenu.add(new JMenuItem(actions.getToUppercaseAction()));
		changeCaseMenu.add(new JMenuItem(actions.getToLowercaseAction()));
		changeCaseMenu.add(new JMenuItem(actions.getInvertCaseAction()));
		toolsMenu.add(changeCaseMenu);
		
		sortMenu = new JMenu("Sortiraj");
		sortMenu.add(new JMenuItem(actions.getSortAscendingAction()));
		sortMenu.add(new JMenuItem(actions.getSortDescendingAction()));
		toolsMenu.add(sortMenu);
		
		toolsMenu.add(new JMenuItem(actions.getUniqueAction()));
		menuBar.add(toolsMenu);
		
		languagesMenu = new JMenu("Jezici");
		languagesMenu.add(new JMenuItem(actions.getEnglishAction()));
		languagesMenu.add(new JMenuItem(actions.getCroatianAction()));
		languagesMenu.add(new JMenuItem(actions.getGermanAction()));
		menuBar.add(languagesMenu);

		setJMenuBar(menuBar);
	}

	/**
	 * Stvara alatnu traku.
	 */
	private void createToolBar() {
		JToolBar toolBar = new JToolBar("Alati");
		toolBar.setFloatable(true);

		toolBar.add(new JButton(actions.getOpenNewDocumentAction()));
		toolBar.add(new JButton(actions.getLoadDocumentAction()));
		toolBar.add(new JButton(actions.getSaveDocumentAction()));
		toolBar.add(new JButton(actions.getSaveAsDocumentAction()));
		toolBar.addSeparator();
		toolBar.add(new JButton(actions.getCutAction()));
		toolBar.add(new JButton(actions.getCopyAction()));
		toolBar.add(new JButton(actions.getPasteAction()));
		toolBar.addSeparator();
		toolBar.add(new JButton(actions.getStatsInfoAction()));
		toolBar.add(new JButton(actions.getCloseDocumentAction()));

		getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}

	/**
	 * Stvara statusnu traku.
	 */
	private void createStatusBar() {
		JPanel statusBar = new JPanel(new GridLayout(1, 3));
		statusBar.setBorder(BorderFactory.createMatteBorder(3, 0, 0, 0, Color.GRAY));

		length = new JLabel(" duljina: ");
		statusBar.add(length);

		JPanel centerGrid = new JPanel(new GridLayout(1, 3));
		centerGrid.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.GRAY));
		ln = new JLabel(" Ln: ");
		col = new JLabel("Col: ");
		sel = new JLabel("Sel: ");
		centerGrid.add(ln);
		centerGrid.add(col);
		centerGrid.add(sel);
		statusBar.add(centerGrid);

		JLabel date = new JLabel();
		date.setHorizontalAlignment(JLabel.RIGHT);
		statusBar.add(date);

		Timer timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
				date.setText(df.format(new Date()));
			}
		});
		timer.start();

		this.add(statusBar, BorderLayout.SOUTH);
	}

	/**
	 * Razred modelira implementaciju {@link WindowAdapter}-a.
	 */
	private class DefaultWindowListener extends WindowAdapter {

		@Override
		public void windowClosing(WindowEvent e) {
			for (SingleDocumentModel document : documentModel) {
				if (document.isModified()) {
					String docName = document.getFilePath() == null ? "(unnamed)" : document.getFilePath().toString();
					int response = JOptionPane.showOptionDialog(JNotepadPP.this,
							"Želite li spremiti promjene u dokumentu " + docName + "?", "Spremanje promjena",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
							new String[] { "Da", "Ne", "Otkaži" }, null);

					if (response == JOptionPane.YES_OPTION) {
						Path path = actions.checkPath(document.getFilePath());
						documentModel.saveDocument(document, path);
					} else if (response == JOptionPane.CANCEL_OPTION)
						return;
				}
			}

			dispose();
		}
	}

	/**
	 * Razred modelira implementaciju {@link MultipleDocumentListener}-a.
	 */
	private class DefaultMultipleDocumentListener implements MultipleDocumentListener {

		@Override
		public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
			if (documentModel.getNumberOfDocuments() == 0) {
				setTitle("JNotepad++");
			} else {
				setTitle(currentModel.getFilePath() == null ? "(unnamed) - JNotepad++"
						: (currentModel.getFilePath() + " - JNotepad++"));
			}
		}

		@Override
		public void documentAdded(SingleDocumentModel model) {
		}

		@Override
		public void documentRemoved(SingleDocumentModel model) {
			boolean enabled = documentModel.getNumberOfDocuments() != 0;
			actions.getToUppercaseAction().setEnabled(enabled);
			actions.getToLowercaseAction().setEnabled(enabled);
			actions.getInvertCaseAction().setEnabled(enabled);
		}

	}

	/**
	 * Razred modelira implementaciju {@link CaretListener}-a.
	 */
	private class DefaultCaretListener implements CaretListener {

		@Override
		public void caretUpdate(CaretEvent e) {
			JTextArea textArea = (JTextArea) e.getSource();
			int caretDot = textArea.getCaret().getDot();
			int caretMark = textArea.getCaret().getMark();

			length.setText(" duljina: " + textArea.getText().length());

			try {
				int line = textArea.getLineOfOffset(caretDot);
				int column = caretDot - textArea.getLineStartOffset(line++) + 1;
				ln.setText(" Ln: " + line);
				col.setText("Col: " + column);
				sel.setText("Sel: " + Math.abs(caretDot - caretMark));
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}

			boolean enabled = (caretDot - caretMark) != 0 && documentModel.getNumberOfDocuments() != 0;
			actions.getToUppercaseAction().setEnabled(enabled);
			actions.getToLowercaseAction().setEnabled(enabled);
			actions.getInvertCaseAction().setEnabled(enabled);
		}

	}
	
	/**
	 * Razred modelira implementaciju {@link ILocalizationListener}-a.
	 */
	private class DefaultLocalizationListener implements ILocalizationListener {

		@Override
		public void localizationChanged() {
			actions.getOpenNewDocumentAction().putValue(Action.NAME, flp.getString("new"));
			actions.getLoadDocumentAction().putValue(Action.NAME, flp.getString("open"));
			actions.getSaveDocumentAction().putValue(Action.NAME, flp.getString("save"));
			actions.getSaveAsDocumentAction().putValue(Action.NAME, flp.getString("saveAs"));
			actions.getCloseDocumentAction().putValue(Action.NAME, flp.getString("close"));
			actions.getStatsInfoAction().putValue(Action.NAME, flp.getString("stats"));
			actions.getExitAction().putValue(Action.NAME, flp.getString("exit"));
			actions.getCutAction().putValue(Action.NAME, flp.getString("cut"));
			actions.getCopyAction().putValue(Action.NAME, flp.getString("copy"));
			actions.getPasteAction().putValue(Action.NAME, flp.getString("paste"));
			
			actions.getToUppercaseAction().putValue(Action.NAME, flp.getString("toUpper"));
			actions.getToLowercaseAction().putValue(Action.NAME, flp.getString("toLower"));
			actions.getInvertCaseAction().putValue(Action.NAME, flp.getString("invert"));
			actions.getSortDescendingAction().putValue(Action.NAME, flp.getString("descending"));
			actions.getSortAscendingAction().putValue(Action.NAME, flp.getString("ascending"));
			actions.getUniqueAction().putValue(Action.NAME, flp.getString("unique"));
			actions.getEnglishAction().putValue(Action.NAME, flp.getString("english"));
			actions.getCroatianAction().putValue(Action.NAME, flp.getString("croatian"));
			actions.getGermanAction().putValue(Action.NAME, flp.getString("german"));
			
			fileMenu.setText(flp.getString("file"));
			editMenu.setText(flp.getString("edit"));
			toolsMenu.setText(flp.getString("tools"));
			changeCaseMenu.setText(flp.getString("changeCase"));
			sortMenu.setText(flp.getString("sort"));
			languagesMenu.setText(flp.getString("language"));
		}
		
	}

	/**
	 * Funkcija koja pokreće aplikaciju.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
	}

}
