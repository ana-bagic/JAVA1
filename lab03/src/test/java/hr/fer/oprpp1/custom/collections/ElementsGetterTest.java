package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Razred koji testira default metodu razreda ElementsGetter.
 * 
 * @author Ana Bagić
 *
 */
public class ElementsGetterTest {
	
    @Test
    public void testProcessRemaining() {
        Collection<String> col = new ArrayIndexedCollection<>();
        col.add("Oslo");
        col.add("Copenhagen");
        col.add("Helsinki");
        
        ElementsGetter<String> getter = col.createElementsGetter();
        getter.getNextElement();
        
        StringBuilder sb = new StringBuilder();
        getter.processRemaining(sb::append);
        assertEquals("CopenhagenHelsinki",sb.toString());
    }
}
