package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.buttons.BinaryOperationButton;
import hr.fer.zemris.java.gui.calc.buttons.DigitButton;
import hr.fer.zemris.java.gui.calc.buttons.InverseOperationButton;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;
import hr.fer.zemris.java.gui.layouts.CalcLayout;

/**
 * Razred stvara korisničko sučelje kalulatora.
 * 
 * @author Ana Bagić
 *
 */
public class Calculator extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Model kalkulatora.
	 */
	private CalcModelImpl model = new CalcModelImpl();
	/**
	 * Spremnik komponenata.
	 */
	private Container cp;
	/**
	 * Stog korišten za realizaciju pojedinih funkcija kalkulatora.
	 */
	private Stack<Double> stack = new Stack<>();
	
	/**
	 * Konstruktor stvara novi kalkulator.
	 */
	public Calculator() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(600, 350);
		initGUI();
	}
	
	/**
	 * Pomoćna funkcija za postavljanje korisničkog sučelja.
	 */
	private void initGUI() {
		cp = getContentPane();
		cp.setLayout(new CalcLayout(5));
		cp.setSize(500, 150);
		
		addScreen();
		addButtons();
		addInvert();
		for(Component c : cp.getComponents()) {
			if(c instanceof JButton || c instanceof JCheckBox)
				c.setBackground(Color.LIGHT_GRAY);
		}
	}

	/**
	 * Pomoćna funkcija za dodavanje checkboxa za inverziju operacija na kalkulatoru.
	 */
	private void addInvert() {
		JCheckBox inv = new JCheckBox("Inv");
		inv.setOpaque(true);
		inv.addActionListener((e) -> {
			for(Component c : cp.getComponents()) {
				if(c instanceof InverseOperationButton)
					((InverseOperationButton) c).invert();
			}
		});
		cp.add(inv, "5,7");
	}

	/**
	 * Pomoćna funkcija za dodavanje ekrana kalkulatora.
	 */
	private void addScreen() {
		JLabel screen = new JLabel("0");
		screen.setBackground(Color.YELLOW);
		screen.setOpaque(true);
		screen.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		screen.setFont(screen.getFont().deriveFont(30f));
		screen.setHorizontalAlignment(SwingConstants.RIGHT);
		model.addCalcValueListener((m) -> {
			screen.setText(m.toString());
		});
		cp.add(screen, "1,1");
	}

	/**
	 * Pomoćna funkcija za dodavanje svih gumba kalkulatora.
	 */
	private void addButtons() {
		cp.add(new DigitButton(0, model), "5,3");
		cp.add(new DigitButton(1, model), "4,3");
		cp.add(new DigitButton(2, model), "4,4");
		cp.add(new DigitButton(3, model), "4,5");
		cp.add(new DigitButton(4, model), "3,3");
		cp.add(new DigitButton(5, model), "3,4");
		cp.add(new DigitButton(6, model), "3,5");
		cp.add(new DigitButton(7, model), "2,3");
		cp.add(new DigitButton(8, model), "2,4");
		cp.add(new DigitButton(9, model), "2,5");
		
		cp.add(new InverseOperationButton("1/x", model), "2,1");
		cp.add(new InverseOperationButton("log", model), "3,1");
		cp.add(new InverseOperationButton("ln", model), "4,1");
		cp.add(new InverseOperationButton("x^n", model), "5,1");
		cp.add(new InverseOperationButton("sin", model), "2,2");
		cp.add(new InverseOperationButton("cos", model), "3,2");
		cp.add(new InverseOperationButton("tan", model), "4,2");
		cp.add(new InverseOperationButton("ctg", model), "5,2");
		
		cp.add(new BinaryOperationButton("/", model), "2,6");
		cp.add(new BinaryOperationButton("*", model), "3,6");
		cp.add(new BinaryOperationButton("-", model), "4,6");
		cp.add(new BinaryOperationButton("+", model), "5,6");
		
		JButton sign = new JButton("+/-");
		sign.addActionListener((e) -> {
			if(model.isEditable())
				model.swapSign();
		});
		cp.add(sign, "5,4");
		
		JButton decimal = new JButton(".");
		decimal.addActionListener((e) -> {
			try {
				if(model.isEditable())
					model.insertDecimalPoint();
			} catch (CalculatorInputException ex) {
			}
		});
		cp.add(decimal, "5,5");
		
		JButton equals = new JButton("=");
		equals.addActionListener((e) -> {
			if(model.isActiveOperandSet() && model.getPendingBinaryOperation() != null) {
				double a = model.getActiveOperand();
				DoubleBinaryOperator op = model.getPendingBinaryOperation();
				double b = model.getValue();
				model.setValue(op.applyAsDouble(a, b));
				model.clearActiveOperand();
			}
		});
		cp.add(equals, "1,6");
		
		JButton clear = new JButton("clr");
		clear.addActionListener((e) -> {
			model.clear();
		});
		cp.add(clear, "1,7");
		
		JButton reset = new JButton("res");
		reset.addActionListener((e) -> {
			model.clearAll();
			stack.clear();
		});
		cp.add(reset, "2,7");
		
		JButton push = new JButton("push");
		push.addActionListener((e) -> {
			stack.push(model.getValue());
		});
		cp.add(push, "3,7");
		
		JButton pop = new JButton("pop");
		pop.addActionListener((e) -> {
			if(!stack.empty()) {
				model.setValue(stack.pop());
			} else {
				System.out.println("Stog je prazan.");
			}
		});
		cp.add(pop, "4,7");
	}

	/**
	 * Pokretanje kalkulatora.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Calculator().setVisible(true));
	}
}
