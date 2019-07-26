package model.game;

import java.io.Serializable;

import model.entities.BasicEnemy;
import model.game.Game.GameStrategy;


/**
 * Modalità di gioco base, dove il punteggio va in base ai nemici uccisi.
 * @author Lorenzo Mazzesi
 */
public class BasicGameStrategy extends AbstractGameStrategy implements GameStrategy, Serializable {

	private static final long serialVersionUID = 3319372834593247626L;
	private static final int SPEED = 80;
	private static final long TIME_NEXT_ENEMY = 2000;
	private static final long TIME_INCREASE_NUMBER_ENEMIES_TO_ADD = 10000;
	private long time;
	private long current;
	private long lastAdded;
	private int actualSpeed;
	private int enemiesToAdd;
	private int lastScore;

	private void init() {
		this.time = 0;
		super.killed = 0;
		this.lastAdded = 0;
		this.enemiesToAdd = 1;
		this.lastScore = 0;
		this.current = System.currentTimeMillis();
	}
	
	@Override
	protected void updateEnvironment(final Game game) {
		super.updateEnvironment(game);
		this.time++;
		game.incrementScore((super.killed - lastScore) * 10);
		this.lastScore = super.killed;
	}
	
	@Override
	public void play(final Game game) {
		if (game.getMillisecond() >= INITIAL_DELAY) {
			final long dt = System.currentTimeMillis();
			
			this.time += dt - this.current;
			
			if ((dt - this.lastAdded) >= TIME_NEXT_ENEMY) {
				this.addEnemies(game.getEnvironment().get(), this.enemiesToAdd);
				if  (this.actualSpeed > SPEED) {
					this.actualSpeed--;
				}
				
				game.getEnvironment().get().getEnemies().stream().forEach(e -> {
					try {
						e.setSpeed(this.actualSpeed);	
					} catch (IllegalArgumentException ex) {
						System.out.println(ex);
						this.actualSpeed = SPEED;
					}
				});
				
				this.lastAdded = dt;
			}
			
			if (this.time >= TIME_INCREASE_NUMBER_ENEMIES_TO_ADD) {
				this.enemiesToAdd++;
				this.time = 0;
			}
			this.current = dt;
		}
		
		this.updateEnvironment(game);
		super.checkGameOver(game);
	}
	
	@Override
	public void initGame(final Game game) {
		if (game.getEnvironment().isPresent()) {
			super.initGame(game);
			this.actualSpeed = BasicEnemy.INITIAL_SPEED;
		}
		
		this.init();
	}
	
	@Override
	public String toString() {
		return "Basic";
	}
}
