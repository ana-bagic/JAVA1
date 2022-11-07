package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.lsystems.impl.Vector2D;

/**
 * Razred predstavlja komandu koja crta liniju od trenutne pozicije kornjače do iduće izračunate, te ažurira novu poziciju.
 * 
 * @author Ana Bagić
 *
 */
public class DrawCommand implements Command {

	/**
	 * Koliko se u odnosu na jedinični korak želimo pomaknuti.
	 */
	private double step;
	
	/**
	 * Konstuktor postavlja koliko se u odnosu na jedinični korak želimo pomaknuti.
	 * 
	 * @param step koliko se u odnosu na jedinični korak želimo pomaknuti
	 */
	public DrawCommand(double step) {
		this.step = step;
	}
	
	/**
	 * Povlači liniju od trenutne pozicije kornjače do iduće izračunate, te ažurira novu poziciju.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState oldSt = ctx.getCurrentState();
		Vector2D oldPos = oldSt.getPosition();
		Vector2D oldDir = oldSt.getDirection();
		
		Vector2D newPos = oldPos.added(oldDir.scaled(step * oldSt.getStepLength()));
		
		painter.drawLine(oldPos.getX(), oldPos.getY(),
				newPos.getX(), newPos.getY(), oldSt.getDrawColor(), 1f);
		
		oldSt.setPosition(newPos);
	}

}
