package controller;

import javax.swing.SwingUtilities;

import model.entities.ShotsOverException;
import model.environment.Direction;
import model.game.Game;
import view.ViewInterface;
/**
 * Implementazione del controller di gioco.
 * @author Lorenzo Mazzesi
 *
 */
public class GameController extends Thread implements GameViewObserver {
	
	private Game game;
	private ViewInterface view;
	private GameControllerAgent agent;
	/**
	 * Costruttore del controller di gioco.
	 * @param game : modello del gioco
	 * @param view : view del gioco
	 */
	public GameController(final Game game, final ViewInterface view) {
		this.view = view;
		this.game = game;
	}
	
	/**
	 * Collega il modello dell'aplicazione.
	 * @param game : modello dell'applicazione
	 */
	public void setModel(final Game game) {
		this.game = game;
	}
	/**
	 * Collega la view dell'applicazione.
	 * @param view : view dell'applicazione
	 */
	public void setView(final ViewInterface view) {
		this.view = view;
	}

	@Override
	public void moveHero(final Direction dir) {
		if (!this.game.isInPause()) {
			this.game.moveHero(dir);
		}
	}

	@Override
	public void shoot(final Direction dir) {
		if (!this.game.isInPause()) {
			try {
				this.game.useHeroWeapon(dir);
			} catch (ShotsOverException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void recharge() {
		if (!this.game.isInPause()) {
			this.game.rechargeHeroWeapon();
		}
	}

	@Override
	public void pauseButton() {
		this.agent.terminate();
		this.game.pause();
		this.view.showPause();
	}

	@Override
	public void startGame() {
		this.agent = new GameControllerAgent();
		
		try {
			this.game.startGame();
			this.view.showGame();
			this.view.drawModel(this.game);
			this.agent.start();
		} catch (IllegalStateException e) {
			System.out.println(e);
			this.view.showError(e.toString(), "Not initialized");
		}
	}

	@Override
	public void resumeGame() {
		this.view.showGame();
		this.agent = new GameControllerAgent();
		this.game.resumeGame();
		this.agent.start();
	}

	@Override
	public void menuButton() {
		this.agent.terminate();
		this.game.terminate();
		MyMusicPlayer.getInstance().stop();
		this.view.showMenu();
	}
	
	private class GameControllerAgent extends Thread {
		private volatile boolean stop;
		
		@Override
		public void run() {
			while (game.isInGame() && !this.stop) {
				if (!game.isInPause()) {
					try {
						SwingUtilities.invokeAndWait(() -> view.drawModel(game));
					} catch (Exception e) {
						System.out.println(e);
					}
				}
				
				try {
					sleep(10);
				} catch (InterruptedException e) {
					System.out.println(e);
				}
			}
			
			if (!this.stop) {
				Controller.getInstance().gameOver(game.getScore());
				view.showgameOver(game.getScore());
			}
		}

		private void terminate() {
			this.stop = true;
		}
	}
}
