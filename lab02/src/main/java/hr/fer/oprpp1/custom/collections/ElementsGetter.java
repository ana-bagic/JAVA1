package hr.fer.oprpp1.custom.collections;

/**
 * Sučelje predstavlja model objekta koji služi za dohvat elemenata kolekcije.
 * 
 * @author Ana Bagić
 *
 */
public interface ElementsGetter {

	/**
	 * Provjerava može li se putem ove instance ElementsGettera dohvatiti još elemenata kolekcije.
	 * 
	 * @return <code>true</code> ako se putem ove instance ElementsGettera može dohvatiti još elemenata kolekcije,
	 * inače <code>false</code>
	 */
	boolean hasNextElement();
	
	/**
	 * Vraća idući element kolekcije koji se može dohvatiti putem ove instance ElementsGettera.
	 * 
	 * @return idući element kolekcije koji se može dohvatiti putem ove instance ElementsGettera
	 */
	Object getNextElement();
	
	/**
	 * Nad preostalim elementima kolekcije (oni koji nisu do sad dohvaćeni) izvršava process metodu predanog Processora.
	 * 
	 * @param p Processor čiju metodu process želimo izvesti nad preostalim elementima kolekcije
	 * @throws NullPointerException ako je parametar <code>null</code>
	 */
	default void processRemaining(Processor p) {
		if(p == null)
			throw new NullPointerException("Argument je null");
		
		while(hasNextElement())
			p.process(getNextElement());
		return;
	}

	
}
