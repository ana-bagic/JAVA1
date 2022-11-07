package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Tip tokena.
 * 
 * @author Ana Bagić
 *
 */
public enum TokenType {
	
	/**
	 * End-of-file.
	 */
	EOF,
	
	/**
	 * Otvaranje tag-a.
	 */
	OPENTAG,
	
	/**
	 * Zatvaranje tag-a.
	 */
	CLOSETAG,
	
	/**
	 * String.
	 */
	STRING,
	
	/**
	 * Int broj.
	 */
	INTNUMBER,
	
	/**
	 * Double broj.
	 */
	DOUBLENUMBER,
	
	/**
	 * Simbol.
	 */
	SYMBOL,
	
	/**
	 * Varijabla.
	 */
	VARIABLE,
	
	/**
	 * Funkcija.
	 */
	FUNCTION
	
}
