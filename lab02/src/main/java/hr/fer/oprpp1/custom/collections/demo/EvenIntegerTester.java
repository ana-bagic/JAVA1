package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.Tester;

/**
 * Provjerava je li objekt parni integer.
 * 
 * @author Ana Bagić
 * 
 */
public class EvenIntegerTester implements Tester {
	
	/**
	 * Metoda koja testira je li poslani objekt parni integer.
	 */
	@Override
	public boolean test(Object obj) {
		if(!(obj instanceof Integer))
			return false;
	
		Integer i = (Integer) obj;
		return i % 2 == 0;
	}

}
