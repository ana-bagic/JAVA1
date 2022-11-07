package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Singleton razred nasljeđuje apstraktan razred {@link AbstractLocalizationProvider}.
 * 
 * @author Ana Bagić
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	/**
	 * Paket koji sadrži prijevode za određenu lokalizaciju.
	 */
	private ResourceBundle bundle;
	/**
	 * Jedini primjerak ovoga razreda koji se koristi.
	 */
	private static final LocalizationProvider provider = new LocalizationProvider();
	
	/**
	 * Defaultni konstruktor stvara novi primjerak razreda sa jezikom postavljenim na engleski.
	 */
	private LocalizationProvider() {
		setLanguage("en");
	}
	
	/**
	 * Dohvaća jedini primjerak ovoga razreda - {@link LocalizationProvider}.
	 * 
	 * @return jedini primjerak ovoga razreda - {@link LocalizationProvider}
	 */
	public static LocalizationProvider getInstance() {
		return provider;
	}
	
	/**
	 * Postavlja jezik lokalizacije na zadani.
	 * 
	 * @param language jezik na koji se želi postaviti lokalizacija
	 */
	public void setLanguage(String language) {
		bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi", Locale.forLanguageTag(language));
	}
	
	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

}
