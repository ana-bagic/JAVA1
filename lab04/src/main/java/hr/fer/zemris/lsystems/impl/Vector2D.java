package hr.fer.zemris.lsystems.impl;

/**
 * Razred modelira 2D vektor s realnim komponentama x i y.
 * 
 * @author Ana Bagić
 *
 */
public class Vector2D {

	/**
	 * Realna komponenta vektora - x.
	 */
	private double x;
	/**
	 * Realna komponenta vektora - y.
	 */
	private double y;
	
	/**
	 * Konstruktor stvara novi vektor s realnim komponentama x i y.
	 * 
	 * @param x realna komponenta x
	 * @param y realna komponenta y
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Vraća realnu komponentu vektora - x
	 * 
	 * @return realnu komponentu vektora - x
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Vraća realnu komponentu vektora - y
	 * 
	 * @return realnu komponentu vektora - y
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Dodaje trenutnom vektoru poslani vektor.
	 * 
	 * @param offset vektor koji želimo dodati trenutnom vektoru
	 */
	public void add(Vector2D offset) {
		this.x += offset.x;
		this.y += offset.y;
	}
	
	/**
	 * Zbraja trenutni i poslani vektor te vraća rezultat. Ne modificira trenutni vektor.
	 * 
	 * @param offset vektor koji želimo zbrojiti s trenutnim vektorom
	 * @return zbroj trenutnog i poslanog vektora
	 */
	public Vector2D added(Vector2D offset) {
		return new Vector2D(this.x + offset.x, this.y + offset.y);
	}
	
	/**
	 * Rotira trenutni vektor za poslanu vrijednost.
	 * 
	 * @param angle stupanj za koji želimo zarotirati trenutni vektor
	 */
	public void rotate(double angle) {
		double xOld = x, yOld = y;
		
		x = Math.cos(angle)*xOld - Math.sin(angle)*yOld;
		y = Math.sin(angle)*xOld + Math.cos(angle)*yOld;
	}
	
	/**
	 * Stvara i vraća novi vektor koji je jednak trenutnom vektoru zarotiranom za poslanu vrijednost.
	 * Ne modificira trenutni vektor.
	 * 
	 * @param angle stupanj za koji želimo da novi vektor bude zarotiran u odnosu na trenutni
	 * @return novi vektor jednak trenutnom zarotiranom za poslanu vrijednost
	 */
	public Vector2D rotated(double angle) {
		return new Vector2D(Math.cos(angle)*x - Math.sin(angle)*y, Math.sin(angle)*x + Math.cos(angle)*y);
	}
	
	/**
	 * Skalira trenutni vektor s poslanim skalarom.
	 * 
	 * @param scaler skalar s kojim želimo skalirati vektor
	 */
	public void scale(double scaler) {
		x *= scaler;
		y *= scaler;
	}
	
	/**
	 * Stvara i vraća novi vektor koji je rezultat skaliranja trenutnog vektora s poslanim skalarom.
	 * Ne modificira trenutni vektor.
	 * 
	 * @param scaler skalar s kojim želimo skalirati vektor
	 * @return rezultat skaliranja trenutnog vektora s poslanim skalarom
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(x*scaler, y*scaler);
	}
	
	/**
	 * Stvara i vraća novu kopiju trenutnog vektora.
	 * 
	 * @return nova kopija trenutnog vektora
	 */
	public Vector2D copy() {
		return new Vector2D(x, y);
	}
}
