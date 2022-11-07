package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/**
 * Razred predstavlja čvor grafa.
 * 
 * @author Ana Bagić
 *
 */
public abstract class Node {
	
	/**
	 * Podređeni čvorovi ovog čvora.
	 */
	private ArrayIndexedCollection children;
	
	/**
	 * Dodaje podređeni čvor ovome.
	 * 
	 * @param child podređeni čvor koji želimo dodati ovome čvoru
	 * @throws NullPointerException ako predamo <code>null</code> argument
	 */
	public void addChildNode(Node child) {
		if(children == null)
			children = new ArrayIndexedCollection();
		
		children.add(child);
	}
	
	/**
	 * Vraća broj direktno podređenih čvorova.
	 * 
	 * @return broj direktno podređenih čvorova
	 */
	public int numberOfChildren() {
		return children == null ? 0 : children.size();
	}
	
	/**
	 * Vraća podređeni čvor na poziciji index, ili baca iznimku ako index nije valjan.
	 * 
	 * @param index index s kojega želimo dohvatiti čvor
	 * @return podređeni čvor s pozicije index
	 * @throws IndexOutOfBoundsException ako se pokuša dohvatiti čvor s nevažeće pozicije
	 * (manje od 0 ili veće od <code>size-1</code>)
	 * @throws NullPointerException ako čvor nema djece
	 */
	public Node getChild(int index) {
		if(children == null)
			throw new NullPointerException("Nema djece");
		
		return (Node) children.get(index);
	}
	
	public abstract String toStringFancy();
	
}
