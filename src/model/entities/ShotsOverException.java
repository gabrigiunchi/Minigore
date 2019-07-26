package model.entities;

/**
 * Eccezione lanciata quando un'arma finisce i colpi.
 * 
 * @author Gabriele Giunchi
 */
public class ShotsOverException extends Exception {

	private static final long serialVersionUID = -4478944826977000193L;
	
	private static final String MSG = "Shots finished, need to recharge";
	
	@Override
	public String toString() {
		return MSG;
	}
}
