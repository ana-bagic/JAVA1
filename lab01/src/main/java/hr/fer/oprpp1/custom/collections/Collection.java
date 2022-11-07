package hr.fer.oprpp1.custom.collections;

/**
 * Razred predstavlja općenitu kolekciju objekata.
 * 
 * @author Ana Bagić
 *
 */
public class Collection {

	/**
	 * Protected default konstruktor.
	 */
	protected Collection() {
		super();
	}

	/**
	 * Vraća je li kolekcija prazna.
	 * 
	 * @return <code>true</code> ako je kolekcija prazna, inače <code>false</code>
	 */
	public boolean isEmpty() {
		if(size() > 0)
			return false;
		return true;
	}
	
	/**
	 * Veličina kolekcije, tj. broj elemenata u kolekciji.
	 * 
	 * @return broj objekata pohranjenih u kolekciji
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * Dodaje novi objekt u kolekciju.
	 * 
	 * @param value objekt koji želimo dodati u kolekciju
	 */
	public void add(Object value) {
	}
	
	/**
	 * Provjerava nalazi li se poslani objekt u kolekciji, moguće je provjeriti i za <code>null</code>.
	 * 
	 * @param value objekt koji tražimo u kolekciji
	 * @return <code>true</code> ako je objekt pronađen u kolekciji, inače <code>false</code>
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	 * Izbacuje poslani objekt iz kolekcije.
	 * 
	 * @param value objekt koji želimo izbaciti iz kolekcije
	 * @return <code>true</code> ako je objekt uspješno pronađen i izbačen iz kolekcije, inače <code>false</code>
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Alocira novo polje iste veličine i sadržaja, te ga vraća.
	 * 
	 * @return novo polje iste veličine i sadržaja
	 * @throws UnsupportedOperationException ako se metodi pristupa preko objekta tipa Collection
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Zove metodu processor.process() nad svakim elementom kolekcije.
	 * 
	 * @param processor Processor čiju metodu process želimo pozvati
	 */
	public void forEach(Processor processor) {
	}
	
	/**
	 * Dodaje sve elemente poslane kolekcije u trenutnu kolekciju.
	 * 
	 * @param other kolekcija čije elemente želimo pohraniti u kolekciju
	 * @throws NullPointerException ako je poslana kolekcija <code>null</code>
	 */
	public void addAll(Collection other)  {
		if(other == null)
			throw new NullPointerException("Poslana kolekcija je null");
		
		class AddProcessor extends Processor {
			
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		
		AddProcessor cp = new AddProcessor();
		other.forEach(cp);
	}
	
	/**
	 * Izbacuje sve elemente iz kolekcije.
	 */
	public void clear() {
	}
}
