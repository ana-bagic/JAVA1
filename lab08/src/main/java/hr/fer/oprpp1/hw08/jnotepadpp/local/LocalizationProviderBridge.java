package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * Razred predstavlja dekorator za neki drugi {@link ILocalizationProvider}.
 * Dohvat vrijednosti za dani ključ delegira dekoriranom {@link ILocalizationProvider}-u.
 * 
 * @author Ana Bagić
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {
	
	/**
	 * Je li veza spojena ili ne.
	 */
	private boolean connected;
	/**
	 * Dekorirani {@link ILocalizationProvider}.
	 */
	private ILocalizationProvider provider;
	/**
	 * Listener koji se može registrirati ili deregistrirati na dekoriranog providera.
	 */
	private ILocalizationListener listener;
	
	/**
	 * Konstruktor stvara primjerak razreda na temelju danog providera.
	 * 
	 * @param provider provider koji se želi dekorirati ovim razredom.
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.provider = provider;
		this.listener = () -> fire();
		this.connected = false;
	}
	
	/**
	 * Registrira listenera na dekoriranog providera.
	 */
	public void connect() {
		if(!connected) {
			connected = true;
			provider.addLocalizationListener(listener);
		}
	}
	
	/**
	 * Deregistrira listenera od dekoriranog providera.
	 */
	public void disconnect() {
		if(connected) {
			connected = false;
			provider.removeLocalizationListener(listener);
		}
	}

	@Override
	public String getString(String key) {
		return provider.getString(key);
	}

	
}
