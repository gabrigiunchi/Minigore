package model.game;

import java.util.Optional;

import model.entities.ShotsOverException;
import model.environment.Direction;
import model.environment.Environment;

/**
 * Interfaccia del gioco. Aderisce al pattern strategy sulla modalità di gioco (le regole, le dinamiche ecc..).
 * 
 * @author Gabriele Giunchi
 */
public interface Game {
	
	/**
	 * Iniza una nuova partita.
	 * 
	 * @throws IllegalStateException se l'ecosistema o la strategia non sono definiti
	 */
	void startGame();
	
	/**
	 * Mette in pausa il gioco, in questo stato il gioco non prosegue finchè non viene ripreso attraverso il metodo {@link resumeGame()}.
	 */
	void pause();
	
	/**
	 * Riprende il gioco che era stato precedentemente messo in pausa.
	 */
	void resumeGame();
	
	/**
	 * Termina il gioco, qualunque sia il suo stato.
	 */
	void terminate();
	
	/**
	 * Usa l'arma del personaggio principale indicandoli di sparare nella direzione data.
	 * 
	 * @param dir : la direzione verso cui deve sparare
	 * @throws ShotsOverException se l'arma ha esaurito i colpi
	 */
	void useHeroWeapon(final Direction dir) throws ShotsOverException;
	
	/**
	 * Muove il personaggio principale verso la destinazione data.
	 * 
	 * @param dir : la direzione verso cui deve muoversi
	 */
	void moveHero(final Direction dir);
	
	/**
	 * Ricarica l'arma del personaggio principale.
	 */
	void rechargeHeroWeapon();
	
	/**
	 * Incrementa o decrementa il punteggio del gioco. se n > punteggio non ha alcun effetto.
	 * @param n : il valore da aggiungere
	 */
	void incrementScore(final int n);

	/**
	 * Setta l'environment di gioco.
	 * 
	 * @param gameEnvironment : l'ecosistema di gioco dove le creatura interagiscono tra loro
	 */
	void setEnvironment(final Environment gameEnvironment);
	
	/**
	 * Setta il tipo di modalità a cui si vuole giocare.
	 * 
	 * @param gameStrategy : la modalità di gioco
	 */
	void setStrategy(final MyGameStrategies gameStrategy);
	
	/**
	 * @return il punteggio attuale del gioco
	 */
	int getScore();
	
	/**
	 * Ritorna il tempo di di gioco, da quanto tempo l'utente sta giocando. 
	 * Corrisponde al tempo effettivo di gioco, 
	 * se il gioco viene messo in pausa il tempo si ferma
	 * @return gameTime
	 */
	long getMillisecond();
	
	/**
	 * @return true se il gioco non è concluso
	 */
	boolean isInGame();
	
	/**
	 * @return true se il gioco è in stato pausa
	 */
	boolean isInPause();

	/**
	 * @return l'ecosistema su cui il gioco si svolge
	 */
	Optional<Environment> getEnvironment();
	
	/**
	 * @return la modalità di gioco
	 */
	Optional<MyGameStrategies> getStrategy();
	
	
	/**
	 * Interfaccia di una modalità di gioco. 
	 * Per modalità si intende la descrizione di quello che accade durante il gioco: 
	 * la modalità di comparsa dei nemici, le vite possedute dal protagonista ecc...
	 * Non è quindi il gioco stesso, il metodo play(Game) rappresenta uno "step" del gioco, ciò che succede nel quanto di tempo
	 * 
	 * @author Lorenzo Mazzesi
	 */
	interface GameStrategy {

		/**
		 * @param game il gioco su cui effettuare le operazioni
		 */
		void play(final Game game);
		
		/**
		 * @param game : il gioco da inizializzare
		 */
		void initGame(final Game game);
	}
}
