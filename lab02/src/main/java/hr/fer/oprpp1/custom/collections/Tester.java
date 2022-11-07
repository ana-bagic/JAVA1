package hr.fer.oprpp1.custom.collections;

/**
 * Sučelje modelira objekt koji primi neki objekt, te ispita je li on prihvatljiv ili ne.
 * 
 * @author Ana Bagić
 *
 */
public interface Tester {

	/**
	 * Metoda koja testira poslani objekt, tj. je li prihvaćen.
	 * 
	 * @param obj objekt koj testiramo
	 * @return <code>true</code> ako je objekt prihvaćen, inače <code>false</code>
	 */
	boolean test(Object obj);
}
