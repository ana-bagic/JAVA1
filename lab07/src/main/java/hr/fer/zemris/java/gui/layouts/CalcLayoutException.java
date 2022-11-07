package hr.fer.zemris.java.gui.layouts;

/**
 * Razred predstavlja neprovjeravanu iznimku koja se dogodi kod pogreške tijekom korištenja razreda CalcLayout.
 * 
 * @author Ana Bagić
 *
 */
public class CalcLayoutException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Defaultni konstruktor stvara iznimku s porukom
	 * "Greška kod dodavanja komponente."
	 */
	public CalcLayoutException() {
		super("Greška kod dodavanja komponente.");
	}

	/**
	 * Kontruktor stvara iznimku sa zadanom porukom.
	 * 
	 * @param message zadana poruka za iznimku
	 */
	public CalcLayoutException(String message) {
		super(message);
	}
	
}
