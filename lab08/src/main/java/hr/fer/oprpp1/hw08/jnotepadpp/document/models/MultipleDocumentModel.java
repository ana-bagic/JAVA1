package hr.fer.oprpp1.hw08.jnotepadpp.document.models;

import java.nio.file.Path;

import hr.fer.oprpp1.hw08.jnotepadpp.document.listeners.MultipleDocumentListener;

/**
 * Sučelje modelira objekt koji sprema više primjeraka razreda koji implementiraju {@link SingleDocumentModel} i radi s njima.
 * 
 * @author Ana Bagić
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {

	/**
	 * Stvara novi dokument.
	 * 
	 * @return novi stvoreni model dokumenta
	 */
	SingleDocumentModel createNewDocument();
	
	/**
	 * Dohvaća trenutni dokument koj se prikazuje korisniku.
	 * 
	 * @return model trenutnog dokumenta
	 */
	SingleDocumentModel getCurrentDocument();
	
	/**
	 * Učitava dokument koji se nalazi na danoj putanji u datotečnom sustavu.
	 * 
	 * @param path putanja do dokumenta
	 * @return model dokumenta učitan s dane putanje
	 */
	SingleDocumentModel loadDocument(Path path);
	
	/**
	 * Sprema dokument na mjesto dano putanjom ili na dosadašnje mjesto ako je putanja <code>null</code>.
	 * 
	 * @param model model dokumenta koji se želi spremiti
	 * @param newPath putanja u datotečnom sustavu na koje je mjesto želi spremiti dokument
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);
	
	/**
	 * Uklanja dani dokument iz modela i o tome obavještava sve registrirane listenere.
	 * 
	 * @param model model dokumenta koji se želi ukloniti
	 */
	void closeDocument(SingleDocumentModel model);
	
	/**
	 * Dodaje dani listener u listu svih listenera registriranih na ovaj model.
	 * 
	 * @param l listener koji se želi dodati
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Uklanja dani listener iz liste svih listenera registriranih na ovaj model.
	 * 
	 * @param l listener koji se želi ukloniti
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Vraća broj dokumenata pohranjenih u ovom modelu.
	 * 
	 * @return broj dokumenata pohranjenih u ovom modelu
	 */
	int getNumberOfDocuments();
	
	/**
	 * Dohvaća dokument koji se nalazi na danom indexu.
	 * 
	 * @param index index s kojega se dokument želi dohvatiti
	 * @return dokument koji se nalazi na danom indexu
	 * @throws IndexOutOfBoundsException ako je index izvan dometa(index < 0 || index >= broj dokumenata)
	 */
	SingleDocumentModel getDocument(int index);

}
