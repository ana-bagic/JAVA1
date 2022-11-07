package hr.fer.oprpp1.custom.collections.demo;

import java.util.ConcurrentModificationException;
//import java.util.NoSuchElementException;
import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.Collection;
import hr.fer.oprpp1.custom.collections.ElementsGetter;
import hr.fer.oprpp1.custom.collections.LinkedListIndexedCollection;

public class ElementsGetterDemo {

	public static void main(String[] args) {
		System.out.println("ArrayIndexedCollection:");
		primjer1(0);
		System.out.println();
		System.out.println("LinkedListIndexedCollection:");
		primjer1(1);
		
		System.out.println();
		System.out.println("ArrayIndexedCollection:");
		primjer2(0);
		System.out.println();
		System.out.println("LinkedListIndexedCollection:");
		primjer2(1);
		
		System.out.println();
		System.out.println("ArrayIndexedCollection:");
		primjer3(0);
		System.out.println();
		System.out.println("LinkedListIndexedCollection:");
		primjer3(1);
	}

	private static void primjer1(int x) {
		Collection col1, col2;
		
		if(x == 0) {
			col1 = new ArrayIndexedCollection();
			col2 = new ArrayIndexedCollection();
		} else {
			col1 = new LinkedListIndexedCollection();
			col2 = new LinkedListIndexedCollection();
		}
		
		
		col1.add("Ivo");
		col1.add("Ana");
		col1.add("Jasna");
		col2.add("Jasmina");
		col2.add("Štefanija");
		col2.add("Karmela");
		
		ElementsGetter getter1 = col1.createElementsGetter();
		ElementsGetter getter2 = col1.createElementsGetter();
		ElementsGetter getter3 = col2.createElementsGetter();
		
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter2.getNextElement());
		System.out.println("Jedan element: " + getter3.getNextElement());
		System.out.println("Jedan element: " + getter3.getNextElement());
	}
	
	private static void primjer2(int x) {
		Collection col;
		
		if(x == 0)
			col = new ArrayIndexedCollection();
		else
			col = new LinkedListIndexedCollection();
		
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		
		ElementsGetter getter = col.createElementsGetter();
		
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		col.clear();
		try {
			System.out.println("Jedan element: " + getter.getNextElement());		
		} catch(ConcurrentModificationException exc) {
			System.out.println("ConcurrentModificationException occured!");
		}
	}
	
	private static void primjer3(int x) {
		Collection col;
		
		if(x == 0)
			col = new ArrayIndexedCollection();
		else
			col = new LinkedListIndexedCollection();
		
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		
		ElementsGetter getter = col.createElementsGetter();
		getter.getNextElement();
		
		getter.processRemaining(System.out::println);
	}

}
