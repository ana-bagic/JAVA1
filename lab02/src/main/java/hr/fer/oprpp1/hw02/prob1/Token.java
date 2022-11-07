package hr.fer.oprpp1.hw02.prob1;

/**
 * Razred predstavlja token stvoren leksičkim analizatorom.
 * 
 * @author Ana Bagić
 *
 */
public class Token {
	
	/**
	 * Tip tokena.
	 */
	private TokenType type;
	/**
	 * Vrijednost tokena.
	 */
	private Object value;

	/**
	 * Konstruktor stvara novi token na temelju tipa i vrijednosti.
	 * 
	 * @param type tip tokena
	 * @param value vrijednost tokena
	 * @throws NullPointerException ako je poslani tip tokena <code>null</code>
	 */
	public Token(TokenType type, Object value) {
		if(type == null)
			throw new NullPointerException("Tip tokena ne može biti null");
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Vraća tip tokena.
	 * 
	 * @return tip tokena
	 */
	public TokenType getType() {
		return type;
	}

	/**
	 * Vraća vrijednost tokena.
	 * 
	 * @return vrijednost tokena
	 */
	public Object getValue() {
		return value;
	}
	
}

