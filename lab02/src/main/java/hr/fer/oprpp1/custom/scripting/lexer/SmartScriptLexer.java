package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.hw02.prob1.LexerException;

/**
 * Razred predstavlja SmartScript leksički analizator.
 * 
 * @author Ana Bagić
 *
 */
public class SmartScriptLexer {

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
	public SmartScriptLexer(String text) {
		data = text.toCharArray();
		currentIndex = 0;
		state = LexerState.TEXT;
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
	 * @throws LexerException ako je poslano stanje (način obrade) <code>null</code>
	 */
	public void setState(LexerState state) {
		if(state == null)
			throw new LexerException("Argument je null");
		
		this.state = state;
	}

	/**
	 * @return novi stvoreni token
	 * @throws LexerException ako dođe do greške kod parsiranja
	 */
	private Token extractNextToken() {
		if(currentIndex == data.length)
			return new Token(TokenType.EOF, null);
		
		switch (state) {
		case TEXT: {
			char nextChar = data[currentIndex];
			
			if(nextChar == '{' && data[currentIndex+1] == '$') {
				currentIndex += 2;
				return new Token(TokenType.OPENTAG, "{$");
			}
				
			return new Token(TokenType.STRING, extractStringText());
		}
		case TAG: {
			skipWhitespace();
			
			char nextChar = data[currentIndex];
			
			if(nextChar == '$' && data[currentIndex+1] == '}') {
				currentIndex += 2;
				return new Token(TokenType.CLOSETAG, "$}");
			}
			
			if(nextChar == '"') {
				currentIndex++;
				return new Token(TokenType.STRING, extractStringTag());
			}
			
			if(Character.isDigit(nextChar) || nextChar == '=' && Character.isDigit(data[currentIndex+1])) {
				String number = extractNumber();
				
				try {
					int value = Integer.parseInt(number);
					return new Token(TokenType.INTNUMBER, value);
				} catch (Exception e) {
					try {
						double value = Double.parseDouble(number);
						return new Token(TokenType.DOUBLENUMBER, value);
					} catch (Exception e2) {
						throw new LexerException("Greška kod leksiranja broja");
					}
				}
			}
			
			if(Character.isLetter(nextChar))
				return new Token(TokenType.VARIABLE, extractIdentifier());
			
			if(nextChar == '@' && Character.isLetter(data[currentIndex+1])) {
				currentIndex++;
				String value = "@" + extractIdentifier();
				return new Token(TokenType.FUNCTION, value);
			}
			
			if(nextChar == '+' || nextChar == '-' || nextChar == '*' ||
					nextChar == '/' || nextChar == '^' || nextChar == '=') {
				currentIndex++;
				return new Token(TokenType.SYMBOL, nextChar);
			}
			
			throw new LexerException("Znak " + nextChar + " nije ispravan");
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
	 * @return novi String tekst izvučen iz ulaznog teksta kada je lekser u TEXT stanju
	 * @throws LexerException ako dođe do greške kod parsiranja
	 */
	private String extractStringText() {
		StringBuilder sb = new StringBuilder();
		
		while(data.length != currentIndex) {
			char nextChar = data[currentIndex];
			
			if(nextChar == '{' && data[currentIndex+1] == '$')
				break;
			
			if(nextChar == '\\') {
				if(data[currentIndex+1] != '{' && data[currentIndex+1] != '\\')
					throw new LexerException("Greška u tekstu: " + data[currentIndex+1] + " ne smije biti nakon \\");
				
				sb.append(data[currentIndex+1]);
				currentIndex += 2;
				continue;
			}
			
			sb.append(nextChar);
			currentIndex++;
		}
		
		return sb.toString();
	}
	
	/**
	 * @return novi String tekst izvučen iz ulaznog teksta kada je lekser u TAG stanju
	 * @throws LexerException ako se neispravno koristi znak \
	 */
	private String extractStringTag() {
		StringBuilder sb = new StringBuilder();
		
		while(data.length != currentIndex) {
			char nextChar = data[currentIndex];
			
			if(nextChar == '"') {
				currentIndex++;
				break;
			}
			
			if(nextChar == '\\') {
				char toAppend;
				
				switch (data[currentIndex+1]) {
					case '\\' -> toAppend = '\\';
					case '"' -> toAppend = '"';
					case 'n' -> toAppend = '\n';
					case 'r' -> toAppend = '\r';
					case 't' -> toAppend = '\t';
					default ->
						throw new LexerException("Neispravno korištenje znaka \\");
				}
				
				sb.append(toAppend);
				currentIndex += 2;
				continue;
			}
			
			sb.append(nextChar);
			currentIndex++;
		}
		
		return sb.toString();
	}
	
	/**
	 * @return novi String koji se može parsirati u broj
	 */
	private String extractNumber() {
		StringBuilder sb = new StringBuilder();
		boolean decimal = false;
		
		if(data[currentIndex] == '-') {
			sb.append('-');
			currentIndex++;
		}
		
		while(data.length != currentIndex) {
			char nextChar = data[currentIndex];
			
			if(nextChar == '.' && Character.isDigit(data[currentIndex+1]) && !decimal) {
				sb.append('.');
				currentIndex++;
				decimal = true;
				continue;
			}
				
			if(Character.isDigit(nextChar)) {
				sb.append(nextChar);
				currentIndex++;
				continue;
			}
			
			break;
		}
		
		return sb.toString();
	}

	/**
	 * @return novi String koji predstavlja identifikator
	 */
	private String extractIdentifier() {
		StringBuilder sb = new StringBuilder();
		
		while(data.length != currentIndex) {
			char nextChar = data[currentIndex];
			
			if(Character.isLetter(nextChar) || Character.isDigit(nextChar) || nextChar == '_') {
				sb.append(nextChar);
				currentIndex++;
				continue;
			}
			
			break;
		}
		
		return sb.toString();
	}

}
