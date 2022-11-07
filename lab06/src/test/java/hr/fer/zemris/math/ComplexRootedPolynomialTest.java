package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Razred koji testira metode i konstruktore razreda ComplexRootedPolynomial.
 * 
 * @author Ana Bagić
 *
 */
public class ComplexRootedPolynomialTest {
	
	@Test
	public void testConstructor() {
		Complex constant = new Complex(2, 0);
		Complex[] roots = {
				Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG
		};
		ComplexRootedPolynomial pol = new ComplexRootedPolynomial(constant, roots);
		
		assertEquals("(2.0+i0.0) * (z-(1.0+i0.0)) * (z-(-1.0+i0.0)) * (z-(0.0+i1.0)) * (z-(0.0-i1.0))", pol.toString());
	}
	
	@Test
	public void testApply() {
		Complex constant = new Complex(1, 0);
		Complex[] roots = {
				new Complex(1, -2),
				new Complex(3, 4),
				new Complex(5, -1),
				new Complex(1.5, 1),
		};
		ComplexRootedPolynomial pol = new ComplexRootedPolynomial(constant, roots);
		
		assertEquals(new Complex(12, -21), pol.apply(new Complex(1, 1)));
	}
	
	@Test
	public void testiIndexOfClosestRoot() {
		Complex constant = new Complex(1, 0);
		Complex[] roots = {
				new Complex(-2, 2),
				new Complex(-1, -2),
				new Complex(1, -4),
				new Complex(0, 3),
				new Complex(3, 2),
				new Complex(5, 3)
		};
		ComplexRootedPolynomial pol = new ComplexRootedPolynomial(constant, roots);
		Complex forComplex = new Complex(2, 3);
		
		assertEquals(4, pol.indexOfClosestRootFor(forComplex, 5));
	}
	
	@Test
	public void testIndexOfClosestRootSmallTreshold() {
		Complex constant = new Complex(1, 0);
		Complex[] roots = {
				new Complex(-2, 2),
				new Complex(-1, -2),
				new Complex(1, -4),
				new Complex(0, 3),
				new Complex(3, 2),
				new Complex(5, 3)
		};
		ComplexRootedPolynomial pol = new ComplexRootedPolynomial(constant, roots);
		Complex forComplex = new Complex(2, 3);
		
		assertEquals(-1, pol.indexOfClosestRootFor(forComplex, 1));
	}
	
	@Test
	public void testEquals() {
		Complex constantFirst = new Complex(1, 0);
		Complex[] rootsFirst = {
				new Complex(-2, 2),
				new Complex(-1, -2),
				new Complex(1, -4),
				new Complex(0, 3),
				new Complex(3, 2),
				new Complex(5, 3)
		};
		ComplexRootedPolynomial polFirst = new ComplexRootedPolynomial(constantFirst, rootsFirst);
		Complex constantSecond = new Complex(1, 0);
		Complex[] rootsSecond = {
				new Complex(-2, 2),
				new Complex(-1, -2),
				new Complex(1, -4),
				new Complex(0, 3),
				new Complex(3, 2),
				new Complex(5, 3)
		};
		ComplexRootedPolynomial polSecond = new ComplexRootedPolynomial(constantSecond, rootsSecond);
		
		assertEquals(polFirst, polSecond);
	}
	
	@Test
	public void testToComplexPolynom() {
		Complex constant = new Complex(2, 0);
		Complex[] roots = {
				Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG
		};
		ComplexRootedPolynomial pol = new ComplexRootedPolynomial(constant, roots);
		
		Complex[] polynomial = {
				new Complex(-2, 0),
				new Complex(0, 0),
				new Complex(0, 0),
				new Complex(0, 0),
				new Complex(2, 0)
		};
		
		assertEquals(new ComplexPolynomial(polynomial), pol.toComplexPolynom());
	}

}
