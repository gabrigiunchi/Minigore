package model.game;

import java.io.Serializable;

import model.game.Game.GameStrategy;


/**
 * Modalità di gioco a tempo, l'obbiettivo è restare in vita il più possibile, il punteggio corrisponde ai secondi di gioco.
 * @author Lorenzo Mazzesi
 */
public class TimeChallenge extends AbstractGameStrategy implements GameStrategy, Serializable {

	private static final long serialVersionUID = -6996796959796323261L;
	
	private static final long TIME_NEXT_ENEMY = 2000;
	private static final long TIME_INCREASE_NUMBER_ENEMIES_TO_ADD = 10000;
	private long temp;
	private long current;
	private long lastAdded;
	private int enemiesToAdd;
	
	private void init() {
		this.current = System.currentTimeMillis();
		this.lastAdded = this.current;
		this.temp = 0;
		this.enemiesToAdd = 1;
	}
	
	@Override
	public void play(final Game game) {
		if (game.getMillisecond() >= INITIAL_DELAY) {
			final long dt = System.currentTimeMillis();
			this.temp += dt - this.current;
			this.current = dt;
		
			// Ogni TIME_NEXT_ENEMY secondi aggiungo enemiesToAdd nemici
			if ((dt - this.lastAdded) >= TIME_NEXT_ENEMY) {
				this.addEnemies(game.getEnvironment().get(), this.enemiesToAdd);
				this.lastAdded = dt;
			}
			
			// Ogni 10 secondi incremento il valore dei nemici da aggiungere
			if (this.temp >= TIME_INCREASE_NUMBER_ENEMIES_TO_ADD) {
				this.enemiesToAdd++;
				this.temp = 0;
			}
		}
		game.incrementScore(Math.round((float) game.getMillisecond() / (float) 1000 - game.getScore()));
		super.updateEnvironment(game);
		super.checkGameOver(game);
	}
	
	@Override
	public void initGame(final Game game) {
		super.initGame(game);
		this.init();
	}

	@Override
	public String toString() {
		return "TimeChallenge";
	}
}
