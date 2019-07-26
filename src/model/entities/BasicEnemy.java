package model.entities;

import java.io.Serializable;
import java.util.Optional;
import model.environment.Environment;
import model.environment.IPosition2D;
import model.environment.IllegalPositionException;
import model.environment.Position2D;

/**
 * Implementazione di {@link Enemy}.
 * 
 * @author Gabriele Giunchi
 */
public class BasicEnemy extends AbstractCreature implements Enemy, Serializable {

	private static final long serialVersionUID = -4393749651151242092L;
	
	/**
	 * Velocità iniziale di BasicEnemy.
	 */
	public static final int INITIAL_SPEED = 150;
	
	private EnemyBehavior behavior;
	private int speed;
	private long lastUpdate;
	
	/**
	 * Viene creato BasicEnemy con una posizione iniziale e l'ecosistema in cui vive.
	 * @param env : l'ecosistema in cui l'oggetto viene posizionato
	 * @param pos : la posizione iniziale
	 */
	public BasicEnemy(final Environment env, final IPosition2D pos) {
		super(env, pos);
		this.speed = INITIAL_SPEED;
	}
	
	/**
	 * Viene creato BasicEnemy con la posizione iniziale, l'ecosistema in cui vive e il comportamento che deve avere.
	 * @param env : l'ecosistema in cui viene posizionato
	 * @param pos : la posizione iniziale
	 * @param behavior : oggetto che descrive il comportamento dell'oggetto
	 */
	public BasicEnemy(final Environment env, final Position2D pos, final EnemyBehavior behavior) {
		this(env, pos);
		this.behavior = behavior;
	}

	@Override
	protected void canMoveTo(final IPosition2D pos) throws IllegalPositionException {
		if (super.getEnvironment().isPresent() && !super.isDead()) {
			super.getEnvironment().get().canMoveTo(pos);
			
			if (super.getEnvironment().get().getEnemies().stream().anyMatch(e -> e.isHere(pos))) {
				throw new IllegalPositionException(pos);
			}
		}
	}

	@Override
	public boolean checkCollision() {	
		if (super.getEnvironment().isPresent() && !super.isDead()) {
			final Optional<Hero> hero = super.getEnvironment().get().getHero();
			
			if (hero.isPresent() && hero.get().isHere(this.position)) {
				hero.get().kill();
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public void kill() {
		super.kill();
	
		if (super.getEnvironment().isPresent()) {
			super.getEnvironment().get().remove(this);
		}
	}
	
	@Override
	public void updatePosition() {
		final long time = System.currentTimeMillis();
		final long dt = time - this.lastUpdate;
		
		if (dt >= this.speed && !super.isDead()) {
			if (this.behavior != null) {
				this.behavior.apply(this);
			}
			
			this.lastUpdate = time;
		}
	}
	
	@Override
	public void setBehavior(final EnemyBehavior creatureBehavior) {
		this.behavior = creatureBehavior;
	}
	
	@Override
	public void setSpeed(final int n) throws IllegalArgumentException {
		if (n > 0) {
			this.speed = n;
		} else {
			throw new IllegalArgumentException("Speed must be positive!");
		}
	}
	
	@Override
	public void increaseSpeed(final int n) {
		if (this.speed - n >= 0) {
			this.speed -= n;
		}
	}

	@Override
	public int getSpeed() {
		return this.speed;
	}
	
	@Override
	public String toString() {
		return "Enemy, " + this.position.toString();
	}
}
