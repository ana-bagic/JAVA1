package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * Razred predstavlja tekstualne podatke.
 * 
 * @author Ana Bagić
 *
 */
public class TextNode extends Node {
	
	/**
	 * Tekst čvora.
	 */
	private String text;

	/**
	 * Konstruktor stvara novi čvor u kojemu je zapisan tekst text.
	 * 
	 * @param text tekst koji želimo zapisati u čvor
	 * @throws NullPointerException ako je argument <code>null</code>
	 */
	public TextNode(String text) {
		if(text == null)
			throw new NullPointerException();
		
		this.text = text;
	}

	/**
	 * Vraća tekst koji je pohranjen u čvoru.
	 * 
	 * @return tekst koji je pohranjen u čvoru
	 */
	public String getText() {
		return text;
	}
	
	@Override
	public String toString() {
		return text;
	}
	
	@Override
	public String toStringFancy() {
		return "TextNode : " + text + "\n";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof TextNode))
			return false;
		TextNode other = (TextNode) obj;
		
		return this.text.equals(other.text);
	}
	
}
