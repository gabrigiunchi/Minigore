package model.game;

/**
 * Interfaccia di uno score, ossia il punteggio associato al nome.
 * 
 * @author Mattia Ricci, Lorenzo Mazzesi
 */
public interface Score {

	/**
	 * @return il punteggio conseguito
	 */
	int getScore();
	
	/**
	 * @return il nome del giocatore
	 */
	String getName();
}
