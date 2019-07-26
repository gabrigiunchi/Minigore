package model.game;

import java.io.Serializable;


/**
 * Implementazione di Score.
 *
 * @author Mattia Ricci, Lorenzo Mazzesi
 */
public class ScoreImpl implements Score, Serializable {

	private static final long serialVersionUID = 1479287510256316948L;
	
	private static final int MIN_LENGTH = 3;
	private static final int MAX_LENGTH = 15;
	private final String name;
	private final int score;

	/**
	 * @param myName il nome del giocatore
	 * @param myScore : il punteggio conseguito
	 * @throws IllegalStateException se il nome del giocatore è più corto di 3 caratteri,
	 * 	   								il nome del giocatore è più lungo di 15 caratteri,
	 * 									lo score è minore di zero.
	 */
	public ScoreImpl(final String myName, final int myScore) throws IllegalStateException {
		if (myName.length() < MIN_LENGTH || myName.length() > MAX_LENGTH || myScore < 0) {
			throw new IllegalArgumentException();
		}
		
		this.name = myName;
		this.score = myScore;
	}
	
	@Override
	public int getScore() {
		return this.score;
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return this.name + " " + this.score + ";";
	}
}
