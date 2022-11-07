package hr.fer.oprpp1.hw08.jnotepadpp.document.listeners;

import hr.fer.oprpp1.hw08.jnotepadpp.document.models.SingleDocumentModel;

/**
 * Razred modelira listenera za {@link SingleDocumentModel}.
 * 
 * @author Ana Bagić
 *
 */
public interface SingleDocumentListener {

	/**
	 * Zove se kada je dokument modificiran.
	 * 
	 * @param model model dokumenta koji je modificiran
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);
	
	/**
	 * Zove se kada je putanja do dokumenta ažurirana.
	 * 
	 * @param model model dokumenta čija je putanja ažurirana.
	 */
	void documentFilePathUpdated(SingleDocumentModel model);

}
