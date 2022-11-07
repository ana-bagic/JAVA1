package hr.fer.zemris.java.hw05.shell;

/**
 * Razred predstavlja neprovjeravanu iznimku koja se dogodi kod pogreške tijekom korištenja ljuske.
 * 
 * @author Ana Bagić
 *
 */
public class ShellIOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Defaultni konstruktor s porukom "Error in shell".
	 */
	public ShellIOException() {
		super("Error in shell");
	}
	
	/**
	 * Konstruktor sa zadanom porukom.
	 * 
	 * @param message poruka koja će biti ispisana
	 */
	public ShellIOException(String message) {
		super(message);
	}
	
}
