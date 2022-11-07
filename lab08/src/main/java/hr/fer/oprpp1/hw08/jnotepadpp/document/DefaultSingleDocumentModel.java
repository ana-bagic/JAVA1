package hr.fer.oprpp1.hw08.jnotepadpp.document;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import hr.fer.oprpp1.hw08.jnotepadpp.document.listeners.SingleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.document.models.SingleDocumentModel;

/**
 * Razred modelira implementaciju {@link SingleDocumentModel}-a.
 * 
 * @author Ana Bagić
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

	/**
	 * Komponenta koja u sebi ima trenutan sadržaj dokumenta.
	 */
	private JTextArea textArea;
	/**
	 * Putanja do dokumenta u datotečnom sustavu.
	 */
	private Path path;
	/**
	 * Zastavica označava je li dokument modificiran.
	 */
	private boolean modified;
	/**
	 * Listeneri za modifikacije na ovom modelu dokumenta.
	 */
	private List<SingleDocumentListener> listeners;

	/**
	 * Constructor creates new instance of this class using given path and sets its
	 * content to given. Konstruktor stvara novi primjerak ovog razreda na temelju
	 * dane putanje u datotečnom sustavu i postavlja njegov sadržaj.
	 * 
	 * @param path    putanja do dokumenta
	 * @param content tekst koji se stavlja u {@link JTextArea}
	 */
	public DefaultSingleDocumentModel(Path path, String content) {
		textArea = new JTextArea(content);
		this.path = path;
		modified = false;
		listeners = new LinkedList<>();

		textArea.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				setModified(true);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				setModified(true);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});
	}

	@Override
	public JTextArea getTextComponent() {
		return textArea;
	}

	@Override
	public Path getFilePath() {
		return path;
	}

	@Override
	public void setFilePath(Path path) {
		Objects.requireNonNull(path, "Putanja do dokumenta u datotečnom sustavu ne može biti null");

		this.path = path;
		for (SingleDocumentListener l : listeners) {
			l.documentFilePathUpdated(this);
		}
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;

		for (SingleDocumentListener l : listeners) {
			l.documentModifyStatusUpdated(this);
		}

	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}

}
