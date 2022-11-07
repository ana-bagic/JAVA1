package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tester za razred ComparisonOperators.
 * 
 * @author Ana Bagić
 *
 */
public class ComparisonOperatorTester {

	@Test
	public void forComparisonOperatorLess() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		assertTrue(oper.satisfied("Ana", "Jasna"));
		assertFalse(oper.satisfied("Jasna", "Ana"));
		assertFalse(oper.satisfied("Ana", "Ana"));
	}
	
	@Test
	public void forComparisonOperatorLessOrEquals() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		assertTrue(oper.satisfied("Ana", "Jasna"));
		assertFalse(oper.satisfied("Jasna", "Ana"));
		assertTrue(oper.satisfied("Ana", "Ana"));
	}
	
	@Test
	public void forComparisonOperatorGreater() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		assertFalse(oper.satisfied("Ana", "Jasna"));
		assertTrue(oper.satisfied("Jasna", "Ana"));
		assertFalse(oper.satisfied("Ana", "Ana"));
	}
	
	@Test
	public void forComparisonOperatorGreaterOrEquals() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		assertFalse(oper.satisfied("Ana", "Jasna"));
		assertTrue(oper.satisfied("Jasna", "Ana"));
		assertTrue(oper.satisfied("Ana", "Ana"));
	}
	
	@Test
	public void forComparisonOperatorEquals() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		assertFalse(oper.satisfied("Ana", "Jasna"));
		assertFalse(oper.satisfied("Jasna", "Ana"));
		assertTrue(oper.satisfied("Ana", "Ana"));
	}
	
	@Test
	public void forComparisonOperatorNotEquals() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		assertTrue(oper.satisfied("Ana", "Jasna"));
		assertTrue(oper.satisfied("Jasna", "Ana"));
		assertFalse(oper.satisfied("Ana", "Ana"));
	}
	
	@Test
	public void forComparisonOperatorLike() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertFalse(oper.satisfied("Zagreb", "Aba*"));
		assertFalse(oper.satisfied("AAA", "AA*AA"));
		assertTrue(oper.satisfied("AAAA", "AA*AA"));
		
		assertTrue(oper.satisfied("Stockholm", "*olm"));
		assertFalse(oper.satisfied("Stockholm", "*olms"));
		assertTrue(oper.satisfied("Stockholm", "Stock*"));
		assertFalse(oper.satisfied("Stockholm", "stock*"));
		assertTrue(oper.satisfied("Stockholm", "St*olm"));
		assertFalse(oper.satisfied("Stockholm", "Str*olm"));
		
		assertTrue(oper.satisfied("Ana", "Ana"));
		assertThrows(IllegalArgumentException.class, () -> oper.satisfied("ana", "*n*"));
	}
}
