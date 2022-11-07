package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Razred predstavlja komandu koja stanje s vrha stoga kopira i kopiju stavlja na stog.
 * 
 * @author Ana Bagić
 *
 */
public class PushCommand implements Command {
	
	/**
	 * Kopira stanje s vrha stoga i kopiju stavlja na stog.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.pushState(ctx.getCurrentState().copy());
	}

}
