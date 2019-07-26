package model.entities;

import java.io.Serializable;
import java.util.Optional;

import model.environment.Environment;
import model.environment.IPosition2D;

/**
 * Arma astratta che implementa le funzioni comuni a tutte le armi.
 * 
 * @author Gabriele Giunchi
 */
public abstract class AbstractWeapon extends AbstractEntity implements Weapon, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * La creatura che sta impugnando l'arma.
	 */
	protected ShooterCreature owner;
	
	/**
	 * L'ecosistema in cui si trova l'arma.
	 */
	protected Environment environment;
	
	/**
	 * Il numero di colpi che può ancora sparare l'arma.
	 */
	protected int shots;
	
	private boolean hasOwner;
	
	/**
	 * Arma inizializzata con l'ecosistema e la posizione iniziale.
	 * @param env : l'ecosistema
	 * @param pos : la posizione iniziale
	 */
	public AbstractWeapon(final Environment env, final IPosition2D pos) {
		super(pos);
		this.environment = env;
		this.hasOwner = false;
	}
	
	/**
	 * Arma inizializzata con l'ecosistema e la creatura che la possiede.
	 * @param env : l'ecosistema
	 * @param creature : la creatura
	 */
	public AbstractWeapon(final Environment env, final ShooterCreature creature) {
		this(env, creature.getPosition());
		this.owner = creature;
	}
	
	@Override
	public Optional<ShooterCreature> getOwner() {
		return Optional.ofNullable(this.owner);
	}

	@Override
	public void equip(final ShooterCreature shooter) {
		this.hasOwner = true;
		this.owner = shooter;
		this.position = shooter.getPosition();
	}
	
	@Override
	public int getRemainingShots() {
		return this.shots;
	}
	
	@Override
	public IPosition2D getPosition() {
		if (this.hasOwner) {
			return this.owner.getPosition();
		}
		
		return super.getPosition();
	}
	
	@Override
	public Optional<Environment> getEnvironment() {
		return Optional.ofNullable(this.environment);
	}
	
	@Override
	public void resetOwner() {
		synchronized (this.owner) {
			if (this.hasOwner) {
				this.position = this.owner.getPosition();
			}
		}
		this.hasOwner = false;
	}
	
	/**
	 * Determina se l'arma può essere usata o meno.
	 * @throws ShotsOverException se i colpi sono esauriti
	 * @throws WeaponOverHeatingException se l'arma ha bisogno di più tempo per caricarsi
	 */
	protected abstract void canUse() throws ShotsOverException, WeaponOverHeatingException;
	
	/**
	 * L'arma viene usata ed effettua la sua azione.
	 * @throws ShotsOverException se i colpi sono esauriti
	 */
	public abstract void use() throws ShotsOverException;
	
	/**
	 * Ricarica i colpi dell'arma.
	 */
	public abstract void recharge();
	
	/**
	 * Un'arma può essere contraddistinta da un nome, un codice o qualunque cosa che la renda riconoscibile.
	 * @return il nome dell'arma
	 */
	public abstract Optional<String> getName();
}
