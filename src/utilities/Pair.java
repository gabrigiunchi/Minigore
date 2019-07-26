package utilities;

import java.io.Serializable;

/**
 * Classe generica che contiene una coppia di elementi.
 * @author Gabriele Giunchi
 *
 * @param <X>
 * @param <Y>
 */
public class Pair <X, Y> implements Serializable {
	
	private static final long serialVersionUID = -7436749076307186241L;
	
	private final X x;
	private final Y y;
	
	/**
	 * @param x : il primo elemento della coppia
	 * @param y : il secondo elemento della coppia
	 */
	public Pair(final X x, final Y y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * @return il primo elemento della coppia
	 */
	public X getFirst() {
		return this.x;
	}
	
	/**
	 * @return il secondo elemento della coppia
	 */
	public Y getSecond() {
		return this.y;
	}

}
