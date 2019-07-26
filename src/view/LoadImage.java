package view;

import java.awt.Image;

import javax.swing.ImageIcon;

/** Lo scopo di questa classe è di permettere con un metodo statico di modificare l'immagine di sfondo
 *  ogni volta che viene cambiato panel. 
 * 
 * @author Mattia Ricci 
 *
 */

public final class LoadImage {
	
	private LoadImage() { }
	
	/**Questo metodo prende in input una stringa contente il path e restituisce l'immagine. 
	 * 
	 * @param path 
	 *               la stringa contente il path dell'immagine
	 * @return
	 * 				 l'immagine da usare come sfondo
	 */
	public static Image load(final String path) {
		return new ImageIcon(LoadImage.class.getResource(path)).getImage();
	}
	
}