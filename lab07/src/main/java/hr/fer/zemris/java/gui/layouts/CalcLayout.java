package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Function;

/**
 * Razred predstavlja custom LayoutManager za modeliranje korisničkog sučelja kalkulatora.
 * 
 * @author Ana Bagić
 *
 */
public class CalcLayout implements LayoutManager2 {
	
	/**
	 * Razmak između redaka i stupaca.
	 */
	private int distance;
	/**
	 * Komponente i njihove pozicije.
	 */
	private Map<RCPosition, Component> components;
	
	/**
	 * Defaultni kostruktor postavlja razmak između redaka i stupaca na 0;
	 */
	public CalcLayout() {
		this(0);
	}

	/**
	 * Konstruktor stvara novi razmještaj na temelju danog razmaka između redaka i stupaca.
	 * 
	 * @param distance razmak između redaka i stupaca
	 */
	public CalcLayout(int distance) {
		if(distance < 0)
			throw new IllegalArgumentException("Razmak mora biti pozitivan broj ili 0.");
		
		this.distance = distance;
		components = new HashMap<>();
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException("Operacija nije podržana.");
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		Objects.requireNonNull(comp, "Komponenta ne smije biti null.");
		Objects.requireNonNull(constraints, "Ograničenje ne smije biti null.");
		
		RCPosition position;
		if(constraints instanceof RCPosition) {
			position = (RCPosition) constraints;
		} else if(constraints instanceof String) {
			try {
				position = RCPosition.parse((String) constraints);
			} catch(Exception e) {
				throw new IllegalArgumentException(e.getMessage());
			}
		}
		else {
			throw new IllegalArgumentException("Ograničenje mora biti tipa String ili RCPosition.");
		}
		
		if(position.getRow() < 1 || position.getRow() > 5)
			throw new CalcLayoutException("Broj retka mora biti iz skupa 1,...,5");
		if(position.getColumn() < 1 || position.getColumn() > 7)
			throw new CalcLayoutException("Broj stupca mora biti iz skupa 1,...,7");
		if(position.getRow() == 1 && position.getColumn() > 1 && position.getColumn() < 6)
			throw new CalcLayoutException("U prvom retku su legalni brojevi stupca 1, 6 i 7.");
		
		if(components.containsKey(position))
			throw new CalcLayoutException("Komponenta " + comp + " već postoji u razmještaju.");
		if(components.containsValue(comp))
			throw new CalcLayoutException("Na poziciji " + position + " već postoji komponenta.");
		
		components.put(position, comp);
	}
	
	@Override
	public void removeLayoutComponent(Component comp) {
		components.values().remove(comp);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Objects.requireNonNull(parent, "Spremnik ne smije biti null.");
		return calculateSize(parent, (c) -> c.getPreferredSize());
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		Objects.requireNonNull(parent, "Spremnik ne smije biti null.");
		return calculateSize(parent, (c) -> c.getMinimumSize());
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		Objects.requireNonNull(target, "Spremnik ne smije biti null.");
		return calculateSize(target, (c) -> c.getMaximumSize());
	}
	
	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		double parentWidth = parent.getSize().getWidth() - insets.left - insets.right - 6*distance;
		double parentHeight = parent.getSize().getHeight() - insets.top - insets.bottom - 4*distance;
		
		int columnWidth = (int) (parentWidth/7);
		int nrOfBigW = (int) (parentWidth)%7;
		int rowHeight = (int) (parentHeight/5);
		int nrOfBigH = (int) (parentHeight)%5;
		
		int yPos = insets.top;
		
		for(int i = 1; i <= 5; i++) {
			int xPos = insets.left;
			
			int height = rowHeight + switch (i) {
			case 1,5 -> nrOfBigH > 2 ? 1 : 0;
			case 2,4 -> nrOfBigH%2 == 0 ? (nrOfBigH == 0 ? 0 : 1) : 0;
			default -> nrOfBigH%2 == 1 ? 1 : 0;
			};
			
			for(int j = 1; j <= 7; j++) {
				int width = calcWidth(columnWidth, nrOfBigW, j, j);
				
				Component c = components.get(new RCPosition(i, j));
				if(c != null && c.isVisible()) {
					c.setBounds(xPos, yPos, width + (i == 1 && j == 1 ?
							distance*4 + calcWidth(columnWidth, nrOfBigW, 2, 5) : 0), height);
				}
				
				xPos += width + distance;
			}
			
			yPos += height + distance;
		}
	}

	@Override
	public void invalidateLayout(Container target) {
		//components.clear();
	}
	
	private int calcWidth(int baseWidth, int nrOfBigW, int indexFrom, int indexTo) {
		int extra = 0;
		for (int i = indexFrom; i <= indexTo; i++)
			extra += baseWidth + switch (i) {
		case 1,7 -> nrOfBigW > 3 ? 1 : 0;
		case 2,6 -> (nrOfBigW > 2 && nrOfBigW != 4) ? 1 : 0;
		case 3,4 -> nrOfBigW%2 == 0 ? (nrOfBigW == 0 ? 0 : 1) : 0;
		default -> nrOfBigW%2 == 1 ? 1 : 0;
		};
		
		return extra;
	}

	/**
	 * Pomoćna metoda za izračun minimalne, maksimalne i preferirane veličine razmještaja.
	 * 
	 * @param container spremnik razmještaja i komponenata
	 * @param func funkcija koja se izvodi ovisno o tome traži li se minimalna, maksimalna ili preferirana veličina
	 * @return minimalne/maksimalne/preferirane dimenzije ovog razmještaja
	 */
	private Dimension calculateSize(Container container, Function<Component, Dimension> func) {
		int width = 0;
		int height = 0;
		
		for(Entry<RCPosition, Component> e : components.entrySet()) {
			Dimension dim = func.apply(e.getValue());
			if(dim != null && e.getValue().isVisible()) {
				int compWidth = (int) dim.getWidth();
				if(e.getKey().getRow() == 1 && e.getKey().getColumn() == 1) {
					double firstCompWidth = (double)(compWidth - 4*distance) / 5;
					compWidth = (int) (Math.ceil(firstCompWidth));
				}
				
				if(compWidth > width)
					width = compWidth;
				if(dim.getHeight() > height)
					height = (int) dim.getHeight();
			}
		}
		
		Insets insets = container.getInsets();
		width = width*7 + distance*6 + insets.left + insets.right;
		height = height*5 + distance*4 + insets.top + insets.bottom;
		
		return new Dimension(width, height);
	}
}
