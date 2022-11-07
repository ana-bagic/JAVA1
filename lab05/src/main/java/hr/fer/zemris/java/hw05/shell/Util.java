package hr.fer.zemris.java.hw05.shell;

import java.util.ArrayList;
import java.util.List;

/**
 * Razred koji sadrži pomoćne metode za rad s ljuskom.
 * 
 * @author Ana Bagić
 *
 */
public class Util {

	/**
	 * Metoda vadi argumente iz poslanog stringa.
	 * 
	 * @param argumentString argumenti u stringu
	 * @return lista argumenata 
	 */
	public static List<String> extractArguments(String argumentString) {
		List<String> arguments = new ArrayList<>();
		char[] data = argumentString.toCharArray();
		int currentIndex = 0;
		
		while(true) {
			while(data.length != currentIndex) {
				char nextChar = data[currentIndex];
				if(nextChar == '\t' || nextChar == ' ') {
					currentIndex++;
					continue;
				}
				break;
			}
			
			if(currentIndex == data.length)
				break;
			
			if(data[currentIndex] == '"') {
				boolean closed = false;
				currentIndex++;
				StringBuilder sb = new StringBuilder();
				
				while(data.length != currentIndex) {
					char nextChar = data[currentIndex++];
					
					if(nextChar == '"') {
						if(currentIndex != data.length && data[currentIndex] != ' ')
							throw new IllegalArgumentException("Argument surrounded with \"\" can only contain space character after it.");
						closed = true;
						break;
					}
					
					if(nextChar == '\\' && (data[currentIndex] == '"' || data[currentIndex] == '\\'))
						nextChar = data[currentIndex++];
					sb.append(nextChar);
				}
				
				if(!closed)
					throw new IllegalArgumentException("Quote is not closed.");
				
				arguments.add(sb.toString());
			} else {
				StringBuilder sb = new StringBuilder();
				
				while(data.length != currentIndex) {
					char nextChar = data[currentIndex++];
					if(nextChar != ' ') {
						sb.append(nextChar);
						continue;
					}
					break;
				}
				
				arguments.add(sb.toString());
			}

		}
		
		return arguments;
	}

}
