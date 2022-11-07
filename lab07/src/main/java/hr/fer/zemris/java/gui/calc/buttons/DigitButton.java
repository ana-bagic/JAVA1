package hr.fer.zemris.java.gui.calc.buttons;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;

/**
 * Razred modelira JButton za unos znamenki.
 * 
 * @author Ana Bagić
 *
 */
public class DigitButton extends JButton {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Stvara novi primjerak razreda na temelju zadane znamenke.
	 * 
	 * @param digit znamenka koju gumb unosi u kalkulator
	 * @param model model kalkulatora
	 */
	public DigitButton(int digit, CalcModelImpl model) {
		super(String.valueOf(digit));
		setFont(getFont().deriveFont(30f));
		
		addActionListener((e) -> {
			if(model.isEditable()) {
				model.insertDigit(digit);
			}
		});
	}

}
