package view;

import java.awt.Color;

import javax.swing.JButton;

/** Lo scopo di questa classe è di creare dei bottoni, rendendo il codice più di chi la invoca più 
 * breve e compatto.
 * 
 * @author Mattia Ricci 
 *
 */

public final class MyJButton {
	
	private static final int DIM = 14;
	
	private MyJButton() { }
	
	/**
	 * 
	 * @param text
	 * 			  la stringa a mettere dentro al bottone
	 * @return
	 * 			  il bottone creato 
	 */
	
	public static JButton createButton(final String text) {
		final JButton button = new JButton(text);
		button.setBackground(Color.BLACK);
		button.setForeground(Color.RED);
		button.setFont(new java.awt.Font("Georgia", 1, DIM));
		button.setFocusable(false);
		return button;
	}
	
}
