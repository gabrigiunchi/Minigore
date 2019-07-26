package utilities;

import java.util.Random;
import model.environment.EnvironmentBounds;
import model.environment.Position2D;

/**
 * Static factory di {@link Position2D}. 
 * Implementa metodi per il calcolo di posizioni relative a bordi e/o dimensioni dell'ecosistema
 * @author Lorenzo Mazzesi
 *
 */
public final class PositionFactory {

	private PositionFactory() { }
	
	/**
	 * Calcola random una posizione che si troverà coincidente con uno dei bordi. 
	 * Questo metodo serve per creare casualmente le posizioni dei nemici che mano a mano compaiono nel terreno di gioco.
	 * Il fatto di creare le posizioni sui bordi da la sensazione che i nemici arrivino da fuori l'ecosistema
	 * e non vengano creati per magia sul terreno
	 * @param bounds : i bordi
	 * @return la posizione calcolata
	 */
	public static Position2D calculatePosition(final EnvironmentBounds bounds) {
		final Random r = new Random();
		final int n = r.nextInt(3);
		
		switch(n) {
			case 0: return new Position2D(r.nextInt(bounds.getXUpper() - 1) + 1, bounds.getYLower());
			case 1: return new Position2D(bounds.getXLower(), r.nextInt(bounds.getYUpper() - 1) + 1);
			case 2: return new Position2D(bounds.getXUpper(), r.nextInt(bounds.getYUpper() - 1) + 1);
			case 3: return new Position2D(r.nextInt(bounds.getXUpper() - 1) + 1, bounds.getYUpper());
			default: return new Position2D(1, 1);
		}
	}
	
	
	/**
	 * Calcola la posizione centrale dell'ecosistema in base ai suoi limiti.
	 * @param bounds : i bordi dell'ecosistema
	 * @return la posizione calcolata
	 */
	public static Position2D centerOfBounds(final EnvironmentBounds bounds) {
		final int xLower = bounds.getXLower();
		final int xUpper = bounds.getXUpper();
		final int yLower = bounds.getYLower();
		final int yUpper = bounds.getYUpper();
		
		return new Position2D((xLower + xUpper) / 2, (yLower + yUpper) / 2);
	}
}
