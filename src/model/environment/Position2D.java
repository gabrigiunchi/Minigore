package model.environment;

import java.io.Serializable;

/**
 * Posizione di un oggetto. Le coordinate sono immutabili
 * Classe presa da quella fatta in laboratoria durante l'anno
 * 
 * @author Danilo Pianili
 */
public class Position2D implements IPosition2D, Serializable {

	private static final long serialVersionUID = -8417820446110258972L;
	
	private final int x;
	private final int y;
	private final String cachedToString;
	
	/**
	 * @param xPoint : la coordinata x
	 * @param yPoint : la coordinata y
	 */
	public Position2D(final int xPoint, final int yPoint) {
		this.x = xPoint;
		this.y = yPoint;
		this.cachedToString = "(" + this.x + ";" + this.y + ")";
	}
	
	/**
	 * Costruttore che prende in ingresso una posizione.
	 * @param point : la posizione
	 */
	public Position2D(final IPosition2D point) {
		this(point.getX(), point.getY());
	}
	
	@Override
	public int getX() {
		return this.x;
	}
	
	@Override
	public int getY() {
		return this.y;
	}
	
	@Override
	public Position2D sumVect(final int xValue, final int yValue) {
		return new Position2D(this.x + xValue, this.y + yValue);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null) {
			return false;
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		final Position2D other = (Position2D) obj;
		if (x != other.x) {
			return false;
		}
		
		if (y != other.y) {
			return false;
		}
		
		return true;
	}

	@Override
	public String toString() {
		return this.cachedToString;
	}
}
