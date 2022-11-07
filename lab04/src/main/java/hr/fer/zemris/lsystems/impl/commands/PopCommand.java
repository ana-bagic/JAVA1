package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Razred predstavlja komandu koja briše jedno stanje s vrha stoga.
 * 
 * @author Ana Bagić
 *
 */
public class PopCommand implements Command {

	/**
	 * Briše jedno stanje s vrha stoga.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();
	}

}
