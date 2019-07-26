package utilities;

import model.entities.BasicEnemy;
import model.entities.BasicEnemyBehavior;
import model.entities.Enemy;
import model.entities.Enemy.EnemyBehavior;
import model.environment.Environment;
import model.environment.Position2D;

/**
 * Static factory di {@link Enemy}. 
 * 
 * @author Gabriele Giunchi
 *
 */
public final class EnemyFactory {
	
	/**
	 * Tipi di nemici che hanno caratteristiche diverse (es. velocità diversa, possibilità o meno di maneggiare armi ecc..)
	 * Lo scopo è quello di avere a disposizione diverse implementazioni con cui creare eserciti di nemici sempre diversi e
	 * rendere il gioco più completo.
	 * @author Gabri
	 *
	 */
	public enum EnemyType {
		/**
		 * Basic enemy, without weapon. It can only move and follow the hero
		 */
		Basic;
	}
	
	private EnemyFactory() { }
	
	/**
	 * Crea un oggetto che implementa l'interfacccia {@link Enemy} in base al tipo. 
	 * L'oggetto viene creato con la posizione e l'environment dati come parametri, 
	 * la creazione del suo behavior (see {@link EnemyBehavior}) è delegata al tipo
	 * Per esempio non ha senso creare un nemico senza armi con un behavior che gli indica di sparare quando è davanti al personaggio,
	 * con questo metodo si ha la certezza di ottenere un nemico con comportamento corretto e consono.

	 * @param type : il tipo di Enemy, see {@link EnemyType}
	 * @param env : l' {@link Environment} in cui si trova
	 * @param pos : la posizione iniziale
	 * @return l'istanza dell'oggetto
	 */
	public static Enemy create(final EnemyType type, final Environment env, final Position2D pos) {
		switch (type) {
			case Basic: return new BasicEnemy(env, pos, new BasicEnemyBehavior());
			default: return new BasicEnemy(env, pos, new BasicEnemyBehavior());
		}
		
	}
	
	/**
	 * Crea un oggetto che implementa l'interfacccia {@link Enemy} in base al tipo. 
	 * L'oggetto viene creato con la posizione, l'environment e il behavior dati come parametri.
	 * 
	 * @param type : il tipo di Enemy, see {@link EnemyType}
	 * @param env : l' {@link Environment} in cui si trova
	 * @param pos : la posizione iniziale
	 * @param behavior : l'oggetto di tipo {@link EnemyBehavior}
	 * @return l'istanza dell'oggetto
	 */
	public static Enemy create(final EnemyType type, final Environment env, final Position2D pos, final EnemyBehavior behavior) {
		switch (type) {
			case Basic: return new BasicEnemy(env, pos, behavior);
			default: return new BasicEnemy(env, pos, behavior);
		}
	}

}
