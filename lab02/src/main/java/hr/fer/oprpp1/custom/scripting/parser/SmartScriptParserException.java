package hr.fer.oprpp1.custom.scripting.parser;

/**
 * Razred predstavlja neprovjeravanu iznimku koja se dogodi kod pogreške u Parseru-u kod parsiranja.
 * 
 * @author Ana Bagić
 *
 */
public class SmartScriptParserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Defaultni konstruktor s porukom "Greška kod parsiranja".
	 */
	public SmartScriptParserException() {
		super("Greška kod parsiranja");
	}
	
	/**
	 * Konstruktor sa zadanom porukom.
	 * 
	 * @param message poruka koja će biti ispisana
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}

}