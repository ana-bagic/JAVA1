package hr.fer.oprpp1.hw05.crypto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * Tester za razred Util.
 * 
 * @author Ana Bagić
 *
 */
public class UtilTester {
	
	@Test
	public void oddStringHexToByteThrows() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("a4c88"));
	}
	
	@Test
	public void invalidCharactersHexToByteThrows() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("7z3a"));
	}
	
	@Test
	public void nullStringHexToByteThrows() {
		assertThrows(NullPointerException.class, () -> Util.hextobyte(null));
	}
	
	@Test
	public void emptyStringHexToByteTest() {
		byte[] array = Util.hextobyte("");
		
		assertEquals(0, array.length);
	}

	@Test
	public void hexToByteTest() {
		byte[] arrayLowercase = Util.hextobyte("01ae22");
		byte[] arrayUppercase = Util.hextobyte("01AE22");
		byte[] arrayLandU = Util.hextobyte("01Ae22");
		
		assertTrue(Arrays.equals(new byte[] {1,  -82, 34}, arrayLowercase));
		assertTrue(Arrays.equals(new byte[] {1,  -82, 34}, arrayUppercase));
		assertTrue(Arrays.equals(new byte[] {1,  -82, 34}, arrayLandU));
	}
	
	@Test
	public void nullArrayByteToHexThrows() {
		assertThrows(NullPointerException.class, () -> Util.bytetohex(null));
	}
	
	@Test
	public void emptyArrayByteToHexTest() {
		String str = Util.bytetohex(new byte[0]);
	
		assertEquals(0, str.length());
	}
	
	@Test
	public void byteToHexTest() {
		String str = Util.bytetohex(new byte[] {1, -82, 34});
		
		assertEquals("01ae22", str);
	}
}
