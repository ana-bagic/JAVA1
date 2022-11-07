package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Razred predstavlja konstantni Double.
 * 
 * @author Ana Bagić
 *
 */
public class ElementConstantDouble implements Element {

	/**
	 * Vrijednost doublea.
	 */
	private double value;

	/**
	 * Konstruktor postavlja vrijednost doublea.
	 * 
	 * @param value vrijednost na koju želimo postaviti Double
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	/**
	 * Vraća vrijednost doublea u obliku stringa.
	 * 
	 * @return vrijednost doublea u obliku stringa
	 */
	@Override
	public String asText() {
		return Double.toString(value);
	}
	
	/**
	 * Vrijednost doublea.
	 * 
	 * @return vrijednost doublea
	 */
	public double getValue() {
		return value;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ElementConstantDouble))
			return false;
		ElementConstantDouble other = (ElementConstantDouble) obj;
		
		return this.asText().equals(other.asText());
	}

}
