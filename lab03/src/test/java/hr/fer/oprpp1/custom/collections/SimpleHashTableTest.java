package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Razred koji testira metode i konstruktore razreda SimpleHashTableTest.
 * 
 * @author Ana Bagić
 *
 */
public class SimpleHashTableTest {
	
	SimpleHashtable<String, Integer> hashTable;
	
	@Test
	public void testGivenExample() {
		hashTable = new SimpleHashtable<>(2);
		
		hashTable.put("Ivana", 2);
		hashTable.put("Ante", 2);
		hashTable.put("Jasna", 2);
		hashTable.put("Kristina", 5);
		hashTable.put("Ivana", 5);
		
		assertEquals(5, hashTable.get("Kristina"));
		assertEquals(4, hashTable.size());
	}
	
	@Test
    public void testHashTableConstructorThrows() {
        assertThrows(IllegalArgumentException.class, () -> hashTable = new SimpleHashtable<>(-1));
    }
	
	@Test
    public void testHashTableIsEmpty() {
        hashTable = new SimpleHashtable<>(2);

        assertTrue(hashTable.isEmpty());
    }
	
	@Test
    public void testHashTableIsEmptyFalse() {
        hashTable = new SimpleHashtable<>(2);

        hashTable.put("Oslo", 1);
        hashTable.put("Stockholm", 2);
        hashTable.put("Copenhagen", 3);
        hashTable.put("Helsinki", 4);
        hashTable.put("Oslo", 5);
        hashTable.put("Reykjavik", 10);
        
        assertFalse(hashTable.isEmpty());
    }
	
    @Test
    public void testHashTablePut() {
        hashTable = new SimpleHashtable<>(2);

        hashTable.put("Oslo", 1);
        assertNull(hashTable.put("Stockholm", 2));
        hashTable.put("Copenhagen", 3);
        hashTable.put("Helsinki", 4);
        assertEquals(1, hashTable.put("Oslo", 5));
        hashTable.put("Reykjavik", 10);

        assertEquals(2, hashTable.get("Stockholm"));
        assertEquals(5, hashTable.get("Oslo"));
        assertEquals(5, hashTable.size());
    }
    
    @Test
    public void testHashTableGetNull() {
        hashTable = new SimpleHashtable<>(2);

        hashTable.put("Oslo", 1);
        hashTable.put("Stockholm", 2);
        hashTable.put("Copenhagen", 3);
        hashTable.put("Helsinki", 4);
        hashTable.put("Oslo", 5);

        assertNull(hashTable.get("Reykjavik"));
    }
    
    @Test
    public void testContainsKey() {
        hashTable = new SimpleHashtable<>(2);

        hashTable.put("Oslo", 1);
        hashTable.put("Stockholm", 2);
        hashTable.put("Copenhagen", 3);
        hashTable.put("Helsinki", 4);
        hashTable.put("Oslo", 5);
        hashTable.put("Reykjavik", 10);

        assertTrue(hashTable.containsKey("Copenhagen"));
        assertFalse(hashTable.containsKey("London"));
    }
    
    @Test
    public void testContainsValue() {
        hashTable = new SimpleHashtable<>(2);

        hashTable.put("Oslo", 1);
        hashTable.put("Stockholm", 2);
        hashTable.put("Copenhagen", 3);
        hashTable.put("Helsinki", 4);
        hashTable.put("Oslo", 5);
        hashTable.put("Reykjavik", 10);

        assertTrue(hashTable.containsValue(10));
        assertFalse(hashTable.containsValue(15));
    }
    
    @Test
    public void testRemove() {
        hashTable = new SimpleHashtable<>(2);

        hashTable.put("Oslo", 1);
        hashTable.put("Stockholm", 2);
        hashTable.put("Copenhagen", 3);
        hashTable.put("Helsinki", 4);
        hashTable.put("Oslo", 5);
        hashTable.put("Reykjavik", 10);

        hashTable.remove("Copenhagen");

        assertFalse(hashTable.containsKey("Copenhagen"));
        assertTrue(hashTable.containsKey("Reykjavik"));
    }

    @Test
    public void testRemove2() {
    	hashTable = new SimpleHashtable<>(2);

        hashTable.put("Oslo", 1);
        hashTable.put("Stockholm", 2);
        hashTable.put("Copenhagen", 3);
        hashTable.put("Helsinki", 4);
        hashTable.put("Oslo", 5);
        hashTable.put("Reykjavik", 10);

        assertEquals(5, hashTable.remove("Oslo"));
        assertNull(hashTable.remove("London"));

        assertFalse(hashTable.containsKey("Oslo"));
        assertTrue(hashTable.containsKey("Copenhagen"));
        assertTrue(hashTable.containsKey("Reykjavik"));
    }

    @Test
    public void testHashTableToString() {
    	hashTable = new SimpleHashtable<>(2);

        hashTable.put("Oslo", 1);
        hashTable.put("Stockholm", 2);
        hashTable.put("Copenhagen", 3);
        hashTable.put("Helsinki", 4);
        hashTable.put("Oslo", 5);
        hashTable.put("Reykjavik", 10);

        assertEquals("[Reykjavik=10, Helsinki=4, Stockholm=2, Copenhagen=3, Oslo=5]", hashTable.toString());
    }
    
    @Test
    public void testHashTableClear() {
    	hashTable = new SimpleHashtable<>(2);

        hashTable.put("Oslo", 1);
        hashTable.put("Stockholm", 2);
        hashTable.put("Copenhagen", 3);
        hashTable.put("Helsinki", 4);
        hashTable.put("Oslo", 5);
        hashTable.put("Reykjavik", 10);
        
        hashTable.clear();

        assertEquals(0, hashTable.size());
        assertNull(hashTable.get("Helsinki"));
        assertNull(hashTable.get("Copenhagen"));
    }
    
    @Test
    public void testHashtableExampleIterator() {
        hashTable = new SimpleHashtable<>(2);

        hashTable.put("Ivana", 2);
        hashTable.put("Ante", 2);
        hashTable.put("Jasna", 2);
        hashTable.put("Kristina", 5);
        hashTable.put("Ivana", 5);
        
        StringBuilder sb = new StringBuilder();
        
        for (SimpleHashtable.TableEntry<String, Integer> pair : hashTable)
            sb.append(String.format("%s => %d\n", pair.getKey(), pair.getValue()));

        assertEquals("Ante => 2\nIvana => 5\nJasna => 2\nKristina => 5\n", sb.toString());
    }
    
    @Test
    public void testHashtableExampleDoubleIterator() {
    	hashTable = new SimpleHashtable<>(2);

        hashTable.put("Oslo", 1);
        hashTable.put("Stockholm", 2);
        hashTable.put("Copenhagen", 3);

        StringBuilder sb = new StringBuilder();

        for(var pair1 : hashTable)
        	for(var pair2 : hashTable)
        		sb.append(pair1).append(" - ").append(pair2);
        
        assertEquals("Stockholm=2 - Stockholm=2Stockholm=2 - Copenhagen=3Stockholm=2 - Oslo=1"
        		+ "Copenhagen=3 - Stockholm=2Copenhagen=3 - Copenhagen=3Copenhagen=3 - Oslo=1"
        		+ "Oslo=1 - Stockholm=2Oslo=1 - Copenhagen=3Oslo=1 - Oslo=1", sb.toString());
    }

    @Test
    public void testHashtableIteratorInForEach() {
    	hashTable = new SimpleHashtable<>(2);

        hashTable.put("Oslo", 1);
        hashTable.put("Stockholm", 2);
        hashTable.put("Copenhagen", 3);
        hashTable.put("Helsinki", 4);
        hashTable.put("Oslo", 5);
        hashTable.put("Reykjavik", 10);

        StringBuilder sb = new StringBuilder();

        for (var element : hashTable)
            sb.append(element);

        assertEquals("Reykjavik=10Helsinki=4Stockholm=2Copenhagen=3Oslo=5", sb.toString());
    }

    @Test
    public void testHashtableIteratorNextThrows() {
    	hashTable = new SimpleHashtable<>(2);

        hashTable.put("Oslo", 1);
        hashTable.put("Stockholm", 2);
        hashTable.put("Copenhagen", 3);

        var iterator = hashTable.iterator();

        iterator.next();
        iterator.next();
        iterator.next();

        assertThrows(NoSuchElementException.class, () -> iterator.next());
    }
    
    @Test
    public void testHashtableIteratorHasNext() {
    	hashTable = new SimpleHashtable<>(2);

        hashTable.put("Oslo", 1);
        hashTable.put("Stockholm", 2);
        hashTable.put("Copenhagen", 3);

        var iterator = hashTable.iterator();

        iterator.next();
        iterator.next();
        iterator.next();

        assertFalse(iterator.hasNext());
    }
    
    @Test
    public void testHashtableIteratorMultipleHasNext() {
    	hashTable = new SimpleHashtable<>(2);

        hashTable.put("Oslo", 1);
        hashTable.put("Stockholm", 2);
        hashTable.put("Copenhagen", 3);

        var iterator = hashTable.iterator();

        iterator.hasNext();
        iterator.hasNext();
        iterator.hasNext();
        iterator.hasNext();
        iterator.hasNext();

        assertEquals("Stockholm", iterator.next().getKey());
    }
    
    @Test
    public void testHashtableIteratorRemoveCalledTwiceThrows() {
    	hashTable = new SimpleHashtable<>(2);

        hashTable.put("Oslo", 1);
        hashTable.put("Stockholm", 2);
        hashTable.put("Copenhagen", 3);
        hashTable.put("Helsinki", 4);
        hashTable.put("Oslo", 5);
        hashTable.put("Reykjavik", 10);

        var iterator = hashTable.iterator();

        assertThrows(IllegalStateException.class, () -> {
            iterator.next();
            iterator.remove();
            iterator.remove();
        });
    }
    
    @Test
    public void testHashtableIteratorRemove() {
    	hashTable = new SimpleHashtable<>(2);

        hashTable.put("Oslo", 1);
        hashTable.put("Stockholm", 2);
        hashTable.put("Copenhagen", 3);
        hashTable.put("Helsinki", 4);
        hashTable.put("Oslo", 5);
        hashTable.put("Reykjavik", 10);

        var iterator = hashTable.iterator();

        while (iterator.hasNext())
            if (iterator.next().getKey().equals("Copenhagen"))
                iterator.remove();

        assertFalse(hashTable.containsKey("Copenhagen"));
        assertEquals(4, hashTable.size());
    }
    
    @Test
    public void testHashtableIteratorConcurrentModification() {
    	hashTable = new SimpleHashtable<>(2);

        hashTable.put("Oslo", 1);
        hashTable.put("Stockholm", 2);
        hashTable.put("Copenhagen", 3);

        var iterator = hashTable.iterator();

        iterator.next();
        hashTable.put("Lucija", 2);

        assertThrows(ConcurrentModificationException.class, () -> iterator.next());
    }
    
    @Test
    public void testHashtableIteratorExample() {
    	hashTable = new SimpleHashtable<>(2);
    	
    	hashTable.put("Oslo", 1);
        hashTable.put("Stockholm", 2);
        hashTable.put("Copenhagen", 3);
        hashTable.put("Helsinki", 4);
        hashTable.put("Oslo", 5);
        hashTable.put("Reykjavik", 10);
        
        var iterator = hashTable.iterator();
        
        while(iterator.hasNext()) {
        	assertNotNull(iterator.next());
        	iterator.remove();
        }
        
        assertTrue(hashTable.isEmpty());
    }

}
