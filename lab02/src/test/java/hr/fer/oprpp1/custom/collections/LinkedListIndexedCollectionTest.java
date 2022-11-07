package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Razred koji testira metode i konstruktore razreda LinkedListIndexedCollection.
 * Kopirano iz prve zadaće.
 * 
 * @author Ana Bagić
 *
 */
public class LinkedListIndexedCollectionTest {

	private LinkedListIndexedCollection list;
	private LinkedListIndexedCollection list2;
	
	@Test
	public void testListDefaultConstructor() {
		list = new LinkedListIndexedCollection();
		assertEquals(0, list.size());
		list.add("1");
		list.add(2);
		list.add(3.0);
		
		assertArrayEquals(new Object[] {"1", 2, 3.0}, list.toArray());
	}
	
	@Test
	public void testListCollectionConstructorThrows() {
		assertThrows(NullPointerException.class, () -> new LinkedListIndexedCollection(null));
	}
	
	@Test
	public void testListCollectionConstructor() {
		list = new LinkedListIndexedCollection();
		list.add("1");
		list.add(2);
		list.add(3.0);
		list2 = new LinkedListIndexedCollection(list);
		
		assertArrayEquals(new Object[] {"1", 2, 3.0}, list2.toArray());
	}
	
	@Test
	public void testListSize0() {
		list = new LinkedListIndexedCollection();
		
		assertEquals(0, list.size());
	}
	
	@Test
	public void testListSize4() {
		list = new LinkedListIndexedCollection();
		list.add("Oslo");
		list.add("Stockholm");
		list.add("Helsinki");
		list.add("Reykjavik");
		
		assertEquals(4, list.size());
	}
	
	@Test
	public void testListAddThrows() {
		list = new LinkedListIndexedCollection();
		list.add("Oslo");
		list.add("Stockholm");
		
		assertThrows(NullPointerException.class, () -> list.add(null));
	}
	
	@Test
	public void testListAdd() {
		list = new LinkedListIndexedCollection();
		list.add("Oslo");
		list.add("Stockholm");
		assertEquals(2, list.size());
		list.add("Helsinki");
		list.add("Reykjavik");
		list.add("Copenhagen");
		assertEquals(5, list.size());
	}
	
	@Test
	public void testListAddDuplicate() {
		list = new LinkedListIndexedCollection();
		list.add("Oslo");
		list.add("Stockholm");
		assertEquals(2, list.size());
		list.add("Oslo");
		list.add("Stockholm");
		list.add("Copenhagen");
		assertEquals(5, list.size());
	}
	
	@Test
	public void testListInsertThrowsNullPointer() {
		list = new LinkedListIndexedCollection();
		list.add("Oslo");
		list.add("Stockholm");
		list.add("Helsinki");
		
		assertThrows(NullPointerException.class, () -> list.insert(null, 1));
	}
	
	@Test
	public void testListInsertThrowsIndexOutOfBoundsNegative() {
		list = new LinkedListIndexedCollection();
		list.add("Oslo");
		list.add("Stockholm");
		list.add("Helsinki");
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.insert("London", -1));
	}
	
	@Test
	public void testListInsertThrowsIndexOutOfBoundsTooBig() {
		list = new LinkedListIndexedCollection();
		list.add("Oslo");
		list.add("Stockholm");
		list.add("Helsinki");
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.insert("London", 5));
	}
	
	@Test
	public void testListInsert() {
		list = new LinkedListIndexedCollection();
		list.add("Oslo");
		list.add("Stockholm");
		list.add("Helsinki");
		list.add("Reykjavik");
		list.insert("London", 1);
		assertEquals(5, list.size());
		assertEquals("London", list.get(1));
		assertEquals("Stockholm", list.get(2));
	}
	
	@Test
	public void testListInsertDuplicate() {
		list = new LinkedListIndexedCollection();
		list.add("Oslo");
		list.add("Stockholm");
		list.add("Helsinki");
		list.add("Reykjavik");
		list.insert("Helsinki", 1);
		assertEquals(5, list.size());
		assertEquals("Helsinki", list.get(1));
		assertEquals("Stockholm", list.get(2));
	}
	
	@Test
	public void testListContainsTrue() {
		list = new LinkedListIndexedCollection();
		list.add("Oslo");
		list.add("Stockholm");
		list.add("Helsinki");
		list.add("Reykjavik");
		assertTrue(list.contains("Helsinki"));
	}
	
	@Test
	public void testListContainsFalse() {
		list = new LinkedListIndexedCollection();
		list.add("Oslo");
		list.add("Stockholm");
		list.add("Helsinki");
		list.add("Reykjavik");
		assertFalse(list.contains("Copenhagen"));
	}
	
	@Test
	public void testListContainsFalseNumbers() {
		list = new LinkedListIndexedCollection();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		assertFalse(list.contains(1));
	}
	
	@Test
	public void testListGetThrowsIndexOutOfBoundsNegative() {
		list = new LinkedListIndexedCollection();
		list.add("Oslo");
		list.add("Stockholm");
		list.add("Helsinki");
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.insert("London", -1));
	}
	
	@Test
	public void testListGetThrowsIndexOutOfBoundsTooBig() {
		list = new LinkedListIndexedCollection();
		list.add("Oslo");
		list.add("Stockholm");
		list.add("Helsinki");
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.insert("London", 6));
	}
	
	@Test
	public void testListGet() {
		list = new LinkedListIndexedCollection();
		list.add("Oslo");
		list.add("Stockholm");
		list.add("Helsinki");
		list.add("Reykjavik");
		list.insert("London", 1);
		assertEquals("Oslo", list.get(0));
		assertEquals("London", list.get(1));
		assertEquals("Stockholm", list.get(2));
		assertEquals("Helsinki", list.get(3));
		assertEquals("Reykjavik", list.get(4));
	}
	
	@Test
	public void testIndexOfNullEmptyList() {
		list = new LinkedListIndexedCollection();
		assertEquals(-1, list.indexOf(null));
	}
	
	@Test
	public void testIndexOfNullFullList() {
		list = new LinkedListIndexedCollection();
		list.add("Oslo");
		list.add("Stockholm");
		list.add("Helsinki");
		list.add("Reykjavik");
		assertEquals(-1, list.indexOf(null));
	}
	
	@Test
	public void testIndexOfNotInList() {
		list = new LinkedListIndexedCollection();
		list.add("Oslo");
		list.add("Stockholm");
		list.add("Helsinki");
		list.add("Reykjavik");
		assertEquals(-1, list.indexOf("Copenhagen"));
	}
	
	@Test
	public void testIndexOfNotInListNumbers() {
		list = new LinkedListIndexedCollection();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		assertEquals(-1, list.indexOf(1));
	}
	
	@Test
	public void testListIndexOf() {
		list = new LinkedListIndexedCollection();
		list.add("Oslo");
		list.add("Stockholm");
		list.add("Helsinki");
		list.add("Reykjavik");
		assertEquals(1, list.indexOf("Stockholm"));
		assertEquals(3, list.indexOf("Reykjavik"));
	}
	
	@Test
	public void testListForEachThrowsNullPointer() {
		list = new LinkedListIndexedCollection();
		list.add("Oslo");
		list.add("Stockholm");
		list.add("Helsinki");
		
		assertThrows(NullPointerException.class, () -> list.forEach(null));
	}
	
	@Test
	public void testListRemoveValueTrue() {
		list = new LinkedListIndexedCollection();
		list.add("Oslo");
		list.add("Stockholm");
		list.add("Helsinki");
		list.add("Reykjavik");
		assertTrue(list.remove("Stockholm"));
		assertTrue(list.remove("Reykjavik"));
		assertEquals(2, list.size());
	}
	
	@Test
	public void testListRemoveValueFalse() {
		list = new LinkedListIndexedCollection();
		list.add("Oslo");
		list.add("Stockholm");
		list.add("Helsinki");
		list.add("Reykjavik");
		assertFalse(list.remove("Copenhagen"));
		assertFalse(list.remove("London"));
		assertEquals(4, list.size());
	}
	
	@Test
	public void testListRemoveValueTrueDuplicate() {
		list = new LinkedListIndexedCollection();
		list.add("Helsinki");
		list.add("Stockholm");
		list.add("Helsinki");
		list.add("Oslo");
		assertTrue(list.remove("Helsinki"));
		assertTrue(list.remove("Helsinki"));
		assertEquals(2, list.size());
	}
	
	@Test
	public void testListRemoveValueFalseDuplicate() {
		list = new LinkedListIndexedCollection();
		list.add("Oslo");
		list.add("Stockholm");
		list.add("Helsinki");
		list.add("Reykjavik");
		assertTrue(list.remove("Stockholm"));
		assertFalse(list.remove("Stockholm"));
		assertEquals(3, list.size());
	}
	
	@Test
	public void testListRemoveValueNull() {
		list = new LinkedListIndexedCollection();
		list.add("Oslo");
		list.add("Stockholm");
		list.add("Helsinki");
		list.add("Reykjavik");
		assertFalse(list.remove(null));
		assertEquals(4, list.size());
	}
	
	@Test
	public void testListRemoveIndexTrue() {
		list = new LinkedListIndexedCollection();
		list.add("Oslo");
		list.add("Stockholm");
		list.add("Helsinki");
		list.add("Reykjavik");
		list.remove(2);
		list.remove(0);
		assertFalse(list.contains("Helsinki"));
		assertFalse(list.contains("Oslo"));
		assertEquals(2, list.size());
	}
	
	@Test
	public void testListRemoveIndexThrowsIndexOutOfBoundsNegative() {
		list = new LinkedListIndexedCollection();
		list.add("Oslo");
		list.add("Stockholm");
		list.add("Helsinki");
		list.add("Reykjavik");
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
		assertEquals(4, list.size());
	}
	
	@Test
	public void testListRemoveIndexThrowsIndexOutOfBoundsTooBig() {
		list = new LinkedListIndexedCollection();
		list.add("Oslo");
		list.add("Stockholm");
		list.add("Helsinki");
		list.add("Reykjavik");
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(6));
		assertEquals(4, list.size());
	}
	
	@Test
	public void testListRemoveIndexThrowsIndexOutOfBoundsDuplicate() {
		list = new LinkedListIndexedCollection();
		list.add("Helsinki");
		list.add("Stockholm");
		list.add("Helsinki");
		list.add("Oslo");
		list.remove(3);
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(3));
		assertEquals(3, list.size());
	}
	
	@Test
	public void testListClearEmpty() {
		list = new LinkedListIndexedCollection();
		list.clear();
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
		assertEquals(0, list.size());
	}
	
	@Test
	public void testListClear() {
		list = new LinkedListIndexedCollection();
		list.add("Helsinki");
		list.add("Stockholm");
		list.add("Helsinki");
		list.add("Oslo");
		
		assertEquals("Helsinki", list.get(2));
		assertEquals(4, list.size());
		list.clear();
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(2));
		assertEquals(0, list.size());
	}
	
	@Test
	public void testListToString() {
		list = new LinkedListIndexedCollection();
		list.add("Oslo");
		list.add("Stockholm");
		list.add("Helsinki");
		list.add("Reykjavik");
		
		assertEquals("LinkedListIndexedCollection [size = 4, elements = "
				+ "[Oslo, Stockholm, Helsinki, Reykjavik]]"
				, list.toString());
	}	
}
