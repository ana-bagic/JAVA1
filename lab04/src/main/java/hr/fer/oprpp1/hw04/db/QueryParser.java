package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Razred modelira parser upita nad bazom podataka.
 * 
 * @author Ana Bagić
 *
 */
public class QueryParser {

	/**
	 * Lista uvjetnih izraza.
	 */
	private List<ConditionalExpression> expressions;
	/**
	 * Jmbag koji je korišten direktnom upitu ili <code>null</code> ako upit nije bio direktan.
	 */
	private String queriedJmbag;
	/**
	 * Polje charactera korišteno za parsiranje upita.
	 */
	private char[] data;
	/**
	 * Index polja, korištenog za parsiranje, na kojemu je parser trenutno.
	 */
	private int index;
	
	/**
	 * Konstruktor prima upit i parsira ga.
	 * 
	 * @param query upit kojeg treba parsirati
	 */
	public QueryParser(String query) {
		expressions = new ArrayList<>();
		
		parse(Objects.requireNonNull(query, "Upit ne može biti null"));
	}
	
	/**
	 * Provjerava je li upit bio direktan.
	 * 
	 * @return <code>true</code> ako je upit bio direktan, inače <code>false</code>
	 */
	public boolean isDirectQuery() {
		return queriedJmbag != null;
	}
	
	/**
	 * Vraća jmbag korišten u upitu ako je bio direktan, inače baca {@link IllegalStateException} iznimku
	 * 
	 * @return jmbag korišten u direktnom upitu
	 * @throws IllegalStateException ako upit nije bio direktan
	 */
	public String getQueriedJMBAG() {
		if(queriedJmbag == null)
			throw new IllegalStateException("Upit nije bio direktan upit");
		
		return queriedJmbag;
	}
	
	/**
	 * Vraća listu svih uvjetnih izraza iz upita.
	 * 
	 * @return lista svih uvjetnih izraza
	 */
	public List<ConditionalExpression> getQuery() {
		return expressions;
	}

	/**
	 * Parsira poslani string upit i izraz sprema u listu.
	 * 
	 * @param query upit koji treba parsirati
	 */
	private void parse(String query) {
		data = query.toCharArray();
		index = 0;
		
		while(true) {
			skipWhitespace();
			IFieldValueGetter getter = parseGetter();
			skipWhitespace();
			IComparisonOperator operator = parseOperator();
			skipWhitespace();
			String literal = parseLiteral();
			
			expressions.add(new ConditionalExpression(getter, literal, operator));
			
			skipWhitespace();
			if(!parseWord().toUpperCase().equals("AND"))
				break;
		}
		
		if(expressions.size() == 1
				&& expressions.get(0).getFieldGetter() == FieldValueGetters.JMBAG
				&& expressions.get(0).getComparisonOperator() == ComparisonOperators.EQUALS)
			queriedJmbag = expressions.get(0).getStringLiteral();
	}
	
	/**
	 * @return iz predanog stringa parsira i vraća jednu riječ koja osim slova i brojeva može sadržavati znak *
	 */
	private String parseWord() {
		StringBuilder sb = new StringBuilder();
		char nextCh;
		
		while(data.length != index) {
			nextCh = data[index];
			
			if(Character.isLetter(nextCh) || Character.isDigit(nextCh) || nextCh == '*') {
				sb.append(nextCh);
				index++;
				continue;
			}
			
			break;
		}
		
		return sb.toString();
	}

	/**
	 * @return znakovni niz parsiran iz upita
	 */
	private String parseLiteral() {
		if(data.length > index + 2 && data[index++] == '"') {
			String literal = parseWord();
			
			if(data.length != index && data[index++] == '"' && !literal.equals(""))
				return literal;
		}
		
		throw new IllegalArgumentException("Znakovni niz nije dobro zadan");
	}

	/**
	 * @return operator usporedbe parsiran iz upita
	 */
	private IComparisonOperator parseOperator() {
		if(data.length != index) {
			char nextCh = data[index++];
			
			switch (nextCh) {
			case '<' : {
				if(data.length != index && data[index] == '=') {
					index++;
					return ComparisonOperators.LESS_OR_EQUALS;
				}
				
				return ComparisonOperators.LESS;
			}
			case '>' : {
				if(data.length != index && data[index] == '=') {
					index++;
					return ComparisonOperators.GREATER_OR_EQUALS;
				}
				
				return ComparisonOperators.GREATER;
			}
			case '=' : return ComparisonOperators.EQUALS;
			case '!' : {
				if(data.length != index && data[index] == '=') {
					index++;
					return ComparisonOperators.NOT_EQUALS;
				}
				
				throw new IllegalArgumentException("Znak ! je krivo upotrebljen");
			}
			case 'L' : {
				String parsedWord = parseWord();
				if(parsedWord.equals("IKE"))
					return ComparisonOperators.LIKE;
				
				throw new IllegalArgumentException("Operator L" + parsedWord + " nije podržan");
			}
			default : throw new IllegalArgumentException("Operator nije podržan: " + nextCh);
			}
		}
		
		throw new IllegalArgumentException("Upit nije ispravan i potpun");
	}

	/**
	 * @return getter parsiran iz upita
	 */
	private IFieldValueGetter parseGetter() {
		String word = String.valueOf(data).substring(index);
		
		if(word.startsWith("jmbag")) {
			index += 5;
			return FieldValueGetters.JMBAG;
		}
		
		if(word.startsWith("firstName")) {
			index += 9;
			return FieldValueGetters.FIRST_NAME;
		}
		
		if(word.startsWith("lastName")) {
			index += 8;
			return FieldValueGetters.LAST_NAME;
		}
		
		throw new IllegalArgumentException("Getter nije podržan: " + word);
	}
	
	/**
	 * Preskače sve praznine.
	 */
	private void skipWhitespace() {
		while(data.length != index) {
			if(data[index] == '\t' || data[index] == ' ')
				index++;
			else break;
		}
	}

}
