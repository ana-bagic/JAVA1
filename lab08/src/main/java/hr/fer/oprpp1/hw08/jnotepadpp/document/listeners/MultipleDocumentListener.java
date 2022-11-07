package hr.fer.oprpp1.hw08.jnotepadpp.document.listeners;

import hr.fer.oprpp1.hw08.jnotepadpp.document.models.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.document.models.SingleDocumentModel;

/**
 * Razred modelira listenera za {@link MultipleDocumentModel}.
 * 
 * @author Ana Bagić
 *
 */
public interface MultipleDocumentListener {

	/**
	 * Zove se kada je trenutan dokument ažuriran.
	 * 
	 * @param previousModel model dokumenta prije promjene
	 * @param currentModel model dokumenta nakon promjene
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);
	
	/**
	 * Zove se kada je dokument dodan.
	 * 
	 * @param model model dokumenta koji je dodan
	 */
	void documentAdded(SingleDocumentModel model);
	
	/**
	 * Zove se kada je dokument obrisan.
	 * 
	 * @param model mmodel model dokumenta koji je obrisan
	 */
	void documentRemoved(SingleDocumentModel model);

}
