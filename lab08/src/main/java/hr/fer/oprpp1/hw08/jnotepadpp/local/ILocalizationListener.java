package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * Sučelje koje predstavlja listener događaja promjene lokalizacije. 
 * 
 * @author Ana Bagić
 *
 */
public interface ILocalizationListener {
	
	/**
	 * Obavještava da je lokalizacija promijenjena.
	 */
	public void localizationChanged();

}
