package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

/**
 * Razred stvara komponentu koja može prikazati stupčasti graf.
 * 
 * @author Ana Bagić
 *
 */
public class BarChartComponent extends JComponent {

	private static final long serialVersionUID = 1L;
	/**
	 * Model grafa.
	 */
	private BarChart chart;
	
	/**
	 * Konstruktor prikazuje graf na temelju poslanog modela.
	 * 
	 * @param chart model grafa
	 */
	public BarChartComponent(BarChart chart) {
		this.chart = chart;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		int H = getHeight();
		int W = getWidth();
		g2.setFont(new Font("default", Font.BOLD, 12));
		FontMetrics fm = g2.getFontMetrics();
		int dist = 10;
		
		// calculating coordinate (0,0) position
		char[] maxY = String.valueOf(chart.getMaxY()).toCharArray();
		int maxYWidth = fm.charsWidth(maxY, 0, maxY.length);
		int xOrigin = fm.getAscent() + maxYWidth + dist*3;
		int yOrigin = H - fm.getAscent()*2 - dist*3;
		
		// descriptions
		char[] descX = chart.getxAxisDesc().toCharArray();
		int xDescX = xOrigin + (W-25-xOrigin - fm.charsWidth(descX, 0, descX.length))/2;
		g2.drawString(chart.getxAxisDesc(), xDescX, H-fm.getDescent()-dist);
		
		AffineTransform defaultAT = g2.getTransform();
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		
		g2.setTransform(at);
		char[] descY = chart.getyAxisDesc().toCharArray();
		int yDescY = yOrigin - (yOrigin-25 - fm.charsWidth(descY, 0, descY.length)-100)/2;
		g2.drawString(chart.getyAxisDesc(), -1*yDescY, fm.getAscent()+dist);
		g2.setTransform(defaultAT);
		
		// x&y axis
		g2.drawLine(xOrigin, yOrigin, W-20, yOrigin);
		g2.drawLine(xOrigin, yOrigin, xOrigin, 20);
		
		// arrows
		g2.fillPolygon(new int[] {W-20, W-20, W-15}, new int[] {yOrigin-5, yOrigin+5, yOrigin}, 3);
		g2.fillPolygon(new int[] {xOrigin-5, xOrigin, xOrigin+5}, new int[] {20, 15, 20}, 3);
		
		// x axis marks & data columns
		int nrOfXs = chart.getValues().size();
		int xWidthDistance = (W-25-xOrigin)/nrOfXs;
		int nrOfYs = (chart.getMaxY()-chart.getMinY())/chart.getyDistance()+1;
		int yHeightDistance = (yOrigin-25)/(nrOfYs-1);
		
		int xPos = xOrigin;
		for(XYValue value : chart.getValues()) {
			g2.drawLine(xPos, yOrigin, xPos, yOrigin+4);
			
			String xNumber = String.valueOf(value.getX());
			int nrPos = xPos + xWidthDistance/2 - fm.charsWidth(xNumber.toCharArray(), 0, xNumber.length())/2;
			g2.drawString(xNumber, nrPos, yOrigin+dist+fm.getAscent());
			
			int height = yHeightDistance * (value.getY()-chart.getMinY())/chart.getyDistance();
			g2.setColor(Color.ORANGE);
			g2.fillRect(xPos+1, yOrigin-height+1, xWidthDistance-1, height-1);
			g2.setColor(Color.BLACK);
			
			xPos += xWidthDistance;
		}
		g2.drawLine(xPos, yOrigin, xPos, yOrigin+4);
		
		// y axis marks
		for(int j = 0; j < nrOfYs; j++) {
			int yPos = yOrigin - j*yHeightDistance;
			g2.drawLine(xOrigin-4, yPos, xOrigin, yPos);
			
			yPos += fm.getAscent()/2;
			String yNumber = String.valueOf(chart.getMinY() + j*chart.getyDistance());
			g2.drawString(yNumber, xOrigin-dist-fm.charsWidth(yNumber.toCharArray(), 0, yNumber.length()), yPos);
		}
	
	}
	
}
