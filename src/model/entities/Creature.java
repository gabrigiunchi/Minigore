package model.entities;

import java.util.Optional;

import model.environment.Direction;
import model.environment.Environment;
import model.environment.IllegalPositionException;

/**
 * Una creatura è un'entità vivente, che può muoversi nell'ecosistema e fare azioni.
 * 
 * @author Gabriele Giunchi
 */
public interface Creature extends Entity {
	
	/**
	 * Muove la creatura verso la direzione data.
	 * @param dir : la direzione in cui deve muoversi, see {@link Direction}
	 * @throws IllegalPositionException Se la creatura non può muoversi in quella direzione
	 */
	void move(final Direction dir) throws IllegalPositionException;
	
	/**
	 * Cambia la direzione della creatura, see {@link Direction}.
	 * @param direction : la direzione in cui deve rivolgersi
	 */
	void changeDirection(final Direction direction);
	
	/**
	 * Colpisce la creatura cercando di ucciderla.
	 */
	void kill();
	
	/**
	 * @return la direzione verso cui è rivolto, see {@link Direction}
	 */
	Direction getDirection();
	
	/**
	 * @return true se l'entità è morta, falso se è viva
	 */
	boolean isDead();
	
	/**
	 * @return l'ecosistema in cui vive la creatura, see {@link Environment}
	 */
	Optional<Environment> getEnvironment();
}
