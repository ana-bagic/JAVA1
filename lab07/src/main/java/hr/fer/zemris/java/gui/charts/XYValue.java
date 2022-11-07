package hr.fer.zemris.java.gui.charts;

import java.util.Objects;

/**
 * Razred predstavlja vrijednosti grafa.
 * 
 * @author Ana Bagić
 *
 */
public class XYValue {

	/**
	 * Broj na x osi grafa.
	 */
	private int x;
	/**
	 * Broj na y osi grafa.
	 */
	private int y;
	
	/**
	 * Konstruktor stvara novu vrijednost na temelju x i y vrijednosti.
	 * 
	 * @param x vrijednost na x osi grafa
	 * @param y vrijednost na y osi grafa
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Metoda tvornica parsira poslani string i na temelju njega stvara novi objekt XYValue.
	 * Format parametra je u obliku "x,y".
	 * 
	 * @param text željene koordinate
	 * @return novi objekt XYValue na temelju poslanog stringa
	 */
	public static XYValue parse(String text) {
		Objects.requireNonNull(text, "Argument je null.");
		
		String[] numbers = text.trim().split(",");
		if(numbers.length != 2)
			throw new IllegalArgumentException("Koordinate su neispravno zadane.");
		
		int x = Integer.parseInt(numbers[0]);
		int y = Integer.parseInt(numbers[1]);
		
		return new XYValue(x, y);
	}
	
	/**
	 * @return vrijednost na x osi grafa
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * @return vrijednost na y osi grafa
	 */
	public int getY() {
		return y;
	}
	
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
	
}
