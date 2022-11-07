package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Razred predstavlja kolekciju implementiranu kao polje promjenjive veličine.
 * Dozvoljena je pohrana duplih elemenata, ali nije <code>null</code> referenci.
 * 
 * @author Ana Bagić
 *
 */
public class ArrayIndexedCollection<T> implements List<T> {
	
	/**
	 * Razred predstavlja implementaciju sučelja ElementsGetter za kolekciju implementiranu kao polje.
	 * 
	 * @author Ana Bagić
	 *
	 */
	private static class ArrayElementsGetter<T> implements ElementsGetter<T> {

		/**
		 * Index elementa kojeg idućeg možemo dohvatiti.
		 */
		private int currentIndex;
		/**
		 * Referenca na kolekciju putem koje je napravljena instanca ovog razreda.
		 */
		private ArrayIndexedCollection<T> col;
		/**
		 * Broj modifikacija kolekcije, čije elemente dohvaćamo, pri stvaranju ovog objekta.
		 */
		private long savedModificationCount;
		
		/**
		 * Stvara novu instancu ovog razreda s referencom na kolekciju.
		 * 
		 * @param col referenca na kolekciju čije elemente želimo dohvaćati
		 */
		public ArrayElementsGetter(ArrayIndexedCollection<T> col) {
			currentIndex = 0;
			this.col = col;
			savedModificationCount = col.modificationCount;
		}
		
		/**
		 * @throws ConcurrentModificationException ako postoje izmjene u kolekciji nakon stvaranja ovog gettera
		 */
		@Override
		public boolean hasNextElement() {
			checkForModification();
			return currentIndex < col.size;
		}

		/**
		 * @throws NoSuchElementException ako nema više elemenata za dohvat
		 * @throws ConcurrentModificationException ako postoje izmjene u kolekciji nakon stvaranja ovog gettera
		 */
		@Override
		public T getNextElement() {
			checkForModification();
			if(currentIndex == col.size)
				throw new NoSuchElementException("Nema više elemenata za dohvat");
			
			return col.get(currentIndex++);
		}
		
		/**
		 * Baca iznimku ako postoje promjene u kolekciji.
		 */
		private void checkForModification() {
			if(savedModificationCount != col.modificationCount)
				throw new ConcurrentModificationException("Kolekcija ima izmjene, ovaj getter više ne vrijedi.");
		}
		
	}

	/**
	 * Broj objekata pohranjenih u kolekciji.
	 */
	private int size;
	/**
	 * Polje u koje se spremaju elementi kolekcije.
	 */
	private T[] elements;
	/**
	 * Broj promjena napravljenih u kolekciji (dodavanje/brisanje elemenata, realociranje polja).
	 */
	private long modificationCount;
	
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
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(int initialCapacity) {
		if(initialCapacity < 1)
			throw new IllegalArgumentException("Parametar je manji od 1");
		
		elements = (T[]) new Object[initialCapacity];
		modificationCount = 0;
	}

	/**
	 * Stvara novo polje i njega puni objektima iz zadane kolekcije.
	 * Novo polje će biti veličine 16 ili veličine poslane kolekcije ako je ona veća od 16
	 * 
	 * @param collection kolekcija s čijim objektima želimo napuniti novo polje
	 * @throws NullPointerException ako je poslana kolekcija <code>null</code>
	 */
	public ArrayIndexedCollection(Collection<? extends T> collection) {
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
	public ArrayIndexedCollection(Collection<? extends T> collection, int initialCapacity) {
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
	
	@Override
	public ElementsGetter<T> createElementsGetter() {
		return new ArrayElementsGetter<T>(this);
	}
	
	/**
	 * Duplicira veličinu polja ako je polje puno.
	 */
	private void checkForCapacity() {
		if(elements.length == size) {
			elements = Arrays.copyOf(elements, size*2);
			modificationCount++;
		}
	}
	
	/**
	 * @throws NullPointerException ako se pokuša dodati <code>null</code> element
	 */
	@Override
	public void add(T value) {
		insert(value, size);
	}
	
	/**
	 * @throws IndexOutOfBoundsException ako se pokuša dodati element na nevažeću poziciju
	 * (manju od 0 ili veću od <code>size</code>)
	 * @throws NullPointerException ako se pokuša dodati <code>null</code> element
	 */
	@Override
	public void insert(T value, int position) {
		if(value == null)
			throw new NullPointerException("Pokušaj dodavanja null reference");
		if(position<0 || position>size)
			throw new IndexOutOfBoundsException("Pozicija je nevažeća");
		checkForCapacity();
		
		for(int i=size; i>position; i--)
			elements[i] = elements[i-1];
		
		elements[position] = value;
		size++;
		modificationCount++;
	}
	
	@Override
	public boolean contains(Object value) {
		if(indexOf(value) != -1)
			return true;
		
		return false;
	}
	
	/**
	 * @throws IndexOutOfBoundsException ako se pokuša dohvatiti objekt s nevažeće pozicije
	 * (manje od 0 ili veće od <code>size-1</code>)
	 */
	@Override
	public T get(int index) {
		if(index<0 || index>size-1)
			throw new IndexOutOfBoundsException("Index je nevažeći");
		
		return elements[index];
	}
	
	@Override
	public int indexOf(Object value) {
		for(int i=0; i<size; i++) {
			if(elements[i].equals(value))
				return i;
		}
			
		return -1;
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
	 * @throws IndexOutOfBoundsException ako se pokuša izbaciti objekt s nevažeće pozicije
	 * (manje od 0 ili veće od <code>size-1</code>)
	 */
	@Override
	public void remove(int index) {
		if(index<0 || index>=size)
			throw new IndexOutOfBoundsException("Index je nevažeći");
		
		for(int i=index; i<size-1; i++)
			elements[i] = elements[i+1];
		elements[size-1] = null;
		
		size--;
		modificationCount++;
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
