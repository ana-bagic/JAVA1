package hr.fer.zemris.java.fractals;

import hr.fer.zemris.math.Complex;

/**
 * Razred za pomoćne metode.
 * 
 * @author Ana Bagić
 *
 */
public class Util {

	/**
	 * Parsira ulazni string u kompleksan broj.
	 * 
	 * @param s kompleksan broj u obliku stringa
	 * @return parsirani kompleksan broj
	 */
	public static Complex parseToComplex(String s) {
		if(s == null || s.equals(""))
			throw new IllegalArgumentException("Kompleksni broj nije zadan");
		if(s.contains("++") || s.contains("+-") || s.contains("-+") || s.contains("--"))
			throw new IllegalArgumentException("Dva operatora su zaredom");
		
		boolean firstIsPos = true, secondIsPos = true;
		if(s.startsWith("-"))
			firstIsPos = false;
		if(s.indexOf("-", 1) != -1)
			secondIsPos = false;
		
		String[] numbers = s.split(" ");
		String tmp = String.join("", numbers);
		if(tmp.startsWith("-") || tmp.startsWith("+"))
			numbers = tmp.substring(1).split("[+-]");
		else
			numbers = tmp.split("[+-]");
		
		double real = 0, imaginary = 0;
		switch (numbers.length) {
		case 1 -> {
			if(!numbers[0].startsWith("i"))
				real = parseDoubleOrThrow(numbers[0]);
			else
				imaginary = numbers[0].length() == 1 ? 1
						: parseDoubleOrThrow(numbers[0].substring(1));
			
			if(!firstIsPos) {
				real *= -1;
				imaginary *= -1;
			}
		}
		case 2 -> {
			real = parseDoubleOrThrow(numbers[0]);
			
			if(numbers[1].startsWith("i"))
				imaginary = numbers[1].length() == 1 ? 1
					: parseDoubleOrThrow(numbers[1].substring(1));
			else
				throw new IllegalArgumentException(numbers[1] + " nije ispravno zadan realni dio kompleksnog broja.");
			
			real = real*(firstIsPos ? 1 : -1);
			imaginary = imaginary*(secondIsPos ? 1 : -1);
		}
		default ->
			throw new IllegalArgumentException(s + " nije kompleksni broj.");
		}
		
		real = real == 0 ? 0 : real;
		imaginary = imaginary == 0 ? 0 : imaginary;
		
		return new Complex(real, imaginary);
	}
	
	/**
	 * Pomoćna metoda koja parsira zadani string u double ili baca iznimku {@link IllegalArgumentException} s odgovarajućom porukom
	 * 
	 * @param d string koji treba parsirati u double
	 * @return parsirani double
	 * @throws IllegalArgumentException ako se string ne može parsirati u double
	 */
	private static double parseDoubleOrThrow(String d) {
		double result;
		
		try {
			result = Double.parseDouble(d);
		} catch(NumberFormatException e) {
			throw new IllegalArgumentException(d + " nije realni broj.");
		}
		
		return result;
	}
}
