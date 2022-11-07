package hr.fer.oprpp1.hw08.jnotepadpp.utils;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;

import javax.swing.ImageIcon;

/**
 * Razred s pomoćnom metodama za aplikaciju Notepad++.
 * 
 * @author Ana Bagić
 *
 */
public class Utils {
	
	/**
	 * Ikonica koja označava da je trenutni dokument modificiran.
	 */
	public static final ImageIcon red = loadImage("red.png");
	/**
	 * Ikonica koja označava da trenutni dokument nije modificiran.
	 */
	public static final ImageIcon green = loadImage("green.png");
	
	/**
	 * Vraća primjerak razreda {@link ImageIcon} na temelju poslanog imena slike.
	 *
	 * @param iconName ime slike za koju se želi napraviti ikonica
	 * @return ikonica
	 */
	private static ImageIcon loadImage(String iconName) {
		Class<Utils> c = Utils.class;
		InputStream is = c.getResourceAsStream("icons/" + iconName);
		if(is == null)
			throw new NoSuchElementException("U projektu nema ikone s imenom " + iconName);
		
		ImageIcon img = null;
		try {
			byte[] bytes = is.readAllBytes();
			is.close();
			img = new ImageIcon(bytes);
			img = new ImageIcon(img.getImage().getScaledInstance(10, 10, Image.SCALE_DEFAULT));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return img;
	}

}
