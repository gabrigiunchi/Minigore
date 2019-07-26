package controller;

import static model.environment.Direction.DOWN;
import static model.environment.Direction.LEFT;
import static model.environment.Direction.RIGHT;
import static model.environment.Direction.UP;
import static java.awt.event.KeyEvent.VK_W;
import static java.awt.event.KeyEvent.VK_S;
import static java.awt.event.KeyEvent.VK_D;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.awt.event.KeyEvent.VK_SPACE;
import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_UP;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.environment.Direction;

/**
 *
 *	KeyListener del gioco, permette di controllare l'eroe con la tastiera.
 *  @author Lorenzo Mazzesi
 */
public class GameKeyListener implements KeyListener, ActionListener {
	
	private static final int MOVE_LEFT = VK_A;
	private static final int MOVE_RIGHT = VK_D;
	private static final int MOVE_UP = VK_W;
	private static final int MOVE_DOWN = VK_S;
	private static final int SHOOT_RIGHT = VK_RIGHT;
	private static final int SHOOT_LEFT = VK_LEFT;
	private static final int SHOOT_UP = VK_UP;
	private static final int SHOOT_DOWN = VK_DOWN;
	private static final int PAUSE_BUTTON = VK_ESCAPE;
	private static final int RECHARGE_BUTTON = VK_SPACE;
	
	private boolean stopMoving;
	private boolean stopShoot;
	private Direction shootingDirection;
	private Direction direction;
	private int lastShotKey;
	private int lastPressed;
	
	private final GameViewObserver gameController;
	
	/**
	 * Viene dato un riferimento al controller del gioco.
	 * @param controller : riferimento del controller
	 */
	public GameKeyListener(final GameViewObserver controller) {
		this.gameController = controller;
		this.stopMoving = true;
		this.stopShoot = true;
	}

	@Override
	public void keyTyped(final KeyEvent e) {
		this.funz(e);
	}
	
	
	/**
	 * Quando viene rilasciato un tasto si controlla se è quello che effettivamente sta controllando il personaggio:
	 * nel caso in cui venga premuto un tasto X e poi si prema un tasto Y che abbia la medesima funzione il controllo passa al tasto Y.
	 * Quando viene rilasciato il tasto X non ci devono essere effetti sul personaggio, 
	 * invece rilasciando il tasto Y il personaggio deve smettere di muoversi/sparare.
	 */
	@Override
	public void keyReleased(final KeyEvent e) {
		final int key = e.getKeyCode();
		if ((key == MOVE_LEFT || key == MOVE_RIGHT || key == MOVE_UP || key == MOVE_DOWN) && key == lastPressed) {
			stopMoving = true;
		} else if ((key == SHOOT_DOWN || key == SHOOT_UP || key == SHOOT_LEFT || key == SHOOT_RIGHT) && key == lastShotKey) {
			stopShoot = true;
		}
	}
	
	/**
	 * Quando viene premuto un tasto il controllo passa a quel tasto, 
	 * questo vuol dire che finchè non viene rilasciato o non viene premuto un altro tasto che svolge la medesima funzione 
	 * il personaggio continua a svolgere l'azione causata dal tasto.
	 */
	@Override
	public void keyPressed(final KeyEvent e) {
		final int key = e.getKeyCode();
		if (key == MOVE_LEFT || key == MOVE_RIGHT || key == MOVE_UP || key == MOVE_DOWN) {
			stopMoving = false;
			lastPressed = key;
		} else if (key == SHOOT_DOWN || key == SHOOT_LEFT || key == SHOOT_RIGHT || key == SHOOT_UP) {
			stopShoot = false;
			lastShotKey = key;
		}
		
		this.funz(e);
	}
	
	private synchronized void funz(final KeyEvent e) {
		final int key = e.getKeyCode();
		
		switch(key) {
			case MOVE_LEFT: direction = LEFT; break;
			case MOVE_RIGHT: direction = RIGHT; break;
			case MOVE_UP: direction = UP; break;
			case MOVE_DOWN: direction = DOWN; break;
			case SHOOT_DOWN: shootingDirection = DOWN; break;
			case SHOOT_LEFT: shootingDirection = LEFT; break;
			case SHOOT_RIGHT: shootingDirection = RIGHT; break;
			case SHOOT_UP: shootingDirection = UP; break;
			case PAUSE_BUTTON: gameController.pauseButton(); break;
			case RECHARGE_BUTTON: gameController.recharge(); break;
			default: break;
		}
	}
	
	/**
	 * Funzione che permette di rendere i movimenti molto più fluidi. 
	 */
	public synchronized void funz() {
		if (!this.stopMoving) {
			this.gameController.moveHero(this.direction);
		}
		if (!this.stopShoot) {
			this.gameController.shoot(this.shootingDirection);
		}
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		this.funz();
	}
	
}