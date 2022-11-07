package hr.fer.oprpp1.hw04.db;

/**
 * Razred modelira dohvat konkretnog atributa iz poslanog StudentRecord-a.
 * 
 * @author Ana Bagić
 *
 */
public class FieldValueGetters {

	/**
	 * Vraća ime.
	 */
	public static final IFieldValueGetter FIRST_NAME = r -> r.getFirstName();
	/**
	 * Vraća prezime.
	 */
	public static final IFieldValueGetter LAST_NAME = r -> r.getLastName();
	/**
	 * Vraća jmbag.
	 */
	public static final IFieldValueGetter JMBAG = r -> r.getJmbag();
}
