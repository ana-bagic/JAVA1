package hr.fer.oprpp1.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;



import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;
import hr.fer.oprpp1.custom.scripting.nodes.ForLoopNode;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;

/**
 * Testiranje parsera.
 * 
 * @author Ana Bagić
 *
 */
public class SmartScriptParserTest {
	
	@Test
	public void example1() {
		SmartScriptParser parser = new SmartScriptParser(readExample(1));
		
		//System.out.println(parser.getDocumentNode().toString());
		//System.out.println(parser.getDocumentNode().toStringFancy());
		
		assertEquals(1, parser.getDocumentNode().numberOfChildren());
		assertEquals(TextNode.class, parser.getDocumentNode().getChild(0).getClass());
	}
	
	@Test
	public void example2() {
		SmartScriptParser parser = new SmartScriptParser(readExample(2));
		
		//System.out.println(parser.getDocumentNode().toString());
		//System.out.println(parser.getDocumentNode().toStringFancy());
		
		assertEquals(1, parser.getDocumentNode().numberOfChildren());
		assertEquals(TextNode.class, parser.getDocumentNode().getChild(0).getClass());
	}
	
	@Test
	public void example3() {
		SmartScriptParser parser = new SmartScriptParser(readExample(3));
		
		//System.out.println(parser.getDocumentNode().toString());
		//System.out.println(parser.getDocumentNode().toStringFancy());
		
		assertEquals(1, parser.getDocumentNode().numberOfChildren());
		assertEquals(TextNode.class, parser.getDocumentNode().getChild(0).getClass());
	}
	
	@Test
	public void example4() {
		assertThrows(SmartScriptParserException.class,
				() -> {
					@SuppressWarnings("unused")
					SmartScriptParser parser = new SmartScriptParser(readExample(4));
				});
	}
	
	@Test
	public void example5() {
		assertThrows(SmartScriptParserException.class,
				() -> {
					@SuppressWarnings("unused")
					SmartScriptParser parser = new SmartScriptParser(readExample(5));
				});
	}
	
	@Test
	public void example6() {
		SmartScriptParser parser = new SmartScriptParser(readExample(6));
		
		//System.out.println(parser.getDocumentNode().toString());
		//System.out.println(parser.getDocumentNode().toStringFancy());
		
		assertEquals(3, parser.getDocumentNode().numberOfChildren());
		assertEquals(TextNode.class, parser.getDocumentNode().getChild(0).getClass());
		assertEquals(EchoNode.class, parser.getDocumentNode().getChild(1).getClass());
		assertEquals(TextNode.class, parser.getDocumentNode().getChild(2).getClass());
	}
	
	@Test
	public void example7() {
		SmartScriptParser parser = new SmartScriptParser(readExample(7));
		
		//System.out.println(parser.getDocumentNode().toString());
		//System.out.println(parser.getDocumentNode().toStringFancy());
		
		assertEquals(3, parser.getDocumentNode().numberOfChildren());
		assertEquals(TextNode.class, parser.getDocumentNode().getChild(0).getClass());
		assertEquals(EchoNode.class, parser.getDocumentNode().getChild(1).getClass());
		assertEquals(TextNode.class, parser.getDocumentNode().getChild(2).getClass());
	}
	
	@Test
	public void example8() {
		assertThrows(SmartScriptParserException.class,
				() -> {
					@SuppressWarnings("unused")
					SmartScriptParser parser = new SmartScriptParser(readExample(8));
				});
	}
	
	@Test
	public void example9() {
		assertThrows(SmartScriptParserException.class,
				() -> {
					@SuppressWarnings("unused")
					SmartScriptParser parser = new SmartScriptParser(readExample(9));
				});
	}
	
	@Test
	public void example10() {
		SmartScriptParser parser = new SmartScriptParser(readExample(10));
		
		//System.out.println(parser.getDocumentNode().toString());
		//System.out.println(parser.getDocumentNode().toStringFancy());
		
		assertEquals(5, parser.getDocumentNode().numberOfChildren());
		assertEquals(TextNode.class, parser.getDocumentNode().getChild(0).getClass());
		assertEquals(ForLoopNode.class, parser.getDocumentNode().getChild(1).getClass());
		assertEquals(TextNode.class, parser.getDocumentNode().getChild(2).getClass());
		assertEquals(ForLoopNode.class, parser.getDocumentNode().getChild(3).getClass());
		assertEquals(TextNode.class, parser.getDocumentNode().getChild(4).getClass());
		
		assertEquals(3, parser.getDocumentNode().getChild(1).numberOfChildren());
		assertEquals(5, parser.getDocumentNode().getChild(3).numberOfChildren());
	}
	
	@Test
	public void toStringEquals() {
		SmartScriptParser parser = new SmartScriptParser(readExample(10));
		DocumentNode document = parser.getDocumentNode();
		
		SmartScriptParser parser2 = new SmartScriptParser(document.toString());
		DocumentNode document2 = parser2.getDocumentNode();
		
		assertEquals(document, document2);
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
}
