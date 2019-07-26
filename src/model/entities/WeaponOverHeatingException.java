package model.entities;

/**
 * Eccezione lanciata quando si prova ad usare l'arma troppo frequentemente:
 * ogni arma ha un tempo di carica durante il quale non può essere usata di nuovo.
 * Il tempo dipende dal tipo di arma.
 * 
 * @author Gabriele Giunchi
 */
public class WeaponOverHeatingException extends Exception {

	private static final long serialVersionUID = -9100920040152992936L;
	
	private static final String MSG = "Cannot use weapon too much frequently";
	
	@Override
	public String toString() {
		return MSG;
	}
}
