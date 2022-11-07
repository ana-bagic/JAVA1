package hr.fer.oprpp1.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.hw02.prob1.LexerException;

public class SmartScriptLexerTest {

	@Test
	public void testNotNull() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		assertNotNull(lexer.nextToken(), "Token was expected but null was returned.");
	}

	@Test
	public void testNullInput() {
		// must throw!
		assertThrows(NullPointerException.class, () -> new SmartScriptLexer(null));
	}

	@Test
	public void testEmpty() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		assertEquals(TokenType.EOF, lexer.nextToken().getType(), "Empty input must generate only EOF token.");
	}

	@Test
	public void testGetReturnsLastNext() {
		// Calling getToken once or several times after calling nextToken must return each time what nextToken returned...
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		Token token = lexer.nextToken();
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
	}

	@Test
	public void testRadAfterEOF() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void testNullState() {
		assertThrows(LexerException.class, () -> new SmartScriptLexer("").setState(null));
	}
	
	@Test
	public void testNotNullInExtended() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		lexer.setState(LexerState.TAG);
		
		assertNotNull(lexer.nextToken(), "Token was expected but null was returned.");
	}

	@Test
	public void testEmptyInExtended() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		lexer.setState(LexerState.TAG);
		
		assertEquals(TokenType.EOF, lexer.nextToken().getType(), "Empty input must generate only EOF token.");
	}

	@Test
	public void testGetReturnsLastNextInExtended() {
		// Calling getToken once or several times after calling nextToken must return each time what nextToken returned...
		SmartScriptLexer lexer = new SmartScriptLexer("");
		lexer.setState(LexerState.TAG);
		
		Token token = lexer.nextToken();
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
	}

	@Test
	public void testRadAfterEOFInExtended() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		lexer.setState(LexerState.TAG);

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	public void testExample1() {
		SmartScriptLexer lexer = new SmartScriptLexer(readExample(1));
		
		checkToken(lexer.nextToken(), new Token(TokenType.STRING, "Ovo je \nsve jedan text node\n"));
		checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));
	}
	
	@Test
	public void testExample2() {
		SmartScriptLexer lexer = new SmartScriptLexer(readExample(2));
		
		checkToken(lexer.nextToken(), new Token(TokenType.STRING, "Ovo je \nsve jedan {$ text node\n"));
		checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));
	}
	
	@Test
	public void testExample3() {
		SmartScriptLexer lexer = new SmartScriptLexer(readExample(3));
		
		checkToken(lexer.nextToken(), new Token(TokenType.STRING, "Ovo je \nsve jedan \\{$text node\n"));
		checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));
	}
	
	@Test
	public void testExample4() {
		SmartScriptLexer lexer = new SmartScriptLexer(readExample(4));
		
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	public void testExample5() {
		SmartScriptLexer lexer = new SmartScriptLexer(readExample(5));
		
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	public void testExample6() {
		SmartScriptLexer lexer = new SmartScriptLexer(readExample(6));
		
		checkToken(lexer.nextToken(), new Token(TokenType.STRING, "Ovo je OK "));
		checkToken(lexer.nextToken(), new Token(TokenType.OPENTAG, "{$"));
		
		lexer.setState(LexerState.TAG);
		
		checkToken(lexer.nextToken(), new Token(TokenType.SYMBOL, Character.valueOf('=')));
		checkToken(lexer.nextToken(), new Token(TokenType.STRING, "String ide\nu više redaka\nčak tri"));
		checkToken(lexer.nextToken(), new Token(TokenType.CLOSETAG, "$}"));
		
		lexer.setState(LexerState.TEXT);
		
		checkToken(lexer.nextToken(), new Token(TokenType.STRING, "\n"));
		checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));
	}
	
	@Test
	public void testExample7() {
		SmartScriptLexer lexer = new SmartScriptLexer(readExample(7));
		
		checkToken(lexer.nextToken(), new Token(TokenType.STRING, "Ovo je isto OK "));
		checkToken(lexer.nextToken(), new Token(TokenType.OPENTAG, "{$"));
		
		lexer.setState(LexerState.TAG);
		
		checkToken(lexer.nextToken(), new Token(TokenType.SYMBOL, Character.valueOf('=')));
		checkToken(lexer.nextToken(), new Token(TokenType.STRING, "String ide\nu \"više\" \nredaka\novdje a stvarno četiri"));
		checkToken(lexer.nextToken(), new Token(TokenType.CLOSETAG, "$}"));
		
		lexer.setState(LexerState.TEXT);
		
		checkToken(lexer.nextToken(), new Token(TokenType.STRING, "\n"));
		checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));
	}
	
	@Test
	public void testExample8() {
		SmartScriptLexer lexer = new SmartScriptLexer(readExample(8));
		
		checkToken(lexer.nextToken(), new Token(TokenType.STRING, "Ovo se ruši "));
		checkToken(lexer.nextToken(), new Token(TokenType.OPENTAG, "{$"));
		
		lexer.setState(LexerState.TAG);
		
		checkToken(lexer.nextToken(), new Token(TokenType.SYMBOL, Character.valueOf('=')));
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	public void testExample9() {
		SmartScriptLexer lexer = new SmartScriptLexer(readExample(9));
		
		checkToken(lexer.nextToken(), new Token(TokenType.STRING, "Ovo se ruši "));
		checkToken(lexer.nextToken(), new Token(TokenType.OPENTAG, "{$"));
		
		lexer.setState(LexerState.TAG);
		
		checkToken(lexer.nextToken(), new Token(TokenType.SYMBOL, Character.valueOf('=')));
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}
	
	private String readExample(int n) {
		try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer"+n+".txt")) {
			if(is == null)
				throw new RuntimeException("Datoteka extra/primjer"+n+".txt je nedostupna.");
			
			byte[] data = is.readAllBytes();
			String text = new String(data, StandardCharsets.UTF_8);
		    return text;
		} catch(IOException ex) {
			throw new RuntimeException("Greška pri čitanju datoteke.", ex);
		}
	}
	
	private void checkToken(Token actual, Token expected) {
			String msg = "Token are not equal.";
			assertEquals(expected.getType(), actual.getType(), msg);
			assertEquals(expected.getValue(), actual.getValue(), msg);
	}

}
