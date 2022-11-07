package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Razred koji testira metode i konstruktore razreda Complex.
 * 
 * @author Ana Bagić
 *
 */
public class ComplexTest {
	

	@Test
	public void testConstructorToStringPositivePositive() {
		assertEquals("2.0+i4.7", new Complex(2, 4.7).toString());
	}
	
	@Test
	public void testConstructorToStringPositiveNegative() {
		assertEquals("2.0-i4.7", new Complex(2, -4.7).toString());
	}
	
	@Test
	public void testConstructorToStringNegativePositive() {
		assertEquals("-2.0+i4.7", new Complex(-2, 4.7).toString());
	}
	
	@Test
	public void testConstructorToStringNegativeNegative() {
		assertEquals("-2.0-i4.7", new Complex(-2, -4.7).toString());
	}
	
	@Test
	public void testGetMagnitude() {
		Complex c1 = new Complex(-4, 3);
		
		assertEquals(5.0, c1.module());
	}
	
	@Test
	public void testAddNull() {
		Complex c1 = new Complex(-5, 6.5);
		
		assertThrows(NullPointerException.class, () -> c1.add(null));
	}
	
	@Test
	public void testAdd() {
		Complex c1 = new Complex(-5, 6.5);
		Complex c2 = new Complex(2.5, 3);
		
		assertEquals(new Complex(-2.5, 9.5), c1.add(c2));
	}
	
	@Test
	public void testSubNull() {
		Complex c1 = new Complex(-5, 6.5);
		
		assertThrows(NullPointerException.class, () -> c1.sub(null));
	}
	
	@Test
	public void testSub() {
		Complex c1 = new Complex(-5, 6.5);
		Complex c2 = new Complex(2.5, 3);
		
		assertEquals(new Complex(-7.5, 3.5), c1.sub(c2));
	}
	
	@Test
	public void testMulNull() {
		Complex c1 = new Complex(-5, 6.5);
		
		assertThrows(NullPointerException.class, () -> c1.multiply(null));
	}
	
	@Test
	public void testMul() {
		Complex c1 = new Complex(-5, 6.5);
		Complex c2 = new Complex(2.5, 3);
		
		assertEquals(new Complex(-32, 1.25), c1.multiply(c2));
	}
	
	@Test
	public void testDivNull() {
		Complex c1 = new Complex(-5, 6.5);
		
		assertThrows(NullPointerException.class, () -> c1.divide(null));
	}
	
	@Test
	public void testDivZero() {
		Complex c1 = new Complex(-5, 6.5);
		
		assertThrows(IllegalArgumentException.class, () -> c1.divide(new Complex(0.0, 0.0)));
	}
	
	@Test
	public void testDiv() {
		Complex c1 = new Complex(-5, 6.5);
		Complex c2 = new Complex(2.5, 3);
		
		assertEquals(new Complex(0.459, 2.0492), c1.divide(c2));
	}
	
	@Test
	public void testPowerThrowsIllegalArgument() {
		Complex c1 = new Complex(-5, 6.5);
		
		assertThrows(IllegalArgumentException.class, () -> c1.power(-2));
	}
	
	@Test
	public void testPowerOf0() {
		Complex c1 = new Complex(0, 0);
		
		assertEquals(new Complex(0, 0), c1.power(2));
	}
	
	@Test
	public void testPower2() {
		Complex c1 = new Complex(-5, 6.5);
		
		assertEquals(new Complex(-17.25, -65), c1.power(2));
	}
	
	@Test
	public void testPower5() {
		Complex c1 = new Complex(-5, 6.5);
		
		assertEquals(new Complex(5060.9375, -36740.8438), c1.power(5));
	}
	
	@Test
	public void testRootThrowsIllegalArgument() {
		Complex c1 = new Complex(-5, 6.5);
		
		assertThrows(IllegalArgumentException.class, () -> c1.root(-1));
	}
	
	@Test
	public void testRootOf0() {
		Complex c1 = new Complex(0, 0);
		Complex[] arr = {new Complex(0, 0),
				new Complex(0, 0)};
		
		assertArrayEquals(arr, c1.root(2).toArray());
	}
	
	@Test
	public void testRoot2() {
		Complex c1 = new Complex(-50, 60.5);
		Complex[] arr = {new Complex(3.7741, 8.0152),
				new Complex(-3.7741, -8.0152)};
		
		assertArrayEquals(arr, c1.root(2).toArray());
	}
	
	@Test
	public void testRoot5() {
		Complex c1 = new Complex(-50, 60.5);
		Complex[] arr = {new Complex(2.1525, 1.0458),
				new Complex(-0.3295, 2.3703),
				new Complex(-2.3561, 0.4191),
				new Complex(-1.1266, -2.1113),
				new Complex(1.6598, -1.7239)};
		
		assertArrayEquals(arr, c1.root(5).toArray());
	}
	
	@Test
	public void testToStringImaginary() {
		assertEquals("0.0-i2.0", new Complex(0, -2).toString());
	}
	
	@Test
	public void testToStringReal() {
		assertEquals("3.71+i0.0", new Complex(3.71, 0).toString());
	}
	
	@Test
	public void testToStringZero() {
		assertEquals("0.0+i0.0", new Complex(0, 0).toString());
	}
}