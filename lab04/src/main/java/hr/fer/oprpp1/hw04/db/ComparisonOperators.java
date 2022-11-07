package hr.fer.oprpp1.hw04.db;

/**
 * Razred modelira konkretne operatore usporedbe.
 * 
 * @author Ana Bagić
 *
 */
public class ComparisonOperators {

	/**
	 * Operator usporedbe manji.
	 */
	public static final IComparisonOperator LESS = (a, b) -> a.compareTo(b) < 0; 
	/**
	 * Operator usporedbe manji ili jednak.
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (a, b) -> a.compareTo(b) <= 0;
	/**
	 * Operator usporedbe veći.
	 */
	public static final IComparisonOperator GREATER = (a, b) -> a.compareTo(b) > 0;
	/**
	 * Operator usporedbe veći ili jednak.
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (a, b) -> a.compareTo(b) >= 0;
	/**
	 * Operator usporedbe jednak.
	 */
	public static final IComparisonOperator EQUALS = (a, b) -> a.compareTo(b) == 0;
	/**
	 * Operator usporedbe nije jednak.
	 */
	public static final IComparisonOperator NOT_EQUALS = (a, b) -> a.compareTo(b) != 0;
	/**
	 * Operator usporedbe LIKE - može se koristiti * što mijenja 0 ili više proizvoljnih znakova.
	 */
	public static final IComparisonOperator LIKE = (a, b) -> {
		int wildcardIndex = b.indexOf('*');
		
		if(wildcardIndex != b.lastIndexOf('*'))
			throw new IllegalArgumentException("Ne može postojati više od jednog znaka * u izrazu");
		
		if(wildcardIndex == -1)
			return a.compareTo(b) == 0;
		if(wildcardIndex == 0)
			return a.endsWith(b.substring(1));
		if(wildcardIndex == b.length() - 1)
			return a.startsWith(b.substring(0, wildcardIndex));
			
		return a.startsWith(b.substring(0, wildcardIndex)) && a.substring(wildcardIndex).endsWith(b.substring(wildcardIndex + 1));
	};
}
