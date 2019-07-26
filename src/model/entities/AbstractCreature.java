package model.entities;

import java.io.Serializable;
import java.util.Optional;

import model.environment.Direction;
import model.environment.Environment;
import model.environment.IPosition2D;
import model.environment.IllegalPositionException;
import model.environment.Position2D;

/**
 * Creatura astratta che implementa caratteristiche comuni a tutte le creature viventi.
 * 
 * @author Gabriele Giunchi
 */
public abstract class AbstractCreature extends AbstractEntity implements Creature, Serializable {
	
	private static final long serialVersionUID = 8055047673538380237L;

	private boolean isAlive;
	private final Environment environment;
	private Direction direction;
	
	/**
	 * @param env : l'ecosistema in cui vive
	 * @param pos : la posizione iniziale
	 */
	public AbstractCreature(final Environment env, final IPosition2D pos) {
		super(pos);
		this.isAlive = true;
		this.environment = env;
		this.direction = Direction.DOWN;
	}
	
	@Override
	public void move(final Direction dir) throws IllegalPositionException {
		if (this.isAlive) {
			IPosition2D pos = new Position2D(0, 0);
			
			synchronized (this.position) {
				switch(dir) {
					case DOWN: pos = this.position.sumVect(0, 1); break;
					case UP: pos = this.position.sumVect(0, -1); break;
					case LEFT: pos = this.position.sumVect(-1, 0); break;
					case RIGHT: pos = this.position.sumVect(1, 0); break;
				}
				this.canMoveTo(pos);
			}
			
			this.position = pos;
		}
	}
	
	@Override
	public void changeDirection(final Direction dir) {
		this.direction = dir;
	}
	
	@Override
	public void kill() {
		this.isAlive = false;
	}
	
	@Override
	public Direction getDirection() {
		return this.direction;
	}
	
	@Override
	public Optional<Environment> getEnvironment() {
		return Optional.ofNullable(this.environment);
	}
	
	@Override
	public boolean isDead() {
		return !this.isAlive;
	}
	
	@Override
	public String toString() {
		return this.position.toString() + ", is Alive:" + this.isAlive;
	}
	
	/**
	 * Decide se la creatura può muoversi verso quella posizione. 
	 * Non tutte le creature hanno le stesse regole, 
	 * per esempio i nemici possono muoversi nella posizione del protagonista ma non nella posizione di un altro nemico
	 * @param pos : la posizione in cui cerca di muoversi
	 * @throws IllegalPositionException se non può muoversi verso quella posizione
	 */
	protected abstract void canMoveTo(final IPosition2D pos) throws IllegalPositionException;
}
