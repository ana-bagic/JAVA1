package hr.fer.zemris.lsystems.impl;

import hr.fer.oprpp1.custom.collections.ObjectStack;

/**
 * Razred omogućuje izvođenje postupka prikazivanja fraktala.
 * 
 * @author Ana Bagić
 *
 */
public class Context {

	/**
	 * Stog za pohranu stanja kornjače.
	 */
	private ObjectStack<TurtleState> stack;
	
	/**
	 * Konstruktor stvara novi stog za pohranu stanja kornjače.
	 */
	public Context() {
		stack = new ObjectStack<>();
	}
	
	/**
	 * Vraća stanje s vrha stoga bez da ga uklanja.
	 * 
	 * @return stanje s vrha stoga bez da ga uklanja
	 */
	public TurtleState getCurrentState() {
		return stack.peek();
	}
	
	/**
	 * Stavlja predano stanje na vrh stoga.
	 * 
	 * @param state stanje koje stavlja na vrh stoga
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}
	
	/**
	 * Briše jedno stanje s vrha stoga.
	 */
	public void popState() {
		stack.pop();
	}

}
