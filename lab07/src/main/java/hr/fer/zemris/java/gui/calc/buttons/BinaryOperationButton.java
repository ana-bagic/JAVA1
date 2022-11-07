package hr.fer.zemris.java.gui.calc.buttons;

import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Razred modelira JButton za obavljanje binarne operacije.
 * 
 * @author Ana Bagić
 *
 */
public class BinaryOperationButton extends JButton {

	private static final long serialVersionUID = 1L;
	/**
	 * Operacija koja se obavlja pritiskom na gumb.
	 */
	private DoubleBinaryOperator operator;
	
	/**
	 * Stvara novi primjerak razreda na temelju zadane operacije.
	 * 
	 * @param operation operacija koja se izvršava pritiskom na gumb
	 * @param model model kalkulatora
	 */
	public BinaryOperationButton(String operation, CalcModelImpl model) {
		super(operation);
		fillOperators(operation);
		
		addActionListener((e) -> {
			if(model.hasFrozenValue())
				throw new CalculatorInputException("Ne mogu se izvoditi operacije kada postoji zamrznuta vrijednost.");
			
			if(model.isActiveOperandSet() && model.getPendingBinaryOperation() != null) {
				double a = model.getActiveOperand();
				DoubleBinaryOperator op = model.getPendingBinaryOperation();
				double b = model.getValue();
				model.setValue(op.applyAsDouble(a, b));
			}
			model.freezeValue(model.toString());
			model.setActiveOperand(model.getValue());
			model.setPendingBinaryOperation(operator);
			model.clear();
		});
	}

	/**
	 * Pomoćna funkcija za inicijaliziranje operacije.
	 * 
	 * @param operation operacija koja se želi postaviti za trenutni gumb
	 */
	private void fillOperators(String operation) {
		operator = switch (operation) {
		case "/" -> (a,b) -> a/b;
		case "*" -> (a,b) -> a*b;
		case "-" -> (a,b) -> a-b;
		case "+" -> (a,b) -> a+b;
		default ->
		throw new IllegalArgumentException("Unexpected value: " + operation);
		};
	}

}
