package hr.fer.oprpp1.hw04.db;

/**
 * Sučelje modelira filter koji kroz metodu accepts filtrira zapise o studentima.
 * 
 * @author Ana Bagić
 *
 */
public interface IFilter {

	/**
	 * Provjerava zadovoljava li poslani zapis filter definiran u metodi.
	 * 
	 * @param record zapis o studentu koji se provjerava
	 * @return <code>true</code> ako poslani zapis zadovoljava filter, inače <code>false</code>
	 */
	public boolean accepts(StudentRecord record);

}
