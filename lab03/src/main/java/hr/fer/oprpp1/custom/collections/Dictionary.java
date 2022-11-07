package hr.fer.oprpp1.custom.collections;

/**
 * Razred predstavlja implementaciju mape - preslikavanje ključa na vrijednost.
 * 
 * @author Ana Bagić
 *
 * @param <K> ključ
 * @param <V> vrijednost
 */
public class Dictionary<K, V> {
	
	/**
	 * Razred predstavlja jedan zapis u mapi - jedan uređeni par.
	 * 
	 * @author Ana Bagić
	 *
	 * @param <K> ključ
	 * @param <V> vrijednost
	 */
	private static class Pair<K, V> {
		/**
		 * Ključ zapisa.
		 */
		private K key;
		/**
		 * Vrijednost zapisa.
		 */
		private V value;
		
		/**
		 * Konstruktor stvara novi zapis na temelju poslanog ključa i vrijednosti.
		 * Ključ ne smije biti <code>null</code>.
		 * 
		 * @param key ključ
		 * @param value vrijednost
		 * @throws NullPointerException ako je poslan ključ <code>null</code>
		 */
		public Pair(K key, V value) {
			if(key == null)
				throw new NullPointerException("Ključ ne smije biti null");
			
			this.key = key;
			this.value = value;
		}
	}
	
	/**
	 * Kolekcija u kojoj se pohranjuju parovi(ključ, vrijednost).
	 */
	private ArrayIndexedCollection<Pair<K, V>> map;

	/**
	 * Konstruktor stvara novu mapu.
	 */
	public Dictionary() {
		map = new ArrayIndexedCollection<>();
	}
	
	/**
	 * Vraća je li mapa prazna.
	 * 
	 * @return <code>true</code> ako je mapa prazna, inače <code>false</code>
	 */
	public boolean isEmpty() {
		return map.isEmpty();
	}
	
	/**
	 * Veličina mape, tj. broj elemenata u mapi.
	 * 
	 * @return broj parova(ključ, vrijednost) pohranjenih u mapi
	 */
	public int size() {
		return map.size();
	}
	
	/**
	 * Izbacuje sve elemente iz mape.
	 */
	public void clear() {
		map.clear();
	}
	
	/**
	 * Stvara novi zapis s parom(ključ, vrijednost) ako takav ključ u mapi još ne postoji,
	 * ili ažurira postojeći zapis s poslanim ključem na poslanu vrijednost, te vraća staru vrijednost.
	 * 
	 * @param key ključ čiju vrijednost želimo postaviti, odnosno ažurirati
	 * @param value vrijednost na koju želimo postaviti zapis s poslanim ključem
	 * @return stara vrijednost zapisa s poslanim ključem, odnosno <code>null</code> ako tog ključa još nema u mapi
	 */
	public V put(K key, V value) {
		int index = indexOf(key);
		
		if(index == -1) {
			map.add(new Pair<K, V>(key, value));
			return null;
		}
		
		V oldValue = map.get(index).value;
		map.get(index).value = value;
		return oldValue;
	}
	
	/**
	 * Vraća vrijednost unutar zapisa s poslanim ključem.
	 * 
	 * @param key ključ čiju vrijednost želimo dohvatiti
	 * @return vrijednost unutar zapisa s poslanim ključem ako je pronađen, inače <code>null</code>
	 */
	public V get(Object key) {		
		int index = indexOf(key);
		if(index != -1)
			return map.get(index).value;
		
		return null;
	}
	
	/**
	 * Izbacuje par s poslanim ključem iz mape.
	 * 
	 * @param key ključ para kojeg želimo izbaciti iz mape
	 * @return <code>true</code> ako je par s poslanim ključem uspješno pronađen i izbačen iz mape, inače <code>false</code>
	 */
	public V remove(K key) {
		int index = indexOf(key);
		if(index != -1) {
			V oldValue = map.get(index).value;
			map.remove(index);
			return oldValue;
		}
		
		return null;
	}
	
	/**
	 * Vraća index zapisa u mapi s poslanim ključem, odnosno -1 ako on ne postoji.
	 * 
	 * @param key ključ čiji zapis tražimo u mapi
	 * @return index zapisa u mapi s poslanim ključem, odnosno -1 ako on ne postoji.
	 */
	private int indexOf(Object key) {
		if(key == null)
			return -1;
		
		for(int i=0; i<size(); i++) {
			if(map.get(i).key.equals(key))
				return i;
		}
			
		return -1;
	}
}
