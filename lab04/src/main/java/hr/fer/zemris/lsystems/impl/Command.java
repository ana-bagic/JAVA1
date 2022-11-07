package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Sučelje predstavlja naredbu koju kornjača može izvesti.
 * 
 * @author Ana Bagić
 *
 */
public interface Command {
	
	/**
	 * Metoda izvršava naredbu.
	 * 
	 * @param ctx trenutni kontekst kornjače
	 * @param painter crtač linija
	 */
	void execute(Context ctx, Painter painter);

}
