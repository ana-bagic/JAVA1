package hr.fer.oprpp1.custom.collections;

/**
 * Sučelje predstavlja općenitu kolekciju objekata.
 * 
 * @author Ana Bagić
 *
 */
public interface Collection {
	
	/**
	 * Veličina kolekcije, tj. broj elemenata u kolekciji.
	 * 
	 * @return broj objekata pohranjenih u kolekciji
	 */
	int size();
	
	/**
	 * Vraća je li kolekcija prazna.
	 * 
	 * @return <code>true</code> ako je kolekcija prazna, inače <code>false</code>
	 */
	default boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * Alocira novo polje iste veličine i sadržaja, te ga vraća.
	 * 
	 * @return novo polje iste veličine i sadržaja
	 */
	Object[] toArray();
	
	/**
	 * Stvara i vraća referencu na novi objekt razreda ElementsGetter
	 * preko kojega se mogu dohvaćati elementi kolekcije.
	 * 
	 * @return referenca na novi objekt razreda ElementsGetter
	 */
	ElementsGetter createElementsGetter();
	
	/**
	 * Dodaje novi objekt u kolekciju.
	 * 
	 * @param value objekt koji želimo dodati u kolekciju
	 */
	void add(Object value);
	
	/**
	 * Dodaje sve elemente poslane kolekcije u trenutnu kolekciju.
	 * 
	 * @param other kolekcija čije elemente želimo pohraniti u kolekciju
	 * @throws NullPointerException ako je poslana kolekcija <code>null</code>
	 */
	default void addAll(Collection other)  {
		if(other == null)
			throw new NullPointerException("Poslana kolekcija je null");
		
		Processor cp = value -> add(value);
		other.forEach(cp);
	}
	
	/**
	 * Na kraj trenutne kolekcije dodaje sve elemente kolekcije col koji zadovoljavaju uvjet testera.
	 * 
	 * @param col kolekcija čije elemente želimo dodati u trenutnu kolekciju
	 * @param tester tester koji testira hoće li element biti prihvaćen ili ne
	 * @throws NullPointerException ako je neki od argumenata <code>null</code>
	 */
	default void addAllSatisfying(Collection col, Tester tester) {
		if(col == null)
			throw new NullPointerException("Poslana kolekcija je null");
		if(tester == null)
			throw new NullPointerException("Poslani tester je null");
		
		ElementsGetter getter = col.createElementsGetter();
		while(getter.hasNextElement()) {
			Object value = getter.getNextElement();
			
			if(tester.test(value))
				add(value);
		}
	}
	
	/**
	 * Provjerava nalazi li se poslani objekt u kolekciji, moguće je provjeriti i za <code>null</code>.
	 * 
	 * @param value objekt koji tražimo u kolekciji
	 * @return <code>true</code> ako je objekt pronađen u kolekciji, inače <code>false</code>
	 */
	boolean contains(Object value);
	
	/**
	 * Zove metodu processor.process() nad svakim elementom kolekcije.
	 * 
	 * @param processor Processor čiju metodu process želimo pozvati
	 * @throws NullPointerException ako je argument <code>null</code>
	 */
	default void forEach(Processor processor) {
		if(processor == null)
			throw new NullPointerException("Processor je null");
		
		ElementsGetter getter = this.createElementsGetter();
		
		while(getter.hasNextElement())
			processor.process(getter.getNextElement());
	}
	
	/**
	 * Izbacuje poslani objekt iz kolekcije.
	 * 
	 * @param value objekt koji želimo izbaciti iz kolekcije
	 * @return <code>true</code> ako je objekt uspješno pronađen i izbačen iz kolekcije, inače <code>false</code>
	 */
	boolean remove(Object value);
	
	/**
	 * Izbacuje sve elemente iz kolekcije.
	 */
	void clear();
}
