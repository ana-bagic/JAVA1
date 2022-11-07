package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;

/**
 * Razred predstavlja kolekciju implementiranu kao polje promjenjive veličine.
 * Dozvoljena je pohrana duplih elemenata, ali nije <code>null</code> referenci.
 * 
 * @author Ana Bagić
 *
 */
public class ArrayIndexedCollection extends Collection {

	/**
	 * Broj objekata pohranjenih u kolekciji.
	 */
	private int size;
	/**
	 * Polje u koje se spremaju elementi kolekcije.
	 */
	private Object[] elements;
	
	/**
	 * Default konstruktor. Stvara novo polje veličine 16.
	 */
	public ArrayIndexedCollection() {
		this(16);
	}
	
	/**
	 * Stvara novo polje zadane veličine.
	 * 
	 * @param initialCapacity veličina novog polja
	 * @throws IllegalArgumentException ako je parametar initialCapacity manji od 1
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if(initialCapacity < 1)
			throw new IllegalArgumentException("Parametar je manji od 1");
		
		elements = new Object[initialCapacity];
	}

	/**
	 * Stvara novo polje i njega puni objektima iz zadane kolekcije.
	 * Novo polje će biti veličine 16 ili veličine poslane kolekcije ako je ona veća od 16
	 * 
	 * @param collection kolekcija s čijim objektima želimo napuniti novo polje
	 * @throws NullPointerException ako je poslana kolekcija <code>null</code>
	 */
	public ArrayIndexedCollection(Collection collection) {
		this(collection, 16);
	}
	
	/**
	 * Stvara novo polje i njega puni objektima iz zadane kolekcije.
	 * Novo polje će biti željene veličine ili veličine poslane kolekcije ako je ona veća od željene veličine.
	 * 
	 * @param collection kolekcija s čijim objektima želimo napuniti novo polje
	 * @param initialCapacity željena veličina polja
	 * @throws NullPointerException ako je poslana kolekcija <code>null</code>
	 */
	public ArrayIndexedCollection(Collection collection, int initialCapacity) {
		this(initialCapacity < collection.size() ? collection.size() : initialCapacity);
		addAll(collection);
	}
	
	@Override
	public int size() {
		return size;
	}
	
	@Override
	public Object[] toArray() {
		return Arrays.copyOf(elements, size);
	}
	
	/**
	 * Duplicira veličinu polja ako je polje puno.
	 */
	private void checkForCapacity() {
		if(elements.length == size)
			elements = Arrays.copyOf(elements, size*2);
	}
	
	/**
	 * {@inheritDoc}
	 * @throws NullPointerException ako se pokuša dodati <code>null</code> element
	 */
	@Override
	public void add(Object value) {
		insert(value, size);
	}
	
	/**
	 * Dodaje novi objekt na poziciju position. Sve objekte nakon njega pomiče za jedno mjesto prema kraju.
	 * 
	 * @param value objekt koji želimo dodati u kolekciju
	 * @param position pozicija na koju želimo dodati novi objekt
	 * @throws NullPointerException ako se pokuša dodati <code>null</code> element
	 * @throws IndexOutOfBoundsException ako se pokuša dodati element na nevažeću poziciju
	 * (manju od 0 ili veću od <code>size</code>)
	 */
	public void insert(Object value, int position) {
		if(value == null)
			throw new NullPointerException("Pokušaj dodavanja null reference");
		if(position<0 || position>size)
			throw new IndexOutOfBoundsException("Pozicija je nevažeća");
		checkForCapacity();
		
		for(int i=size; i>position; i--)
			elements[i] = elements[i-1];
		
		elements[position] = value;
		size++;
	}
	
	@Override
	public boolean contains(Object value) {
		if(indexOf(value) != -1)
			return true;
		
		return false;
	}
	
	/**
	 * Vraća objekt koji se nalazi na poziciji index.
	 * 
	 * @param index index polja s kojega se želi dohvatiti element
	 * @return objekt na poziciji index
	 * @throws IndexOutOfBoundsException ako se pokuša dohvatiti objekt s nevažeće pozicije
	 * (manje od 0 ili veće od <code>size-1</code>)
	 */
	public Object get(int index) {
		if(index<0 || index>size-1)
			throw new IndexOutOfBoundsException("Index je nevažeći");
		
		return elements[index];
	}
	
	/**
	 * Vraća index prve pojave poslanog objekta ili -1 u slučaju da nije nađen.
	 * 
	 * @param value objekt koji tražimo u kolekciji
	 * @return index prve pojave objekta, odnosno -1 ako nije nađen
	 */
	public int indexOf(Object value) {
		for(int i=0; i<size; i++) {
			if(elements[i].equals(value))
				return i;
		}
			
		return -1;
	}
	
	/**
	 * {@inheritDoc}
	 * @throws NullPointerException ako je argument <code>null</code> element
	 */
	@Override
	public void forEach(Processor processor) {
		if(processor == null)
			throw new NullPointerException("Argument je null");
		
		for(int i=0; i<size; i++)
			processor.process(elements[i]);
	}
	
	/**
	 * {@inheritDoc}
	 * Njegov prvi primjerak.
	 */
	@Override
	public boolean remove(Object value) {
		int indexRemove = indexOf(value);
		if(indexRemove == -1)
			return false;
		
		remove(indexRemove);
		return true;
	}
	
	/**
	 * Izbacuje objekt s pozicije index. Sve objekte nakon njega pomiče za jedno mjesto prema početku.
	 * 
	 * @param index pozicija s koje želimo maknuti objekt
	 * @throws IndexOutOfBoundsException ako se pokuša izbaciti objekt s nevažeće pozicije
	 * (manje od 0 ili veće od <code>size-1</code>)
	 */
	public void remove(int index) {
		if(index<0 || index>=size)
			throw new IndexOutOfBoundsException("Index je nevažeći");
		
		for(int i=index; i<size-1; i++)
			elements[i] = elements[i+1];
		elements[size-1] = null;
		
		size--;
	}
	
	@Override
	public void clear() {
		for(int i=0; i<size; i++)
			remove(i);
		
		size = 0;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ArrayIndexedCollection [size = " + size + ", elements = [");
		
		for(int i=0; i<size; i++) {
			sb.append(elements[i]);
			if(i != size-1)
				sb.append(", ");
		}
		
		sb.append("]]");
		return sb.toString();
	}
}
