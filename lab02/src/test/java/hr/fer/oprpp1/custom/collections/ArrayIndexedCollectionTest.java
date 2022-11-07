package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Razred koji testira metode i konstruktore razreda ArrayIndexedCollection.
 * Kopirano iz prve zadaće.
 * 
 * @author Ana Bagić
 *
 */
public class ArrayIndexedCollectionTest {

	private ArrayIndexedCollection array;
	private ArrayIndexedCollection array2;
	
	@Test
	public void testArrayDefaultConstructor() {
		array = new ArrayIndexedCollection();
		array.add("1");
		array.add(2);
		array.add(3.0);
		
		assertArrayEquals(new Object[] {"1", 2, 3.0}, array.toArray());
	}
	
	@Test
	public void testArrayInitialCapacityConstructorThrows() {
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(0));
	}
	
	@Test
	public void testArrayInitialCapacityConstructor() {
		array = new ArrayIndexedCollection(6);
		array.add("1");
		array.add(2);
		array.add(3.0);
		
		assertArrayEquals(new Object[] {"1", 2, 3.0}, array.toArray());
	}

	@Test
	public void testArrayInitialCapacityConstructorReallocate() {
		array = new ArrayIndexedCollection(2);
		array.add("1");
		array.add(2);
		array.add(3.0);
		
		assertArrayEquals(new Object[] {"1", 2, 3.0}, array.toArray());
	}
	
	@Test
	public void testArrayCollectionConstructorThrows() {
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null));
	}
	
	@Test
	public void testArrayCollectionConstructor() {
		array = new ArrayIndexedCollection(6);
		array.add("1");
		array.add(2);
		array.add(3.0);
		array2 = new ArrayIndexedCollection(array);
		
		assertArrayEquals(new Object[] {"1", 2, 3.0}, array2.toArray());
	}
	
	@Test
	public void testArrayCollectionInitialCapacityConstructorThrows() {
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null, 5));
	}
	
	@Test
	public void testArrayCollectionInitialCapacityBigConstructor() {
		array = new ArrayIndexedCollection(6);
		array.add("1");
		array.add(2);
		array.add(3.0);
		array2 = new ArrayIndexedCollection(array, 10);
		
		assertArrayEquals(new Object[] {"1", 2, 3.0}, array2.toArray());
	}
	
	@Test
	public void testArrayCollectionInitialCapacitySmallConstructor() {
		array = new ArrayIndexedCollection(6);
		array.add("1");
		array.add(2);
		array.add(3.0);
		array2 = new ArrayIndexedCollection(array, 2);
		
		assertArrayEquals(new Object[] {"1", 2, 3.0}, array2.toArray());
	}
	
	@Test
	public void testArraySize0() {
		array = new ArrayIndexedCollection(10);
		
		assertEquals(0, array.size());
	}
	
	@Test
	public void testArraySize4() {
		array = new ArrayIndexedCollection(10);
		array.add("Oslo");
		array.add("Stockholm");
		array.add("Helsinki");
		array.add("Reykjavik");
		
		assertEquals(4, array.size());
	}
	
	@Test
	public void testArrayAddThrows() {
		array = new ArrayIndexedCollection(10);
		array.add("Oslo");
		array.add("Stockholm");
		
		assertThrows(NullPointerException.class, () -> array.add(null));
	}
	
	@Test
	public void tesArraytAddReallocate() {
		array = new ArrayIndexedCollection(3);
		array.add("Oslo");
		array.add("Stockholm");
		assertEquals(2, array.size());
		array.add("Helsinki");
		array.add("Reykjavik");
		array.add("Copenhagen");
		assertEquals(5, array.size());
	}
	
	@Test
	public void testArrayAddDuplicate() {
		array = new ArrayIndexedCollection(3);
		array.add("Oslo");
		array.add("Stockholm");
		assertEquals(2, array.size());
		array.add("Oslo");
		array.add("Stockholm");
		array.add("Copenhagen");
		assertEquals(5, array.size());
	}
	
	@Test
	public void testArrayInsertThrowsNullPointer() {
		array = new ArrayIndexedCollection(10);
		array.add("Oslo");
		array.add("Stockholm");
		array.add("Helsinki");
		
		assertThrows(NullPointerException.class, () -> array.insert(null, 1));
	}
	
	@Test
	public void testArrayInsertThrowsIndexOutOfBoundsNegative() {
		array = new ArrayIndexedCollection(10);
		array.add("Oslo");
		array.add("Stockholm");
		array.add("Helsinki");
		
		assertThrows(IndexOutOfBoundsException.class, () -> array.insert("London", -1));
	}
	
	@Test
	public void testArrayInsertThrowsIndexOutOfBoundsTooBig() {
		array = new ArrayIndexedCollection(10);
		array.add("Oslo");
		array.add("Stockholm");
		array.add("Helsinki");
		
		assertThrows(IndexOutOfBoundsException.class, () -> array.insert("London", 5));
	}
	
	@Test
	public void testArrayInsert() {
		array = new ArrayIndexedCollection(6);
		array.add("Oslo");
		array.add("Stockholm");
		array.add("Helsinki");
		array.add("Reykjavik");
		array.insert("London", 1);
		assertEquals(5, array.size());
		assertEquals("London", array.get(1));
		assertEquals("Stockholm", array.get(2));
	}
	
	@Test
	public void testArrayInsertDuplicate() {
		array = new ArrayIndexedCollection(6);
		array.add("Oslo");
		array.add("Stockholm");
		array.add("Helsinki");
		array.add("Reykjavik");
		array.insert("Helsinki", 1);
		assertEquals(5, array.size());
		assertEquals("Helsinki", array.get(1));
		assertEquals("Stockholm", array.get(2));
	}
	
	@Test
	public void testArrayInsertReallocate() {
		array = new ArrayIndexedCollection(4);
		array.add("Oslo");
		array.add("Stockholm");
		array.add("Helsinki");
		array.add("Reykjavik");
		array.insert("London", 3);
		array.add("Copenhagen");
		assertEquals(6, array.size());
		assertEquals("London", array.get(3));
		assertEquals("Reykjavik", array.get(4));
	}
	
	@Test
	public void testArrayContainsTrue() {
		array = new ArrayIndexedCollection(4);
		array.add("Oslo");
		array.add("Stockholm");
		array.add("Helsinki");
		array.add("Reykjavik");
		assertTrue(array.contains("Helsinki"));
	}
	
	@Test
	public void testArrayContainsFalse() {
		array = new ArrayIndexedCollection(4);
		array.add("Oslo");
		array.add("Stockholm");
		array.add("Helsinki");
		array.add("Reykjavik");
		assertFalse(array.contains("Copenhagen"));
	}
	
	@Test
	public void testArrayContainsFalseNumbers() {
		array = new ArrayIndexedCollection(4);
		array.add("1");
		array.add("2");
		array.add("3");
		array.add("4");
		assertFalse(array.contains(1));
	}
	
	@Test
	public void testArrayGetThrowsIndexOutOfBoundsNegative() {
		array = new ArrayIndexedCollection(10);
		array.add("Oslo");
		array.add("Stockholm");
		array.add("Helsinki");
		
		assertThrows(IndexOutOfBoundsException.class, () -> array.insert("London", -1));
	}
	
	@Test
	public void testArrayGetThrowsIndexOutOfBoundsTooBig() {
		array = new ArrayIndexedCollection(10);
		array.add("Oslo");
		array.add("Stockholm");
		array.add("Helsinki");
		
		assertThrows(IndexOutOfBoundsException.class, () -> array.insert("London", 6));
	}
	
	@Test
	public void testArrayGet() {
		array = new ArrayIndexedCollection(6);
		array.add("Oslo");
		array.add("Stockholm");
		array.add("Helsinki");
		array.add("Reykjavik");
		array.insert("London", 1);
		assertEquals("Oslo", array.get(0));
		assertEquals("London", array.get(1));
		assertEquals("Stockholm", array.get(2));
		assertEquals("Helsinki", array.get(3));
		assertEquals("Reykjavik", array.get(4));
	}
	
	@Test
	public void testIndexOfNullEmptyArray() {
		array = new ArrayIndexedCollection(10);
		assertEquals(-1, array.indexOf(null));
	}
	
	@Test
	public void testIndexOfNullFullArray() {
		array = new ArrayIndexedCollection(10);
		array.add("Oslo");
		array.add("Stockholm");
		array.add("Helsinki");
		array.add("Reykjavik");
		assertEquals(-1, array.indexOf(null));
	}
	
	@Test
	public void testIndexOfNotInArray() {
		array = new ArrayIndexedCollection(10);
		array.add("Oslo");
		array.add("Stockholm");
		array.add("Helsinki");
		array.add("Reykjavik");
		assertEquals(-1, array.indexOf("Copenhagen"));
	}
	
	@Test
	public void testIndexOfNotInArrayNumbers() {
		array = new ArrayIndexedCollection(4);
		array.add("1");
		array.add("2");
		array.add("3");
		array.add("4");
		assertEquals(-1, array.indexOf(1));
	}
	
	@Test
	public void testArrayIndexOf() {
		array = new ArrayIndexedCollection(10);
		array.add("Oslo");
		array.add("Stockholm");
		array.add("Helsinki");
		array.add("Reykjavik");
		assertEquals(1, array.indexOf("Stockholm"));
		assertEquals(3, array.indexOf("Reykjavik"));
	}
	
	@Test
	public void testArrayForEachThrowsNullPointer() {
		array = new ArrayIndexedCollection(10);
		array.add("Oslo");
		array.add("Stockholm");
		array.add("Helsinki");
		
		assertThrows(NullPointerException.class, () -> array.forEach(null));
	}
	
	@Test
	public void testArrayRemoveValueTrue() {
		array = new ArrayIndexedCollection(10);
		array.add("Oslo");
		array.add("Stockholm");
		array.add("Helsinki");
		array.add("Reykjavik");
		assertTrue(array.remove("Stockholm"));
		assertTrue(array.remove("Reykjavik"));
		assertEquals(2, array.size());
	}
	
	@Test
	public void testArrayRemoveValueFalse() {
		array = new ArrayIndexedCollection(10);
		array.add("Oslo");
		array.add("Stockholm");
		array.add("Helsinki");
		array.add("Reykjavik");
		assertFalse(array.remove("Copenhagen"));
		assertFalse(array.remove("London"));
		assertEquals(4, array.size());
	}
	
	@Test
	public void testArrayRemoveValueTrueDuplicate() {
		array = new ArrayIndexedCollection(10);
		array.add("Helsinki");
		array.add("Stockholm");
		array.add("Helsinki");
		array.add("Oslo");
		assertTrue(array.remove("Helsinki"));
		assertTrue(array.remove("Helsinki"));
		assertEquals(2, array.size());
	}
	
	@Test
	public void testArrayRemoveValueFalseDuplicate() {
		array = new ArrayIndexedCollection(10);
		array.add("Oslo");
		array.add("Stockholm");
		array.add("Helsinki");
		array.add("Reykjavik");
		assertTrue(array.remove("Stockholm"));
		assertFalse(array.remove("Stockholm"));
		assertEquals(3, array.size());
	}
	
	@Test
	public void testArrayRemoveValueNull() {
		array = new ArrayIndexedCollection(10);
		array.add("Oslo");
		array.add("Stockholm");
		array.add("Helsinki");
		array.add("Reykjavik");
		assertFalse(array.remove(null));
		assertEquals(4, array.size());
	}
	
	@Test
	public void testArrayRemoveIndexTrue() {
		array = new ArrayIndexedCollection(10);
		array.add("Oslo");
		array.add("Stockholm");
		array.add("Helsinki");
		array.add("Reykjavik");
		array.remove(2);
		array.remove(0);
		assertFalse(array.contains("Helsinki"));
		assertFalse(array.contains("Oslo"));
		assertEquals(2, array.size());
	}
	
	@Test
	public void testArrayRemoveIndexThrowsIndexOutOfBoundsNegative() {
		array = new ArrayIndexedCollection(10);
		array.add("Oslo");
		array.add("Stockholm");
		array.add("Helsinki");
		array.add("Reykjavik");
		
		assertThrows(IndexOutOfBoundsException.class, () -> array.remove(-1));
		assertEquals(4, array.size());
	}
	
	@Test
	public void testArrayRemoveIndexThrowsIndexOutOfBoundsTooBig() {
		array = new ArrayIndexedCollection(10);
		array.add("Oslo");
		array.add("Stockholm");
		array.add("Helsinki");
		array.add("Reykjavik");
		
		assertThrows(IndexOutOfBoundsException.class, () -> array.remove(6));
		assertEquals(4, array.size());
	}
	
	@Test
	public void testArrayRemoveIndexThrowsIndexOutOfBoundsDuplicate() {
		array = new ArrayIndexedCollection(10);
		array.add("Helsinki");
		array.add("Stockholm");
		array.add("Helsinki");
		array.add("Oslo");
		array.remove(3);
		
		assertThrows(IndexOutOfBoundsException.class, () -> array.remove(3));
		assertEquals(3, array.size());
	}
	
	@Test
	public void testArrayClearEmpty() {
		array = new ArrayIndexedCollection(10);
		array.clear();
		
		assertThrows(IndexOutOfBoundsException.class, () -> array.get(0));
		assertEquals(0, array.size());
	}
	
	@Test
	public void testArrayClear() {
		array = new ArrayIndexedCollection(10);
		array.add("Helsinki");
		array.add("Stockholm");
		array.add("Helsinki");
		array.add("Oslo");
		
		assertEquals("Helsinki", array.get(2));
		assertEquals(4, array.size());
		array.clear();
		
		assertThrows(IndexOutOfBoundsException.class, () -> array.get(2));
		assertEquals(0, array.size());
	}
	
	@Test
	public void testArrayToString() {
		array = new ArrayIndexedCollection(10);
		array.add("Oslo");
		array.add("Stockholm");
		array.add("Helsinki");
		array.add("Reykjavik");
		
		assertEquals("ArrayIndexedCollection [size = 4, elements = "
				+ "[Oslo, Stockholm, Helsinki, Reykjavik]]"
				, array.toString());
	}
}
