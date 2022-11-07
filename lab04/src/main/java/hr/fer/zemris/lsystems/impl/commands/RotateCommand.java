package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Razred predstavlja komandu koja rotira vektor smjera gledanja za zadani kut.
 * 
 * @author Ana Bagić
 *
 */
public class RotateCommand implements Command {
	
	/**
	 * Kut za koji se želi zarotirati trenutni vektor smjera.
	 */
	private double angle;

	/**
	 * Konstruktor postavlja kut za koji se želi zarotirati trenutni vektor smjera.
	 * 
	 * @param angle kut za koji se želi zarotirati trenutni vektor smjera
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}
	
	/**
	 * Rotira vektor smjera gledanja kornjače.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		double inRadians = angle * (Math.PI/180);
		
		ctx.getCurrentState().getDirection().rotate(inRadians);
	}

}
