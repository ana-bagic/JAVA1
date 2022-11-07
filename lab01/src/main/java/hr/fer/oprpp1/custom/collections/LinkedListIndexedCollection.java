package hr.fer.oprpp1.custom.collections;

/**
 * Razred predstavlja kolekciju implementiranu kao povezanu listu.
 * Dozvoljena je pohrana duplih elemenata, ali nije <code>null</code> referenci.
 * 
 * @author Ana Bagić
 *
 */
public class LinkedListIndexedCollection extends Collection {

	/**
	 * Razred koji predstavlja jedan čvor liste.
	 * 
	 * @author Ana Bagić
	 *
	 */
	private static class ListNode {
		/**
		 * Vrijednost pohranjena u čvoru.
		 */
		private Object value;
		/**
		 * Pokazivač na prethodni čvor u listi.
		 */
		private ListNode previous;
		/**
		 * Pokazivač na idući čvor u listi.
		 */
		private ListNode next;
		
		/**
		 * @param value vrijednost koju želimo pohraniti u čvor
		 */
		ListNode(Object value) {
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
	private ListNode first;
	/**
	 * Pokazivač na zadnji čvor liste.
	 */
	private ListNode last;
	
	/**
	 * Default konstruktor. Stvara praznu listu.
	 */
	public LinkedListIndexedCollection() {
		first = last = null;
		size = 0;
	}
	
	/**
	 * Stvara listu koju puni s objektima poslane kolekcije.
	 * 
	 * @param collection kolekcija kojom će se napuniti nova
	 * @throws NullPointerException ako je poslana kolekcija <code>null</code>
	 */
	public LinkedListIndexedCollection(Collection collection) {
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
	
	/**
	 * {@inheritDoc}
	 * @throws NullPointerException ako se pokuša dodati <code>null</code> element
	 */
	@Override
	public void add(Object value) {
		// iako metoda insert nije O(1),
		// u ovom slucaju će biti jer neće ući u for petlju
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
		
		ListNode newNode = new ListNode(value);
		if(size == 0) {
			first = last = newNode;
		} else {
			if(position != size) {
				ListNode search = first;
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
	 * @param index index liste s kojega se želi dohvatiti element
	 * @return objekt na poziciji index
	 * @throws IndexOutOfBoundsException ako se pokuša dohvatiti objekt s nevažeće pozicije
	 * (manje od 0 ili veće od <code>size-1</code>)
	 */
	public Object get(int index) {
		if(index<0 || index>size-1)
			throw new IndexOutOfBoundsException("Index je nevažeći");
		
		if(size-index >= index) {
			ListNode search = first;
			for(int i=0; i<index; i++)
				search = search.next;
			return search.value;
		}
		ListNode search = last;
		for(int i=size-1; i>index; i--)
			search = search.previous;
		return search.value;
	}
	
	/**
	 * Vraća index prve pojave poslanog objekta ili -1 u slučaju da nije nađen.
	 * 
	 * @param value objekt koji tražimo u kolekciji
	 * @return index prve pojave objekta, odnosno -1 ako nije nađen
	 */
	public int indexOf(Object value) {
		ListNode search = first;
		for(int i=0; i<size; i++, search = search.next) {
			if(search.value.equals(value))
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
		
		ListNode search = first;
		for(int i=0; i<size; i++, search = search.next)
			processor.process(search.value);
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
		
		ListNode search = first;
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
	}
	
	@Override
	public void clear() {
		first = last = null;
		size = 0;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("LinkedListIndexedCollection [size = " + size + ", elements = [");
		
		ListNode search = first;
		for(int i=0; i<size; i++, search = search.next) {
			sb.append(search.value);
			if(i != size-1)
				sb.append(", ");
		}
		
		sb.append("]]");
		return sb.toString();
	}
	
}
