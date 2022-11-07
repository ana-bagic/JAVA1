package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

/**
 * Razred koji testira metode i konstruktore razreda CalcLayout.
 * 
 * @author Ana Bagić
 *
 */
public class CalcLayoutTest {

	@Test
	public void constraintRowIncorrectThrows() {
		JPanel p = new JPanel(new CalcLayout());

		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel(), new RCPosition(-1, 5)));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel(), "6,5"));
	}
	
	@Test
	public void constraintColumnIncorrectThrows() {
		JPanel p = new JPanel(new CalcLayout());

		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel(), new RCPosition(3, -1)));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel(), "3,8"));
	}
	
	@Test
	public void constraintColumnIncorrectFirstRowThrows() {
		JPanel p = new JPanel(new CalcLayout());

		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel(), new RCPosition(1, 2)));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel(), "1,4"));
	}
	
	@Test
	public void constraintSameThrows() {
		JPanel p = new JPanel(new CalcLayout());

		p.add(new JLabel(), "2,5");
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel(), new RCPosition(2, 5)));
	}
	
	@Test
	public void preferredSizeTest() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(10, 30));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(20, 15));
		
		p.add(l1, new RCPosition(2, 2));
		p.add(l2, new RCPosition(3, 3));
		
		assertEquals(new Dimension(152, 158), p.getPreferredSize());
	}

	@Test
	public void preferredSizeFirstRowTest() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(108, 15));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(16, 30));
		
		p.add(l1, new RCPosition(1, 1));
		p.add(l2, new RCPosition(3, 3));
		
		assertEquals(new Dimension(152, 158), p.getPreferredSize());
	}

}
