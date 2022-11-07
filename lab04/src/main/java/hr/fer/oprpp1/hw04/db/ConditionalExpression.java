package hr.fer.oprpp1.hw04.db;

import java.util.Objects;

/**
 * Razred modelira kompletni uvjetni izraz.
 * 
 * @author Ana Bagić
 *
 */
public class ConditionalExpression {
	
	/**
	 * Getter željenog atributa.
	 */
	private IFieldValueGetter fieldGetter;
	/**
	 * Operand s kojim uspoređujemo željeni atribut.
	 */
	private String stringLiteral;
	/**
	 * Operator usporedbe kojega želimo primjeniti nad atributom.
	 */
	private IComparisonOperator comparisonOperator;

	/**
	 * Konstruktor stvara uvjetni izraz na temelju poslanih vrijednosti.
	 * 
	 * @param fieldGetter getter željenog atributa
	 * @param stringLiteral operand s kojim uspoređujemo željeni atribut
	 * @param comparisonOperator operator usporedbe kojega želimo primjeniti nad atributom
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral, IComparisonOperator comparisonOperator) {
		this.fieldGetter = Objects.requireNonNull(fieldGetter, "Getter ne smije biti null");
		this.stringLiteral = Objects.requireNonNull(stringLiteral, "String ne smije biti null");
		this.comparisonOperator = Objects.requireNonNull(comparisonOperator, "Komparator ne smije biti null");
	}

	/**
	 * @return getter željenog atributa
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * @return operand s kojim uspoređujemo željeni atribut
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * @return operator usporedbe kojega želimo primjeniti nad atributom
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
	
}
