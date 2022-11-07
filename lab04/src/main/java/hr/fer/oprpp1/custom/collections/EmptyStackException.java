package hr.fer.oprpp1.custom.collections;

/**
 * Razred predstavlja neprovjeravanu iznimku koja se baca kad se dohvaća element s praznog stoga.
 * 
 * @author Ana Bagić
 *
 */
public class EmptyStackException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Defaultni konstruktor s porukom "Stog je prazan".
	 */
	public EmptyStackException() {
		super("Stog je prazan");
	}
	
	/**
	 * Konstruktor sa zadanom porukom.
	 * 
	 * @param message poruka koja će biti ispisana
	 */
	public EmptyStackException(String message) {
		super(message);
	}
	
}
