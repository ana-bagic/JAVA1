package hr.fer.oprpp1.hw04.db;

/**
 * Sučelje predstavlja strategiju za operatore usporedbe.
 * 
 * @author Ana Bagić
 *
 */
public interface IComparisonOperator {

	/**
	 * Vraća je li usporedba istinita ili ne.
	 * 
	 * @param value1 prvi operand
	 * @param value2 drugi operand
	 * @return <code>true</code> ako je usporedba istinita, inače <code>false</code>
	 */
	boolean satisfied(String value1, String value2);
}
