package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Razred predstavlja komandu koja u trenutno stanje kornjače zapisuje predanu boju.
 * 
 * @author Ana Bagić
 *
 */
public class ColorCommand implements Command {

	/**
	 * Boja na koju se postavlja trenutna.
	 */
	private Color color;
	
	/**
	 * Konstruktor postavlja boju kretanja kornjače na koju se želi postaviti trenutna.
	 * 
	 * @param color boja kretanja kornjače na koju se želi postaviti trenutna
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}
	
	/**
	 * U trenutno stanje kornjače zapisuje predanu boju.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setDrawColor(color);
	}

}
