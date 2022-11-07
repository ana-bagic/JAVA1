package hr.fer.oprpp1.hw01;

/**
 * Razred predstavlja nepromjenjivi kompleksni broj.
 * 
 * @author Ana Bagić
 *
 */
public class ComplexNumber {

	/**
	 * Realni dio kompleksnog broja.
	 */
	private double real;
	/**
	 * Imaginarni dio kompleksnog broja.
	 */
	private double imaginary;
	
	/**
	 * Konstruktor stvara novi kompleksni broj zadavanjem realnog i imaginarnog dijela.
	 * 
	 * @param real realni dio kompleksnog broja
	 * @param imaginary imaginarni dio kompleksnog broja
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	/**
	 * Stvara novi kompleksni broj iz realnog dijela.
	 * Imaginarni dio je postavljen na 0. Novi broj spada u podskup realnih brojeva.
	 * 
	 * @param real realni dio kompleksnog broja
	 * @return stvoreni kompleksni broj
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}
	
	/**
	 * Stvara novi kompleksni broj iz imaginarnog dijela.
	 * Realni dio je postavljen na 0.
	 * 
	 * @param imaginary imaginarni dio kompleksnog broja
	 * @return stvoreni kompleksni broj
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * Stvara novi kompleksni broj iz polarnih koordinata.
	 * 
	 * @param magnitude udaljenost od ishodišta - modul
	 * @param angle kut (u radijanima) u odnosu na apscisu - realnu os
	 * @return stvoreni kompleksni broj
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude*Math.cos(angle), magnitude*Math.sin(angle));
	}
	
	/**
	 * Stvara novi kompleksni broj iz stringa.
	 * 
	 * @param s string kompleksnog broja kojega želimo stvoriti
	 * @return stvoreni kompleksni broj
	 * @throws IllegalArgumentException ako je parametar null ili sadrži dva operatora zaredom
	 */
	public static ComplexNumber parse(String s) {
		if(s == null)
			throw new IllegalArgumentException("Parametar je null");
		if(s.contains("++") || s.contains("+-") || s.contains("-+") || s.contains("--"))
			throw new IllegalArgumentException("Dva operatora su zaredom");
		
		boolean firstIsPos = true, secondIsPos = true;
		if(s.startsWith("-"))
			firstIsPos = false;
		if(s.indexOf("-", 1) != -1)
			secondIsPos = false;
		
		String[] numbers;
		if(s.startsWith("-") || s.startsWith("+"))
			numbers = s.substring(1).split("[+-]");
		else
			numbers = s.split("[+-]");
		
		double real = 0, imaginary = 0;
		if(numbers.length == 1) {
			if(!numbers[0].contains("i"))
				real = Double.parseDouble(numbers[0]);
			else
				imaginary = Double.parseDouble(numbers[0].substring(0, numbers[0].length()-1));
			
			if(!firstIsPos) {
				real *= -1;
				imaginary *= -1;
			}
		} else {
			real = Double.parseDouble(numbers[0]);
			imaginary = Double.parseDouble(numbers[1].substring(0, numbers[1].length()-1));
			
			real = real*(firstIsPos ? 1 : -1);
			imaginary = imaginary*(secondIsPos ? 1 : -1);
		}
		
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Vraća realan dio kompleksnog broja.
	 * 
	 * @return realan dio kompleksnog broja
	 */
	public double getReal() {
		return real;
	}
	
	/**
	 * Vraća imaginaran dio kompleksnog broja.
	 * 
	 * @return imaginaran dio kompleksnog broja
	 */
	public double getImaginary() {
		return imaginary;
	}
	
	/**
	 * Vraća modul kompleksnog broja.
	 * 
	 * @return modul kompleksnog broja
	 */
	public double getMagnitude() {
		return Math.sqrt(real*real + imaginary*imaginary);
	}
	
	/**
	 * Vraća kut (u radijanima) kompleksnog broja u odnosu na realnu os.
	 * 
	 * @return kut (u radijanima) kompleksnog broja u odnosu na realnu os
	 */
	public double getAngle() {
		double arctg = Math.atan2(imaginary, real);
		
		if(arctg < 0)
			arctg = Math.PI*2 + arctg;
		return arctg;
	}
	
	/**
	 * Zbraja kompleksni broj nad koji pozivamo metodu s onim kojega šaljemo.
	 * 
	 * @param c kompleksni broj kojeg zbrajamo
	 * @return zbroj kompleksnih brojeva u obliku kompleksnog broja
	 * @throws IllegalArgumentException ako je poslani kompleksni broj null
	 */
	public ComplexNumber add(ComplexNumber c) {
		if(c == null)
			throw new IllegalArgumentException("Argument je null");
		
		return new ComplexNumber(this.real + c.real, this.imaginary + c.imaginary);
	}
	
	/**
	 * Od kompleksnog broja nad kojim pozivamo metodu oduzima onog kojega šaljemo.
	 * 
	 * @param c kompleksni broj kojeg oduzimamo
	 * @return razlika kompleksnih brojeva u obliku kompleksnog broja
	 * @throws IllegalArgumentException ako je poslani kompleksni broj null
	 */
	public ComplexNumber sub(ComplexNumber c) {
		if(c == null)
			throw new IllegalArgumentException("Argument je null");
		
		return new ComplexNumber(this.real - c.real, this.imaginary - c.imaginary);
	}
	
	/**
	 * Množi kompleksni broj nad kojim pozivamo metodu s onim kojega šaljemo.
	 * 
	 * @param c kompleksni broj kojeg množimo
	 * @return umnožak kompleksnih brojeva u obliku kompleksnog broja
	 * @throws IllegalArgumentException ako je poslani kompleksni broj null
	 */
	public ComplexNumber mul(ComplexNumber c) {
		if(c == null)
			throw new IllegalArgumentException("Argument je null");
		
		double real = this.real*c.real - this.imaginary*c.imaginary;
		double imaginary = this.real*c.imaginary + c.real*this.imaginary;
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Dijeli kompleksni broj nad kojim pozivamo metodu s onim kojega šaljemo.
	 * 
	 * @param c kompleksni broj s kojim dijelimo
	 * @return količnik kompleksnih brojeva u obliku kompleksnog broja
	 * @throws IllegalArgumentException ako je poslani kompleksni broj null ili 0.0
	 */
	public ComplexNumber div(ComplexNumber c) {
		if(c == null)
			throw new IllegalArgumentException("Argument je null");
		if(c.real == 0.0 && c.imaginary == 0.0)
			throw new IllegalArgumentException("Argument je 0");
		
		double realN = this.real*c.real + this.imaginary*c.imaginary;
		double denominator = c.real*c.real + c.imaginary*c.imaginary;
		double imaginaryN = this.imaginary*c.real - this.real*c.imaginary;
		
		return new ComplexNumber(realN/denominator, imaginaryN/denominator);
	}
	
	/**
	 * Potencira kompleksni broj na odabranu potenciju.
	 * 
	 * @param n potencija na koju potenciramo
	 * @return kompleksni broj na n-tu potenciju
	 * @throws IllegalArgumentException ako je parametar negativan broj
	 */
	public ComplexNumber power(int n) {
		if(n < 0)
			throw new IllegalArgumentException("Argument je negativan broj");
		
		return fromMagnitudeAndAngle(Math.pow(getMagnitude(), n), n*getAngle());
	}
	
	/**
	 * Vraća polje kompleksnih brojeva koji su rezultati n-tog korijena kompleksnog broja.
	 * 
	 * @param n korijen koji tražimo
	 * @return polje kompleksih brojeva na n-tu potenciju
	 * @throws IllegalArgumentException ako parametar nije pozitivan broj
	 */
	public ComplexNumber[] root(int n) {
		if(n <= 0)
			throw new IllegalArgumentException("Argument nije pozitivan broj");
		
		ComplexNumber[] numbers = new ComplexNumber[n];
		double rootMagnitude = Math.pow(getMagnitude(), 1.0/n);
		
		for(int i=0; i<n; i++)
			numbers[i] = fromMagnitudeAndAngle(rootMagnitude, (getAngle()+2*i*Math.PI)/n);
		
		return numbers;
	}
	
	@Override
	public String toString() {
		StringBuilder sb =  new StringBuilder();
		
		if(real != 0) {
			sb.append(real);
			if(imaginary != 0) {
				sb.append(imaginary > 0 ? " + " : " - ");
				sb.append(Math.abs(imaginary)).append("i");
			}
		} else {
			if(imaginary != 0)
				sb.append(imaginary).append("i");
			else
				sb.append("0.0");
		}
		
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ComplexNumber))
			return false;
		ComplexNumber other = (ComplexNumber) obj;
		
		if (round(real) != round(other.real))
			return false;
		if (round(imaginary) != round(other.imaginary))
			return false;
		return true;
	}
	
	/**
	 * Metoda za zaokruživanje na 4 decimalna mjesta kako bi se mogli uspoređivati doubleovi.
	 * 
	 * @param value double koji želimo zaokružiti
	 * @return zaokruženi double
	 */
	private double round(double value) {
		return (double) Math.round(value * 10000d) / 10000d;
	}
	
}
