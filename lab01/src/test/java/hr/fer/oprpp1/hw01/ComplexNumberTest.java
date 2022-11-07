package hr.fer.oprpp1.hw01;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Razred koji testira metode i konstruktore razreda ComplexNumber
 * 
 * @author Ana Bagić
 *
 */
public class ComplexNumberTest {
	
	/**
	 * Metoda za zaokruživanje na 4 decimalna mjesta kako bi se mogli uspoređivati doubleovi.
	 * 
	 * @param value double koji želimo zaokružiti
	 * @return zaokruženi double
	 */
	private double round(double value) {
		return (double) Math.round(value * 10000d) / 10000d;
	}

	@Test
	public void testConstructorToStringPositivePositive() {
		assertEquals("2.0 + 4.7i", new ComplexNumber(2, 4.7).toString());
	}
	
	@Test
	public void testConstructorToStringPositiveNegative() {
		assertEquals("2.0 - 4.7i", new ComplexNumber(2, -4.7).toString());
	}
	
	@Test
	public void testConstructorToStringNegativePositive() {
		assertEquals("-2.0 + 4.7i", new ComplexNumber(-2, 4.7).toString());
	}
	
	@Test
	public void testConstructorToStringNegativeNegative() {
		assertEquals("-2.0 - 4.7i", new ComplexNumber(-2, -4.7).toString());
	}
	
	@Test
	public void testFromRealPositive() {
		assertEquals("7.23", ComplexNumber.fromReal(7.23).toString());
	}
	
	@Test
	public void testFromRealNegative() {
		assertEquals("-7.23", ComplexNumber.fromReal(-7.23).toString());
	}
	
	@Test
	public void testFromImaginaryPositive() {
		assertEquals("21.12i", ComplexNumber.fromImaginary(21.12).toString());
	}
	
	@Test
	public void testFromImaginaryNegative() {
		assertEquals("-21.12i", ComplexNumber.fromImaginary(-21.12).toString());
	}
	
	@Test
	public void testFromMagnitudeAndAngle1() {
		assertEquals(new ComplexNumber(-3.5355, 3.5355),
				ComplexNumber.fromMagnitudeAndAngle(5, 3*Math.PI/4));
	}
	
	@Test
	public void testFromMagnitudeAndAngle2() {
		assertEquals(new ComplexNumber(-0.7949, -1.1887),
				ComplexNumber.fromMagnitudeAndAngle(1.43, 4.123));
	}
	
	@Test
	public void testParseThrowsNull() {
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse(null));
	}
	
	@Test
	public void testParseThrowsDoubleOperators1() {
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("+2-+3.5i"));
	}
	
	@Test
	public void testParseThrowsDoubleOperators2() {
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("++2+3.5i"));
	}
	
	@Test
	public void testParseComplex() {
		assertEquals(new ComplexNumber(2, -5.7), ComplexNumber.parse("+2-5.7i"));
	}
	
	@Test
	public void testParseReal() {
		assertEquals(ComplexNumber.fromReal(-7.7), ComplexNumber.parse("-7.7"));
	}
	
	@Test
	public void testParseImg() {
		assertEquals(ComplexNumber.fromImaginary(5.7), ComplexNumber.parse("5.7i"));
	}
	
	@Test
	public void testGetReal() {
		ComplexNumber c1 = new ComplexNumber(-2, 1);
		
		assertEquals(-2.0, c1.getReal());
	}

	@Test
	public void testGetImaginary() {
		ComplexNumber c1 = new ComplexNumber(-2, 1);
		
		assertEquals(1.0, c1.getImaginary());
	}
	
	@Test
	public void testGetMagnitude() {
		ComplexNumber c1 = new ComplexNumber(-4, 3);
		
		assertEquals(5.0, c1.getMagnitude());
	}
	
	@Test
	public void testGetAngle() {
		ComplexNumber c1 = new ComplexNumber(-4, 3);
		
		assertEquals(2.4981, round(c1.getAngle()));
	}
	
	@Test
	public void testGetAngleI() {
		ComplexNumber c1 = new ComplexNumber(0, 1);
		
		assertEquals(1.5708, round(c1.getAngle()));
	}
	
	@Test
	public void testGetAngleMinusI() {
		ComplexNumber c1 = new ComplexNumber(0, -1);
		
		assertEquals(4.7124, round(c1.getAngle()));
	}
	
	@Test
	public void testGetAngleOne() {
		ComplexNumber c1 = new ComplexNumber(1, 0);
		
		assertEquals(0, round(c1.getAngle()));
	}
	
	@Test
	public void testGetAngleMinusOne() {
		ComplexNumber c1 = new ComplexNumber(-1, 0);
		
		assertEquals(3.1416, round(c1.getAngle()));
	}
	
	@Test
	public void testAddNull() {
		ComplexNumber c1 = new ComplexNumber(-5, 6.5);
		
		assertThrows(IllegalArgumentException.class, () -> c1.add(null));
	}
	
	@Test
	public void testAdd() {
		ComplexNumber c1 = new ComplexNumber(-5, 6.5);
		ComplexNumber c2 = new ComplexNumber(2.5, 3);
		
		assertEquals(new ComplexNumber(-2.5, 9.5), c1.add(c2));
	}
	
	@Test
	public void testSubNull() {
		ComplexNumber c1 = new ComplexNumber(-5, 6.5);
		
		assertThrows(IllegalArgumentException.class, () -> c1.sub(null));
	}
	
	@Test
	public void testSub() {
		ComplexNumber c1 = new ComplexNumber(-5, 6.5);
		ComplexNumber c2 = new ComplexNumber(2.5, 3);
		
		assertEquals(new ComplexNumber(-7.5, 3.5), c1.sub(c2));
	}
	
	@Test
	public void testMulNull() {
		ComplexNumber c1 = new ComplexNumber(-5, 6.5);
		
		assertThrows(IllegalArgumentException.class, () -> c1.mul(null));
	}
	
	@Test
	public void testMul() {
		ComplexNumber c1 = new ComplexNumber(-5, 6.5);
		ComplexNumber c2 = new ComplexNumber(2.5, 3);
		
		assertEquals(new ComplexNumber(-32, 1.25), c1.mul(c2));
	}
	
	@Test
	public void testDivNull() {
		ComplexNumber c1 = new ComplexNumber(-5, 6.5);
		
		assertThrows(IllegalArgumentException.class, () -> c1.div(null));
	}
	
	@Test
	public void testDivZero() {
		ComplexNumber c1 = new ComplexNumber(-5, 6.5);
		
		assertThrows(IllegalArgumentException.class, () -> c1.div(new ComplexNumber(0.0, 0.0)));
	}
	
	@Test
	public void testDiv() {
		ComplexNumber c1 = new ComplexNumber(-5, 6.5);
		ComplexNumber c2 = new ComplexNumber(2.5, 3);
		
		assertEquals(new ComplexNumber(0.459, 2.0492), c1.div(c2));
	}
	
	@Test
	public void testPowerThrowsIllegalArgument() {
		ComplexNumber c1 = new ComplexNumber(-5, 6.5);
		
		assertThrows(IllegalArgumentException.class, () -> c1.power(-2));
	}
	
	@Test
	public void testPowerOf0() {
		ComplexNumber c1 = new ComplexNumber(0, 0);
		
		assertEquals(new ComplexNumber(0, 0), c1.power(2));
	}
	
	@Test
	public void testPower2() {
		ComplexNumber c1 = new ComplexNumber(-5, 6.5);
		
		assertEquals(new ComplexNumber(-17.25, -65), c1.power(2));
	}
	
	@Test
	public void testPower5() {
		ComplexNumber c1 = new ComplexNumber(-5, 6.5);
		
		assertEquals(new ComplexNumber(5060.9375, -36740.8438), c1.power(5));
	}
	
	@Test
	public void testRootThrowsIllegalArgument() {
		ComplexNumber c1 = new ComplexNumber(-5, 6.5);
		
		assertThrows(IllegalArgumentException.class, () -> c1.root(-1));
	}
	
	@Test
	public void testRootOf0() {
		ComplexNumber c1 = new ComplexNumber(0, 0);
		ComplexNumber[] arr = {new ComplexNumber(0, 0),
				new ComplexNumber(0, 0)};
		
		assertArrayEquals(arr, c1.root(2));
	}
	
	@Test
	public void testRoot2() {
		ComplexNumber c1 = new ComplexNumber(-50, 60.5);
		ComplexNumber[] arr = {new ComplexNumber(3.7741, 8.0152),
				new ComplexNumber(-3.7741, -8.0152)};
		
		assertArrayEquals(arr, c1.root(2));
	}
	
	@Test
	public void testRoot5() {
		ComplexNumber c1 = new ComplexNumber(-50, 60.5);
		ComplexNumber[] arr = {new ComplexNumber(2.1525, 1.0458),
				new ComplexNumber(-0.3295, 2.3703),
				new ComplexNumber(-2.3561, 0.4191),
				new ComplexNumber(-1.1266, -2.1113),
				new ComplexNumber(1.6598, -1.7239)};
		
		assertArrayEquals(arr, c1.root(5));
	}
	
	@Test
	public void testToStringImaginary() {
		assertEquals("-2.0i", new ComplexNumber(0, -2).toString());
	}
	
	@Test
	public void testToStringReal() {
		assertEquals("3.71", new ComplexNumber(3.71, 0).toString());
	}
	
	@Test
	public void testToStringZero() {
		assertEquals("0.0", new ComplexNumber(0, 0).toString());
	}
}
