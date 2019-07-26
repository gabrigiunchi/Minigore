package model.game;

import java.io.Serializable;
import java.util.Optional;

import model.entities.ShotsOverException;
import model.environment.Direction;
import model.environment.Environment;
import model.environment.MyEnvironment;
import model.environment.IllegalPositionException;

/**
 * Implementazione di {@link Game}.
 * 
 * @author Gabriele Giunchi
 */
public class GameImpl implements Serializable, Game {
	
	private static final long serialVersionUID = 497936651400053672L;
	
	private boolean inPause;
	private boolean inGame;
	private boolean isInitialized;
	private int score;
	private Environment environment;
	private MyGameStrategies selectedStrategy;
	private GameStrategy concreteStrategy;
	private transient GameAgent agent;
	private long gameTime;
	
	/**
	 * Inizializzazione di default con environment di default e gameStrategy Basic.
	 */
	public GameImpl() {
		this.selectedStrategy = MyGameStrategies.Basic;
		this.concreteStrategy = MyGameStrategies.get(this.selectedStrategy);
		this.environment = MyEnvironment.get();
		this.inGame = true;
		this.inPause = true;
		this.score = 0;
	}
	
	/**
	 * Costruttore con argomenti gli elementi per giocare.
	 * 
	 * @param gameEnvironment : l'ecosistema di gioco
	 * @param gameStrategy : la modalità
	 */
	public GameImpl(final Environment gameEnvironment, final MyGameStrategies gameStrategy) {
		this();
		this.environment = gameEnvironment;
		this.selectedStrategy = gameStrategy;
	}

	private void initGame() {
		if (!this.isInitialized && this.selectedStrategy != null) {
			this.concreteStrategy.initGame(this);
			this.isInitialized = true;
		}
		
		this.score = 0;
		this.inGame = true;
		this.inPause = false;
		this.gameTime = 0;
	}
	
	@Override
	public void startGame() throws IllegalStateException {
		if (this.environment == null || this.selectedStrategy == null) {
			throw new IllegalStateException("Gioco non inizializzato correttamente!");
		}
		
		this.initGame();
		this.agent = new GameAgent(this.concreteStrategy, this);
		this.agent.start();	
	}
	
	@Override
	public void pause() {
		this.inPause = true;
		this.agent.terminate();
	}

	@Override
	public void resumeGame() {
		this.inPause = false;
		this.agent = new GameAgent(this.concreteStrategy, this);
		this.agent.start();
	}

	@Override
	public void terminate() {
		this.isInitialized = false;
		this.inGame = false;
		this.agent.terminate();
	}
	
	@Override
	public void useHeroWeapon(final Direction dir) throws ShotsOverException {
		synchronized (this.environment) {
			if (this.environment.getHero().isPresent()) {
				this.environment.getHero().get().changeDirection(dir);
				this.environment.getHero().get().useWeapon();
			}
		}
	}
	
	@Override
	public void moveHero(final Direction dir) {
		synchronized (this.environment) {
			if (this.environment.getHero().isPresent()) {
				try {
					this.environment.getHero().get().moveHero(dir);
				} catch (IllegalPositionException e) {
					System.out.println(e);
				}
			}
		}
	}
	
	@Override
	public void rechargeHeroWeapon() {
		if (this.environment.getHero().isPresent()) {
			this.environment.getHero().get().recharge();
		}
	}
	
	@Override
	public synchronized void incrementScore(final int n) {
		this.score += n;
	}

	@Override
	public void setStrategy(final MyGameStrategies gameStrategy) {
		this.selectedStrategy = gameStrategy;
		this.concreteStrategy = MyGameStrategies.get(this.selectedStrategy);
	}

	@Override
	public void setEnvironment(final Environment gameEnvironment) {
		this.environment = gameEnvironment;
	}
	
	@Override
	public synchronized boolean isInGame() {
		return this.inGame;
	}
	
	@Override
	public synchronized boolean isInPause() {
		return this.inPause;
	}
	
	@Override
	public synchronized int getScore() {
		return this.score;
	}
	
	@Override
	public synchronized long getMillisecond() {
		return this.gameTime;
	}
	
	@Override
	public Optional<Environment> getEnvironment() {
		return Optional.ofNullable(this.environment);
	}
	
	@Override
	public Optional<MyGameStrategies> getStrategy() {
		return Optional.ofNullable(this.selectedStrategy);
	}
	
	@Override
	public String toString() {
		return "In game: " + this.inGame + ", in pause: " + this.inPause;
	}
	
	private final class GameAgent extends Thread {
		private final GameStrategy strategy;
		private final Game game;
		private volatile boolean stop;
		private long current;
		
		private GameAgent(final GameStrategy gameStrategy, final Game g) {
			this.strategy = gameStrategy;
			this.game = g;
			this.stop = false;
		}
		
		@Override
		public void run() {
			this.current = System.currentTimeMillis();
			
			while (inGame && !stop) {
				final long dt = System.currentTimeMillis();
				gameTime += dt - this.current;
				this.current = dt;
				
				if (!inPause) {
					this.strategy.play(this.game);
				}
				try {
					sleep(10);
				} catch (InterruptedException e) {
					System.out.println(e);
				}
			}
		}
		
		private void terminate() {
			this.stop = true;
		}
	}
}
