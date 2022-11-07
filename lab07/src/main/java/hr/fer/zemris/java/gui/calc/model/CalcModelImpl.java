package hr.fer.zemris.java.gui.calc.model;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.DoubleBinaryOperator;

/**
 * Implementacija modela kalkulatora.
 * 
 * @author Ana Bagić
 *
 */
public class CalcModelImpl implements CalcModel {
	
	/**
	 * Je li model editabilan (može se dopisivati).
	 */
	private boolean editable = true;
	/**
	 * Je li broj pozitivan.
	 */
	private boolean positive = true;
	/**
	 * Unesene znamenke.
	 */
	private String input = "";
	/**
	 * Vrijednost unesenih znamenki.
	 */
	private double inputValue = 0;
	/**
	 * Zamrznuta vrijednost prikaza.
	 */
	private String frozenValue = null;
	/**
	 * Aktivan operand.
	 */
	private Double activeOperand = null;
	/**
	 * Zakazana operacija.
	 */
	private DoubleBinaryOperator pendingOperation = null;
	/**
	 * Promatrači ovog modela kalkulatora.
	 */
	private Set<CalcValueListener> listeners = new LinkedHashSet<>();

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		Objects.requireNonNull(l, "Promatrač ne smije biti null.");
		
		listeners.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		Objects.requireNonNull(l, "Promatrač ne smije biti null.");
		
		listeners.remove(l);
	}
	
	@Override
	public String toString() {
		if(frozenValue != null)
			return frozenValue;
		return (positive ? "" : "-") + (input.isEmpty() ? 0 : input);
	}

	@Override
	public double getValue() {
		return inputValue*(positive ? 1 : -1);
	}

	@Override
	public void setValue(double value) {
		editable = false;
		positive = value >= 0;
		inputValue = Math.abs(value);
		input = String.valueOf(Math.abs(value));
		frozenValue = null;
		listeners.forEach((l) -> l.valueChanged(this));
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void freezeValue(String value) {
		frozenValue = value;
	}

	@Override
	public boolean hasFrozenValue() {
		return frozenValue != null;
	}

	@Override
	public void clear() {
		input = "";
		inputValue = 0;
		positive = true;
		editable = true;
		listeners.forEach((l) -> l.valueChanged(this));
	}

	@Override
	public void clearAll() {
		clear();
		activeOperand = null;
		pendingOperation = null;
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if(!editable)
			throw new CalculatorInputException("Kalkulator nije editabilan.");
		
		positive = !positive;
		frozenValue = null;
		listeners.forEach((l) -> l.valueChanged(this));
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if(!editable)
			throw new CalculatorInputException("Kalkulator nije editabilan.");
		if(input.contains(".") || input.isEmpty())
			throw new CalculatorInputException("Pogreška kod dodavanja decimalne točke.");
		
		input += ".";
		frozenValue = null;
		listeners.forEach((l) -> l.valueChanged(this));
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if(digit < 0 || digit > 9)
			throw new IllegalArgumentException("Znamenka treba biti iz intervala 0,..,9.");
		if(!editable)
			throw new CalculatorInputException("Kalkulator nije editabilan.");
		
		if(Double.isInfinite(Double.parseDouble(input + digit)))
			throw new CalculatorInputException("Broj je prevelik za konačnu reprezentaciju u double tipu.");
	
		if(!input.contains(".")) {
			inputValue *= 10;
			inputValue += digit;
		} else {
			inputValue += digit*Math.pow(10, input.indexOf(".") - input.length());
		}
		
		if(input.equals("0")) {
			input = String.valueOf(digit);
		}
		else {
			input += digit;
		}
		frozenValue = null;
		listeners.forEach((l) -> l.valueChanged(this));
	}

	@Override
	public boolean isActiveOperandSet() {
		return activeOperand != null;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if(activeOperand == null)
			throw new IllegalStateException("Aktivan operand nije postavljen.");
		
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = null;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		this.pendingOperation = op;
	}

}
