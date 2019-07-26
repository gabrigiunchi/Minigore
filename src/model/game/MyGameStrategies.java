package model.game;

import java.io.Serializable;

import model.game.Game.GameStrategy;

/**
 * Elenco di modalità di gioco create per l'applicazione.
 * @author Lorenzo Mazzesi
 */
public enum MyGameStrategies implements Serializable {
	
	/**
	 * See {@link BasicGameStrategy}.
	 */
	Basic("Basic.bin"),
	
	/**
	 * See {@link TimeChallenge}.
	 */
	TimeChallenge("TimeChallenge.bin"),
	
	/**
	 * See {@link MultipleLifesMod}.
	 */
	MultipleLifes("MultipleLifes.bin");
	
	private final String highScoreFilename;
	
	private MyGameStrategies(final String filename) {
		this.highScoreFilename = filename;
	}
	
	/**
	 * Getter per il nome del file degli highscore.
	 * @return il nome del file su cui sono salvati gli highscore della modalità
	 */
	public String getFilename() {
		return this.highScoreFilename;
	}
	
	/**
	 * Static factory che crea un oggetto che implementa GameStrategy in base al tipo richiesto.
	 * 
	 * @param strategy : il tipo di modalità richiesto
	 * @return : l'oggetto gameStrategy appropriato
	 */
	public static GameStrategy get(final MyGameStrategies strategy) {
		switch(strategy) {
			case Basic : return new BasicGameStrategy();
			case TimeChallenge: return new TimeChallenge();
			case MultipleLifes: return new MultipleLifesMod();
			default: return new BasicGameStrategy();
		}
	}
}
