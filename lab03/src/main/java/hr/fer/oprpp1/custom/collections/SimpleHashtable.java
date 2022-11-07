package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Razred predstavlja tablicu raspršenog adresiranja.
 * 
 * @author Ana Bagić
 *
 * @param <K> ključ
 * @param <V> vrijednost
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {
	
	/**
	 * Razred predstavlja jedno mjesto u tablici.
	 * 
	 * @author Ana Bagić
	 *
	 * @param <K> ključ
	 * @param <V> vrijednost
	 */
	public static class TableEntry<K, V> {
		
		/**
		 * Ključ zapisa.
		 */
		private K key;
		/**
		 * Vrijednost zapisa.
		 */
		private V value;
		/**
		 * Sljedeći primjerak zapisa koji se nalazi na istom mjestu tablice.
		 */
		private TableEntry<K, V> next;
		
		/**
		 * Konstruktor stvara novi zapis na temelju poslanog ključa i vrijednosti.
		 * Ključ ne smije biti <code>null</code>.
		 * 
		 * @param key ključ
		 * @param value vrijednost
		 * @param next sljedeći zapis koji se nalazi na istom mjestu tablice
		 * @throws NullPointerException ako je poslan ključ <code>null</code>
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			if(key == null)
				throw new NullPointerException("Ključ ne smije biti null");
			
			this.key = key;
			this.value = value;
			this.next = next;
		}
		
		/**
		 * Vraća ključ zapisa.
		 * 
		 * @return ključ zapisa
		 */
		public K getKey() {
			return key;
		}
		
		/**
		 * Postavlja vrijednost zapisa na novu poslanu vrijednost.
		 * 
		 * @param value vrijednost koju želimo staviti u zapis
		 */
		public void setValue(V value) {
			this.value = value;
		}
		
		/**
		 * Vraća vrijednost zapisa.
		 * 
		 * @return vrijednost zapisa
		 */
		public V getValue() {
			return value;
		}
		
		@Override
		public String toString() {
			return key + "=" + value;
		}
		
	}

	/**
	 * Polje referenci na glave ulančanih lista.
	 */
	private TableEntry<K, V>[] table;
	/**
	 * Broj zapisa pohranjenih u tablici.
	 */
	private int size;
	/**
	 * Broj promjena napravljenih u tablici (dodavanje/brisanje elemenata, promjena veličine tablice).
	 */
	private int modificationCount;
	
	/**
	 * Default kontruktor stvara tablicu veličine 16 mjesta.
	 */
	public SimpleHashtable() {
		this(16);
	}
	
	/**
	 * Konstruktor stvara tablicu veličine koja je potencija broja 2, a prva je veća ili jednaka predanom broju.
	 * 
	 * @param capacity željeni kapacitet tablice
	 * @throws IllegalArgumentException ako je predani kapacitet manji od 1
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if(capacity < 1)
			throw new IllegalArgumentException("Broj mora biti veći od 0");
		
		int newCapacity = 1;
		while (newCapacity < capacity)
        	newCapacity = newCapacity << 1;
		
		table = (TableEntry<K, V>[]) new TableEntry[newCapacity];
		size = 0;
		modificationCount = 0;
	}
	
	/**
	 * Vraća broj zapisa pohranjenih u tablici.
	 * 
	 * @return broj zapisa pohranjenih u tablici
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Vraća je li tablica prazna.
	 * 
	 * @return <code>true</code> ako je tablica prazna, inače <code>false</code>
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * Stvara novi zapis s parom(ključ, vrijednost) ako takav ključ u tablici još ne postoji,
	 * ili ažurira postojeći zapis s poslanim ključem na poslanu vrijednost, te vraća staru vrijednost.
	 * 
	 * @param key ključ čiju vrijednost želimo postaviti, odnosno ažurirati
	 * @param value vrijednost na koju želimo postaviti zapis s poslanim ključem
	 * @return stara vrijednost zapisa s poslanim ključem, odnosno <code>null</code> ako tog ključa još nema u tablici
	 * @throws NullPointerException ako je poslan ključ <code>null</code>
	 */
	public V put(K key, V value) {
		checkForOverflow();
		int index = calculateTableSlot(key);
		TableEntry<K, V> entry = table[index];
		
		if(entry != null) {
			 while(true) {
				if(entry.key.equals(key)) {
					V oldValue = entry.value;
					entry.value = value;
					return oldValue;
				}
				
				if(entry.next == null)
					break;
				
				entry = entry.next;
			}
		}
		
		size++;
		modificationCount++;
		if(entry != null) entry.next = new TableEntry<>(key, value, null);
		else table[index] = new TableEntry<>(key, value, null);
		return null;
	}
	
	/**
	 * Vraća vrijednost unutar zapisa s poslanim ključem.
	 * 
	 * @param key ključ čiju vrijednost želimo dohvatiti
	 * @return vrijednost unutar zapisa s poslanim ključem ako je pronađen, inače <code>null</code>
	 */
	public V get(Object key) {
		TableEntry<K, V> entry = getEntryByKey(key);
		
		return entry != null ? entry.value : null;
	}
	
	/**
	 * Provjerava postoji li zapis s poslanim ključem.
	 * 
	 * @param key ključ za koji želimo provjeriti postoji li u tablici
	 * @return <code>true</code> ako postoji zapis s poslanim ključem, inače <code>false</code>
	 */
	public boolean containsKey(Object key) {
		TableEntry<K, V> entry = getEntryByKey(key);
		
		return entry != null ? true : false;
	}
	
	/**
	 * Provjerava postoji li zapis s poslanom vrijednosti.
	 * 
	 * @param value vrijednost za koju želimo provjeriti postoji li u tablici
	 * @return <code>true</code> ako postoji zapis s poslanom vrijednosti, inače <code>false</code>
	 */
	public boolean containsValue(Object value) {
		TableEntry<K, V> entry;
		
		for(int i=0; i<table.length; i++) {
			entry = table[i];
			
			while(entry != null) {
				if(entry.value.equals(value))
					return true;
					
				entry = entry.next;
			}
		}
		
		return false;
	}
	
	/**
	 * Izbacuje zapis s poslanim ključem iz tablice.
	 * 
	 * @param key ključ zapisa kojeg želimo izbaciti iz tablice
	 * @return <code>true</code> ako je zapis s poslanim ključem uspješno pronađen i izbačen iz tablice, inače <code>false</code>
	 */
	public V remove(Object key) {
		int index = calculateTableSlot(key);
		TableEntry<K, V> entry = table[index];
		
		if(entry != null) {
			modificationCount++;
			
			if(entry.key.equals(key)) {
				size--;
				V oldValue = entry.value;
				table[index] = entry.next;
				return oldValue;
			}
			
			while(entry.next != null) {
				if(entry.next.key.equals(key)) {
					size--;
					V oldValue = entry.next.value;
					entry.next = entry.next.next;
					return oldValue;
				}
				
				entry = entry.next;
			}
			
		}
		
		return null;
	}
	
	/**
	 * Briše sve parove iz tablice.
	 */
	public void clear() {
		for(int i=0; i<table.length; i++)
			table[i] = null;
		
		size = 0;
		modificationCount++;
	}
	
	/**
	 * Alocira novo polje iste veličine tablice i puni ga zapisima u tablici.
	 * 
	 * @return novo polje iste veličine tablice napunjeno zapisima u tablici
	 */
	public TableEntry<K,V>[] toArray() {
		@SuppressWarnings("unchecked")
		TableEntry<K, V>[] array = (TableEntry<K, V>[]) new TableEntry[size];
		TableEntry<K, V> entry;
		int indexNewArray = 0;
		
		for(int i=0; i<table.length; i++) {
			entry = table[i];
			
			while(entry != null) {
				array[indexNewArray++] = entry;
					
				entry = entry.next;
			}
		}
		
		return array;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		TableEntry<K, V>[] array = toArray();
		sb.append("[");
		
		for(int i=0; i<array.length; i++) {
			if(i != 0)
				sb.append(", ");
			sb.append(array[i]);
		}
		
		sb.append("]");
		return sb.toString();
	}
	
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
	
	/**
	 * Implementacija iteratora SimpleHashtable razreda.
	 * 
	 * @author Ana Bagić
	 *
	 */
	private class IteratorImpl implements Iterator<TableEntry<K,V>> {
		
		/**
		 * Index elementa kojeg idućeg možemo dohvatiti.
		 */
		int currentIndex;
		/**
		 * Index elementa koji je zadnji dohvaćen pozivom next() metode.
		 */
		int lastRetIndex;
		/**
		 * Broj modifikacija tablice pri stvaranju ovog iteratora.
		 */
		int savedModificationCount;

		public IteratorImpl() {
			currentIndex = 0;
			lastRetIndex = -1;
			savedModificationCount = modificationCount;
		}

		/**
		 * Provjerava može li se putem ove instance Iteratora dohvatiti još parova tablice.
		 * 
		 * @return <code>true</code> ako se putem ove instance Iteratora može dohvatiti još parova tablice,
		 * inače <code>false</code>
		 * @throws ConcurrentModificationException ako postoje izmjene u tablici nakon stvaranja ovog iteratora
		 */
		public boolean hasNext() {
			checkForModification();
			return currentIndex < size();
		}

		/**
		 * Vraća idući par iz tablice koji se može dohvatiti putem ove instance Iteratora.
		 * 
		 * @return idući par iz tablice koji se može dohvatiti putem ove instance Iteratora
		 * @throws NoSuchElementException ako nema više parova za dohvat
		 * @throws ConcurrentModificationException ako postoje izmjene u tablici nakon stvaranja ovog iteratora
		 */
        public TableEntry<K, V> next() {
            checkForModification();
        	if(currentIndex == size())
				throw new NoSuchElementException("Nema više parova za dohvat");
        	
        	lastRetIndex = currentIndex;
        	return getEntryByIndex(currentIndex++);
        }

        /**
         * Izbacuje iz tablice par koji je zadnji dohvaćen pozivom funkcije next()
         * 
         * @throws ConcurrentModificationException ako postoje izmjene u tablici nakon stvaranja ovog iteratora
         * @throws IllegalStateException ako se ova metoda poziva više od jednom
         * za trenutni par nad kojim je iterator ili prije nego što je ijednom pozvana metoda next()
         */
        public void remove() {
        	checkForModification();
            if (lastRetIndex < 0)
                throw new IllegalStateException();

            TableEntry<K, V> entry = getEntryByIndex(lastRetIndex);
            SimpleHashtable.this.remove(entry.key);
            
            currentIndex--;
            lastRetIndex = -1;
            savedModificationCount = modificationCount;
        }

        /**
		 * Baca iznimku ako postoje promjene u tablici.
		 */
        private void checkForModification() {
            if (modificationCount != savedModificationCount)
                throw new ConcurrentModificationException();
        }
    }
	
	/**
	 * Računa index tablice koji odgovara poslanom ključu.
	 * 
	 * @param key ključ za koji tražimo index tablice
	 * @return index tablice koji odgovara poslanom ključu
	 */
	private int calculateTableSlot(Object key) {
		return Math.abs(key.hashCode()) % table.length;
	}
	
	/**
	 * Vraća zapis koji odgovara poslanom ključu, odnosno <code>null</code> ako on ne postoji.
	 * 
	 * @param key ključ čiji zapis tražimo
	 * @return zapis koji odgovara poslanom ključu, odnosno <code>null</code> ako on ne postoji
	 */
	private TableEntry<K, V> getEntryByKey(Object key) {
		int index = calculateTableSlot(key);
		TableEntry<K, V> entry = table[index];
		
		while(entry != null) {
			if(entry.key.equals(key))
				return entry;
				
			entry = entry.next;
		}
		
		return null;
	}
	
	/**
	 * Dohvaća par na indexu index.
	 * 
	 * @param index index s kojega želimo dohvatiti par
	 * @return par koji se nalazi na indexu index
	 * @throws IndexOutOfBoundsException ako je poslan index koji u tablici ne postoji
	 */
	private TableEntry<K, V> getEntryByIndex(int index) {
		if(index < 0 || index >= size)
			throw new IndexOutOfBoundsException("Par s tim indexom ne postoji");
		
		TableEntry<K, V> entry;
		int counter = 0;
			
		for(int i=0; i<table.length; i++) {
			entry = table[i];
				
			while(entry != null) {
				if(counter == index)
					return entry;
				entry = entry.next;
				counter++;
			}
		}	
		
		return null;
	}
	
	/**
	 * Provjerava je li tablica prepunjena, te ako je alocira novu sa duplo više mjesta.
	 */
	@SuppressWarnings("unchecked")
	private void checkForOverflow() {
		double occupancy = 1.0*size/table.length;
		
		if(occupancy >= 0.75*table.length) {
			TableEntry<K, V>[] array = toArray();
			
			table = (TableEntry<K, V>[]) new TableEntry[table.length*2];
			size = 0;
			
			for(int i=0; i<array.length; i++)
				put(array[i].key, array[i].value);
		}
	}
	
}
