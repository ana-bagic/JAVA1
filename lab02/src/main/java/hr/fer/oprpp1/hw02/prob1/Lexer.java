package hr.fer.oprpp1.hw02.prob1;

/**
 * Razred predstavlja leksički analizator.
 * 
 * @author Ana Bagić
 *
 */
public class Lexer {

	/**
	 * Ulazni tekst.
	 */
	private char[] data;
	/**
	 * Trenutni token.
	 */
	private Token token;
	/**
	 * Indeks prvog neobrađenog znaka.
	 */
	private int currentIndex;
	/**
	 * Način obrade leksera.
	 */
	private LexerState state;
	
	/**
	 * Konstruktor koji prima ulazni tekst koji se tokenizira.
	 * 
	 * @param text ulazni tekst koji se tokenizira
	 * @throws NullPointerException ako je ulazni tekst <code>null</code>
	 */
	public Lexer(String text) {
		data = text.toCharArray();
		currentIndex = 0;
		state = LexerState.BASIC;
	}
	
	/**
	 * Generira i vraća sljedeći token.
	 * 
	 * @return generirani token
	 * @throws LexerException ako dođe do pogreške pri stvaranju novog tokena
	 */
	public Token nextToken() {
		if(token != null && token.getType() == TokenType.EOF)
			throw new LexerException("Nema više tokena");
		
		token = extractNextToken();
		return token;
	}
	
	/**
	 * Vraća zadnje generirani token. Ne stvara novi token.
	 * 
	 * @return zadnje generirani token
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * Postavlja način obrade leksera na poslani.
	 * 
	 * @param state način obrade na koji želimo postaviti lekser
	 * @throws NullPointerException ako je poslano stanje (način obrade) <code>null</code>
	 */
	public void setState(LexerState state) {
		if(state == null)
			throw new NullPointerException("Argument je null");
		
		this.state = state;
	}

	
	/**
	 * @return novi stvoreni token
	 */
	private Token extractNextToken() {
		skipWhitespace();
		
		if(currentIndex == data.length)
			return new Token(TokenType.EOF, null);
		
		char nextChar = data[currentIndex];
		
		switch (state) {
		case BASIC: {
			if(Character.isDigit(nextChar))
				return new Token(TokenType.NUMBER, extractNumberBasic());
			
			if(Character.isLetter(nextChar) || nextChar == '\\')
				return new Token(TokenType.WORD, extractWordBasic());
			
			currentIndex++;
			return new Token(TokenType.SYMBOL, nextChar);
		}
		case EXTENDED: {
			if(nextChar == '#') {
				currentIndex++;
				return new Token(TokenType.SYMBOL, '#');
			}
			
			return new Token (TokenType.WORD, extractWordExtended());
			
		}
		default:
			throw new LexerException("Stanje ne može biti null");
		}
	}
	
	/**
	 * Preskače sve praznine.
	 */
	private void skipWhitespace() {
		while(data.length != currentIndex) {
			char nextChar = data[currentIndex];
			
			if(nextChar == '\r' || nextChar == '\n' || nextChar == '\t' || nextChar == ' ') {
				currentIndex++;
				continue;
			}
			
			break;
		}
	}
	
	/**
	 * @return novi Long broj izvučen iz ulaznog teksta
	 */
	private Long extractNumberBasic() {
		long number = 0l;
		
		while(data.length != currentIndex) {
			char nextChar = data[currentIndex];
			
			if(Character.isDigit(nextChar)) {
				number *= 10;
				number += Character.getNumericValue(nextChar);
				
				if(number < 0)
					throw new LexerException("Broj je preveliki");
				
				currentIndex++;
				continue;
			}
			
			break;
		}
		
		return Long.valueOf(number);
	}
	
	/**
	 * @return nova String riječ izvučena iz ulaznog teksta kada je lekser u BASIC stanju
	 */
	private String extractWordBasic() {
		StringBuilder sb = new StringBuilder();
		boolean escaped = false;
		
		while(data.length != currentIndex) {
			char nextChar = data[currentIndex];
			
			if(Character.isLetter(nextChar) || escaped) {
				if(escaped && !(Character.isDigit(nextChar) || nextChar == '\\'))
					throw new LexerException("Neispravno korištenje znaka \\");
				
				sb.append(nextChar);
				escaped = false;
				currentIndex++;
				continue;
			}
			
			if(nextChar == '\\') {
				if(currentIndex == data.length-1)
					throw new LexerException("Neispravno korištenje znaka \\");
				
				escaped = true;
				currentIndex++;
				continue;
			}
			
			break;
		}
		
		return sb.toString();
	}
	
	/**
	 * @return nova String riječ izvučena iz ulaznog teksta kada je lekser u EXTENDED stanju
	 */
	private String extractWordExtended() {
		StringBuilder sb = new StringBuilder();
		
		while(data.length != currentIndex) {
			char nextChar = data[currentIndex];
			
			if(!(nextChar == '\r' || nextChar == '\n' || nextChar == '\t' || nextChar == ' ' || nextChar == '#')) {
				sb.append(nextChar);
				currentIndex++;
				continue;
			}
			
			break;
		}
		
		return sb.toString();
	}

}
