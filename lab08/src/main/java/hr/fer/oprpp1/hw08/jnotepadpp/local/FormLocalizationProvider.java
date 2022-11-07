package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Razred nasljeđuje {@link LocalizationProviderBridge} i ponaša se kao listener za dani {@link JFrame}.
 * 
 * @author Ana Bagić
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * Konstruktor stvara primjerak razreda na temelju danog providera i framea.
	 * 
	 * @param provider {@link ILocalizationProvider} za lokalizaciju
	 * @param frame {@link JFrame} za koji se želi dodati listener
	 */
	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
		super(provider);
		frame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
			
		});
	}

}
