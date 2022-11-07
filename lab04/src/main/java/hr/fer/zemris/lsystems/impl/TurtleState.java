package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

/**
 * Razred modelira trenutno stanje kornjače koja se koristi za iscrtavanje fraktala.
 * 
 * @author Ana Bagić
 *
 */
public class TurtleState {
	
	/**
	 * Trenutna pozicija na kojoj se kornjača nalazi.
	 */
	private Vector2D position;
	/**
	 * Smjer u kojem kornjača gleda.
	 */
	private Vector2D direction;
	/**
	 * Boja kojom kornjača crta.
	 */
	private Color drawColor;
	/**
	 * Trenutna efektivna duljina pomaka kornjače.
	 */
	private double stepLength;
	
	/**
	 * Konstuktor stvara novi primjerak razreda na temelju danih parametara.
	 * 
	 * @param position pozicija na kojoj se kornjača nalazi
	 * @param direction smjer u kojem kornjača gleda
	 * @param drawColor boja kojom kornjača crta
	 * @param stepLength efektivna duljina pomaka kornjače
	 */
	public TurtleState(Vector2D position, Vector2D direction, Color drawColor, double stepLength) {
		this.position = position;
		this.direction = direction;
		this.drawColor = drawColor;
		this.stepLength = stepLength;
	}

	/**
	 * Stvara i vraća novi primjerak razreda TurtleState koji je kopija trenutnog.
	 * 
	 * @return novi primjerak razreda TurtleState koji je kopija trenutnog
	 */
	public TurtleState copy() {
		return new TurtleState(position.copy(), direction.copy(), drawColor, stepLength);
	}

	/**
	 * @return pozicija na kojoj se kornjača nalazi
	 */
	public Vector2D getPosition() {
		return position;
	}

	/**
	 * @param position pozicija na kojoj se kornjača nalazi
	 */
	public void setPosition(Vector2D position) {
		this.position = position;
	}

	/**
	 * @return smjer u kojem kornjača gleda
	 */
	public Vector2D getDirection() {
		return direction;
	}

	/**
	 * @param direction smjer u kojem kornjača gleda
	 */
	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}

	/**
	 * @return boja kojom kornjača crta
	 */
	public Color getDrawColor() {
		return drawColor;
	}

	/**
	 * @param drawColor boja kojom kornjača crta
	 */
	public void setDrawColor(Color drawColor) {
		this.drawColor = drawColor;
	}

	/**
	 * @return efektivna duljina pomaka kornjače
	 */
	public double getStepLength() {
		return stepLength;
	}

	/**
	 * @param stepLength efektivna duljina pomaka kornjače
	 */
	public void setStepLength(double stepLength) {
		this.stepLength = stepLength;
	}
	
}
