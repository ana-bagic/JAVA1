package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Razred modelira stupčasti dijagram.
 * 
 * @author Ana Bagić
 *
 */
public class BarChart {
	
	/**
	 * Podatci koji se prikazuju na dijagramu.
	 */
	private List<XYValue> values;
	/**
	 * Opis uz x-os.
	 */
	private String xAxisDesc;
	/**
	 * Opis uz y-os.
	 */
	private String yAxisDesc;
	/**
	 * Minimalna vrijednost y koja se prikazuje na osi.
	 */
	private int minY;
	/**
	 * Maksimalna vrijednost y koja se prikazuje na osi.
	 */
	private int maxY;
	/**
	 * Udaljenost između dva susjedna y na osi.
	 */
	private int yDistance;
	
	/**
	 * Konstruktor stvara novi stupčasti dijagram na temelju danih parametara.
	 * 
	 * @param values podatci - vrijednosti za prikazati na dijagramu
	 * @param xAxisDesc opis uz x-os
	 * @param yAxisDesc opis uz y-os
	 * @param minY minimalna vrijednost y koja se prikazuje na osi
	 * @param maxY maksimalna vrijednost y koja se prikazuje na osi
	 * @param yDistance udaljenost između dva susjedna y na osi
	 */
	public BarChart(List<XYValue> values, String xAxisDesc, String yAxisDesc, int minY, int maxY, int yDistance) {
		if(minY < 0)
			throw new IllegalArgumentException("Minimalna vrijednost y ne smije biti negativan broj.");
		if(maxY <= minY)
			throw new IllegalArgumentException("Maksimalna vrijdnost y mora biti strogo veća od minimalne vrijednosti.");
		for(XYValue v : values) {
			if(v.getY() < minY)
				throw new IllegalArgumentException("Vrijednost y u " + v + " je manja od minimalne.");
		}
		
		this.values = values;
		this.xAxisDesc = xAxisDesc;
		this.yAxisDesc = yAxisDesc;
		this.minY = minY;
		this.yDistance = yDistance;
		this.maxY = maxY + ((maxY-minY) % yDistance == 0 ? 0 : (yDistance - (maxY-minY) % yDistance));
	}

	/**
	 * @return podatci koji se prikazuju na dijagramu
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * @return opis uz x-os
	 */
	public String getxAxisDesc() {
		return xAxisDesc;
	}

	/**
	 * @return opis uz y-os
	 */
	public String getyAxisDesc() {
		return yAxisDesc;
	}

	/**
	 * @return minimalna vrijednost y koja se prikazuje na osi
	 */
	public int getMinY() {
		return minY;
	}

	/**
	 * @return maksimalna vrijednost y koja se prikazuje na osi
	 */
	public int getMaxY() {
		return maxY;
	}

	/**
	 * @return udaljenost između dva susjedna y na osi
	 */
	public int getyDistance() {
		return yDistance;
	}

}
