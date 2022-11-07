package hr.fer.oprpp1.hw08.jnotepadpp.document.models;

import java.nio.file.Path;

import javax.swing.JTextArea;

import hr.fer.oprpp1.hw08.jnotepadpp.document.listeners.SingleDocumentListener;

/**
 * Sučelje modelira objekt koji predstavlja jedan dokument.
 * 
 * @author Ana Bagić
 *
 */
public interface SingleDocumentModel {

	/**
	 * Vraća {@link JTextArea} komponentu s tekstualnim sadržajem.
	 * 
	 * @return {@link JTextArea} komponentu ovog modela
	 */
	JTextArea getTextComponent();
	
	/**
	 * Vraća putanju do ovog dokumenta u datotečnom sustavu.
	 * 
	 * @return putanja do ovog dokumenta
	 */
	Path getFilePath();
	
	/**
	 * Postavlja putanju i obavještava sve listenere ako se ona promijenila.
	 * 
	 * @param path nova putanja do dokumenta
	 */
	void setFilePath(Path path);
	
	/**
	 * Provjerava je li dokument modificiran.
	 * 
	 * @return <code>true</code> ako je dokument modificiran, inače <code>false</code>
	 */
	boolean isModified();
	
	/**
	 * Postavlja vrijednost zastavice koja označava modifikaciju dokumenta, te obavještava sve listenere ako je ona <code>true</code>.
	 * 
	 * @param modified vrijednost zastavice
	 */
	void setModified(boolean modified);
	
	/**
	 * Dodaje listenera u listu svih listenera registriranih na ovaj dokument.
	 * 
	 * @param l listener koji se dodaje
	 */
	void addSingleDocumentListener(SingleDocumentListener l);
	
	/**
	 * Briše listenera iz liste svih listenera registriranih na ovaj dokument.
	 * 
	 * @param l listener koji se briše
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);

}
