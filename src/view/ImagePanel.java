package view;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

import static utilities.SystemInformation.PANELS_FOLDER;

/**
 * Lo scopo di questa classe è quello di creare un JPanel con sfondo personalizzato, 
 * estendibile per tutti i panel. Alle classi che lo estenderanno basterà modificare  
 * il campo protetto dell'immagine. 
 * 
 * @author Mattia Ricci 
 *
 */

public class ImagePanel extends JPanel {
	/**
	 * Il campo Image img è protetto perchè ogni panel che estende questa classe può 
	 * impostare il proprio sfondo. 
	 */
	private static final long serialVersionUID = 1L;
	protected Image img;  
	
	/**
	 * Imposta l'immagine da utilizzare.
	 */
	public ImagePanel() {
		this.img = LoadImage.load(PANELS_FOLDER + "Sfondo.png");
	}
	
	/**
	 * Disegna l'immagine nel panel, adattandola in base alla grandezza del panel. 
	 * 
	 * @param g
	 *
	 */
	
	@Override
	protected void paintComponent(final Graphics g) {
			setOpaque(false);
		    g.drawImage(this.img, 0, 0, this.getWidth(), this.getHeight(), this);
		    super.paintComponent(g);
	}
	
	
}
