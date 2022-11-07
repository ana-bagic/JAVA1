package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Razred koji testira default metode razreda Collection.
 * 
 * @author Ana Bagić
 *
 */
public class CollectionTest {
	
    @Test
    public void testAddAllSatisfyingAndForEach() {
        Collection<Integer> col1 = new LinkedListIndexedCollection<>();
        Collection<Integer> col2 = new ArrayIndexedCollection<>();
        
        col1.add(2);
        col1.add(4);
        col1.add(5);
        col1.add(7);
        col1.add(9);
        col2.add(10);
        col2.addAllSatisfying(col1, value -> value % 3 == 1);
        col2.add(0);

        StringBuilder sb = new StringBuilder();
        col2.forEach(sb::append);

        assertEquals("10470", sb.toString());
    }
}
