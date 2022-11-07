package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Razred predstavlja operator.
 * 
 * @author Ana Bagić
 *
 */
public class ElementOperator implements Element {

	/**
	 * Simbol operatora.
	 */
	private String symbol;
	
	/**
	 * Konstruktor postavlja simbol operatora.
	 * 
	 * @param symbol simbol koji želimo dati operatoru
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}
	
	/**
	 * Vraća simbol operatora.
	 * 
	 * @return simbol operatora
	 */
	@Override
	public String asText() {
		return symbol;
	}
	
	/**
	 * Vraća simbol operatora.
	 * 
	 * @return simbol operatora
	 */
	public String getName() {
		return symbol;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ElementOperator))
			return false;
		ElementOperator other = (ElementOperator) obj;
		
		return this.asText().equals(other.asText());
	}

}
