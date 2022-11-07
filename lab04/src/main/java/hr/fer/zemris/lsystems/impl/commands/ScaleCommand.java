package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Razred predstavlja komandu koja skalira trenutnu efektivnu duljinu pomaka s poslanim skalarom.
 * 
 * @author Ana Bagić
 *
 */
public class ScaleCommand implements Command {

	/**
	 * Skalar s kojim se skalira trenutna efektivna duljina pomaka.
	 */
	private double factor;
	
	/**
	 * Konstruktor postavlja skalar s kojim se želi skalirati efektivnu duljinu pomaka.
	 * 
	 * @param factor skalar s kojim se želi skalirati efektivnu duljinu pomaka
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}
	
	/**
	 * Skalira trenutnu efektivnu duljinu pomaka s poslanim skalarom.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		state.setStepLength(state.getStepLength() * factor);
	}

}
