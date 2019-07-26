package model.game;

import java.io.Serializable;
import java.util.Random;

import utilities.EnemyFactory;
import utilities.PositionFactory;
import utilities.EnemyFactory.EnemyType;
import model.entities.BasicWeapon;
import model.entities.Enemy;
import model.entities.Hero;
import model.entities.HeroImpl;
import model.environment.Environment;
import model.environment.IPosition2D;
import model.environment.Position2D;
import model.game.Game.GameStrategy;

/**
 * Modalità di gioco astratta che implementa caratteristiche base come il controllo del game over o l'inizializzazione del gioco.
 * @author Lorenzo Mazzesi
 *
 */
public abstract class AbstractGameStrategy implements GameStrategy, Serializable {

	private static final long serialVersionUID = -2890682489265688426L;
	/**
	 * Numero iniziale di nemici in gioco.
	 */
	protected static final int INITIAL_ENEMIES = 5;
	/**
	 * Delay iniziale. I nemici sono già creati ma si da il tempo all'utente di iniziare il gioco.
	 */
	protected static final long INITIAL_DELAY = 2000;
	/**
	 * Contatore per il numero di nemici uccisi.
	 */
	protected int killed;
	
	/**
	 * Costruttore della modalità di gioco.
	 * Inizializza il contatore dei nemici uccisi a zero.
	 */
	public AbstractGameStrategy() {
		this.killed = 0;
	}
	
	/**
	 * Aggiunge un certo numero di nemici all'ecosistema.
	 * @param env : l'ecosistema in cui aggiungere i nemici
	 * @param n : il numero di nemici da aggiungere
	 */
	protected void addEnemies(final Environment env, final int n) {
		for (int i = 0; i < n; i++) {
			Position2D pos = new Position2D(new Random().nextInt(50), new Random().nextInt(50));
			
			if (env.getBounds().isPresent()) {
				pos = PositionFactory.calculatePosition(env.getBounds().get());
			} 
			
			final Enemy temp = EnemyFactory.create(EnemyType.Basic, env, pos);
			env.addEnemy(temp);
		}
	}
	
	/**
	 * Aggiorna l'ecosistema muovendo le creature e controllando le collisioni.
	 * @param game : il modello del gioco
	 */
	protected void updateEnvironment(final Game game) {
		final Environment env = game.getEnvironment().get();
		
		env.getShots().stream().forEach(s -> {
			boolean flag = s.checkCollision();
			
			if (!flag) {
				s.updatePosition();
				flag = s.checkCollision();
			}
			
			if (flag) {
				this.killed++;
			}
		});
		
		/* All'inizio del gioco i nemici ci sono ma sono in stato di attesa per INITIAL_DELAY, 
			così da lasciare il tempo al giocatore di "ambientarsi" */
		if (game.getMillisecond() >= INITIAL_DELAY) {
			env.getEnemies().stream().forEach(e -> {
				e.checkCollision();
				e.updatePosition();
				e.checkCollision();
			});
		}
	}
	
	/**
	 * Controlla se ci sono le condizioni necessarie per dichiarare game over.
	 * L'implementazione è banale e si limita a controllare se l'eroe è ancora in vita, 
	 * ma sono possibili altre condizioni aggiuntive (es. il termine del tempo consentito)
	 * @param game : il modello del gioco
	 * @return true se il gioco è finito
	 */
	protected boolean checkGameOver(final Game game) {
		final Environment env = game.getEnvironment().get();
		
		if (env.getHero().isPresent() && env.getHero().get().isDead()) {
			game.terminate();
			return true;
		}
		
		return false;
	}
	
	/**
	 * Inizializza il gioco aggiungendo l'eroe e nemici all'ecosistema.
	 * @param game : il modello del gioco
	 */
	public void initGame(final Game game) {
		if (game.getEnvironment().isPresent()) {
			final Environment env = game.getEnvironment().get();
			env.clear();
		
			this.addEnemies(env, INITIAL_ENEMIES);
			
			IPosition2D heroPos = new Position2D(10, 10);
			if (env.getBounds().isPresent()) {
				heroPos = PositionFactory.centerOfBounds(env.getBounds().get());
			}
		
			final Hero hero = new HeroImpl(env, heroPos);
			hero.equip(new BasicWeapon(env, hero));
			env.setHero(hero);
		}
	}
	
	@Override
	public String toString() {
		return "Abstract Game Strategy";
	}
	@Override
	public abstract void play(final Game game);
}
