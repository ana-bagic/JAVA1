package hr.fer.oprpp1.custom.collections;

/**
 * Sučelje nasljeđuje Collection i predstavlja kolekciju u obliku liste.
 * 
 * @author Ana Bagić
 *
 */
public interface List extends Collection {

	/**
	 * Vraća objekt koji se nalazi na poziciji index.
	 * 
	 * @param index index kolekcije s kojega se želi dohvatiti element
	 * @return objekt na poziciji index
	 */
	Object get(int index);
	
	/**
	 * Dodaje novi objekt na poziciju position. Sve objekte nakon njega pomiče za jedno mjesto prema kraju.
	 * 
	 * @param value objekt koji želimo dodati u kolekciju
	 * @param position pozicija na koju želimo dodati novi objekt
	 */
	void insert(Object value, int position);
	
	/**
	 * Vraća index prve pojave poslanog objekta ili -1 u slučaju da nije nađen.
	 * 
	 * @param value objekt koji tražimo u kolekciji
	 * @return index prve pojave objekta, odnosno -1 ako nije nađen
	 */
	int indexOf(Object value);
	
	/**
	 * Izbacuje objekt s pozicije index. Sve objekte nakon njega pomiče za jedno mjesto prema početku.
	 * 
	 * @param index pozicija s koje želimo maknuti objekt
	 */
	void remove(int index);

}
