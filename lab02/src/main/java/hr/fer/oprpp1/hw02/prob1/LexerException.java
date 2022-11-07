package hr.fer.oprpp1.hw02.prob1;

/**
 * Razred predstavlja neprovjeravanu iznimku koja se dogodi kod pogreške u Lexer-u kod tokeniziranja.
 * 
 * @author Ana Bagić
 *
 */
public class LexerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Defaultni konstruktor s porukom "Greška kod tokeniziranja".
	 */
	public LexerException() {
		super("Greška kod tokeniziranja");
	}
	
	/**
	 * Konstruktor sa zadanom porukom.
	 * 
	 * @param message poruka koja će biti ispisana
	 */
	public LexerException(String message) {
		super(message);
	}

}
