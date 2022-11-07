package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Razred modelira listu primarnih brojeva.
 * 
 * @author Ana Bagić
 *
 */
public class PrimListModel implements ListModel<Integer> {
	
	/**
	 * Elementi liste.
	 */
	private List<Integer> elements = new ArrayList<>();
	/**
	 * Promatrači iste.
	 */
	private List<ListDataListener> listeners = new ArrayList<>();
	/**
	 * Idući broj koji treba testirati.
	 */
	private int nextPrim = 1;
	
	/**
	 * Konstruktor stvara novu listu primarnih brojeva i u nju dodaje prvi primarni broj.
	 */
	public PrimListModel() {
		elements.add(nextPrim);
	}
	
	@Override
	public int getSize() {
		return elements.size();
	}
	@Override
	public Integer getElementAt(int index) {
		return elements.get(index);
	}
	
	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}
	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}

	/**
	 * Metoda traži idući primarni broj, dodaje ga u listu i o tome obavještava sve zainteresirane promatrače.
	 */
	public void next() {
		while(true) {
			nextPrim++;
			if(isPrime())
				break;
		}
		elements.add(nextPrim);
		
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, elements.size(), elements.size());
		for(ListDataListener l : listeners)
			l.intervalAdded(event);
	}

	/**
	 * Pomoćna funckija koja ispituje je li broj primaran.
	 * 
	 * @return <code>true</code> ako je broj primaran, <code>false</code> inače
	 */
	private boolean isPrime() {
	    for (int i = 2; i <= nextPrim / 2; i++) {
	    	if (nextPrim % i == 0)
	          return false;
	    }
	    
	    return true;
	}

}
