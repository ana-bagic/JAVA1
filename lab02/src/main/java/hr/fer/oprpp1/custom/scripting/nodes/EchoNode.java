package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;

/**
 * Razred predstavlja komandu koja dinamično generira tekstualni output.
 * 
 * @author Ana Bagić
 *
 */
public class EchoNode extends Node {
	
	/**
	 * Elementi Echo čvora.
	 */
	private Element[] elements;
	
	/**
	 * Konstruktor stvara novi čvor koristeći poslano polje elemenata.
	 * 
	 * @param elements polje elemenata koje želimo pohraniti u čvor
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}

	/**
	 * Vraća polje elemenata čvora.
	 * 
	 * @return polje elemenata čvora
	 */
	public Element[] getElements() {
		return elements;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{$ = ");
		
		for(Element el : elements)
			sb.append(el.asText()).append(" ");
		
		sb.append("$}");
		return sb.toString();
	}
	
	@Override
	public String toStringFancy() {
		StringBuilder sb = new StringBuilder();
		sb.append("EchoNode : ");
		
		for(Element el : elements)
			sb.append(el.asText()).append(" ");
		
		sb.append("\n");
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof EchoNode))
			return false;
		EchoNode other = (EchoNode) obj;
		
		return this.elements.equals(other.elements);
	}
	
}
