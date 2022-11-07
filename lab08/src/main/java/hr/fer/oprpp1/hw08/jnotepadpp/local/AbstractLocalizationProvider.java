package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Apstraktan razred implementira sučelje {@link ILocalizationProvider} i dodaje mogućnost
 * registracije, deregistracije i obavještavanja svih listenera.
 * 
 * @author Ana Bagić
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	/**
	 * Lista svih registriranih listenera.
	 */
	List<ILocalizationListener> listeners;
	
	/**
	 * Defaultni konstruktor stvara novi primjerak ovog razreda.
	 */
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<>();
	}
	
	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Obavještava sve registrirane listenere o promjeni kod lokalizacije.
	 */
	public void fire() {
		for(ILocalizationListener listener : listeners)
			listener.localizationChanged();
	}

	
}
