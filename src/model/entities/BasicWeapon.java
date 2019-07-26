package model.entities;

import java.io.Serializable;
import java.util.Optional;
import model.environment.Environment;
import model.environment.IPosition2D;

/**
 * Arma con colpi illimitati.
 * 
 * @author Gabriele Giunchi
 */
public class BasicWeapon extends AbstractWeapon implements Weapon, Serializable {
	
	private static final long serialVersionUID = -4132217770115725046L;

	private static final String NAME = "Illimited Weapon";
	
	private static final int USE_FREQUENCY = 150;
	private long lastUpdate;
	
	/**
	 * @param env : l'ecosistema
	 * @param pos : la posizione iniziale
	 */
	public BasicWeapon(final Environment env, final IPosition2D pos) {
		super(env, pos);
		this.shots = INIFITE_SHOTS;
	}
	
	/**
	 * @param env : l'ecosistema
	 * @param owner : la creatura che possiede l'arma
	 */
	public BasicWeapon(final Environment env, final ShooterCreature owner) {
		super(env, owner);
		this.shots = INIFITE_SHOTS;
	}

	@Override
	public void use() throws IllegalStateException {
		try {
			this.canUse();
			final Shot shot = new ShotImpl(this.environment, this.owner.getPosition(), this.owner.getDirection());
			this.environment.addShot(shot);
		} catch (WeaponOverHeatingException e) {
			System.out.println(e);
		}
	}
	
	@Override
	protected void canUse() throws IllegalStateException, WeaponOverHeatingException {
		if (this.owner == null) {
			throw new IllegalStateException();
		}
		
		final long time = System.currentTimeMillis();
		final long dt = time - this.lastUpdate;
		
		if (dt >= USE_FREQUENCY) {
			this.lastUpdate = time;
		} else {
			throw new WeaponOverHeatingException();
		}
	}

	@Override
	public void recharge() {
		// Do nothing, this weapong has illimited shots
	}
	
	@Override
	public Optional<String> getName() {
		return Optional.of(NAME);
	}
	
	@Override
	public String toString() {
		return NAME + ", illimited shots";
	}
}
