package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.lsystems.LSystem;

/**
 * Tester za implementaciju generate() metode u razredu LSystem iz LSystemBuilder-a.
 * 
 * @author Ana Bagić
 *
 */
public class LSystemBuilderImplTester {

	@Test
	public void generateTest() {
		LSystemBuilderImpl builder = new LSystemBuilderImpl();
		
		LSystem system = builder
				.registerProduction('F', "F+F--F+F")
				.setAxiom("F")
				.build();
		
		assertEquals("F", system.generate(0));
		assertEquals("F+F--F+F", system.generate(1));
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", system.generate(2));
	}
}
