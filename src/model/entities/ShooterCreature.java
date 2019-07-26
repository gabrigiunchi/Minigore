package model.entities;

import java.util.Optional;

/**
 * Interfaccia di una creatura che può maneggiare una e una sola arma.
 * 
 * @author Gabriele Giunchi
 *
 */
public interface ShooterCreature extends Creature {
	
	/**
	 * Equipaggia la creatura con un'arma, see {@link Weapon}.
	 * @param weapon : l'arma da equipaggiare
	 */
	void equip(final Weapon weapon);
	
	/**
	 * Toglie l'arma che sta impugnando, see {@link Weapon}.
	 * Se la creatura non sta impugnando nessuna arma non ha alcun effetto
	 */
	void removeWeapon();
	
	/**
	 * Usa l'arma impugnata, see {@link Weapon}.
	 * @throws ShotsOverException se l'arma ha finito i colpi, see {@link ShotsOverException}
	 * @throws IllegalStateException se non sta impugnando un'arma
	 */
	void useWeapon() throws ShotsOverException, IllegalStateException;
	
	/**
	 * Ricarica l'arma, see {@link Weapon}.
	 * @throws IllegalStateException se la creatura non sta impugnando nessuna arma
	 */
	void recharge() throws IllegalStateException;
	
	/**
	 * @return l'arma equipaggiata, Optional.empty se non ne possiede una
	 */
	Optional<Weapon> getWeapon();
}
