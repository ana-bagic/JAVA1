package hr.fer.oprpp1.hw08.jnotepadpp.document;

import java.awt.GridLayout;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import hr.fer.oprpp1.hw08.jnotepadpp.document.listeners.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.document.listeners.SingleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.document.models.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.document.models.SingleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.utils.Utils;

/**
 * Razred modelira implementaciju {@link MultipleDocumentModel}-a i
 * {@link JTabbedPane}-a.
 * 
 * @author Ana Bagić
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	private static final long serialVersionUID = 1L;
	/**
	 * Lista svih dokumenata sadržanih u ovom modelu.
	 */
	private List<SingleDocumentModel> documents;
	/**
	 * Referenca na trenutan dokument koji se prikazuje korisniku.
	 */
	private SingleDocumentModel currentDocument;
	/**
	 * Listeneri modifikacija u ovome modelu.
	 */
	private List<MultipleDocumentListener> listeners;

	public DefaultMultipleDocumentModel() {
		documents = new LinkedList<>();
		listeners = new LinkedList<>();

		this.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int index = getSelectedIndex();

				if (index != -1) {
					for (MultipleDocumentListener listener : listeners) {
						listener.currentDocumentChanged(currentDocument, documents.get(index));
					}
					currentDocument = documents.get(index);
				}
			}
		});
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		return createNewDocument("", null);
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		Objects.requireNonNull(path, "Putanja do dokumenta u datotečnom sustavu ne može biti null.");

		if (!Files.isReadable(path)) {
			JOptionPane.showMessageDialog(this, "Datoteku " + path.toAbsolutePath() + " nije moguće pročitati.",
					"Pogreška", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		for (SingleDocumentModel model : documents) {
			if (model.getFilePath() != null && model.getFilePath().equals(path)) {
				currentDocument = model;
				setSelectedIndex(documents.indexOf(currentDocument));
				return currentDocument;
			}
		}

		byte[] bytes;
		try {
			bytes = Files.readAllBytes(path);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Pogreška prilikom čitanja datoteke " + path.toAbsolutePath() + ".",
					"Pogreška", JOptionPane.ERROR_MESSAGE);
			return null;
		}

		String content = new String(bytes, StandardCharsets.UTF_8);
		return createNewDocument(content, path);
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		byte[] bytes = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
		Path path = newPath != null ? newPath : model.getFilePath();

		try {
			Files.write(path, bytes);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Pogreška prilikom spremanja datoteke " + path.toAbsolutePath() + ".",
					"Pogreška", JOptionPane.ERROR_MESSAGE);
			return;
		}
		JOptionPane.showMessageDialog(this, "Datoteka je uspješno spremljena.", "Uspjeh",
				JOptionPane.INFORMATION_MESSAGE);

		model.setFilePath(path);
		model.setModified(false);
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		int index = documents.indexOf(model);
		if (index == -1)
			return;

		remove(index);
		documents.remove(model);

		for (MultipleDocumentListener l : listeners) {
			l.documentRemoved(model);
		}
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return documents.get(index);
	}

	/**
	 * Pomoćna funkcija za stvaranje i dodavanje novog dokumenta u model na temelju
	 * putanje i sadržaja.
	 * 
	 * @param content sadržaj datoteke
	 * @param path putanja u datotečnom sustavu do tog dokumenta
	 * @return novi primjerak modela dokumenta
	 */
	private SingleDocumentModel createNewDocument(String content, Path path) {
		SingleDocumentModel newDocument = new DefaultSingleDocumentModel(path, content);
		newDocument.addSingleDocumentListener(new DefaultSingleDocumentListener());
		documents.add(newDocument);
		
		for (MultipleDocumentListener listener : listeners) {
			listener.currentDocumentChanged(currentDocument, newDocument);
			listener.documentAdded(newDocument);
		}
		currentDocument = newDocument;

		JPanel panel = new JPanel(false);
		panel.setLayout(new GridLayout(1, 1));
		panel.add(new JScrollPane(newDocument.getTextComponent()));
		
		String documentName = path != null ? path.getFileName().toString() : "(unnamed)";
		String tooltipText = path != null ? path.toString() : "(unnamed)";
		addTab(documentName, Utils.green, panel, tooltipText);
		setSelectedIndex(documents.size() - 1);

		return newDocument;
	}

	/**
	 * Razred modelira implementaciju {@link SingleDocumentListener}-a.
	 */
	private class DefaultSingleDocumentListener implements SingleDocumentListener {

		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel model) {
			setIconAt(documents.indexOf(model), model.isModified() ? Utils.red : Utils.green);
		}

		@Override
		public void documentFilePathUpdated(SingleDocumentModel model) {
			setTitleAt(documents.indexOf(model), model.getFilePath().toFile().getName());
			setToolTipTextAt(documents.indexOf(model), model.getFilePath().toString());
			for (MultipleDocumentListener listener : listeners) {
				listener.currentDocumentChanged(model, model);
			}
		}

	}

}
