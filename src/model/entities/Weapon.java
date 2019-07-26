package model.entities;

import java.util.Optional;
import model.environment.Environment;


/**
 * Interfaccia di un'arma.
 * 
 * @author Gabriele Giunchi
 */
public interface Weapon extends Entity {
	
	/**
	 * Costante che rappresenta il concetto di colpi infiniti.
	 */
	int INIFITE_SHOTS = -1;
	
	/**
	 * Usa l'arma.
	 * @throws ShotsOverException se l'arma ha finito i colpi, see {@link ShotsOverException}
	 */
	void use() throws ShotsOverException;
	
	/**
	 * Ricarica l'arma.
	 */
	void recharge();
	
	/**
	 * L'arma viene impugnata da una creatura che ora può utlizzarla, see {@link ShooterCreature}.
	 * @param shooter : la creatura che ha preso l'arma
	 */
	void equip(final ShooterCreature shooter);
	
	/**
	 * L'arma è stata lasciata dalla creatura che la stava impugnando.
	 */
	void resetOwner();
	
	/**
	 * @return i colpi rimanenti
	 */
	int getRemainingShots();
	
	/**
	 * @return il nome dell'arma
	 */
	Optional<String> getName();
	
	/**
	 * @return la creatura che sta impugnando l'arma, see {@link ShooterCreature}
	 */
	Optional<ShooterCreature> getOwner();
	
	/** 
	 * @return l'ecosistema in cui si trova l'arma
	 */
	Optional<Environment> getEnvironment();
}
