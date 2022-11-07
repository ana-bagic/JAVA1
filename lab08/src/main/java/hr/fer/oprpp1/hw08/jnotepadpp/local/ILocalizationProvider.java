package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * Objekti koji su primjerak razreda koji nasljeđuju ovo sučelje moći će vratiti prijevod za dani ključ
 * i obavijestiti sve registrirane listenere o promjeni.
 * 
 * @author Ana Bagić
 *
 */
public interface ILocalizationProvider {
	
	/**
	 * Dodaje listenera za promjene lokalizacije.
	 * 
	 * @param listener listener koji će se dodati
	 */
	public void addLocalizationListener(ILocalizationListener listener);
	
	/**
	 * Briše listenera za promjene lokalizacije.
	 * 
	 * @param listener listener koji će se izbrisati
	 */
	public void removeLocalizationListener(ILocalizationListener listener);
	
	/**
	 * Dohvaća vrijednost za dani ključ.
	 * 
	 * @param key ključ za koji se dohvaća vrijednost
	 * @return dohvaćena vrijednost
	 */
	public String getString(String key);

}
