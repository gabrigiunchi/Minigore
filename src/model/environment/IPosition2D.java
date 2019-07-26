package model.environment;

/**
 * Interfaccia di una posizione di un oggetto in 2 dimensioni.
 * 
 * @author Danilo Pianini
 *
 */
public interface IPosition2D {
	
	/**
	 * Restituisce la coordinata x della posizione.
	 * @return la coordinata x
	 */
	int getX();
	
	/**
	 * Restituisce la coordinata y della posizione.
	 * @return la coordinata y
	 */
	int getY();
	
	/**
	 * Somma le coordinate x e y con i valori passati come parametri e restituisce la nuova posizione calcolata.
	 * @param x : il valore da aggiungere alla coordinata x
	 * @param y : il valore da aggiungere alla coordinata y
	 * @return : la posizione calcolata
	 */
	IPosition2D sumVect(final int x, final int y);
}
