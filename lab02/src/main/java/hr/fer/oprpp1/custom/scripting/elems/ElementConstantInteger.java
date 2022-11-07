package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Razred predstavlja konstantni Integer.
 * 
 * @author Ana Bagić
 *
 */
public class ElementConstantInteger implements Element {

	/**
	 * Vrijednost integera.
	 */
	private int value;
	
	/**
	 * Konstruktor postavlja vrijednost integera.
	 * 
	 * @param value vrijednost na koju želimo postaviti Integer
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	/**
	 * Vraća vrijednost integera u obliku stringa.
	 * 
	 * @return vrijednost integera u obliku stringa
	 */
	@Override
	public String asText() {
		return Integer.toString(value);
	}
	
	/**
	 * Vraća vrijednost integera.
	 * 
	 * @return vrijednost integera
	 */
	public int getValue() {
		return value;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ElementConstantInteger))
			return false;
		ElementConstantInteger other = (ElementConstantInteger) obj;
		
		return this.asText().equals(other.asText());
	}

}
