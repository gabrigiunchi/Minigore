package view;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;

/**
 * Lo scopo di questa classe è di creare dei label, rendendo il codice più di chi la invoca più
 * breve e compatto.
 *  
 * @author Mattia Ricci 
 *
 */

public final class CreateLabel {
	
	private CreateLabel() { }
	
	/**
	 * 
	 * @param s
	 * 			  la stringa che compone il label
	 * @param font
	 * 			  il font del label
	 * @return
	 * 			  il label creato 
	 */
	
	public static JLabel createLabel(final String s, final Font font) {
		final JLabel label = new JLabel(s);
		label.setFont(font);
		label.setForeground(Color.WHITE);
		label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		return label;
	}
	
}
