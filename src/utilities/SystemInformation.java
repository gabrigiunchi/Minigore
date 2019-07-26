package utilities;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Informazioni del sistema e direttive utili per i salvataggi dei dati dell'applicazione.
 * 
 * @author Mattia Ricci 
 */
public final class SystemInformation {
	
	/**
	 * Informazioni del sistema.
	 */
	public static final String HOME_FOLDER = System.getProperty("user.home");
	/**
	 * Informazioni del sistema.
	 */
	public static final String FILE_SEPARATOR = System.getProperty("file.separator");
	/**
	 * Dimensione dello schermo.
	 */
	public static final Dimension SCREEN_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();
	/**
	 * Larghezza dello schermo.
	 */
	public static final int W_SCREEN = (int) SCREEN_DIMENSION.getWidth();
	/**
	 * Altezza del sistema.
	 */
	public static final int H_SCREEN = (int) SCREEN_DIMENSION.getHeight();
	
	/**
	 * Cartelle di destinazione dei dati dell'applicazione.
	 */
	public static final String MY_FOLDER = HOME_FOLDER + FILE_SEPARATOR + "Minigore" + FILE_SEPARATOR;
	/**
	 * Cartelle di destinazione dei dati dell'applicazione.
	 */
	public static final String HIGHSCORE_FOLDER = MY_FOLDER + FILE_SEPARATOR + "HighScore" + FILE_SEPARATOR;
	/**
	 * Cartelle di destinazione dei dati dell'applicazione.
	 */
	public static final String SAVES_FOLDER = MY_FOLDER + FILE_SEPARATOR + "Saves" + FILE_SEPARATOR;
	
	/**
	 * File su cui salvare le partite.
	 */
	public static final String SAVES_FILENAME = "game.bin";
	
	/**
	 * Risorse presenti nel workspace.
	 */
	public static final String BLOCK_FOLDER = "/block/";
	/**
	 * Directory del workspace per le immagini.
	 */
	public static final String IMAGES_FOLDER = "/images/";
	/**
	 * Directory del workspace per i panel.
	 */
	public static final String PANELS_FOLDER =  "/panels/";
	/**
	 * Directory del workspace per i suoni.
	 */
	public static final String SOUNDS_FOLDER = "/sounds/";
	
	private SystemInformation() { };
}
