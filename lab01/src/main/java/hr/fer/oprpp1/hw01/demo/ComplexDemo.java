package hr.fer.oprpp1.hw01.demo;

import hr.fer.oprpp1.hw01.ComplexNumber;

/**
 * Testiranje implementacije razreda ComplexNumber.
 * 
 * @author Ana Bagić
 *
 */
public class ComplexDemo {

	/**
	 * Testiranje metoda i konstruktora razreda ComplexNumber
	 * 
	 * @param args ulazni argumenti (ne koriste se tu)
	 */
	public static void main(String[] args) {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
		
		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57))
				.div(c2).power(3).root(2)[1];
		
		System.out.println(c3);
	}
}
