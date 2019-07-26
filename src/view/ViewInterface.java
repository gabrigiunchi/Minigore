package view;

import controller.ViewObserver;
import model.game.Game;

/** Interfaccia per mettere in comunicazione il ViewImpl con i vari panel e con il controller.
 * 
 * @author Mattia Ricci 
 *
 */

public interface ViewInterface {
	
	/**
	 * Questo metodo permette al Main di passare alla view il controller.
	 * 
	 * @param controller 
	 *                     il ViewObserver
	 */
	void setController(final ViewObserver controller);
	
	/**
	 * 	Permette la disegnazione degli elementi del GamePanel.
	 * 
	 * @param game
	 * 				la classe Game del Model
	 */
	void drawModel(final Game game);
	
	/**
	 * Mostra il PausePanel.
	 */
	void showPause();
	
	/**
	 * Mostra il GameOverPanel, e gli passa il punteggio finale.
	 * @param score	
	 * 				punteggio finale della partita
	 */
	void showgameOver(final int score);
	
	/**
	 * Mostra il MenuPanel.
	 */
	void showMenu();
	
	/**
	 * Mostra il CreditPanel.
	 */
	void showCredits();
	
	/**
	 * Mostra il GamePanel.
	 */
	void showGame();
	
	/**
	 * Mostra l'OptionPanel.
	 */
	void showOptions();
	
	/**
	 * Mostra il ModesPanel.
	 */
	void showModes();
	
	/**
	 * Mostra l'HighScorePanel.
	 */
	void showHighScore();
	
	/**
	 * Mostra una finestra d'errore, in base all'errore e alla stringa.
	 * 
	 * @param error
	 *                Viene spiegato l'errore
	 * @param title
	 *                Titolo della finestra
	 */
	void showError(final String error, final String title);
	
	/**
	 * Apre un dialog che richiede il nome in caso il punteggio finale sia tra i migliori 10
	 * di quella modalità. Quando verrà cercato di salvare il nome, verrà invocato il metodo showError
	 * se la stringa non rispetta certi parametri.
	 */
	void askName();
}
