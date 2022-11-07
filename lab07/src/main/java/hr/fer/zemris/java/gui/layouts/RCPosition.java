package hr.fer.zemris.java.gui.layouts;

import java.util.Objects;

/**
 * Razred predstavlja poziciju unutar neke tablice zadavanjem broja retka i broja stupca.
 * 
 * @author Ana Bagić
 *
 */
public class RCPosition {
	
	/**
	 * Broj retka.
	 */
	private int row;
	/**
	 * Broj stupca
	 */
	private int column;
	
	/**
	 * Konstruktor stvara poziciju zadavanje retka i stupca.
	 * 
	 * @param row broj retka
	 * @param column broj stupca
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Metoda tvornica parsira poslani string i na temelju njega stvara novi objekt RCPosition.
	 * Format parametra je u obliku "brojRetka,brojStupca".
	 * 
	 * @param text željena pozicija
	 * @return novi objekt RCPosition na temelju poslanog stringa
	 */
	public static RCPosition parse(String text) {
		Objects.requireNonNull(text, "Argument je null.");
		
		String[] numbers = text.trim().split(",");
		if(numbers.length != 2)
			throw new IllegalArgumentException("Pozicija je neispravno zadana.");
		
		int row = Integer.parseInt(numbers[0]);
		int column = Integer.parseInt(numbers[1]);
		
		return new RCPosition(row, column);
	}

	/**
	 * @return broj retka
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * @return broj stupca
	 */
	public int getColumn() {
		return column;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof RCPosition))
			return false;
		RCPosition other = (RCPosition) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "(" + row + "," + column + ")";
	}
	
}
