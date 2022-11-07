package hr.fer.oprpp1.hw04.db;

/**
 * Sučelje predstavlja strategiju za dohvat traženog atributa iz poslanog StudentRecord-a.
 * 
 * @author Ana Bagić
 *
 */
public interface IFieldValueGetter {

	/**
	 * Dohvaća traženo polje iz poslanog StudentRecord-a.
	 * 
	 * @param record zapis o studentu iz kojega želimo dohvatiti traženo polje
	 * @return traženo polje iz poslanog zapisa o studentu
	 */
	String get(StudentRecord record);

}
