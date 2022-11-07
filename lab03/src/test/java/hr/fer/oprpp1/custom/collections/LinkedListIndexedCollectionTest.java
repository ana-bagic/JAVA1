package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Razred koji testira metode i konstruktore razreda LinkedListIndexedCollection.
 * Sve liste i metode se zovu array jer nisam imala snage prepravljat svaku posebno.
 * 
 * @author Ana Bagić
 *
 */
public class LinkedListIndexedCollectionTest {
	
	private LinkedListIndexedCollection<Number> array;
	private LinkedListIndexedCollection<Integer> array2;
	private LinkedListIndexedCollection<String> array3;
	private LinkedListIndexedCollection<String> array4;
	
	@Test
	public void testArrayDefaultConstructor() {
		array = new LinkedListIndexedCollection<>();
		array.add(1l);
		array.add(2);
		array.add(3.0);
		
		assertArrayEquals(new Object[] {1l, 2, 3.0}, array.toArray());
	}
	
	@Test
	public void testArrayCollectionConstructorThrows() {
		assertThrows(NullPointerException.class, () -> new LinkedListIndexedCollection<>(null));
	}
	
	@Test
	public void testArrayCollectionConstructor() {
		array2 = new LinkedListIndexedCollection<>();
		array2.add(1);
		array2.add(2);
		array2.add(3);
		array = new LinkedListIndexedCollection<>(array2);
		
		assertArrayEquals(new Object[] {1, 2, 3}, array.toArray());
	}
	
	@Test
	public void testArraySize0() {
		array = new LinkedListIndexedCollection<>();
		
		assertEquals(0, array.size());
	}
	
	@Test
	public void testArraySize4() {
		array3 = new LinkedListIndexedCollection<>();
		array3.add("Oslo");
		array3.add("Stockholm");
		array3.add("Helsinki");
		array3.add("Reykjavik");
		
		assertEquals(4, array3.size());
	}
	
	@Test
	public void testArrayAddThrows() {
		array3 = new LinkedListIndexedCollection<>();
		array3.add("Oslo");
		array3.add("Stockholm");
		
		assertThrows(NullPointerException.class, () -> array3.add(null));
	}
	
	@Test
	public void tesArraytAdd() {
		array3 = new LinkedListIndexedCollection<>();
		array3.add("Oslo");
		array3.add("Stockholm");
		assertEquals(2, array3.size());
		array3.add("Helsinki");
		array3.add("Reykjavik");
		array3.add("Copenhagen");
		assertEquals(5, array3.size());
	}
	
	@Test
	public void testArrayAddDuplicate() {
		array3 = new LinkedListIndexedCollection<>();
		array3.add("Oslo");
		array3.add("Stockholm");
		assertEquals(2, array3.size());
		array3.add("Oslo");
		array3.add("Stockholm");
		array3.add("Copenhagen");
		assertEquals(5, array3.size());
	}
	
	@Test
	public void testArrayInsertThrowsNullPointer() {
		array3 = new LinkedListIndexedCollection<>();
		array3.add("Oslo");
		array3.add("Stockholm");
		array3.add("Helsinki");
		
		assertThrows(NullPointerException.class, () -> array3.insert(null, 1));
	}
	
	@Test
	public void testArrayInsertThrowsIndexOutOfBoundsNegative() {
		array3 = new LinkedListIndexedCollection<>();
		array3.add("Oslo");
		array3.add("Stockholm");
		array3.add("Helsinki");
		
		assertThrows(IndexOutOfBoundsException.class, () -> array3.insert("London", -1));
	}
	
	@Test
	public void testArrayInsertThrowsIndexOutOfBoundsTooBig() {
		array3 = new LinkedListIndexedCollection<>();
		array3.add("Oslo");
		array3.add("Stockholm");
		array3.add("Helsinki");
		
		assertThrows(IndexOutOfBoundsException.class, () -> array3.insert("London", 5));
	}
	
	@Test
	public void testArrayInsert() {
		array3 = new LinkedListIndexedCollection<>();
		array3.add("Oslo");
		array3.add("Stockholm");
		array3.add("Helsinki");
		array3.add("Reykjavik");
		array3.insert("London", 1);
		assertEquals(5, array3.size());
		assertEquals("London", array3.get(1));
		assertEquals("Stockholm", array3.get(2));
	}
	
	@Test
	public void testArrayInsertDuplicate() {
		array3 = new LinkedListIndexedCollection<>();
		array3.add("Oslo");
		array3.add("Stockholm");
		array3.add("Helsinki");
		array3.add("Reykjavik");
		array3.insert("Helsinki", 1);
		assertEquals(5, array3.size());
		assertEquals("Helsinki", array3.get(1));
		assertEquals("Stockholm", array3.get(2));
	}
	
	@Test
	public void testArrayContainsTrue() {
		array3 = new LinkedListIndexedCollection<>();
		array3.add("Oslo");
		array3.add("Stockholm");
		array3.add("Helsinki");
		array3.add("Reykjavik");
		assertTrue(array3.contains("Helsinki"));
	}
	
	@Test
	public void testArrayContainsFalse() {
		array3 = new LinkedListIndexedCollection<>();
		array3.add("Oslo");
		array3.add("Stockholm");
		array3.add("Helsinki");
		array3.add("Reykjavik");
		assertFalse(array3.contains("Copenhagen"));
	}
	
	@Test
	public void testArrayContainsFalseNumbers() {
		array3 = new LinkedListIndexedCollection<>();
		array3.add("1");
		array3.add("2");
		array3.add("3");
		array3.add("4");
		assertFalse(array3.contains(1));
	}
	
	@Test
	public void testArrayGetThrowsIndexOutOfBoundsNegative() {
		array3 = new LinkedListIndexedCollection<>();
		array3.add("Oslo");
		array3.add("Stockholm");
		array3.add("Helsinki");
		
		assertThrows(IndexOutOfBoundsException.class, () -> array3.insert("London", -1));
	}
	
	@Test
	public void testArrayGetThrowsIndexOutOfBoundsTooBig() {
		array3 = new LinkedListIndexedCollection<>();
		array3.add("Oslo");
		array3.add("Stockholm");
		array3.add("Helsinki");
		
		assertThrows(IndexOutOfBoundsException.class, () -> array3.insert("London", 6));
	}
	
	@Test
	public void testArrayGet() {
		array3 = new LinkedListIndexedCollection<>();
		array3.add("Oslo");
		array3.add("Stockholm");
		array3.add("Helsinki");
		array3.add("Reykjavik");
		array3.insert("London", 1);
		assertEquals("Oslo", array3.get(0));
		assertEquals("London", array3.get(1));
		assertEquals("Stockholm", array3.get(2));
		assertEquals("Helsinki", array3.get(3));
		assertEquals("Reykjavik", array3.get(4));
	}
	
	@Test
	public void testIndexOfNullEmptyArray() {
		array3 = new LinkedListIndexedCollection<>();
		assertEquals(-1, array3.indexOf(null));
	}
	
	@Test
	public void testIndexOfNullFullArray() {
		array3 = new LinkedListIndexedCollection<>();
		array3.add("Oslo");
		array3.add("Stockholm");
		array3.add("Helsinki");
		array3.add("Reykjavik");
		assertEquals(-1, array3.indexOf(null));
	}
	
	@Test
	public void testIndexOfNotInArray() {
		array3 = new LinkedListIndexedCollection<>();
		array3.add("Oslo");
		array3.add("Stockholm");
		array3.add("Helsinki");
		array3.add("Reykjavik");
		assertEquals(-1, array3.indexOf("Copenhagen"));
	}
	
	@Test
	public void testIndexOfNotInArrayNumbers() {
		array3 = new LinkedListIndexedCollection<>();
		array3.add("1");
		array3.add("2");
		array3.add("3");
		array3.add("4");
		assertEquals(-1, array3.indexOf(1));
	}
	
	@Test
	public void testArrayIndexOf() {
		array3 = new LinkedListIndexedCollection<>();
		array3.add("Oslo");
		array3.add("Stockholm");
		array3.add("Helsinki");
		array3.add("Reykjavik");
		assertEquals(1, array3.indexOf("Stockholm"));
		assertEquals(3, array3.indexOf("Reykjavik"));
	}
	
	@Test
	public void testArrayForEachThrowsNullPointer() {
		array3 = new LinkedListIndexedCollection<>();
		array3.add("Oslo");
		array3.add("Stockholm");
		array3.add("Helsinki");
		
		assertThrows(NullPointerException.class, () -> array3.forEach(null));
	}
	
	@Test
	public void testArrayRemoveValueTrue() {
		array3 = new LinkedListIndexedCollection<>();
		array3.add("Oslo");
		array3.add("Stockholm");
		array3.add("Helsinki");
		array3.add("Reykjavik");
		assertTrue(array3.remove("Stockholm"));
		assertTrue(array3.remove("Reykjavik"));
		assertEquals(2, array3.size());
	}
	
	@Test
	public void testArrayRemoveValueFalse() {
		array3 = new LinkedListIndexedCollection<>();
		array3.add("Oslo");
		array3.add("Stockholm");
		array3.add("Helsinki");
		array3.add("Reykjavik");
		assertFalse(array3.remove("Copenhagen"));
		assertFalse(array3.remove("London"));
		assertEquals(4, array3.size());
	}
	
	@Test
	public void testArrayRemoveValueTrueDuplicate() {
		array3 = new LinkedListIndexedCollection<>();
		array3.add("Helsinki");
		array3.add("Stockholm");
		array3.add("Helsinki");
		array3.add("Oslo");
		assertTrue(array3.remove("Helsinki"));
		assertTrue(array3.remove("Helsinki"));
		assertEquals(2, array3.size());
	}
	
	@Test
	public void testArrayRemoveValueFalseDuplicate() {
		array3 = new LinkedListIndexedCollection<>();
		array3.add("Oslo");
		array3.add("Stockholm");
		array3.add("Helsinki");
		array3.add("Reykjavik");
		assertTrue(array3.remove("Stockholm"));
		assertFalse(array3.remove("Stockholm"));
		assertEquals(3, array3.size());
	}
	
	@Test
	public void testArrayRemoveValueNull() {
		array3 = new LinkedListIndexedCollection<>();
		array3.add("Oslo");
		array3.add("Stockholm");
		array3.add("Helsinki");
		array3.add("Reykjavik");
		assertFalse(array3.remove(null));
		assertEquals(4, array3.size());
	}
	
	@Test
	public void testArrayRemoveIndexTrue() {
		array3 = new LinkedListIndexedCollection<>();
		array3.add("Oslo");
		array3.add("Stockholm");
		array3.add("Helsinki");
		array3.add("Reykjavik");
		array3.remove(2);
		array3.remove(0);
		assertFalse(array3.contains("Helsinki"));
		assertFalse(array3.contains("Oslo"));
		assertEquals(2, array3.size());
	}
	
	@Test
	public void testArrayRemoveIndexThrowsIndexOutOfBoundsNegative() {
		array3 = new LinkedListIndexedCollection<>();
		array3.add("Oslo");
		array3.add("Stockholm");
		array3.add("Helsinki");
		array3.add("Reykjavik");
		
		assertThrows(IndexOutOfBoundsException.class, () -> array3.remove(-1));
		assertEquals(4, array3.size());
	}
	
	@Test
	public void testArrayRemoveIndexThrowsIndexOutOfBoundsTooBig() {
		array3 = new LinkedListIndexedCollection<>();
		array3.add("Oslo");
		array3.add("Stockholm");
		array3.add("Helsinki");
		array3.add("Reykjavik");
		
		assertThrows(IndexOutOfBoundsException.class, () -> array3.remove(6));
		assertEquals(4, array3.size());
	}
	
	@Test
	public void testArrayRemoveIndexThrowsIndexOutOfBoundsDuplicate() {
		array3 = new LinkedListIndexedCollection<>();
		array3.add("Helsinki");
		array3.add("Stockholm");
		array3.add("Helsinki");
		array3.add("Oslo");
		array3.remove(3);
		
		assertThrows(IndexOutOfBoundsException.class, () -> array3.remove(3));
		assertEquals(3, array3.size());
	}
	
	@Test
	public void testArrayClearEmpty() {
		array3 = new LinkedListIndexedCollection<>();
		array3.clear();
		
		assertThrows(IndexOutOfBoundsException.class, () -> array3.get(0));
		assertEquals(0, array3.size());
	}
	
	@Test
	public void testArrayClear() {
		array3 = new LinkedListIndexedCollection<>();
		array3.add("Helsinki");
		array3.add("Stockholm");
		array3.add("Helsinki");
		array3.add("Oslo");
		
		assertEquals("Helsinki", array3.get(2));
		assertEquals(4, array3.size());
		array3.clear();
		
		assertThrows(IndexOutOfBoundsException.class, () -> array3.get(2));
		assertEquals(0, array3.size());
	}
	
	@Test
	public void testArrayToString() {
		array3 = new LinkedListIndexedCollection<>();
		array3.add("Oslo");
		array3.add("Stockholm");
		array3.add("Helsinki");
		array3.add("Reykjavik");
		
		assertEquals("LinkedListIndexedCollection [size = 4, elements = "
				+ "[Oslo, Stockholm, Helsinki, Reykjavik]]"
				, array3.toString());
	}

    @Test
    public void testElementsGetter() {
        array3 = new LinkedListIndexedCollection<>();
        array4 = new LinkedListIndexedCollection<>();
        array3.add("Oslo");
        array3.add("Stockholm");
        array3.add("Copenhagen");
        array4.add("Helsinki");
        array4.add("Reykjavik");
        array4.add("London");
        
        ElementsGetter<String> getter1 = array3.createElementsGetter();
        ElementsGetter<String> getter2 = array3.createElementsGetter();
        ElementsGetter<String> getter3 = array4.createElementsGetter();

        StringBuilder sb = new StringBuilder();
        sb.append(getter1.getNextElement()).append(" ");
        sb.append(getter1.getNextElement()).append(" ");
        sb.append(getter2.getNextElement()).append(" ");
        sb.append(getter3.getNextElement()).append(" ");
        sb.append(getter3.getNextElement());

        assertEquals("Oslo Stockholm Oslo Helsinki Reykjavik", sb.toString());
    }

    @Test
    public void testElementsGetterException() {
        assertThrows(NoSuchElementException.class, () -> {
        	array3 = new LinkedListIndexedCollection<>();
        	array3.add("Oslo");
        	array3.add("Stockholm");
            array3.add("Copenhagen");
            ElementsGetter<String> getter1 = array3.createElementsGetter();
            
            getter1.getNextElement();
            getter1.getNextElement();
            getter1.getNextElement();
            getter1.getNextElement();
        });
    }

    @Test
    public void hasNextElementsGetterTest() {
        array3 = new LinkedListIndexedCollection<>();
        array3.add("Oslo");
    	array3.add("Stockholm");
        array3.add("Copenhagen");
        ElementsGetter<String> getter = array3.createElementsGetter();
        
        getter.getNextElement();
        getter.getNextElement();
        
        assertEquals(true, getter.hasNextElement());
        getter.getNextElement();
        assertEquals(false, getter.hasNextElement());
    }

    @Test
    public void testElementsGetterThrowsConcurrentModificationException() {
        assertThrows(ConcurrentModificationException.class, () -> {
        	array3 = new LinkedListIndexedCollection<>();
            array3.add("Oslo");
        	array3.add("Stockholm");
            array3.add("Copenhagen");
            ElementsGetter<String> getter = array3.createElementsGetter();
            
            getter.getNextElement();
            getter.getNextElement();
            array3.clear();
            getter.getNextElement();
        });
    }

}
