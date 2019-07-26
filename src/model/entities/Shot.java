package model.entities;

import java.util.Optional;

import model.environment.Direction;
import model.environment.Environment;
import model.environment.IllegalPositionException;

/**
 * Interfaccia di un proiettile che viene sparato da un'arma e che si muove nell'ecosistema.
 * La direzione è la posizione di partenza sono determinate in base alla direzione e alla posizione dell'arma da cui è sparato.
 *
 * @author Gabriele Giunchi
 */
public interface Shot extends Entity {
	
	/**
	 * Muove il proiettile verso la direzione su cui è rivolto, see {@link Direction}.
	 * Questo metodo è utilizzato solamente in fase di test,
	 * durante il gioco viene utilizzato updatePosition() che tiene conto della velocità del proiettile
	 * @throws IllegalPositionException se il proiettile non può muoversi in quella direzione
	 */
	void move() throws IllegalPositionException;
	
	/**
	 * Aggiorna la posizione del proiettile. Di solito il meccanismo si basa sulla velocità del proiettile.
	 */
	void updatePosition();
	
	/**
	 * Controlla se ha colpito qualcosa e in caso positivo si "uccide".
	 * @return true se ha colpito qualcosa
	 */
	boolean checkCollision();
	
	/**
	 * @return l'ecosistema in cui è presente il proiettile
	 */	
	Optional<Environment> getEnvironment();
}
