package model.entities;

import java.io.Serializable;

import model.environment.IPosition2D;
import model.environment.Position2D;

/**
 * Implementazione di un entità base, dotata di posizione.
 * 
 * @author Gabriele Giunchi
 */
public class AbstractEntity implements Entity, Serializable {
	
	private static final long serialVersionUID = -8458536245984128790L;
	
	/**
	 * La posizione dell'entità.
	 */
	protected IPosition2D position;
	
	/**
	 * @param pos : la posizione iniziale
	 */
	public AbstractEntity(final IPosition2D pos) {
		if (pos == null) {
			this.position = new Position2D(0, 0);
		} else {
			this.position = pos;
		}
	}
	
	@Override
	public IPosition2D getPosition() {
		synchronized (this.position) {
			return this.position;
		}
	}

	@Override
	public boolean isHere(final IPosition2D pos) {
		synchronized (this.position) {
			return this.position.equals(pos);
		}
	}

	@Override
	public String toString() {
		return this.position.toString();
	}
}
