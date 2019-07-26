package model.environment;

/**
 * Eccezione che rappresenta l'impossibilità di muoversi verso una certa posizione.
 * 
 * @author Gabriele Giunchi
 */
public class IllegalPositionException extends Exception {
	
	private static final long serialVersionUID = 8056961116878659031L;
	
	private static final String MSG = "Cannot move to position ";
	private final IPosition2D position;
	
	/**
	 * @param pos : la posizione verso cui non ci si può muovere
	 */
	public IllegalPositionException(final IPosition2D pos) {
		this.position = pos;
	}
	
	@Override
	public String toString() {
		return MSG  + this.position;
	}

}
