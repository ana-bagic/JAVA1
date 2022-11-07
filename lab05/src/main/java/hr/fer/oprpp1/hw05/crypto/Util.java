package hr.fer.oprpp1.hw05.crypto;

/**
 * Razred sadrži pomoćne metode za kriptiranje i računanje digitalnog potpisa.
 * 
 * @author Ana Bagić
 *
 */
public class Util {

	/**
	 * Pretvara heksadekadski niz brojeva u polje bajtova.
	 * 
	 * @param keyText heksadekadski niz brojeva
	 * @return polje bajtova
	 */
	public static byte[] hextobyte(String keyText) {
		int len = keyText.length();
		
		if(len % 2 != 0)
			throw new IllegalArgumentException("Length of the argument has to be even.");
		
	    byte[] data = new byte[len/2];
	    
	    for (int i = 0; i < len; i += 2) {
	    	int numberLeft = hexCharToInt(keyText.charAt(i));
	    	int numberRight = hexCharToInt(keyText.charAt(i+1));
	    	
	    	if(numberLeft != -1 && numberRight != -1)
	    		data[i/2] = (byte) ((numberLeft << 4) + numberRight);
	    	else
	    		throw new IllegalArgumentException("Invalid characters: "
	    				+ keyText.charAt(i) + keyText.charAt(i+1));
	    }
	    
	    return data;
	}

	/**
	 * Pretvara polje bajtova u heksadekadski niz brojeva.
	 * 
	 * @param bytearray polje bajtova
	 * @return heksadekadski niz brojeva
	 */
	public static String bytetohex(byte[] bytearray) {
		StringBuilder sb = new StringBuilder();
		
	    for (byte b : bytearray) {
	    	int firstNumber = (int) (b & 0xf0) >> 4;
	    	int secondNumber = (int) b & 0x0f;
	    	
			char firstHex = intToHexChar(firstNumber);
			char secondHex = intToHexChar(secondNumber);
			
			sb.append(firstHex).append(secondHex);
	    }
	    
	    return sb.toString();
	}
	
	/**
	 * Pomoćna funkcija za pretvorbu dekadskog broja u heksadekadsku znamenku (lowercase).
	 */
	private static char intToHexChar(int number) {
		if(number >= 0 && number <= 9)
			return (char) (number + 48);
		
		if(number >= 10 && number <= 15)
			return (char) (number + 87);
		
		return (char) 0;
	}

	/**
	 * Pomoćna funkcija za pretvorbu heksadekadske znamenke u dekadski broj.
	 */
	private static int hexCharToInt(char ch) {
		if(ch >= 48 && ch <= 57)
			return ch - 48;
		
		if(ch >= 65 && ch <= 70)
			return ch - 55;
		
		if(ch >= 97 && ch <= 102)
			return ch - 87;
		
		return -1;	
	}

}
