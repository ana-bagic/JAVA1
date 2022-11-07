package hr.fer.zemris.java.gui.calc.buttons;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Razred modelira JButton za obavljanje operacije koja ima inverz.
 * 
 * @author Ana Bagić
 *
 */
public class InverseOperationButton extends JButton {

	private static final long serialVersionUID = 1L;
	/**
	 * Naziv primarne operacije.
	 */
	private String primary;
	/**
	 * Naziv operacije inverzne primarnoj.
	 */
	private String secondary;
	/**
	 * Primarna operacija.
	 */
	private DoubleUnaryOperator primOperator;
	/**
	 * Operacija inverzna primarnoj.
	 */
	private DoubleUnaryOperator secOperator;
	/**
	 * Zastavica koja označava je li trenutna operacija inverzna ili primarna.
	 */
	private boolean inverted = false;
	
	/**
	 * Stvara novi primjerak razreda na temelju zadane operacije.
	 * 
	 * @param operation operacija koja se izvršava pritiskom na gumb
	 * @param model model kalkulatora
	 */
	public InverseOperationButton(String operation, CalcModelImpl model) {
		super(operation);
		primary = operation;
		fillOperators();
		
		if(operation.equals("x^n")) {
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
				model.setPendingBinaryOperation((a,b) -> Math.pow(a, inverted ? 1/b : b));
				model.clear();
			});
		} else {
			addActionListener((e) -> {
				if(model.hasFrozenValue())
					throw new CalculatorInputException("Ne mogu se izvoditi operacije kada postoji zamrznuta vrijednost.");
				
				model.setValue(inverted ? secOperator.applyAsDouble(model.getValue())
										: primOperator.applyAsDouble(model.getValue()));
			});
		}
	}
	
	/**
	 * Invertira operaciju.
	 */
	public void invert() {
		inverted = !inverted;
		setText(inverted ? secondary : primary);
	}

	/**
	 * Pomoćna funkcija za inicijaliziranje operacija.
	 */
	private void fillOperators() {
		switch (primary) {
		case "sin" -> {
			secondary = "arcsin";
			primOperator = Math::sin;
			secOperator = Math::asin;
		}
		case "cos" -> {
			secondary = "arccos";
			primOperator = Math::cos;
			secOperator = Math::acos;
		}
		case "tan" -> {
			secondary = "arctan";
			primOperator = Math::tan;
			secOperator = Math::atan;
		}
		case "ctg" -> {
			secondary = "arcctg";
			primOperator = (x) -> (1.0/Math.tan(x));
			secOperator = (x) -> Math.atan(1.0/x);
		}
		case "log" -> {
			secondary = "10^x";
			primOperator = Math::log10;
			secOperator = (x) -> Math.pow(10, x);
		}
		case "ln" -> {
			secondary = "e^x";
			primOperator = Math::log;
			secOperator = (x) -> Math.pow(Math.E, x);
		}
		case "x^n" -> {
			secondary = "x^(1/n)";
		}
		case "1/x" -> {
			secondary = primary;
			primOperator = (x) -> 1.0/x;
			secOperator = primOperator;
		}
		default ->
			throw new IllegalArgumentException("Unexpected value: " + primary);
		}
	}

}
