package controller;

import model.environment.Direction;

/**
 * Interfaccia del controller di gioco.
 * @author Lorenzo Mazzesi
 *
 */
public interface GameViewObserver {

	/**
	 * Muove l'eroe nella direzione indicata.
	 * @param dir : la direzione in cui si muove
	 */
	void moveHero(final Direction dir);
	/**
	 * Spara nella direzione indicata.
	 * @param dir : la direzione dello sparo
	 */
	void shoot(final Direction dir);
	/**
	 * Ricarica l'arma.
	 */
	void recharge();
	/**
	 * Mette il gioco in pausa e visualizza il menù di pausa.
	 */
	void pauseButton();
	/**
	 * Fa iniziare il gioco.
	 */
	void startGame();
	/**
	 * Fa ricominciare il gioco dopo averlo messo in pausa.
	 */
	void resumeGame();
	/**
	 * Termina il gioco e mostra il menù principale.
	 */
	void menuButton();
}
