package model.entities;

/**
 * Interfaccia dei nemici del gioco. 
 * Aderisce al pattern strategy sul suo behavior, ossia l'insieme di azioni che compie ({@link EnemyBehavior}
 * 
 * @author Gabriele Giunchi
 */
public interface Enemy extends Creature {
	
	/**
	 * Aggiorna la sua posizione in base alla velocità che possiede.
	 */
	void updatePosition();
	
	/**
	 * Controlla se ha colpito qualcosa. In genere si controlla se è riuscito a colpire l'eroe
	 * @return true se ha colpito qualcosa
	 */
	boolean checkCollision();
	
	/**
	 * Setta la velocità della creatura.
	 * @param n : la velocità
	 * @throws IllegalArgumentException se n <= 0
	 */
	void setSpeed(final int n) throws IllegalArgumentException;
	
	/**
	 * Incrementa o decrementa la velocità della creatura. 
	 * Se la velocità raggiunge il limite il metodo non ha alcun effetto
	 * @param n : il valore
	 */
	void increaseSpeed(final int n);
	
	/**
	 * Setta il behavior della creatura.
	 * @param b : l'oggetto EnemyBehavior
	 */
	void setBehavior(final EnemyBehavior b);
	
	/**
	 * @return la velocità della cratura
	 */
	int getSpeed();
	
	/**
	 * Comportamento di un nemico, determina le azioni che compie.
	 * @author Gabriele Giunchi
	 */
	 interface EnemyBehavior {

		/**
		 * @param enemy : la creatura su cui effettuare le operazioni
		 */
		 void apply(final Enemy enemy);
	}
}
