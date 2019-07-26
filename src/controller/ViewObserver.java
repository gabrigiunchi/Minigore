package controller;

import java.util.Optional;

import model.environment.MyEnvironmentBlocks;
import model.game.MyGameStrategies;


/**
 * Interfaccia del controller.
 * @author Lorenzo Mazzesi
 *
 */
public interface ViewObserver {

	/**
	 * Collega l'ecosistema dell'applicazione.
	 * @param env : ecosistema da collegare
	 */
	void setEnvironment(final MyEnvironmentBlocks env);
	/**
	 * Imposta la strategia del gioco.
	 * @param strategy : strategia da impostare
	 */
	void setStrategyGame(final MyGameStrategies strategy);
	/**
	 * Inizia un nuovo gioco.
	 */
	void newGame();
	/**
	 * Salva il gioco.
	 */
	void saveGame();
	/**
	 * Carica il gioco salvato in precedenza.
	 */
	void loadGame();
	/**
	 * Esce dal gioco.
	 */
	void quit();
	/**
	 * Viene richiamato a partita finita per trasmettere alla view il punteggio finale.
	 * @param score : punteggio realizzato
	 */
	void gameOver(final int score);
	/**
	 * Imposta un nuovo highscore.
	 * @param name : nome del giocatore che ha fatto il punteggio
	 */
	void setNewHighscore(final String name);
	/**
	 * Resetta tutti gli highscore.
	 * @param filename : nome del file che contiene gli highscore da resettare
	 */
	void clearHighScore(final String filename);
	/**
	 * Getter del game controller.
	 * @return gameController oppure Optional.empty()
	 */
	Optional<GameViewObserver> getGameController();
}
