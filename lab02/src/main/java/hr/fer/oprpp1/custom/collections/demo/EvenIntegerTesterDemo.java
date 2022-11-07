package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.Collection;
import hr.fer.oprpp1.custom.collections.LinkedListIndexedCollection;
import hr.fer.oprpp1.custom.collections.Tester;

/**
 * Testiranje EvenIntegerTestera.
 * 
 * @author Ana Bagić
 *
 */
public class EvenIntegerTesterDemo {

	public static void main(String[] args) {
		System.out.println("Test 1:");
		primjer1();
		
		System.out.println();
		System.out.println("Test 2:");
		primjer2();
	}
	
	private static void primjer1() {
		Tester t = new EvenIntegerTester();

		System.out.println(t.test("Ivo"));
		System.out.println(t.test(22));
		System.out.println(t.test(3));
	}

	private static void primjer2() {
		Collection col1 = new LinkedListIndexedCollection();
		Collection col2 = new ArrayIndexedCollection();
		
		col1.add(2);
		col1.add(3);
		col1.add(4);
		col1.add(5);
		col1.add(6);
		
		col2.add(12);
		col2.addAllSatisfying(col1, new EvenIntegerTester());
		col2.forEach(System.out::println);
	}

}
