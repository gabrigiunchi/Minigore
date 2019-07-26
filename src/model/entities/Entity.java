package model.entities;

import model.environment.IPosition2D;

/**
 * Interfaccia di un'entità base, dotata di posizione ma che non può muoversi.
 * 
 * @author Gabriele Giunchi
 */
public interface Entity {

	/** 
	 * @return the position of the entity
	 */
	IPosition2D getPosition();
	
	/**
	 * @param pos : la posizione da esaminare
	 * @return true if the entity is at the position given 
	 */
	boolean isHere(final IPosition2D pos);
}

