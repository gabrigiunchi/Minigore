package model.entities;

import model.environment.Direction;
import model.environment.IllegalPositionException;

/**
 * Interfaccia del protagonista del gioco: una creatura che può sparare ed eventualmente dotata di più vite.
 * E' comandabile attraverso i metodi {@link moveHero(Direction)} e {@link useWeapon()}, see {@link ShooterCreature}.
 *	
 *	@author Gabriele Giunchi
 */
public interface Hero extends ShooterCreature {
	
	/**
	 * Muove il protagonista tenendo conto della sua velocità.
	 * @param direction : la direzione verso cui muoversi
	 * @throws IllegalPositionException se non ci si può muovere in quella direzione
	 */
	void moveHero(final Direction direction) throws IllegalPositionException;
	
	/**
	 * Setta il numero di vite del protagonista.
	 * @param n : il numero di vite
	 * @throws IllegalArgumentException se n <= 0
	 */
	void setLives(final int n) throws IllegalArgumentException;
	
	/**
	 * @return la vita dell'eroe
	 */
	int getLife();
}
