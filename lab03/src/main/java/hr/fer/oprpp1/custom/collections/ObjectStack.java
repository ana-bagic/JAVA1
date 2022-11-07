package hr.fer.oprpp1.custom.collections;

/**
 * Razred predstavlja stog.
 * 
 * @author Ana Bagić
 *
 */
public class ObjectStack<T> {

	/**
	 * Objekt ArrayIndexedCollection koji se koristi za implementaciju stoga.
	 */
	private ArrayIndexedCollection<T> stack;
	
	/**
	 * Default konstruktor. Stvara novi stog.
	 */
	public ObjectStack() {
		stack = new ArrayIndexedCollection<>();
	}
	
	/**
	 * Vraća je li stog prazan.
	 * 
	 * @return <code>true</code> ako je stog prazan, inače <code>false</code>
	 */
	public boolean isEmpty() {
		return stack.isEmpty();
	}
	
	/**
	 * Veličina stoga, tj. broj objekata na stogu.
	 * 
	 * @return broj objekata pohranjenih na stogu
	 */
	public int size() {
		return stack.size();
	}
	
	/**
	 * Dodaje novi objekt na stog.
	 * 
	 * @param value objekt koji želimo dodati na stog
	 * @throws NullPointerException ako se pokuša dodati <code>null</code> element
	 */
	public void push(T value) {
		stack.add(value);
	}
	
	
	/**
	 * Skida objekt sa stoga i vraća njegovu vrijednost.
	 * 
	 * @return objekt koji je skinut sa stoga
	 * @throws EmptyStackException ako je stog prazan
	 */
	public T pop() {
		T last = peek();
		stack.remove(stack.size()-1);
		
		return last;
	}
	
	/**
	 * Vraća objekt koji se nalazi na vrhu stoga.
	 * 
	 * @return objekt koji je na vrhu stoga
	 * @throws EmptyStackException ako je stog prazan
	 */
	public T peek() {
		if(stack.isEmpty())
			throw new EmptyStackException();
		
		T last = stack.get(stack.size()-1);
		return last;
	}
	
	/**
	 * Izbacuje sve elemente sa stoga.
	 */
	public void clear() {
		stack.clear();
	}
}
