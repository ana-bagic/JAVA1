package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Razred modelira prozor koji prikazuje dvije liste primarnih brojeva.
 * 
 * @author Ana Bagić
 *
 */
public class PrimDemo extends JFrame {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Konstruktor stvara novi objekt razreda PrimDemo.
	 */
	public PrimDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(600, 350);
		setTitle("Generator primarnih brojeva");
		initGUI();
	}

	/**
	 * Pomoćna funkcija za postavljanje korisničkog sučelja.
	 */
	private void initGUI() {
		PrimListModel model = new PrimListModel();
		
		JPanel central = new JPanel(new GridLayout(1, 0));
		central.add(new JScrollPane(new JList<>(model)));
		central.add(new JScrollPane(new JList<>(model)));
		
		JButton nextButton = new JButton("Sljedeći");
		nextButton.addActionListener(e -> {
			model.next();
		});
		
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(central, BorderLayout.CENTER);
		cp.add(nextButton, BorderLayout.PAGE_END);
	}

	/**
	 * Pokretanje programa.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new PrimDemo().setVisible(true));
	}

}
