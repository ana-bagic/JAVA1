package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Razred predstavlja funkciju.
 * 
 * @author Ana Bagić
 *
 */
public class ElementFunction implements Element {

	/**
	 * Ime funkcije.
	 */
	private String name;
	
	/**
	 * Konstruktor postavlja ime funkcije.
	 * 
	 * @param name ime koje želimo dati funkciji
	 */
	public ElementFunction(String name) {
		this.name = name;
	}
	
	/**
	 * Vraća ime funkcije.
	 * 
	 * @return ime funkcije
	 */
	@Override
	public String asText() {
		return name;
	}
	
	/**
	 * Vraća ime funkcije.
	 * 
	 * @return ime funkcije
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ElementFunction))
			return false;
		ElementFunction other = (ElementFunction) obj;
		
		return this.asText().equals(other.asText());
	}

}

