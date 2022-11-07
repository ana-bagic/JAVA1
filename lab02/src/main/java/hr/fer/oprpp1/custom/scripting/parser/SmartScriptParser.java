package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.ElementsGetter;
import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantDouble;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantInteger;
import hr.fer.oprpp1.custom.scripting.elems.ElementFunction;
import hr.fer.oprpp1.custom.scripting.elems.ElementOperator;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.oprpp1.custom.scripting.lexer.Token;
import hr.fer.oprpp1.custom.scripting.lexer.TokenType;
import hr.fer.oprpp1.custom.scripting.lexer.LexerState;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;
import hr.fer.oprpp1.custom.scripting.nodes.ForLoopNode;
import hr.fer.oprpp1.custom.scripting.nodes.Node;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;

/**
 * Razred predstavlja parser.
 * 
 * @author Ana Bagić
 *
 */
public class SmartScriptParser {

	/**
	 * Lekser kojeg parser koristi.
	 */
	private SmartScriptLexer lexer;
	/**
	 * Pomoćni stog.
	 */
	private ObjectStack stack;
	/**
	 * Vršni čvor dokumenta.
	 */
	private DocumentNode doc;
	
	/**
	 * Konstruktor parsira zadani tekst.
	 * 
	 * @param body tekst koji želimo parsirati
	 * @throws SmartScriptParserException ako se dogodi bilo kakva greška u parsiranju
	 */
	public SmartScriptParser(String body) {
		lexer = new SmartScriptLexer(body);
		stack = new ObjectStack();
		doc = new DocumentNode();
		
		try {
			parse();
		} catch(Exception e) {
			throw new SmartScriptParserException(e.getMessage());
		}
	}

	/**
	 * Vraća vršni čvor dokumenta.
	 * 
	 * @return vršni čvor dokumenta
	 */
	public DocumentNode getDocumentNode() {
		return doc;
	}
	
	/**
	 * Parsira koristeći lekser.
	 * 
	 * @throws SmartScriptParserException ako dođe do bilo kakve greške kod parsiranja
	 */
	private void parse() {
		stack.push(doc);
		Token nextToken = lexer.nextToken();
		
		while(nextToken.getType() != TokenType.EOF) {
			
			switch (nextToken.getType()) {
				case OPENTAG -> {
					lexer.setState(LexerState.TAG);
					solveInsideTag();
				}
				case STRING -> {
					TextNode textNode = new TextNode(nextToken.getValue().toString());
					((Node) stack.peek()).addChildNode(textNode);
				}
				
				default ->
					throw new SmartScriptParserException("Ova vrsta tokena " + nextToken.getType() + " nije važeća na ovom mjestu");
			}
			
			nextToken = lexer.nextToken();
		}
		
		if(stack.size() != 1 || !(stack.pop() instanceof DocumentNode))
			throw new SmartScriptParserException("Na stogu nije ostao točno jedan element");
	}

	/**
	 * Parsira unutar tag-a.
	 * 
	 * @throws SmartScriptParserException ako dođe do bilo kakve greške kod parsiranja
	 */
	private void solveInsideTag() {
		Token nextToken = lexer.nextToken();
		
		if(nextToken.getType() == TokenType.SYMBOL && (Character) nextToken.getValue() == '=') {
			solveInsideEqualsTag();
			return;
		}
		
		if(nextToken.getType() == TokenType.VARIABLE) {
			switch (nextToken.getValue().toString().toUpperCase()) {
				case "FOR" -> {
					ForLoopNode forNode = solveInsideForTag();
					((Node) stack.peek()).addChildNode(forNode);
					stack.push(forNode);
					return;
				}
				case "END" -> {
					if(lexer.nextToken().getType() == TokenType.CLOSETAG) {
						lexer.setState(LexerState.TEXT);
						stack.pop();
						return;
					}
				}
			}
		}
		
		throw new SmartScriptParserException("Krivo ime taga: " + nextToken.getValue());
	}

	/**
	 * Parsira unutar FOR tag-a.
	 * 
	 * @return novi isparsiran ForLoopNode
	 * @throws SmartScriptParserException ako dođe do bilo kakve greške kod parsiranja
	 */
	private ForLoopNode solveInsideForTag() {
		ArrayIndexedCollection elements = new ArrayIndexedCollection();
		Token nextToken = lexer.nextToken();
		ElementVariable variable;
		int counter = 0;
		
		if(nextToken.getType() == TokenType.VARIABLE)
			variable = new ElementVariable(nextToken.getValue().toString());
		else
			throw new SmartScriptParserException("Prvi element FOR petlje treba biti varijabla");
		
		while(counter < 4) {
			nextToken = lexer.nextToken();
			
			if(nextToken.getType() == TokenType.CLOSETAG) {
				lexer.setState(LexerState.TEXT);
				break;
			}
			
			switch (nextToken.getType()) {
				case STRING ->
					elements.add(new ElementString(nextToken.getValue().toString()));
				case DOUBLENUMBER ->
					elements.add(new ElementConstantDouble((Double) nextToken.getValue()));
				case INTNUMBER ->
					elements.add(new ElementConstantInteger((Integer) nextToken.getValue()));
				case VARIABLE ->
					elements.add(new ElementVariable(nextToken.getValue().toString()));
				default ->
					throw new SmartScriptParserException("Nedopušteni tip tokena u for petlji");
			}
			
			counter++;	
		}
		
		if(elements.size() == 2)
			return new ForLoopNode(variable, (Element) elements.get(0), (Element) elements.get(1), null);
		if(elements.size() == 3)
			return new ForLoopNode(variable, (Element) elements.get(0), (Element) elements.get(2), (Element) elements.get(1));
		
		throw new SmartScriptParserException("Broj elemenata u FOR petlji nije ispravan. Mora biti 3 ili 4");
	}

	/**
	 * Parsira unutar = tag-a.
	 * 
	 * @throws SmartScriptParserException ako dođe do bilo kakve greške kod parsiranja
	 */
	private void solveInsideEqualsTag() {
		ArrayIndexedCollection elements = new ArrayIndexedCollection();
		Token nextToken;
		
		while(true) {
			nextToken = lexer.nextToken();
			
			if(nextToken.getType() == TokenType.CLOSETAG) {
				lexer.setState(LexerState.TEXT);
				break;
			}
			
			switch (nextToken.getType()) {
				case STRING ->
					elements.add(new ElementString(nextToken.getValue().toString()));
				case DOUBLENUMBER ->
					elements.add(new ElementConstantDouble((Double) nextToken.getValue()));
				case INTNUMBER ->
					elements.add(new ElementConstantInteger((Integer) nextToken.getValue()));
				case FUNCTION ->
					elements.add(new ElementFunction(nextToken.getValue().toString()));
				case SYMBOL -> {
					if((Character) nextToken.getValue() != '=')
						elements.add(new ElementOperator(nextToken.getValue().toString()));
					else
						throw new SmartScriptParserException("Operator ne može biti =");
				}
				case VARIABLE ->
					elements.add(new ElementVariable(nextToken.getValue().toString()));
				default ->
					throw new SmartScriptParserException("Nedopušteni tip tokena u = tagu");
			}
			
		}
		
		Element[] elems = new Element[elements.size()];
		ElementsGetter getter = elements.createElementsGetter();
		
		for(int i=0; getter.hasNextElement(); i++)
			elems[i] = (Element) getter.getNextElement();
		
		((Node) stack.peek()).addChildNode(new EchoNode(elems));
	}

}