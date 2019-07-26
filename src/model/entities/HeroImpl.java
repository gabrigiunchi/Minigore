package model.entities;

import java.io.Serializable;
import java.util.Optional;

import model.environment.Direction;
import model.environment.Environment;
import model.environment.IPosition2D;
import model.environment.IllegalPositionException;

/**
 * Implementazione dell'interfaccia {@link Hero}. Può usare una sola arma per volta.
 * 
 * @author Gabriele Giunchi
 */
public class HeroImpl extends AbstractCreature implements Hero, Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final int DELAY = 1500; // Quando viene colpito deve passare questo tempo affinchè possa essere colpito di nuovo
	private static final int DEFAULT_LIFE = 1;
	private static final int SPEED = 50;
	private Weapon weapon;
	private int life;
	private long lastDamage;
	private long lastUpdate;
	
	/**
	 * Viene creato il personaggio nell'ecosistema dato, con la posizione iniziale e le vite.
	 * 
	 * @param env : l'ecosistema
	 * @param pos : la posizione iniziale
	 * @param heroLife : il numero di vite iniziali
	 */
	public HeroImpl(final Environment env, final IPosition2D pos, final int heroLife) {
		super(env, pos);
		this.life = heroLife;
	}
	
	/**
	 * Viene creato il personaggio nell'ecosistema dato e la posizione iniziale.
	 * 
	 * @param env : l'ecosistema
	 * @param pos : la posizione iniziale
	 */
	public HeroImpl(final Environment env, final IPosition2D pos) {
		this(env, pos, DEFAULT_LIFE);
	}
	
	private void checkWeapon() throws IllegalStateException {
		if (this.weapon == null) {
			throw new IllegalStateException("Nessuna arma impugnata");
		}
	}
	
	@Override
	protected void canMoveTo(final IPosition2D pos) throws IllegalPositionException {
		if (super.getEnvironment().isPresent() && !super.isDead()) {
			super.getEnvironment().get().canMoveTo(pos);
		}
	}
	
	@Override
	public void moveHero(final Direction dir) throws IllegalPositionException {
		final long dt = System.currentTimeMillis();
		final long time = dt - this.lastUpdate;
		
		if (time >= SPEED && !super.isDead()) {
			this.lastUpdate = dt;
			super.move(dir);
		}
	}
	
	@Override
	public void kill() {
		final long time = System.currentTimeMillis();
        final long dt = time - this.lastDamage;
        
        if (dt >= DELAY && !super.isDead()) {
        	this.life--;
        
        	if (this.life == 0) {
        		super.kill();
        	}
        	
        	this.lastDamage = time;
        }
	}

	@Override
	public void equip(final Weapon heroWeapon) {
		if (!super.isDead()) {
			this.weapon = heroWeapon;
			this.weapon.equip(this);
		}
	}
	
	@Override
	public void removeWeapon() {
		this.checkWeapon();
		this.weapon.resetOwner();
		this.weapon = null;
	}
	
	@Override
	public void useWeapon() throws ShotsOverException, IllegalStateException {
		if (!super.isDead()) {
			this.checkWeapon();
			this.weapon.use();
		}
	}

	@Override
	public void recharge() throws IllegalStateException {
		if (!super.isDead()) {
			this.checkWeapon();
			this.weapon.recharge();
		}
	}
	
	@Override
	public void setLives(final int n) throws IllegalArgumentException {
		if (n > 0) {
			this.life = n;
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	@Override
	public int getLife() {
		return this.life;
	}
	
	@Override
	public Optional<Weapon> getWeapon() {
		return Optional.ofNullable(this.weapon);
	}
	
	@Override
	public String toString() {
		return "Hero, Life:" + this.life + ", " + this.position; 
	}
}
