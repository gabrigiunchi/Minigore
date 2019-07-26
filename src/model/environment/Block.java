package model.environment;

import model.entities.Entity;

/**
 * Interfaccia di un ostacolo, ossia un'entità con una forma particolare, descritta da BlockType.
 * 
 * @author Gabriele Giunchi
 */
public interface Block extends Entity {

	/**
	 * @return il tipo del blocco
	 */
	BlockType getType();
}
