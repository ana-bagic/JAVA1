package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Razred predstavlja kolekciju implementiranu kao povezana lista.
 * Dozvoljena je pohrana duplih elemenata, ali nije <code>null</code> referenci.
 * 
 * @author Ana Bagić
 *
 */
public class LinkedListIndexedCollection<T> implements List<T> {

	/**
	 * Razred predstavlja implementaciju sučelja ElementsGetter za kolekciju implementiranu kao listu.
	 * 
	 * @author Ana Bagić
	 *
	 */
	private static class ListElementsGetter<T> implements ElementsGetter<T> {

		/**
		 * Broj dohvaćenih elemenata.
		 */
		private int fetchedElements;
		/**
		 * Referenca na kolekciju putem koje je napravljena instanca ovog razreda.
		 */
		private LinkedListIndexedCollection<T> col;
		/**
		 * Referenca na čvor liste koji idući možemo dohvatiti.
		 */
		private ListNode<T> currentNode;
		/**
		 * Broj modifikacija kolekcije, čije elemente dohvaćamo, pri stvaranju ovog objekta.
		 */
		private long savedModificationCount;
		
		/**
		 * Stvara novu instancu ovog razreda s referencom na kolekciju.
		 * 
		 * @param col referenca na kolekciju čije elemente želimo dohvaćati
		 */
		public ListElementsGetter(LinkedListIndexedCollection<T> col) {
			fetchedElements = 0;
			this.col = col;
			currentNode = col.first;
			savedModificationCount = col.modificationCount;
		}
		
		/**
		 * @throws ConcurrentModificationException ako postoje izmjene u kolekciji nakon stvaranja ovog gettera
		 */
		@Override
		public boolean hasNextElement() {
			checkForModification();
			return fetchedElements < col.size;
		}

		/**
		 * @throws NoSuchElementException ako nema više elemenata za dohvat
		 * @throws ConcurrentModificationException ako postoje izmjene u kolekciji nakon stvaranja ovog gettera
		 */
		@Override
		public T getNextElement() {
			checkForModification();
			if(fetchedElements == col.size)
				throw new NoSuchElementException("Nema više elemenata za dohvat");
			
			T value = currentNode.value;
			currentNode = currentNode.next;
			
			fetchedElements++;
			return value;
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
	 * Razred koji predstavlja jedan čvor liste.
	 * 
	 * @author Ana Bagić
	 *
	 */
	private static class ListNode<T> {
		/**
		 * Vrijednost pohranjena u čvoru.
		 */
		private T value;
		/**
		 * Pokazivač na prethodni čvor u listi.
		 */
		private ListNode<T> previous;
		/**
		 * Pokazivač na idući čvor u listi.
		 */
		private ListNode<T> next;
		
		/**
		 * @param value vrijednost koju želimo pohraniti u čvor
		 */
		ListNode(T value) {
			this.value = value;
		}
	}
	
	/**
	 * Broj čvorova u listi.
	 */
	private int size;
	/**
	 * Pokazivač na prvi čvor liste.
	 */
	private ListNode<T> first;
	/**
	 * Pokazivač na zadnji čvor liste.
	 */
	private ListNode<T> last;
	/**
	 * Broj promjena napravljenih u kolekciji (dodavanje/brisanje čvorova).
	 */
	private long modificationCount;
	
	/**
	 * Default konstruktor. Stvara praznu listu.
	 */
	public LinkedListIndexedCollection() {
		first = last = null;
		size = 0;
		modificationCount = 0;
	}
	
	/**
	 * Stvara listu koju puni s objektima poslane kolekcije.
	 * 
	 * @param collection kolekcija kojom će se napuniti nova
	 * @throws NullPointerException ako je poslana kolekcija <code>null</code>
	 */
	public LinkedListIndexedCollection(Collection<? extends T> collection) {
		this();
		addAll(collection);
	}
	
	@Override
	public int size() {
		return size;
	}
	
	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		
		for(int i=0; i<size; i++)
			array[i] = get(i);
		
		return array;
	}
	
	@Override
	public ElementsGetter<T> createElementsGetter() {
		return new ListElementsGetter<T>(this);
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
		
		ListNode<T> newNode = new ListNode<T>(value);
		if(size == 0) {
			first = last = newNode;
		} else {
			if(position != size) {
				ListNode<T> search = first;
				for(int i=0; i<position; i++)
					search = search.next;
				
				newNode.next = search;
				newNode.previous = search.previous;
				newNode.previous.next = newNode;
				search.previous = newNode;
			} else {
				newNode.previous = last;
				last.next = newNode;
				last = newNode;
			}
		}
		
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
		
		if(size-index >= index) {
			ListNode<T> search = first;
			for(int i=0; i<index; i++)
				search = search.next;
			return search.value;
		}
		ListNode<T> search = last;
		for(int i=size-1; i>index; i--)
			search = search.previous;
		return search.value;
	}
	
	@Override
	public int indexOf(Object value) {
		ListNode<T> search = first;
		for(int i=0; i<size; i++, search = search.next) {
			if(search.value.equals(value))
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
		
		ListNode<T> search = first;
		for(int i=0; i<index; i++)
			search = search.next;
		
		if(index == 0) {
			first = search.next;
			search.next.previous = null;
			search.next = null;
		} else if(index == size-1) {
			search.previous.next = null;
			search.previous = null;
		} else {
			search.previous.next = search.next;
			search.next.previous = search.previous;
			search.previous = search.next = null;
		}
		
		size--;
		modificationCount++;
	}
	
	@Override
	public void clear() {
		first = last = null;
		size = 0;
		modificationCount++;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("LinkedListIndexedCollection [size = " + size + ", elements = [");
		
		ListNode<T> search = first;
		for(int i=0; i<size; i++, search = search.next) {
			sb.append(search.value);
			if(i != size-1)
				sb.append(", ");
		}
		
		sb.append("]]");
		return sb.toString();
	}
	
}
