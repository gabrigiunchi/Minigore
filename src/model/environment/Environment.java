package model.environment;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import model.entities.Enemy;
import model.entities.Entity;
import model.entities.Hero;
import model.entities.Shot;

/**
 * Ecosistema del gioco. 
 * Può essere limitato da dei bordi (see {@link EnvironmentBounds}) e può contenere degli ostacoli immobili (see {@link Block})
 * Fornisce dei metodi per aggiungere e rimuovere creature, settare i bordi e gli ostacoli.
 * 
 * @author Gabriele Giunchi
 */
public interface Environment {
	
	/**
	 * Determina se ci si può muovere verso la posizione data.
	 * @param pos : la posizione in cui si cerca di muoversi
	 * @throws IllegalPositionException se si ha raggiunto il limite dell'ecosistema o c'è un ostacolo il quella posizione
	 */
	void canMoveTo(final IPosition2D pos) throws IllegalPositionException;
	
	/**
	 * Aggiunge un nuovo proiettile all'ecosistema, see {@link Shot}.
	 * @param shot : il proiettile da aggiungere
	 */
	void addShot(final Shot shot);
	
	/**
	 * Setta l'eroe del gioco, see {@link Hero}.
	 * @param hero : il protagonista da aggiungere
	 */
	void setHero(final Hero hero);
	
	/**
	 * Aggiunge un nemico all'ecosistema, see {@link Enemy}.
	 * @param enemy : il nemico da aggiungere 
	 */
	void addEnemy(final Enemy enemy);
	
	/**
	 * Rimuove un'entità dall'ecosistema.
	 * @param entity : l'entità da rimuovere
	 * @return true se l'entità è stata rimossa correttamente
	 */
	boolean remove(final Entity entity);
	
	/**
	 * Toglie tutte le entità presenti nell'ecosistema. Non modifica i bordi nè gli ostacoli presenti
	 */
	void clear();
	
	/**
	 * Sostituisce gli ostacoli eventualmente presenti nell'ecosistema con quelli dati per parametro, see {@link Block}.
	 * @param blocks la lista di ostacoli da settare
	 */
	void setBlocks(final Set<Block> blocks);
	
	/**
	 * Setta i bordi dell'ecosistema, see {@link EnvironmentBounds}.
	 * @param bounds : i bordi da settare
	 */
	void setBounds(final EnvironmentBounds bounds);
	
	/**
	 * @return la lista dei nemici presenti nell'ecosistema, oppure una lista vuota se non sono presenti
	 */
	List<Enemy> getEnemies();
	
	/**
	 * @return la lista dei proiettili dell'ecosistema, oppure una lista vuota se non sono presenti
	 */
	List<Shot> getShots();
	
	/**
	 * @return la lista dei blocchi presenti nell'ecosistema, o un set vuoto se non sono presenti
	 */
	Set<Block> getBlocks();
	
	/**
	 * @return il protagonista
	 */
	Optional<Hero> getHero();
	
	/**
	 * @return i bordi dell'ecosistema
	 */
	Optional<EnvironmentBounds> getBounds();	
}