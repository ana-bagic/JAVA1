package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Razred stvara novi model grafa na temelju datoteke i prikazuje ga.
 * 
 * @author Ana Bagić
 *
 */
public class BarChartDemo extends JFrame {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Konstruktor stvara novi prozor s prikazom grafa.
	 * 
	 * @param model model grafa
	 * @param path putanja do datoteke s modelom grafa
	 */
	public BarChartDemo(BarChart model, String path) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(600, 500);
		drawChart(model, path);
	}

	/**
	 * Funkcija dodaje komponente grafa i putanje do modela na prozor.
	 * 
	 * @param model model grafa
	 * @param path putanja do datoteke s modelom grafa
	 */
	private void drawChart(BarChart model, String path) {
		Path p = Paths.get(path).toAbsolutePath();
		BarChartComponent chart = new BarChartComponent(model);
		JLabel label = new JLabel(p.toString());
		label.setHorizontalAlignment(SwingConstants.CENTER);
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(label, BorderLayout.PAGE_START);
		cp.add(chart, BorderLayout.CENTER);
	}

	/**
	 * Pokretanje programa.
	 */
	public static void main(String[] args) {
		if(args.length != 1)
			throw new IllegalArgumentException("Mora postojati jedan argument");
		
		Scanner sc = null;
		try {
			sc = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String xDesc = sc.nextLine();
		String yDesc = sc.nextLine();
		String[] valuesString = sc.nextLine().split(" ");
		List<XYValue> values = new LinkedList<>();
		for(String v : valuesString) {
			values.add(XYValue.parse(v));
		}
		int minY = Integer.parseInt(sc.nextLine());
		int maxY = Integer.parseInt(sc.nextLine());
		int distanceY = Integer.parseInt(sc.nextLine());
		
		sc.close();
		
		BarChart model = new BarChart(values, xDesc, yDesc, minY, maxY, distanceY);
		
		SwingUtilities.invokeLater(() -> new BarChartDemo(model, args[0]).setVisible(true));
	}

}
