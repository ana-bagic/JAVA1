package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Razred koji testira metode i konstruktore razreda ComplexPolynomial.
 * 
 * @author Ana Bagić
 *
 */
public class ComplexPolynomialTest {

	@Test
	public void testConstructor() {
		Complex[] factors = {
				Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG, new Complex(4, 3)
		};
		ComplexPolynomial pol = new ComplexPolynomial(factors);
		
		assertEquals("(4.0+i3.0)*z^4 + (0.0-i1.0)*z^3 + (0.0+i1.0)*z^2 + (-1.0+i0.0)*z^1 + (1.0+i0.0)", pol.toString());
	}

	@Test
	public void testApply() {
		Complex[] factors = {
				new Complex(1.5, 1),
				new Complex(5, -1),
				new Complex(3, 4),
				new Complex(1, -2)
		};
		ComplexPolynomial pol = new ComplexPolynomial(factors);
		
		assertEquals(new Complex(1.5, 17), pol.apply(new Complex(1, 1)));
	}
	
	@Test
	public void testOrder() {
		Complex[] factors = {
				new Complex(1.5, 1),
				new Complex(5, -1),
				new Complex(3, 4),
				new Complex(1, -2)
		};
		ComplexPolynomial pol = new ComplexPolynomial(factors);
		
		assertEquals(3, pol.order());
	}
	
	@Test
	public void testDerive() {
		Complex[] factorsFrom = {
				new Complex(1.5, 1),
				new Complex(5, -1),
				new Complex(3, 4),
				new Complex(1, -2)
		};
		ComplexPolynomial polFrom = new ComplexPolynomial(factorsFrom);
		Complex[] factorsTo = {
				new Complex(5, -1),
				new Complex(6, 8),
				new Complex(3, -6)
		};
		ComplexPolynomial polTo = new ComplexPolynomial(factorsTo);

		assertEquals(polTo, polFrom.derive());
	}

	@Test
	public void testMultiply() {
		Complex[] factors1 = {
				new Complex(5, 0),
				new Complex(0, 0),
				new Complex(10, 0),
				new Complex(6, 0)
		};
		ComplexPolynomial pol1 = new ComplexPolynomial(factors1);
		Complex[] factors2 = {
				new Complex(1, 0),
				new Complex(2, 0),
				new Complex(4, 0)
		};
		ComplexPolynomial pol2 = new ComplexPolynomial(factors2);
		Complex[] result = {
				new Complex(5, 0),
				new Complex(10, 0),
				new Complex(30, 0),
				new Complex(26, 0),
				new Complex(52, 0),
				new Complex(24, 0)
		};
		ComplexPolynomial polResult = new ComplexPolynomial(result);

		assertEquals(polResult, pol1.multiply(pol2));
	}
}
