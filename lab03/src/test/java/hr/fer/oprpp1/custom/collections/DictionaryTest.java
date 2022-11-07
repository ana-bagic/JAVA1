package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Razred koji testira metode i konstruktore razreda Dictionary.
 * 
 * @author Ana Bagić
 *
 */
public class DictionaryTest {
	
	Dictionary<String, Integer> dictionary;
	
	@Test
    public void testIsEmpty(){
        dictionary = new Dictionary<>();

	    assertTrue(dictionary.isEmpty());
    }
	
	@Test
    public void testSize(){
        dictionary = new Dictionary<>();

        dictionary.put("Oslo", 1);
        dictionary.put("Stockholm", 2);

        assertEquals(2, dictionary.size());
    }

    @Test
    public void testClear(){
        dictionary = new Dictionary<>();

        dictionary.put("Oslo", 1);
        dictionary.put("Stockholm", 2);
        dictionary.clear();

        assertTrue(dictionary.isEmpty());
    }
	
    @Test
    public void testPutAndGet(){
        dictionary = new Dictionary<>();

        dictionary.put("Oslo", 1);
        dictionary.put("Stockholm", 2);
        dictionary.put("Helsinki", 2);
        dictionary.put("Copenhagen", 4);

        assertEquals(2, dictionary.get("Stockholm"));
    }
    
    @Test
    public void testPutThrows(){
        dictionary = new Dictionary<>();

        dictionary.put("Oslo", 1);
        dictionary.put("Stockholm", 2);
        dictionary.put("Helsinki", 2);
        assertThrows(NullPointerException.class, () -> dictionary.put(null, 4));
    }
    
    @Test
    public void testPutOldKeyAndGet(){
        dictionary = new Dictionary<>();

        dictionary.put("Oslo", 1);
        dictionary.put("Stockholm", 2);
        dictionary.put("Helsinki", 2);
        dictionary.put("Oslo", 4);
        assertEquals(3, dictionary.size());

        assertEquals(4, dictionary.get("Oslo"));
    }
    
    @Test
    public void testGetNoKey(){
        dictionary = new Dictionary<>();

        dictionary.put("Oslo", 1);
        dictionary.put("Stockholm", 2);
        dictionary.put("Helsinki", 2);

        assertNull(dictionary.get("Copenhagen"));
    }

    @Test
    public void testRemove(){
        dictionary = new Dictionary<>();

        dictionary.put("Oslo", 1);
        dictionary.put("Stockholm", 2);
        dictionary.put("Helsinki", 3);
        assertEquals(3, dictionary.size());

        dictionary.remove("Stockholm");
        assertNull(dictionary.get("Stockholm"));
        assertEquals(2, dictionary.size());
    }

    @Test
    public void testRemoveGetsOldValue(){
        dictionary = new Dictionary<>();

        dictionary.put("Helsinki", 1);
        dictionary.put("Oslo", 1);
        dictionary.put("Stockholm", 2);
        dictionary.put("Helsinki", 3);

        assertEquals(3, dictionary.remove("Helsinki"));
    }

}
