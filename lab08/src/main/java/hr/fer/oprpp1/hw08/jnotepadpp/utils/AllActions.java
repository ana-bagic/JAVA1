package hr.fer.oprpp1.hw08.jnotepadpp.utils;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.oprpp1.hw08.jnotepadpp.document.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.document.models.SingleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;

/**
 * Pomoćni razred koji sadrži sve akcije korištene u aplikaciji Notepad++.
 * 
 * @author Ana Bagić
 *
 */
public class AllActions {

	/**
	 * Akcija otvaranja novog dokumenta.
	 */
	private Action openNewDocument;
	/**
	 * Akcija učitavanja postojećeg dokumenta.
	 */
	private Action loadDocument;
	/**
	 * Akcija spremanja trenutnog dokumenta.
	 */
	private Action saveDocument;
	/**
	 * Akcija spremanja kao trenutnog dokumenta.
	 */
	private Action saveAsDocument;
	/**
	 * Akcija zatvaranja trenutnog dokumenta.
	 */
	private Action closeDocument;
	/**
	 * Akcija izlaska iz aplikacije.
	 */
	private Action exit;
	/**
	 * Akcija prikaza statistike dokumenta.
	 */
	private Action statsInfo;
	/**
	 * Akcija izrezivanja označenog dijela teksta u dokumentu.
	 */
	private Action cut;
	/**
	 * Akcija kopiranja označenog dijela teksta u dokumentu.
	 */
	private Action copy;
	/**
	 * Akcija ljepljenja sadržaja iz međuspremnika na označeno mjesto u dokumentu.
	 */
	private Action paste;
	/**
	 * Akcija pretvaranja označenog dijela teksta u velika slova.
	 */
	private Action toUppercase;
	/**
	 * Akcija pretvaranja označenog dijela teksta u mala slova.
	 */
	private Action toLowercase;
	/**
	 * Akcija pretvaranja označenog dijela teksta u suprotna slova.
	 */
	private Action invertCase;
	/**
	 * Akcija uzlaznog sortiranja.
	 */
	private Action sortAscending;
	/**
	 * Akcija silaznog sortiranja.
	 */
	private Action sortDescending;
	/**
	 * Akcija prikaza jedinstvenih linija teksta.
	 */
	private Action unique;
	/**
	 * Akcija mijenjanja jezika na engleski.
	 */
	private Action english;
	/**
	 * Akcija mijenjanja jezika na hrvatski.
	 */
	private Action croatian;
	/**
	 * Akcija mijenjanja jezika na njemački.
	 */
	private Action german;
	/**
	 * Međuspremnik kopiranog/izrezanog teksta.
	 */
	private String clipboard;
	/**
	 * Frame koji sadrži korisničko sučelje aplikacije.
	 */
	private JFrame frame;
	/**
	 * Model koji sadrži sve modele otvorenih dokumenata.
	 */
	private DefaultMultipleDocumentModel model;
	/**
	 * Caret listener korišten u aplikaciji.
	 */
	private CaretListener caretlistener;
	
	/**
	 * Konstruktor stvara primjerak ovog razreda zadavanjem framea aplikacije, modela dokumenata i listenera pomaka pokazivača u tekstu.
	 * 
	 * @param frame galvni frame aplikacije
	 * @param model model dokumenata korišten u aplikaciji
	 * @param caretListener caret listener
	 */
	public AllActions(JFrame frame, DefaultMultipleDocumentModel model, CaretListener caretListener) {
		this.frame = frame;
		this.model = model;
		this.caretlistener = caretListener;
		
		createActions();
	}
	
	/**
	 * Pomoćna funkcija za stvaranje svih akcija.
	 */
	private void createActions() {
		openNewDocument = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				SingleDocumentModel document = model.createNewDocument();
				document.getTextComponent().addCaretListener(caretlistener);
			}
		};
		openNewDocument.putValue(Action.NAME, "Novo");
		openNewDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		openNewDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		openNewDocument.putValue(Action.SHORT_DESCRIPTION, "Stvara novi dokument.");
		
		loadDocument = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Učitaj dokument");
				if (fc.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION)
					return;

				Path path = fc.getSelectedFile().toPath();
				SingleDocumentModel document = model.loadDocument(path);
				document.getTextComponent().addCaretListener(caretlistener);
			}
		};
		loadDocument.putValue(Action.NAME, "Učitaj");
		loadDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		loadDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		loadDocument.putValue(Action.SHORT_DESCRIPTION, "Otvara postojeći dokument sa diska.");

		closeDocument = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				model.closeDocument(model.getCurrentDocument());
			}
		};
		closeDocument.putValue(Action.NAME, "Zatvori");
		closeDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("esc"));
		closeDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_ESCAPE);
		closeDocument.putValue(Action.SHORT_DESCRIPTION, "Zatvara trenutno otvoreni dokument.");

		
		saveDocument = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if(model.getNumberOfDocuments() == 0)
					return;
				
				Path path = checkPath(model.getCurrentDocument().getFilePath());
				if(path == null)
					return;
				
				model.saveDocument(model.getCurrentDocument(), path);
			}
		};
		saveDocument.putValue(Action.NAME, "Spremi");
		saveDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocument.putValue(Action.SHORT_DESCRIPTION, "Sprema trenutan dokument na disk");
		
		saveAsDocument = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if(model.getNumberOfDocuments() == 0)
					return;
				
				Path path = checkPath(null);
				if(path == null)
					return;
				
				model.saveDocument(model.getCurrentDocument(), path);
			}
		};
		saveAsDocument.putValue(Action.NAME, "Spremi kao");
		saveAsDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F12"));
		saveAsDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_F12);
		saveAsDocument.putValue(Action.SHORT_DESCRIPTION, "Sprema trenutan dokument na disk kao novu datoteku.");
		
		exit = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};
		exit.putValue(Action.NAME, "Izađi");
		exit.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		exit.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		exit.putValue(Action.SHORT_DESCRIPTION, "Izlazi iz aplikacije.");
		
		statsInfo = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if(model.getNumberOfDocuments() == 0)
					return;
				
				String document = model.getCurrentDocument().getTextComponent().getText();
				int nrOfChars = 0, nrOfNonBlank = 0, nrOfLines = 1;

				for(char c : document.toCharArray()) {
					if (!Character.isWhitespace(c))
						nrOfNonBlank++;
					if (c == '\n')
						nrOfLines++;
					nrOfChars++;
				}

				JOptionPane.showMessageDialog(frame,
						"Dokument sadrži " + nrOfChars + " znakova, " + nrOfNonBlank
							+ " znakova koji nisu praznine i " + nrOfLines + " linija.",
						"Statistika", JOptionPane.INFORMATION_MESSAGE);
			}
		};
		statsInfo.putValue(Action.NAME, "Statistika");
		statsInfo.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		statsInfo.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		statsInfo.putValue(Action.SHORT_DESCRIPTION, "Prikazuje statistiku dokumenta.");
		
		cut = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				textEdit("cut");
			}
		};
		cut.putValue(Action.NAME, "Izreži");
		cut.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cut.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		cut.putValue(Action.SHORT_DESCRIPTION, "Izrezuje označeni dio teksta iz dokumenta.");

		copy = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				textEdit("copy");
			}
		};
		copy.putValue(Action.NAME, "Kopiraj");
		copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copy.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		copy.putValue(Action.SHORT_DESCRIPTION, "Kopira označeni dio teksta iz dokumenta.");

		paste = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				textEdit("paste");
			}
		};
		paste.putValue(Action.NAME, "Zalijepi");
		paste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		paste.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		paste.putValue(Action.SHORT_DESCRIPTION, "Lijepi sadržaj iz međuspremnika na označeno mjesto u dokumentu.");
		
		toUppercase = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				textEdit("toUpper");
			}
		};
		toUppercase.putValue(Action.NAME, "U velika slova");
		toUppercase.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		toUppercase.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		toUppercase.putValue(Action.SHORT_DESCRIPTION, "Pretvara označeni dio teksta u velika slova.");
		
		toLowercase = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				textEdit("toLower");
			}
		};
		toLowercase.putValue(Action.NAME, "U mala slova");
		toLowercase.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
		toLowercase.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		toLowercase.putValue(Action.SHORT_DESCRIPTION, "Pretvara označeni dio teksta u mala slova.");
		
		invertCase = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				textEdit("invert");
			}
		};
		invertCase.putValue(Action.NAME, "U suprotna slova");
		invertCase.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control R"));
		invertCase.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);
		invertCase.putValue(Action.SHORT_DESCRIPTION, "Pretvara označeni dio teksta u suprotna slova.");
		
		sortAscending = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				textEdit("ascending");
			}
		};
		sortAscending.putValue(Action.NAME, "Uzlazno");
		sortAscending.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));
		sortAscending.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		sortAscending.putValue(Action.SHORT_DESCRIPTION, "Uzlazno sortira označene linije teksta.");

		sortDescending = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				textEdit("descending");
			}
		};
		sortDescending.putValue(Action.NAME, "Silazno");
		sortDescending.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control D"));
		sortDescending.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		sortDescending.putValue(Action.SHORT_DESCRIPTION, "Silazno sortira označene linije teksta.");
		
		unique = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				textEdit("unique");
			}
		};
		unique.putValue(Action.NAME, "Jedinstveno");
		unique.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control K"));
		unique.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_K);
		unique.putValue(Action.SHORT_DESCRIPTION, "Prikazuje jednistvene linije kod označenog teksta.");
		
		english = new AbstractAction() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("en");
				LocalizationProvider.getInstance().fire();
			}
		};
		english.putValue(Action.NAME, "Engleski");

		croatian = new AbstractAction() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("hr");
				LocalizationProvider.getInstance().fire();
			}
		};
		croatian.putValue(Action.NAME, "Hrvatski");

		german = new AbstractAction() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("de");
				LocalizationProvider.getInstance().fire();
			}
		};
		german.putValue(Action.NAME, "Njemački");
	}
	
	/**
	 * Pomoćna funkcija koja se koristi kad se želi izmjeniti tekst unutar editora.
	 * 
	 * @param op operacija koja se želi izvesti nad tekstom
	 */
	private void textEdit(String op) {
		JTextArea area = model.getCurrentDocument().getTextComponent();
		Document doc = area.getDocument();
		int caretDot = area.getCaret().getDot();
		int caretMark = area.getCaret().getMark();
		
		int length = Math.abs(caretDot - caretMark);
		int offset = Math.min(caretDot, caretMark);
		try {
			switch (op) {
			case "cut" -> {
				clipboard = area.getText(offset, length);
				doc.remove(offset, length);
			}
			case "copy" -> {
				clipboard = area.getText(offset, length);
			}
			case "paste" -> {
				doc.insertString(offset, clipboard, null);
			}
			case "toUpper" -> {
				String changed = doc.getText(offset, length).toUpperCase();
				doc.remove(offset, length);
				doc.insertString(offset, changed, null);
			}
			case "toLower" -> {
				String changed = doc.getText(offset, length).toLowerCase();
				doc.remove(offset, length);
				doc.insertString(offset, changed, null);
			}
			case "invert" -> {
				StringBuilder sb = new StringBuilder();
				for(char c : doc.getText(offset, length).toCharArray()) {
					if(Character.isUpperCase(c))
						sb.append(Character.toLowerCase(c));
					else
						sb.append(Character.toUpperCase(c));
				}
				doc.remove(offset, length);
				doc.insertString(offset, sb.toString(), null);
			}
			case "ascending", "descending" -> {
				int startLine = area.getLineOfOffset(offset);
				int endLine = area.getLineOfOffset(offset + length);

				List<String> selected = new ArrayList<>();
				String[] wholeText = doc.getText(0, doc.getLength()).toString().split("\n");
				for (int i = startLine; i <= endLine; i++)
					selected.add(wholeText[i]);

				Collections.sort(selected);
				if(op.equals("descending"))
					Collections.reverse(selected);

				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < selected.size() - 1; i++)
					sb.append(selected.get(i)).append("\n");
				sb.append(selected.get(selected.size() - 1));

				doc.remove(doc.getDefaultRootElement().getElement(startLine).getStartOffset(), sb.toString().length());
				doc.insertString(doc.getDefaultRootElement().getElement(startLine).getStartOffset(), sb.toString(), null);
			}
			case "unique" -> {
				int startLine = area.getLineOfOffset(offset);
				int endLine = area.getLineOfOffset(offset + length);

				Set<String> unique = new LinkedHashSet<>();
				String[] wholeText = doc.getText(0, doc.getLength()).toString().split("\n");
				StringBuilder selected = new StringBuilder();
				for (int i = startLine; i <= endLine; i++) {
					selected.append(wholeText[i]).append("\n");
					unique.add(wholeText[i]);
				}

				StringBuilder sb = new StringBuilder();
				int i = 0;
				for (String s : unique) {
					sb.append(s);
					if(i != unique.size() - 1) {
						sb.append("\n");
					}
					i++;
				}

				doc.remove(doc.getDefaultRootElement().getElement(startLine).getStartOffset(), selected.toString().length() - 1);
				doc.insertString(doc.getDefaultRootElement().getElement(startLine).getStartOffset(), sb.toString(), null);
			}
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Pomoćna funkcija sprema trenutni dokument u modelu na dani path.
	 * Ako je path <code>null</code> stvara novi dokument u datotečnom sustavu.
	 * 
	 * @param path path na koji se želi spremiti dokument ili <code>null</code>
	 */
	public Path checkPath(Path path) {
		Path newPath = null;
		
		if (path == null) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Spremi dokument kao");
			if (fc.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(frame, "Promjene nisu spremljene.",
						"Upozorenje", JOptionPane.WARNING_MESSAGE);
				return null;
			}
			
			newPath = fc.getSelectedFile().toPath();
			if (newPath.toFile().exists()) {
				JOptionPane.showMessageDialog(frame, "Datoteka već postoji.",
						"Upozorenje", JOptionPane.WARNING_MESSAGE);
				return null;
			}
			
			return newPath;
		}
		
		return path;
	}

	/**
	 * @return akciju otvaranja novog dokumenta
	 */
	public Action getOpenNewDocumentAction() {
		return openNewDocument;
	}

	/**
	 * @return akciju učitavanja postojećeg dokumenta
	 */
	public Action getLoadDocumentAction() {
		return loadDocument;
	}

	/**
	 * @return akciju spremanja trenutnog dokumenta
	 */
	public Action getSaveDocumentAction() {
		return saveDocument;
	}

	/**
	 * @return akciju spremanja kao trenutnog dokumenta
	 */
	public Action getSaveAsDocumentAction() {
		return saveAsDocument;
	}

	/**
	 * @return akciju zatvaranja trenutnog dokumenta
	 */
	public Action getCloseDocumentAction() {
		return closeDocument;
	}

	/**
	 * @return akciju izlaska iz aplikacije
	 */
	public Action getExitAction() {
		return exit;
	}
	
	/**
	 * @return akciju prikaza statistike dokumenta
	 */
	public Action getStatsInfoAction() {
		return statsInfo;
	}
	
	/**
	 * @return akciju izrezivanja označenog dijela teksta u dokumentu
	 */
	public Action getCutAction() {
		return cut;
	}
	
	/**
	 * @return akciju kopiranja označenog dijela teksta u dokumentu
	 */
	public Action getCopyAction() {
		return copy;
	}
	
	/**
	 * @return akciju ljepljenja sadržaja iz međuspremnika na označeno mjesto u dokumentu
	 */
	public Action getPasteAction() {
		return paste;
	}
	
	/**
	 * @return akciju pretvaranja označenog dijela teksta u velika slova
	 */
	public Action getToUppercaseAction() {
		return toUppercase;
	}
	
	/**
	 * @return akciju pretvaranja označenog dijela teksta u mala slova
	 */
	public Action getToLowercaseAction() {
		return toLowercase;
	}
	
	/**
	 * @return akciju pretvaranja označenog dijela teksta u suprotna slova
	 */
	public Action getInvertCaseAction() {
		return invertCase;
	}

	/**
	 * @return akciju uzlaznog sortiranja
	 */
	public Action getSortAscendingAction() {
		return sortAscending;
	}

	/**
	 * @return akciju silaznog sortiranja
	 */
	public Action getSortDescendingAction() {
		return sortDescending;
	}

	/**
	 * @return akciju prikaza jedinstvenih linija teksta
	 */
	public Action getUniqueAction() {
		return unique;
	}

	/**
	 * @return akciju mijenjanja jezika na engleski
	 */
	public Action getEnglishAction() {
		return english;
	}

	/*
	 * @return akciju mijenjanja jezika na hrvatski
	 */
	public Action getCroatianAction() {
		return croatian;
	}

	/**
	 * @return akciju mijenjanja jezika na njemački
	 */
	public Action getGermanAction() {
		return german;
	}
	

}
