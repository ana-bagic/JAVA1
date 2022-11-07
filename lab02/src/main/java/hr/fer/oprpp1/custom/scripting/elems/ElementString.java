package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Razred predstavlja String.
 * 
 * @author Ana Bagić
 *
 */
public class ElementString implements Element {

	/**
	 * Vrijednost stringa.
	 */
	private String value;
	
	/**
	 * Konstruktor postavlja vrijednost stringa.
	 * 
	 * @param value vrijednost na koju želimo postaviti String
	 */
	public ElementString(String value) {
		this.value = value;
	}
	
	/**
	 * Vraća tekstualni oblik Stringa.
	 * 
	 * @return tekstualni oblik stringa
	 */
	@Override
	public String asText() {
		return '"' + value + '"';
	}
	
	/**
	 * Vraća vrijednost stringa.
	 * 
	 * @return vrijednost stringa
	 */
	public String getValue() {
		return value;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ElementString))
			return false;
		ElementString other = (ElementString) obj;
		
		return this.asText().equals(other.asText());
	}

}
