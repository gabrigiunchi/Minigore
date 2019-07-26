package model.entities;

import java.util.List;
import java.util.Optional;

import model.environment.Direction;
import model.environment.Environment;
import model.environment.IPosition2D;
import model.environment.IllegalPositionException;
import model.environment.Position2D;

/**
 * Implementazione dell'interfaccia {@link Shot}.
 * 
 * @author Gabriele Giunchi
 */
public class ShotImpl extends AbstractEntity implements Shot {
	
	private static final long serialVersionUID = 5488900563290372641L;
	
	private static final int SPEED = 10;
	private long lastUpdate;
	private final Environment environment;
	private final Direction direction;
	private boolean dead;
	
	/**
	 * @param env : l'ecosistema in cui viene sparato il proiettile
	 * @param pos : la posizione di partenza
	 * @param dir : la direzione in cui si muove
	 */
	public ShotImpl(final Environment env, final IPosition2D pos, final Direction dir) {
		super(pos);
		this.environment = env;
		this.direction = dir;
		this.dead = false;
	}
	
	private void kill() {
		this.dead = true;
		
		if (this.environment != null) {
			this.environment.remove(this);
		}
	}
	
	private void canMoveTo(final IPosition2D pos) throws IllegalPositionException {
		if (this.environment != null) {
			try {
				this.environment.canMoveTo(pos);
			} catch (IllegalPositionException e) {
				this.kill();
				throw e;
			}
		}
	}

	@Override
	public void move() throws IllegalPositionException {
		IPosition2D pos = new Position2D(0, 0);
		
		synchronized (pos) {
			switch (this.direction) {
				case UP: pos = this.position.sumVect(0, -1); break;
				case DOWN: pos = this.position.sumVect(0, 1); break;
				case LEFT: pos = this.position.sumVect(-1, 0); break;
				case RIGHT: pos = this.position.sumVect(1, 0); break;
			}
			this.canMoveTo(pos);
		}
		
		this.position = pos;
	}

	@Override
	public synchronized void updatePosition() {
		final long time = System.currentTimeMillis();
        final long dt = time - this.lastUpdate;
		
		if (dt >= SPEED && !this.dead) {
			try {
				this.move();
			} catch (IllegalPositionException e) {
				this.kill();
			}
			this.lastUpdate = time;
		}
	}
	
	@Override
	public boolean checkCollision() {
		if (this.environment != null && !this.dead) {
			final List<Enemy> enemies = this.environment.getEnemies();
			
			for (final Enemy temp: enemies) {
				if (temp.isHere(this.position)) {
					temp.kill();
					this.kill();
					return true;
				}
			}
		}
		
		return false;
	}
	
	@Override
	public Optional<Environment> getEnvironment() {
		return Optional.ofNullable(this.environment);
	}
	
	@Override
	public String toString() {
		return "Shot, " + this.position.toString();
	}
}
