package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Razred predstavlja varijablu.
 * 
 * @author Ana Bagić
 *
 */
public class ElementVariable implements Element {

	/**
	 * Ime varijable.
	 */
	private String name;
	
	/**
	 * Konstruktor postavlja ime varijable.
	 * 
	 * @param name ime koje želimo dati varijabli
	 */
	public ElementVariable(String name) {
		this.name = name;
	}
	
	/**
	 * Vraća ime varijable.
	 * 
	 * @return ime varijable
	 */
	@Override
	public String asText() {
		return name;
	}
	
	/**
	 * Vraća ime varijable.
	 * 
	 * @return ime varijable
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ElementVariable))
			return false;
		ElementVariable other = (ElementVariable) obj;
		
		return this.asText().equals(other.asText());
	}

}
