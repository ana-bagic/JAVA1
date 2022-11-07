package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

/**
 * Demonstracija rada stoga korištenjem postfix notacije.
 * 
 * @author Ana Bagić
 *
 */
public class StackDemo {

	/**
	 * Metoda koja testira implementaciju ObjectStack razreda.
	 * 
	 * @param args argumenti iz komandne linije
	 */
	public static void main(String[] args) {
		String[] expression = args[0].split("\\s+");
		ObjectStack stack = new ObjectStack();
		
		for(String element : expression) {
			try {  
				int number = Integer.parseInt(element); 
				stack.push(number);
			} catch(NumberFormatException exception) {  
				int b = (int) stack.pop();
				int a = (int) stack.pop();
				
				int result = switch (element) {
					case "+" -> a+b;
					case "-" -> a-b;
					case "*" -> a*b;
					case "/" -> {
						if(b == 0)
							throw new IllegalArgumentException("Pokušaj dijeljenja s nulom");
						yield a/b;
					}
					case "%" -> {
						if(b == 0)
							throw new IllegalArgumentException("Pokušaj dijeljenja s nulom");
						yield a%b;
					}
					default -> throw new IllegalArgumentException("Neispravan operator: " + element);
				};
				
				stack.push(result);
			}
		}
		
		if(stack.size() != 1)
			System.err.println("Krivo zadan izraz");
		else
			System.out.println("Izraz je evaluiran na " + stack.pop() + ".");
	}
}
