package model.environment;

/**
 * Bordi di un ecosistema su 2 dimensioni che fungono da limiti veri e propri oltre i quali non si può andare.
 * valori dei bordi inferiori devono essere necessariamente minori dei bordi superiori.
 * Questa classe è un rifacimento di quanto fatto a laboratorio, 
 * in particolare il metodo isWithinBounds(Position2D) ha lo stesso nome
 * Aderisce al pattern Builder
 *
 * @author Gabriele Giunchi
 */
public interface EnvironmentBounds {
	
	/**
	 * Determina se una certa posizione è entro i limiti.
	 * @param pos : la posizione da esaminare
	 * @return true se ci si può muovere verso quella posizione
	 */
	boolean isWithinBounds(final IPosition2D pos);
	
	/**
	 * Ritorna il limite inferiore sulla direzione x.
	 * @return il valore della x inferiore
	 */
	int getXLower();
	
	/**
	 * Ritorna il limite superiore sulla direzione x.
	 * @return il valore della x superiore
	 */
	int getXUpper();
	
	/**
	 * Ritorna il limite inferiore sulla direzione y.
	 * @return il valore della y inferiore
	 */
	int getYLower();
	
	/**
	 * Ritorna il limite superiore sulla direzione x.
	 * @return il valore della x superiore
	 */
	int getYUpper();

}
